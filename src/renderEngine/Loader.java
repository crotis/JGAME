package renderEngine;

//import java.awt.List;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

//Loads 3D Models into memory by storing positional data about the model in a VAO
public class Loader {
	
	//Memory Management: Keep track of VAO's and VBO's that we create & close them down the game
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	
	//Takes in positions of the models vertices, loads data into VAO
	//and returns information about the VAO as a raw model object
	//3 steps required for the loadToVAO method
    //1: CreateVAO
	//2: StoreDataInAttrubuteList
	//3: UnbindVAo
	public RawModel loadToVAO(float[] positions) {
		
		//Creates VAO and retrieves its ID
		int vaoID = createVAO();
		
		//Stores positional Data in the attribute list (0)
		storeDataInAttrubiteList(0, positions);
		
		//Now that we've finished using VAO, we unbind it
		unbindVAO();
		
		//Returns data we've created as a raw model
		//Takes ID and needs position/3 as 3 vertex has 3 floats
		return new RawModel(vaoID, positions.length/3);
	}
	
	//Cleans up VAOS and VBO's on game close
	public void cleanUP() {
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
	}
	
	
	
	//Creates a new empty VAO & returns its ID - We must bind it to use it!
	private int createVAO() {
		
		//Creates empty VAO and returns ID
		int vaoID = GL30.glGenVertexArrays();
		
		//Adds VAO to our list so we can track it
		vaos.add(vaoID);
		
		//Binds Vertex Array (Stays bound until we unbind it)
		GL30.glBindVertexArray(vaoID);
		return vaoID;
		
	}
	
	//Store data into one of the attribute lists of the VAO, takes number of attribute list where we want to store the data and teh data itself
	private void storeDataInAttrubiteList(int attributeNumber, float[] data) {
		//Returns ID of VBO that we've created
		int vboID = GL15.glGenBuffers();
		//Adds VBO to our list so we can track it
		vbos.add(vboID);
		//Bids Buffer, so we can now store data into it
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		//Converts data into floatbuffer
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		//Stores into VBO, Takes Type of VBO, data from buffer, and data use (Unedited static in this case)
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		//Puts VBO into one of the attribute lists of VAO
		//Takes number of attribute list, length of each vertex, type of data, whether data is normalised, and the distance between each vertices
		GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
		//Unbinds VBO now that we're finished using it
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);		
	}
	
	//When we stop using a VAO we must Unbind it!
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	
	//Converts floatArray of data into a float buffer
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		//Gives buffer the data
		buffer.put(data);
		//Prepares buffer to be read for, means we've finished writing and is ready to be read from
		buffer.flip();
		return buffer;
		
		
	}

}
