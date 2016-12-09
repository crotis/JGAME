package engineTester;


import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

	public static void main (String[] args) {
		
		DisplayManager.createDisplay();
		
		//Our Classes
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		//OpenGL expects vertices to be defined counter clockwise by default
		float[] vertices = {
				// Left bottom triangle
				-0.5f, 0.5f, 0,   //v0 
				-0.5f, -0.5f, 0,  //v1
				0.5f, 0.5f, 0,    //v3
				// Right top triangle
				0.5f, -0.5f, 0f,  //v3
				0.5f, 0.5f, 0f,   //v1
				-0.5f, 0.5f, 0f   //v2
		};
		
		//Loads into raw models
		RawModel model = loader.loadToVAO(vertices);
			
		while(!Display.isCloseRequested()) {
			//Prepares Renderer every frame
			renderer.prepare();
			//game logic
			//render
			renderer.render(model);
			DisplayManager.updateDisplay();
		}
		//Closes VAO's and VBO's
		loader.cleanUP();
		DisplayManager.closeDisplay();
		
		
	}
}
