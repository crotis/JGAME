package engineTester;


import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main (String[] args) {
		
		DisplayManager.createDisplay();
		
		//Our Classes
		Loader loader = new Loader();
		
		//Used by GPU when rendering 
		StaticShader shader = new StaticShader();
		
		Renderer renderer = new Renderer(shader);
		
		//OpenGL expects vertices to be defined counter clockwise by default
		float[] vertices = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
				
		};
		
		//Determines where on the vertices the texture maps too
		float[] textureCoords = {
				
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0
		};
		
		int[] indices = {
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22

		};
			
		//Loads into raw models
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		
		//Loads Model Texture
		ModelTexture texture = new ModelTexture(loader.loadTexture("spiral_seamless"));
		
		//Creates TexturedModel object
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("devil")));
		
		//Creates Entity
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-5), 0,0,0,1);
		
		//Creates a camera
		Camera camera = new Camera();
			
		//game logic
		while(!Display.isCloseRequested()) {
			//transformation
			entity.increaseRotation(1, 1, 0);
            //Moves Camera according the keyboard inputs
			camera.move();
			//Prepares Renderer every frame
			renderer.prepare();
			//Starts our shader program
			shader.start();
			//Loads camera to the shader
			shader.loadViewMatrix(camera);
			//render
			renderer.render(entity, shader);
			//Stops shader after the rendering has completed
			shader.stop();
			DisplayManager.updateDisplay();
		}
		//Closes Shader's, VAO'S, VBO's and Display
		shader.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();
	}
}
