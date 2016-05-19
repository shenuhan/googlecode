package fr.game.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import fr.game.fight.Army;
import fr.game.fight.Range;

public class Parser {
	private File config;

	public Parser(String path) {
		config = new File(path);
		if (!config.exists()) {
			throw new RuntimeException("Cannot find config file");
		}
	}

	static public class Tree {
		public Tree parent;
		public String name;
		public String value;
		public List<Tree> children;
		public Object object;

		@Override
		public String toString() {
			return (name == null ? "" : name) + "/" + value;
		}
	}

	public List<Army> parse() throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(config)));

		Tree res = new Tree();

		Tree tree = res;
		tree.parent = null;
		String line = reader.readLine();
		int currentDepth = 0;
		while (line != null) {
			int depth = 0;
			while (line.startsWith("\t")) {
				line = line.substring(1);
				depth++;
			}
			if (currentDepth == depth) {
				tree = add(tree, line);
				currentDepth = depth + 1;
			} else {
				while (depth < currentDepth) {
					currentDepth--;
					tree = tree.parent;
				}
				tree = add(tree, line);
				currentDepth = depth + 1;
			}
			line = reader.readLine();
		}

		List<Army> armies = new ArrayList<Army>();
		for (Tree child : res.children) {
			armies.add((Army) child.object);
		}

		return armies;
	}

	@SuppressWarnings("unchecked")
	private Tree add(Tree tree, String line) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, ClassNotFoundException {
		if (tree.children == null) {
			tree.children = new ArrayList<Parser.Tree>();
		}
		Tree child = new Tree();
		child.parent = tree;
		int pos = line.indexOf(" ");
		if (pos < 0) {
			child.name = line;
			child.value = "";
		} else {
			child.name = line.substring(0, pos).trim();
			child.value = line.substring(pos).trim();
		}

		if (child.name.equals("Army")) {
			Army army = new Army();
			child.object = army;
			if (child.value != null)
				army.setName(child.value);

		} else if (child.value.equals("{}")) {
			child.object = new ArrayList<Object>();
			for (Method setter : tree.object.getClass().getDeclaredMethods()) {
				if (setter.getName().equalsIgnoreCase("set" + child.name)
						&& setter.getParameterTypes().length == 1) {
					setter.invoke(tree.object, child.object);
					break;
				}
			}
		} else if (tree.value.equals("{}")) {
			Class<?> objectType = ClassLoader.getSystemClassLoader().loadClass(
					"fr.game.fight." + child.name);
			child.object = objectType.newInstance();
			((List<Object>) tree.object).add(child.object);
			if (child.value != null) {
				try {
					Method nameSetter;
					nameSetter = objectType.getDeclaredMethod("setName",
							String.class);
					nameSetter.invoke(child.object, child.value);
				} catch (SecurityException e) {
				} catch (NoSuchMethodException e) {
				}
			}
		} else {
			boolean found = false;
			for (Method setter : tree.object.getClass().getDeclaredMethods()) {
				if (setter.getName().equalsIgnoreCase("set" + child.name)
						&& setter.getParameterTypes().length == 1) {
					found = true;
					Class<?> type = setter.getParameterTypes()[0];
					if (type == Range.class) {
						String[] s = child.value.split("\t");
						child.object = new Range(Integer.parseInt(s[0]),
								Integer.parseInt(s[s.length - 1]));
					} else if (type == Integer.class
							|| type.getName().equals("int"))
						child.object = Integer.parseInt(child.value);
					else if (type == Long.class
							|| type.getName().equals("long"))
						child.object = Long.parseLong(child.value);
					else if (type == Float.class
							|| type.getName().equals("float"))
						child.object = Float.parseFloat(child.value);
					else if (type == Double.class
							|| type.getName().equals("double"))
						child.object = Double.parseDouble(child.value);
					else if (type.isEnum()) {
						for (Object t : type.getEnumConstants()) {
							if (((Enum<?>) t).name().equals(child.value)) {
								child.object = t;
								break;
							}
						}
						if (child.object == null)
							throw new RuntimeException("Cannot find enum " + child.value);
					} else {
						child.object = type.newInstance();
						try {
							Method nameSetter;
							nameSetter = type.getDeclaredMethod("setName",
									String.class);
							nameSetter.invoke(child.object, child.value);
						} catch (SecurityException e) {
						} catch (NoSuchMethodException e) {
						}
					}
					if (child.object == null) {
						throw new RuntimeException("Cannot parse value "
								+ child.value);
					}
					setter.invoke(tree.object, child.object);
					break;
				}
			}
			if (!found)
				throw new RuntimeException("Cannot find setter for "
						+ child.name);
		}

		tree.children.add(child);
		return child;
	}
}
