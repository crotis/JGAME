package engineTester;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main (String[] args) {
		
		DisplayManager.createDisplay();
		
		//Our Classes
		Loader loader = new Loader();
	
		
		//Loads into raw models
		RawModel model = OBJLoader.loadObjModel("Dragon/dragon", loader);
		
		//Loads Model Texture
//		ModelTexture texture = new ModelTexture(loader.loadTexture("Stall/stallTexture"));
		
		//Creates TexturedModel object
		TexturedModel dragonModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("white")));
		
		//Creates specular lighting
		ModelTexture texture = dragonModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		//Creates Light Source
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		//Creates a camera
		Camera camera = new Camera();
		
		
		List<Entity>allDragons = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i=0; i<200; i++){
			float x = random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * -300;
			allDragons.add(new Entity(dragonModel, new Vector3f(x,y,z), random.nextFloat() * 180f,
					random.nextFloat() * 180f, 0f, 1f));
		}
	
			
		MasterRenderer renderer = new MasterRenderer();
		
		//game logic
		while(!Display.isCloseRequested()) {
            //Moves Camera according the keyboard inputs
			camera.move();
			
			for(Entity dragon: allDragons){
				renderer.processEntitiy(dragon);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		//Closes Shader's, VAO'S, VBO's and Display
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
