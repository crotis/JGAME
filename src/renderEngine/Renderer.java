package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

//Renders model from VAO
public class Renderer {
	
	//Called once every frame; prepares openGL to render the game
	public void prepare() {
		
		//Clears colour from the last frame
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(1, 0, 0, 1);
	}
	
	//Renders Raw Model
	public void render(RawModel model) {
	//Binds VAO so we can use it
	GL30.glBindVertexArray(model.getVaoID());
	//Activates attribute list in which our data is stores
	GL20.glEnableVertexAttribArray(0);
	//Renders! What we want to render. 
	GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	//Disables Attribute list
	GL20.glDisableVertexAttribArray(0);
	//Unbinds the VAO
	GL30.glBindVertexArray(0);
    }
}
