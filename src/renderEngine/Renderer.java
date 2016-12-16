package renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import toolbox.Maths;

//Renders model from VAO
public class Renderer {
	
	//Field of few angle for projection matrix
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	//How far you can see into the distance
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;
	
	public Renderer(StaticShader shader){
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	//Called once every frame; prepares openGL to render the game
	public void prepare() {
		
		//Tells openGL to render triangles in the correct order depending on the depth from the camera
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		//Clears colour from the last frame
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0.3f, 0.0f, 1);
	}
	
	//Renders Raw Model
	public void render(Entity entity, StaticShader shader) {
	//	
    TexturedModel model = entity.getModel();
    //Provides access to raw model
	RawModel rawModel = model.getRawModel();	
	//Binds VAO so we can use it
	GL30.glBindVertexArray(rawModel.getVaoID());
	//Activates attribute list in which our data is stores
	GL20.glEnableVertexAttribArray(0);
	GL20.glEnableVertexAttribArray(1);
	GL20.glEnableVertexAttribArray(2);
	
	//Loads up Entity's transformations(position, rotation and scale) to the transformation matrix Uniform Model
	Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPositions(),
			entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
	//Loads transformation matrix to the shader
	shader.loadTransformationMatrix(transformationMatrix);
		
	//Tells openGL which texture we wish to render onto our quad.
	GL13.glActiveTexture(GL13.GL_TEXTURE0);
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
	//Renders! What we want to render. 
	GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	//Disables Attribute list
	GL20.glDisableVertexAttribArray(0);
	GL20.glDisableVertexAttribArray(1);
	GL20.glEnableVertexAttribArray(2);
	//Unbind's the VAO
	GL30.glBindVertexArray(0);
    }
	
	private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}
