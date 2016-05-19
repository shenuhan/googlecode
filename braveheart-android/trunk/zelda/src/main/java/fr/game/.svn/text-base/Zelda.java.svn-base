package fr.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

//code un peu crado mais qui marche base sur libgdx qui marche sur pc et android et applet web :)
// lire : http://code.google.com/p/libgdx/wiki/GraphicsFundamentalsViewport
// add vertice et add cube c est de la description de surface 3d, ce serait mieux d'utiliser des object blender
// howto blender pour libgdx : http://code.google.com/p/libgdx/wiki/BlenderModels
public class Zelda implements ApplicationListener {
	final static String MAP = "/map.txt";
	final static String HEIGHT = "/height.txt";
    private Map<Integer,Mesh> squareMesh;
    private Camera camera;

    private static class Tile {
    	public final int id;

    	public float height;

    	public Tile(int id) {
			this.id = id;
			this.height = 0;
		}
    }

    private Tile[][] maps;
    private short width,height;
    final private Map<Integer,Texture> textures;
    final Map<Integer,List<Float>> vertices;
    final Map<Integer,List<Short>> indices;

    final private int camerawidth, cameraheight;

    public Zelda(int camerawidth, int cameraheight) {
        textures = new HashMap<Integer, Texture>();
        vertices = new HashMap<Integer, List<Float>>();
        indices = new HashMap<Integer, List<Short>>();
        squareMesh = new HashMap<Integer, Mesh>();

        this.camerawidth = camerawidth;
        this.cameraheight = cameraheight;
    }

    private enum Face {
    	Up, Left, Right, Front, Back
    }

    private void addVertices(Face f, short i, short j) {
    	List<Float> vertices = this.vertices.get(maps[i][j].id);
    	List<Short> indices = this.indices.get(maps[i][j].id);
    	if (vertices == null) {
    		vertices = new ArrayList<Float>();
    		this.vertices.put(maps[i][j].id,vertices);
    		indices = new ArrayList<Short>();
    		this.indices.put(maps[i][j].id,indices);
    	}

    	float tileHeight = maps[i][j].height;
    	short index = (short)(vertices.size()/6);
    	switch (f) {
		case Up:
	    	vertices.add((float)i);
	    	vertices.add((float)j);
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(1f);
	    	vertices.add((float)(i+1));
	    	vertices.add((float)j);
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(1f);
	    	vertices.add((float)(i+1));
	    	vertices.add((float)(j+1));
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(0f);
	    	vertices.add((float)i);
	    	vertices.add((float)(j+1));
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(0f);
			break;
		case Right:
	    	vertices.add((float)i);
	    	vertices.add((float)j);
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(0f);
	    	vertices.add((float)i);
	    	vertices.add((float)j + 1);
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(0f);
	    	vertices.add((float)i);
	    	vertices.add((float)j + 1);
	    	vertices.add(i == 0 ? tileHeight - 1 : maps[i-1][j].height);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(1f);
	    	vertices.add((float)i);
	    	vertices.add((float)j);
	    	vertices.add(i == 0 ? tileHeight - 1 : maps[i-1][j].height);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(1f);
			break;
		case Left:
	    	vertices.add((float)(i+1));
	    	vertices.add((float)(j+1));
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(0f);
	    	vertices.add((float)(i+1));
	    	vertices.add((float)j);
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(0f);
	    	vertices.add((float)(i+1));
	    	vertices.add((float)j);
	    	vertices.add(i == width - 1 ? tileHeight - 1 : maps[i+1][j].height);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(1f);
	    	vertices.add((float)(i+1));
	    	vertices.add((float)(j+1));
	    	vertices.add(i == width - 1 ? tileHeight - 1 : maps[i+1][j].height);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(1f);
			break;
		case Front:
	    	vertices.add((float)i);
	    	vertices.add((float)(j+1));
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(0f);
	    	vertices.add((float)(i+1));
	    	vertices.add((float)(j+1));
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(0f);
	    	vertices.add((float)(i+1));
	    	vertices.add((float)(j+1));
	    	vertices.add(j == height - 1 ? tileHeight - 1 : maps[i][j+1].height);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(1f);
	    	vertices.add((float)i);
	    	vertices.add((float)(j+1));
	    	vertices.add(j == height - 1 ? tileHeight - 1 : maps[i][j+1].height);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(1f);
			break;
		case Back:
	    	vertices.add((float)(i+1));
	    	vertices.add((float)j);
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(0f);
	    	vertices.add((float)i);
	    	vertices.add((float)j);
	    	vertices.add(tileHeight);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(0f);
	    	vertices.add((float)i);
	    	vertices.add((float)j);
	    	vertices.add(j == 0 ? tileHeight - 1 : maps[i][j-1].height);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(0f);
	    	vertices.add(1f);
	    	vertices.add((float)(i+1));
	    	vertices.add((float)j);
	    	vertices.add(j == 0 ? tileHeight - 1 : maps[i][j-1].height);
	    	vertices.add(Color.toFloatBits(255, 255, 255, 255));
	    	vertices.add(1f);
	    	vertices.add(1f);
			break;

		default:
			break;
		}
    	indices.add(index);
    	indices.add((short)(index + 1));
    	indices.add((short)(index + 2));
    	indices.add((short)(index + 2));
    	indices.add((short)(index + 3));
    	indices.add(index);
    }

