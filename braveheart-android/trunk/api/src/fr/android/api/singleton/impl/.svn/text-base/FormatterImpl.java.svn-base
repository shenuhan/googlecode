package fr.android.api.singleton.impl;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.android.api.annotation.Singleton;
import fr.android.api.singleton.Accessor;
import fr.android.api.singleton.Factory;
import fr.android.api.singleton.Formatter;
import fr.android.api.util.Logger;


public class FormatterImpl implements Formatter {
	final Format decimalFormatter;
	final Format integerFormatter;

	static final char SEPARATOR = '|';
	static final String REPLACER = "&pipe;";

	static final char LHOOK = '{';
	static final char RHOOK = '}';
	static final String REPLACERLHOOK = "&lhook;";
	static final String REPLACERRHOOK = "&rhook;";

	@Singleton
	protected Factory factory;
	@Singleton
	protected Accessor accessor;

	private class Token {
		public String value;
		public List<Token> subToken;

		public Token(String value) {
			this.value = value.replace(REPLACER, ""+SEPARATOR).replace(REPLACERLHOOK, "" + LHOOK).replace(REPLACERRHOOK, "" + RHOOK);;
			subToken = new ArrayList<FormatterImpl.Token>();
			if (value.length() > 0 && value.charAt(0) == LHOOK) {
				value = value.substring(1,value.length()-1);
				while(value.length() != 0) {
					int pos;
					int currentHook = 0;
					for (pos = 0; pos < value.length() ;pos++) {
						if (value.charAt(pos) == LHOOK) {
							currentHook++;
						} else if (value.charAt(pos) == RHOOK) {
							currentHook--;
						} else if (value.charAt(pos) == SEPARATOR && currentHook == 0) {
							break;
						}
					}
					if (pos == value.length()) {
						subToken.add(new Token(value));
						value = "";
					} else {
						subToken.add(new Token(value.substring(0,pos)));
						value = value.substring(pos+1);
						if (value.length() == 0) {
							subToken.add(new Token(value));
						}
					}
				}
			}
		}
	}

	public FormatterImpl() {
		decimalFormatter = new DecimalFormat("0.0##", new DecimalFormatSymbols(Locale.US));
		integerFormatter = new DecimalFormat("0");
	}

	@Override
	public String format(Object o) {
		return format(o, new HashMap<Object, Integer>());
	}

