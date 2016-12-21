package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

//Renders model from VAO
public class EntityRenderer{
	
	private StaticShader shader;
	
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix){
		
		this.shader = shader;
				
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	//Renders Raw Model, all relevant values must be loaded inside this method
	public void render(Map<TexturedModel, List<Entity>> entities){
		
		//Loops through all textured model Keys in hashmap, for each we get
		//all the entities which use the model and add them to the list
		for(TexturedModel model: entities.keySet()){
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			
			//For each entity in batch we prepare them for final render
			for(Entity entity: batch){
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model){
	    //Provides access to raw model
		RawModel rawModel = model.getRawModel();	
		//Binds VAO so we can use it
		GL30.glBindVertexArray(rawModel.getVaoID());
		//Activates attribute list in which our data is stores
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		//Loads up Specular-Lighting variables
		ModelTexture texture = model.getTexture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
			
		//Tells openGL which texture we wish to render onto our quad.
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
	}
	
	private void unbindTexturedModel(){
		//Disables Attribute list
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		//Unbind's the VAO
	    GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity){
		//Loads up Entity's transformations(position, rotation and scale) to the transformation matrix Uniform Model
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPositions(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		//Loads transformation matrix to the shader
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
