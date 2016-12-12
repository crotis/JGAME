package engineTester;


import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {

	public static void main (String[] args) {
		
		DisplayManager.createDisplay();
		
		//Our Classes
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		//Used by GPU when rendering 
		StaticShader shader = new StaticShader();
		
		//OpenGL expects vertices to be defined counter clockwise by default
		float[] vertices = {
				-0.5f, 0.5f, 0,   //V0
				-0.5f, -0.5f, 0,   //V1
				0.5f, -0.5f, 0,   //V2
				0.5f, 0.5f, 0     //V3
		};
		
		int[] indices = {
				0,1,3, //Top left triangle (V0, V1, V3)
				3,1,2 //Bottom right triangle (V3, V1, V2)
		};
		
		//Loads into raw models
		RawModel model = loader.loadToVAO(vertices, indices);
			
		while(!Display.isCloseRequested()) {
			//Prepares Renderer every frame
			renderer.prepare();
			//Starts our shader program
			shader.start();
			//game logic
			//render
			renderer.render(model);
			//Stops shader after the rendering has completed
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		//Closes VAO's and VBO's
		loader.cleanUP();
		DisplayManager.closeDisplay();
	}
}
