package fr.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import fr.game.battlefield.Battlefield;
import fr.game.battlefield.Tile;

public class Copy_2_of_Battle implements ApplicationListener {
	private Mesh mesh;
	private Mesh cube;
	private ShaderProgram shadowGenShader;
	private ShaderProgram shadowMapShader;
	private PerspectiveCamera cam;
	private PerspectiveCamera lightCam;
	private FrameBuffer shadowMap;
	
	private SpriteBatch spriteBatch;
	private Texture texture;
	
	private final Battlefield battlefield;
	
	public Copy_2_of_Battle(Battlefield battlefield) {
		this.battlefield = battlefield;
	}

	private int getIndice(int i, int j) {
		return battlefield.getWidth() * j + i;
	}
	
	private float getX(int i, int j) {
		float widthUnit = 2.0f / ((float) battlefield.getWidth() + 0.5f); 
		return ((float)i + (j%2==0?0.5f:1f)) * widthUnit - 1;
	}
	
	private float getY(int i, int j) {
		float heightUnit = 2.0f / ((float) battlefield.getHeight());
		return 1 - ((float)j+0.5f) * heightUnit;
	}
	
	private void setupScene () {
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0, -1f, 1);
		cam.lookAt(0, 0.5f, 0);
		cam.update();
	}

	private void setupShadowMap () {
		shadowMap = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		lightCam = new PerspectiveCamera(67, shadowMap.getWidth(), shadowMap.getHeight());
		lightCam.position.set(4, -4, 3);
		lightCam.lookAt(0, 2, 0);
		lightCam.update();

		shadowGenShader = new ShaderProgram(Gdx.files.internal("data/shaders/shadowgen-vert.glsl").readString(), Gdx.files
			.internal("data/shaders/shadowgen-frag.glsl").readString());
		if (!shadowGenShader.isCompiled())
			throw new GdxRuntimeException("Couldn't compile shadow gen shader: " + shadowGenShader.getLog());

		shadowMapShader = new ShaderProgram(Gdx.files.internal("data/shaders/shadowmap-vert.glsl").readString(), Gdx.files
			.internal("data/shaders/shadowmap-frag.glsl").readString());
		if (!shadowMapShader.isCompiled())
			throw new GdxRuntimeException("Couldn't compile shadow map shader: " + shadowMapShader.getLog());
	}
	
	@Override
	public void create() {
		mesh = new Mesh(true, battlefield.getWidth() * battlefield.getHeight(), 6 * (battlefield.getWidth()-1) * (battlefield.getHeight()-1), new VertexAttribute(Usage.Position, 3,ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(Usage.ColorPacked, 4,"a_color"), new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		setupScene();
		setupShadowMap();
		createCube();
		
		float[] ver = new float[6 * battlefield.getWidth() * battlefield.getHeight()];
		short[] ind = new short[6 * (battlefield.getWidth()-1) * (battlefield.getHeight()-1)];
		
		Random r = new Random();
		
		for(int j =0;j < battlefield.getHeight(); j++) {
			for(int i =0;i < battlefield.getWidth(); i++) {
				ver[6*getIndice(i, j)] = getX(i, j);
				ver[6*getIndice(i, j) + 1] = getY(i, j); 
				ver[6*getIndice(i, j) + 2] = 0.1f+battlefield.getTiles()[i][j].getK() * 0.05f;
				ver[6*getIndice(i, j) + 3] = Color.toFloatBits(0, r.nextFloat(), 0, 0.5f);
				ver[6*getIndice(i, j) + 4] = getX(i, j) / 2 + 1;
				ver[6*getIndice(i, j) + 5] = getY(i, j) / 2 + 1;
			}
		}
		
		
		int index = 0;
		for(int j =0;j < battlefield.getHeight() - 1; j++) {
			for(int i =0;i < battlefield.getWidth() - 1; i++) {
				if (j%2 == 0) {
					ind[index++] = (short) getIndice(i, j);
					ind[index++] = (short) getIndice(i + 1, j);
					ind[index++] = (short) getIndice(i, j + 1);
					ind[index++] = (short) getIndice(i + 1, j);
					ind[index++] = (short) getIndice(i + 1, j + 1);
					ind[index++] = (short) getIndice(i, j + 1);;
				}
				if (j%2 == 1) {
					ind[index++] = (short) getIndice(i, j);
					ind[index++] = (short) getIndice(i + 1, j + 1);
					ind[index++] = (short) getIndice(i, j + 1);
					ind[index++] = (short) getIndice(i, j);
					ind[index++] = (short) getIndice(i + 1, j);
					ind[index++] = (short) getIndice(i + 1, j + 1);;
				}
			}
		}
		
		mesh.setVertices(ver);
		mesh.setIndices(ind);
		
		spriteBatch = new SpriteBatch();
		texture = new Texture(new FileTextureData(Gdx.files.internal("data/t8890.png"), null, null, false));
	}
	
	private void createCube() {
		float r = 1f;
		
		cube = new Mesh(true, 8, 24, new VertexAttribute(Usage.Position, 3,ShaderProgram.POSITION_ATTRIBUTE));
		cube.setVertices(new float[] {-1*r,-1*r,0,   -1*r,r,0,   r,r,0,   r,-1*r,0,
									  -1*r,-1*r,2*r, -1*r,r,2*r, r,r,2*r, r,-1*r,2*r});
		cube.setIndices(new short[] {0,1,2,0,2,3,1,5,6,1,6,2,0,4,5,0,5,1,6,7,3,6,3,2});
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
//		texture.bind();

//		shadowMap.begin();
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//		Gdx.gl.glEnable(GL20.GL_CULL_FACE);
//		Gdx.gl.glCullFace(GL20.GL_FRONT);
//		shadowGenShader.begin();
//		shadowGenShader.setUniformMatrix("u_projTrans", lightCam.combined);
//		mesh.render(shadowGenShader, GL20.GL_TRIANGLES);
//		cube.render(shadowGenShader, GL20.GL_TRIANGLES);
//		shadowGenShader.end();
//		shadowMap.end();
//		Gdx.gl.glDisable(GL20.GL_CULL_FACE);

		shadowMapShader.begin();
		shadowMap.getColorBufferTexture().bind();
		shadowMapShader.setUniformi("s_shadowMap", 0);
		shadowMapShader.setUniformMatrix("u_projTrans", cam.combined);
		shadowMapShader.setUniformMatrix("u_lightProjTrans", lightCam.combined);
		texture.bind();
		mesh.render(shadowMapShader, GL20.GL_TRIANGLES);
//		shadowMapShader.setUniformf("u_color", 0.2f, 0.2f, 0.2f, 1);
//		cube.render(shadowMapShader, GL20.GL_TRIANGLES);
		shadowMapShader.end();
//		shadowGenShader.end();

		spriteBatch.begin();
		Tile[][] tiles = battlefield.getTiles();
		for (int i = 0 ; i < tiles.length ; i++)
			for (int j = 0; j < tiles[i].length; j++) {
				Tile tile = tiles[i][j];
				if (tile.getFighter() != null) {
					spriteBatch.draw(texture, getX(i, j), getY(i, j),10,10);
				}
			}
	
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}
}