	private String format(Object o, Map<Object, Integer> doneReferences) {
		if (o == null)
			return "";

		if (o instanceof Double || o instanceof Float) {
			return decimalFormatter.format(o);
		}

		if (o instanceof Integer || o instanceof Long || o instanceof Short) {
			return integerFormatter.format(o);
		}

		if (o instanceof String) {
			return ((String) o).replace(""+SEPARATOR, REPLACER).replace(""+RHOOK, REPLACERRHOOK).replace(""+LHOOK, REPLACERLHOOK);
		}

		if (o.getClass().isEnum()) {
			return ((Enum<?>)o).name();
		}

		if (o instanceof Object[]) {
			Object[] c = (Object[]) o;
			if (c.length == 0) {
				return ""+LHOOK+RHOOK;
			}
			StringBuffer b = new StringBuffer("" + LHOOK);

			Object element = c[0];
			b.append(getClassName(element));

			for (Object sub : c) {
				b.append(SEPARATOR).append(format(sub,doneReferences));
			}
			b.append(RHOOK);
			return b.toString();
		}

		if (o instanceof Map<?,?>) {
			Map<?,?> c = (Map<?,?>) o;
			if (c.isEmpty()) {
				return ""+LHOOK+RHOOK;
			}
			Entry<?,?> element = c.entrySet().iterator().next();

			StringBuffer b = new StringBuffer("" + LHOOK);
			b.append(LHOOK).append(getClassName(element.getKey())).append(SEPARATOR);
			b.append(getClassName(element.getValue())).append(RHOOK);

			for (Entry<?, ?> sub : c.entrySet()) {
				b.append(SEPARATOR).append(LHOOK).append(format(sub.getKey(),doneReferences)).append(SEPARATOR);
				b.append(format(element.getValue(),doneReferences)).append(RHOOK);
			}
			b.append(RHOOK);
			return b.toString();
		}

		if (o instanceof Collection<?>) {
			Collection<?> c = (Collection<?>) o;
			if (c.isEmpty()) {
				return ""+LHOOK+RHOOK;
			}
			Object element = c.iterator().next();

			StringBuffer b = new StringBuffer("" + LHOOK);
			b.append(getClassName(element));

			for (Object sub : c) {
				b.append(SEPARATOR).append(format(sub,doneReferences));
			}
			b.append(RHOOK);
			return b.toString();
		}

		if (factory.isManaged(o.getClass())) {
			StringBuffer b = new StringBuffer("" + LHOOK);
			Integer ref = doneReferences.get(o);
			if (ref == null) {
				ref = doneReferences.size();
			}
			b.append(ref);

			if (doneReferences.put(o,ref) == null) {
				for (String field : accessor.getFields(o.getClass())) {
					Object fieldObject = accessor.get(o, field);
					b.append(SEPARATOR).append(LHOOK).append(field).append(SEPARATOR).append(format(fieldObject,doneReferences)).append(RHOOK);
				}
			}
			b.append(RHOOK);
			return b.toString();
		}

		throw Logger.t("Formatter", "Cannot format : " + o.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	static final Set<Class<?>> collectionType = new HashSet<Class<?>>(Arrays.asList(String.class,Double.class,Float.class,Integer.class,Long.class, Short.class));

	private String getClassName(Object element) {
		if (collectionType.contains(element.getClass())) {
			return element.getClass().getCanonicalName();
		} else if (element instanceof Object[]) {
			return element.getClass().getCanonicalName();
		} else if (element instanceof Map<?,?>) {
			return Map.class.getCanonicalName();
		} else if (element instanceof Set<?>) {
			return Set.class.getCanonicalName();
		} else if (element instanceof Collection<?>) {
			return Collection.class.getCanonicalName();
		} else if (factory.isManaged(element.getClass())) {
			Class<?> i = factory.getManagedInterface(element.getClass());
			return i.getCanonicalName();
		} else {
			throw Logger.t("Formatter", "Cannot format this collection");
		}
	}

	@Override
	public <T> T parse(Class<T> clazz, String value) {
		return parse(clazz, new Token(value), new HashMap<Integer,Object>());
	}

	@SuppressWarnings("unchecked")
	public <T> T parse(Class<T> clazz, Token token, Map<Integer,Object> doneObjects) {
		if (clazz == Double.class || clazz == double.class) {
			return (T) new Double(token.value);
		}

		if (clazz == Float.class || clazz == float.class) {
			return (T) new Float(token.value);
		}

		if (clazz == Integer.class || clazz == int.class) {
			return (T) new Integer(token.value);
		}

		if (clazz == Short.class || clazz == short.class) {
			return (T) new Short(token.value);
		}

		if (clazz == Long.class || clazz == long.class) {
			return (T) new Long(token.value);
		}

		if (clazz == String.class) {
			return (T) token.value;
		}

		if (clazz.isEnum()) {
			for (T t : clazz.getEnumConstants()) {
				if (((Enum<?>)t).name().equals(token.value)) {
					return t;
				}
			}
			throw Logger.t("Formatter", "The enum " + token.value + " does not exist for type : " + clazz.getCanonicalName());
		}

		if (Object[].class.isAssignableFrom(clazz)) {
			return (T) parseArray(token,doneObjects);
		}

		if (Set.class.isAssignableFrom(clazz)) {
			return (T) parseSet(token,doneObjects);
		}

		if (Collection.class.isAssignableFrom(clazz)) {
			return (T) parseCollection(token,doneObjects);
		}

		if (Map.class.isAssignableFrom(clazz)) {
			return (T) parseMap(token,doneObjects);
		}

		if (factory.isManaged(clazz)) {
			int reference = getReference(token.subToken.get(0).value);
			T res = (T) doneObjects.get(reference);
			if (res == null) {
				res = factory.make(clazz);
				doneObjects.put(reference, res);
				for (int i=1 ; i < token.subToken.size() ; i++) {
					Token t = token.subToken.get(i);
					String field = t.subToken.get(0).value;
					Token value = t.subToken.get(1);
					accessor.set(res, field, parse(accessor.getFieldsClass(res.getClass(), field),value,doneObjects));
				}
			}
			return res;
		}

		throw Logger.t("Factory", "Cannot parse : " + token.value);
	}

	private Class<?> getClass(String className) {
		try {
			Class<?> clazz;
			if (className.endsWith("[]")) {
				int nb = 0;
				do {
					className = className.substring(0,className.length() - 2);
					nb++;
				} while(className.endsWith("[]"));
				int[] dim = new int[nb];
				for (int i=0 ; i < nb; i++) {
					dim[i] = 1;
				}
				clazz = Array.newInstance(Class.forName(className),dim).getClass();
			} else {
				clazz = Class.forName(className);
			}
			return clazz;
		} catch (ClassNotFoundException e) {
			throw Logger.t("Formatter", "Cannot find class",e);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> Collection<T> parseCollection(Token token, Map<Integer,Object> doneObjects) {
		if (token.subToken.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		@SuppressWarnings("rawtypes")
		Collection<T> res = new ArrayList();
		Class<T> collectionClass = null;
		for (Token t : token.subToken) {
			if (collectionClass == null) {
				collectionClass = (Class<T>) getClass(t.value);
			} else {
				res.add(parse(collectionClass,t,doneObjects));
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private <T> Set<T> parseSet(Token token, Map<Integer,Object> doneObjects) {
		if (token.subToken.isEmpty()) {
			return Collections.EMPTY_SET;
		}
		Set<T> res = new HashSet<T>();
		Class<T> collectionClass = null;
		for (Token t : token.subToken) {
			if (collectionClass == null) {
				collectionClass = (Class<T>) getClass(t.value);
			} else {
				res.add(parse(collectionClass,t,doneObjects));
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private <T,V> Map<T,V> parseMap(Token token, Map<Integer,Object> doneObjects) {
		if (token.subToken.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		Map<T,V> res = new HashMap<T,V>();
		Class<T> keyClass = null;
		Class<V> valueClass = null;
		for (Token t : token.subToken) {
			if (keyClass == null) {
				keyClass = (Class<T>) getClass(t.subToken.get(0).value);
				valueClass = (Class<V>) getClass(t.subToken.get(1).value);
			} else {
				res.put(parse(keyClass,t.subToken.get(0),doneObjects),parse(valueClass,t.subToken.get(1),doneObjects));
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private <T> T[] parseArray(Token t, Map<Integer,Object> doneObjects) {
		if (t.subToken.isEmpty()) {
			return null;
		}

		Class<T> arrayClass = (Class<T>) getClass(t.subToken.get(0).value);
		T[] res = (T[]) Array.newInstance(arrayClass, t.subToken.size() - 1);

		return parseCollection(t, doneObjects).toArray(res);
	}

	private int getReference(String value) {
		int startIndex = value.charAt(0) == LHOOK ? 1 : 0;
		int endIndex;
		for (endIndex = startIndex; endIndex < value.length(); endIndex++) {
			if (value.charAt(endIndex) == LHOOK || value.charAt(endIndex) == RHOOK) {
				break;
			}
		}
		return Integer.parseInt(value.substring(startIndex,endIndex));
	}

}
