package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.TexturedModel;
import shaders.StaticShader;
import entities.Camera;
import entities.Entity;
import entities.Light;

//Handles all Rendering
public class MasterRenderer {
	
	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	
	public void render(Light sun, Camera camera){
		renderer.prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	//Puts Entitites into HashMap every frame
	public void processEntitiy(Entity entity){
		//Finds which model the Entity uses
		TexturedModel entityModel = entity.getModel();
		//Gets list that corresponds to that Entity
		List<Entity> batch = entities.get(entityModel);
		//If batch exists, adds entry to batch 
		if(batch!=null){
			batch.add(entity);
	    //Else creates new list of Entities
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
;		}
	}
	
	//Cleans up Shaders on game close
	public void cleanUp(){
		shader.cleanUp();
	}
	

}
