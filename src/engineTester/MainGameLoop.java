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
