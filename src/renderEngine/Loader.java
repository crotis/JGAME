package renderEngine;

import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

//Loads 3D Models into memory by storing positional data about the model in a VAO
public class Loader {
	
	//Memory Management: Keep track of VAO's and VBO's that we create & close them down the game
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>(); 
	
	//Takes in positions of the models vertices, loads data into VAO
	//and returns information about the VAO as a raw model object
	//3 steps required for the loadToVAO method
    //1: CreateVAO
	//2: StoreDataInAttrubuteList
	//3: UnbindVAo
	//It takes the 4 vertices as positions and indices tells it the order in which they are to be connected.
	public RawModel loadToVAO(float[] positions, float[] textureCoordinates, float[] normals, int[] indices) {
		
		//Creates VAO and retrieves its ID
		int vaoID = createVAO();
		//Binds indicesBuffer to VAO
		bindIndicesBuffer(indices);
		
		//Stores positional Data in the attribute list (0)
		storeDataInAttrubiteList(0, 3, positions);
		
		//Stores positional Data of the texture in the attribute list (0)
		storeDataInAttrubiteList(1, 2, textureCoordinates);
		
		//Stores positional Data of the normals in the attribute list
		storeDataInAttrubiteList(2, 3, normals);
		
		//Now that we've finished using VAO, we unbind it
		unbindVAO();
		
		//Returns data we've created as a raw model
		//Takes ID and needs position/3 as 3 vertex has 3 floats
		return new RawModel(vaoID, indices.length);
	}
	
	//Loads texture into OpenGL so we can use it
	public int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG",
                    new FileInputStream("res/" + fileName + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Tried to load texture " + fileName + ".png , didn't work");
            System.exit(-1);
        }
        textures.add(texture.getTextureID());
        return texture.getTextureID();
    }
	
	//Cleans up VAOS, VBO's & Textures on game close
	public void cleanUp() {
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures) {
			GL11.glDeleteTextures(texture);
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
	private void storeDataInAttrubiteList(int attributeNumber, int coordinateSize, float[] data) {
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
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		//Unbinds VBO now that we're finished using it
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);		
	}
	
	//When we stop using a VAO we must Unbind it!
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	//Loads indices and binds it to VAO that we want to render
	private void bindIndicesBuffer(int[] indices) {
		//Create empty VBO
		int vboID = GL15.glGenBuffers();
		//Adds to our List of VBO's
		vbos.add(vboID);
		//Binds the buffer so we may use it
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		//Converts
		IntBuffer buffer = storeDataInIntBuffer(indices);
		//Stores intBuffer in VAO. (Static as unchanging)
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		
	}
	
	//Converts array of indices into an intBuffer
	private IntBuffer storeDataInIntBuffer(int[] data) {
		//Create empty IntBuffer
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		//Give the data to the Buffer
		buffer.put(data);
		//Flips so its ready to be read from
		buffer.flip();
		return buffer;
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