    private void addCube(short i, short j) {
    	if (j == 0 || maps[i][j-1].height < maps[i][j].height) {
        	addVertices(Face.Back, i, j);
    	}

    	if (i == width - 1 || maps[i + 1][j].height < maps[i][j].height) {
        	addVertices(Face.Left, i, j);
    	}

    	if (j == height - 1 || maps[i][j+1].height < maps[i][j].height) {
        	addVertices(Face.Front, i, j);
    	}

    	if (i == 0 || maps[i-1][j].height < maps[i][j].height) {
        	addVertices(Face.Right, i, j);
    	}

    	addVertices(Face.Up, i, j);
    }

    @Override
    public void create() {
    	try {
        	width = 0; height = 0;
			BufferedReader reader  = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(Zelda.MAP)));
			String line;
			while((line = reader.readLine()) != null) {
				String[] ids = line.split(" ");
				if (ids.length > 0) {
					width = (short) ids.length;
					height++;
				}
			}

			reader.close();

			maps = new Tile[width][height];
			reader  = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(Zelda.MAP)));
			int j=0;
			while((line = reader.readLine()) != null) {
				String[] ids = line.split(" ");
				for (int i=0; i < ids.length; i++) {
					int id = Integer.parseInt(ids[i]);
					maps[i][height - j - 1] = new Tile(id);
					if (!textures.containsKey(id)) {
						textures.put(id,new Texture(Gdx.files.classpath("imgs/"+id+".png")));
					}
				}
				j++;
			}
			reader.close();
			reader  = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(Zelda.HEIGHT)));
			j=0;
			while((line = reader.readLine()) != null) {
				String[] heights = line.split(" ");
				for (int i=0; i < heights.length; i++) {
					float height = Float.parseFloat(heights[i]);
					maps[i][this.height - j - 1].height = height;
				}
				j++;
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

        if (squareMesh.isEmpty()) {
            for (short j=0;j<height;j++) {
                for (short i=0;i<width;i++) {
                	addCube(i, j);
                }
        	}

            for (int id : this.vertices.keySet()) {
	            float[] vertices = new float[this.vertices.get(id).size()];
	            short[] indices = new short[this.indices.get(id).size()];

	            int i=0;
	            for (Float f : this.vertices.get(id)) vertices[i++] = f;
	            i=0;
	            for (Short s : this.indices.get(id)) indices[i++] = s;

	        	Mesh squareMesh = new Mesh(true, vertices.length/6,indices.length,
	        			new VertexAttribute(Usage.Position, 3, "a_position"),
	        			new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
	        			new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));

	        	squareMesh.setVertices(vertices);
	        	squareMesh.setIndices(indices);
	        	this.squareMesh.put(id, squareMesh);
            }
        }
        Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyUp(int keycode) {
				if (keycode == Input.Keys.DOWN)
					keyDown = false;
				else if (keycode == Input.Keys.LEFT)
					keyLeft = false;
				else if (keycode == Input.Keys.RIGHT)
					keyRight = false;
				else if (keycode == Input.Keys.UP)
					keyUp = false;
				return false;
			}

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.DOWN)
					keyDown = true;
				else if (keycode == Input.Keys.LEFT)
					keyLeft = true;
				else if (keycode == Input.Keys.RIGHT)
					keyRight = true;
				else if (keycode == Input.Keys.UP)
					keyUp = true;
				else if (keycode == Input.Keys.SPACE) {
			        camera.position.set(120, height - 83, maps[120][height - 83].height + 1.01f);
			        camera.direction.set(0,0,-1f);
			        camera.up.set(0,1,0);
			        camera.rotate(90, 1f, 0f, 0f);
				}
				return false;
			}
		});
    }

    boolean keyUp = false;
    boolean keyDown = false;
    boolean keyLeft = false;
    boolean keyRight = false;

    @Override
    public void dispose() { }

    @Override
    public void pause() { }

    // tile per second
    float speed = 5f;
    float anglespeed = 180f;
    float walldistance = 0.1f;
    @Override
    public void render() {
    	camera.update();
        camera.apply(Gdx.gl10);
        Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
        for (Entry<Integer, Mesh> entry : squareMesh.entrySet()) {
	        textures.get(entry.getKey()).bind();
	        entry.getValue().render(GL10.GL_TRIANGLES);
        }
        long time = System.nanoTime();
        float delta = (float) (time - this.time) / 1000000000;
        this.time = time;

        float x = camera.direction.x;
        float y = camera.direction.y;

        float xp = camera.position.x;
        float yp = camera.position.y;
        camera.translate(speed * delta * x * (keyUp ? 1 : keyDown ? -1 : 0f), speed * delta * y * (keyUp ? 1 : keyDown ? -1 : 0f), 0f);
        camera.rotate(anglespeed * delta * (keyLeft ? 1 : keyRight ? -1 : 0f), 0, 0, 1);

        float xpnew = camera.position.x;
        float ypnew = camera.position.y;

        float xwall = xpnew > xp ? walldistance : -walldistance ;
        float ywall = ypnew > yp ? walldistance : -walldistance ;

        if (xpnew >= 0 && xpnew < this.width && ypnew >= 0 && ypnew < this.height) {

        	if (!canWalk((int)xpnew,(int)(ypnew+ywall))) {
        		ypnew = yp;
    		}
        	if(!canWalk((int)(xpnew+xwall),(int)yp)) {
        		xpnew = xp;
    		}
        	if(!canWalk((int)(xpnew+xwall),(int)(ypnew+ywall))) {
        		ypnew = yp;
        		xpnew = xp;
        	}
        	camera.position.set(xpnew,ypnew,maps[(int)xpnew][(int)ypnew].height + 1.01f);
        }
    }

    private boolean canWalk(int i, int j) {
    	int id = maps[i][j].id;
    	return (Constants.flatsIds.contains(id) || Constants.stairsIds.contains(id)) && !Constants.waterIds.contains(id);
	}

	long time;
    @Override
    public void resize(int width, int height) {
        camera = new PerspectiveCamera(60, camerawidth, cameraheight);
        camera.near = 0.01f;
        camera.translate(this.width/2, -10f, 20f);
        camera.lookAt(this.width/2,this.height, 2f);
        time = System.nanoTime();
    }

    @Override
    public void resume() { }

}