package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.TerrainShader;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.Maths;

public class TerrainRenderer {

	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<Terrain> terrains){
		for(Terrain terrain: terrains){
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	private void prepareTerrain(Terrain terrain){
	    //Provides access to raw model
		RawModel rawModel = terrain.getModel();	
		//Binds VAO so we can use it
		GL30.glBindVertexArray(rawModel.getVaoID());
		//Activates attribute list in which our data is stores
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		//Loads up Specular-Lighting variables
		ModelTexture texture = terrain.getTexture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
			
		//Tells openGL which texture we wish to render onto our quad.
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
	}
	
	private void unbindTexturedModel(){
		//Disables Attribute list
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		//Unbind's the VAO
	    GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Terrain terrain){
		//Loads up Terrains's transformations(position and scale) to the transformation matrix Uniform Model
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()),
				0, 0, 0, 1);
		//Loads transformation matrix to the shader
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
