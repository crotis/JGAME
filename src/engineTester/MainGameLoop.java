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
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {
	
	 public static void main(String[] args) {
		 
	        DisplayManager.createDisplay();
	        Loader loader = new Loader();
	         
	         
	        RawModel treeModel = OBJLoader.loadObjModel("tree", loader);
	        RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
	         
	        TexturedModel staticModelTree = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
	        TexturedModel staticModelGrass = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grassTexture")));
	         
	        List<Entity> entities = new ArrayList<Entity>();
	        Random random = new Random();
	        for(int i=0;i<500;i++){
	        	entities.add(new Entity(staticModelTree, new Vector3f(random.nextFloat()*800 - 800,0,random.nextFloat() * -800),0,0,0,3));
//	            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 600,0,random.nextFloat() * -600),0,0,0,3));
	        	
	        	entities.add(new Entity(staticModelGrass, new Vector3f(random.nextFloat()*800 - 800,0,random.nextFloat() * -800),0,0,0,3));
	        }
	         
	        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
	         
//	        Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
	        Terrain terrain2 = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
	         
	        Camera camera = new Camera();   
	        MasterRenderer renderer = new MasterRenderer();
	         
	        while(!Display.isCloseRequested()){
	            camera.move();
	             
//	            renderer.processTerrain(terrain);
	            renderer.processTerrain(terrain2);
	            for(Entity entity:entities){
	                renderer.processEntity(entity);
	            }
	            renderer.render(light, camera);
	            DisplayManager.updateDisplay();
	        }
	 
	        renderer.cleanUp();
	        loader.cleanUp();
	        DisplayManager.closeDisplay();
	 
	    }
	 
	}
	

//	public static void main (String[] args) {
//		
//		DisplayManager.createDisplay();
//		
//		//Our Classes
//		Loader loader = new Loader();
//	
//		
//		//Loads into raw models
//		RawModel model = OBJLoader.loadObjModel("Dragon/dragon", loader);
//		
//		//Loads Model Texture
////		ModelTexture texture = new ModelTexture(loader.loadTexture("Stall/stallTexture"));
//		
//		//Creates TexturedModel object
//		TexturedModel dragonModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("white")));
//		
//		//Creates specular lighting
//		ModelTexture texture = dragonModel.getTexture();
//		texture.setShineDamper(10);
//		texture.setReflectivity(1);
//		
//		//Creates Light Source
//		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
//		
//		Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
//		Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass")));
//		
//		
//		//TODO: Set Camera position to be above terrain on Game Start
//		//Creates a camera
//		Camera camera = new Camera();
//		
//		List<Entity>allDragons = new ArrayList<Entity>();
//		Random random = new Random();
//		
//		for(int i=0; i<200; i++){
//			float x = random.nextFloat() * 100 - 50;
//			float y = random.nextFloat() * 100 - 50;
//			float z = random.nextFloat() * -300;
//			allDragons.add(new Entity(dragonModel, new Vector3f(x,y,z), random.nextFloat() * 180f,
//					random.nextFloat() * 180f, 0f, 1f));
//		}
//	
//			
//		MasterRenderer renderer = new MasterRenderer();
//		
//		//game logic
//		while(!Display.isCloseRequested()) {
//            //Moves Camera according the keyboard inputs
//			camera.move();
//			
//			renderer.processTerrain(terrain);
//			renderer.processTerrain(terrain2);
//			
//			
//			for(Entity dragon: allDragons){
//				renderer.processEntitiy(dragon);
//			}
//			renderer.render(light, camera);
//			DisplayManager.updateDisplay();
//		}
//		//Closes Shader's, VAO'S, VBO's and Display
//		renderer.cleanUp();
//		loader.cleanUp();
//		DisplayManager.closeDisplay();
//	}
