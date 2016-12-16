package engineTester;


import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
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
		
		//Loads into raw models
		RawModel model = OBJLoader.loadObjModel("Dragon/dragon", loader);
		
		//Loads Model Texture
//		ModelTexture texture = new ModelTexture(loader.loadTexture("Stall/stallTexture"));
		
		//Creates TexturedModel object
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("white")));
		
		//Creates Entity
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-50), 0,0,0,1);
		
		//Creates Light Source
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		//Creates a camera
		Camera camera = new Camera();
			
		//game logic
		while(!Display.isCloseRequested()) {
			//transformation
			entity.increaseRotation(0, 1, 0);
            //Moves Camera according the keyboard inputs
			camera.move();
			//Prepares Renderer every frame
			renderer.prepare();
			//Starts our shader program
			shader.start();
			//Loads Light to the shader
			shader.loadLight(light);
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
