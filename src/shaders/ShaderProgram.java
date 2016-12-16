package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
 
//Represents a generic shader program.
public abstract class ShaderProgram {
     
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
    
    //Allows us to load up a Matrix into uniform location
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
     
  //Takes source code files and generates our shader Objects
    public ShaderProgram(String vertexFile,String fragmentFile){
        vertexShaderID = loadShader(vertexFile,GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,GL20.GL_FRAGMENT_SHADER);
        
        //Creates new program that ties the vertex and fragment shader's together
        programID = GL20.glCreateProgram();
         
        //Attaches shader's to the program 
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        getAllUniformLocations();
    }
    
    protected int getUniformLocation(String uniformName){
    	return GL20.glGetUniformLocation(programID, uniformName);
    }
    
    //Ensures all the shader classes how a method that allows us to get all the uniform locations
    protected abstract void getAllUniformLocations();
     
    public void start(){
        GL20.glUseProgram(programID);
    }
     
    public void stop(){
        GL20.glUseProgram(0);
    }
     
    //Memory Management: Deletes shader's when they're no longer needed
    public void cleanUp(){
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }
     
    //Links inputs to the shaderPrograms to one of the attributes of the VAO we call in.
    protected abstract void bindAttributes();
     
    //Binds Attribute
    protected void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }
     
    //Loads float value to a uniform location
    protected void loadFloat(int location, float value){
    	GL20.glUniform1f(location, value);
    }
    
    //Loads a vector into a uniform location
    protected void loadVector(int location, Vector3f vector){
    	GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }
    
    //Loads a boolean into uniform location. There isn't a boolean in shader code, so we load up either 0 or a 1 
    protected void loadBoolean(int location, boolean value){
    	float toLoad = 0;
    	if(value) {
    		toLoad = 1;
    	}
    	GL20.glUniform1f(location, toLoad);
    } 
    
    //Loads a Matrix into the uniform location. 
    protected void loadMatrix(int location, Matrix4f matrix) {
    	//Stores matrix in buffer
    	matrix.store(matrixBuffer);
    	//Prepares buffer to be read from
    	matrixBuffer.flip();
    	//Loads into location of the uniform variable
    	GL20.glUniformMatrix4(location, false, matrixBuffer);
    }
    
    //Loads shader source-code files.
  	//Opens source code files, reads all the lines and connects them into one String in order to create 
  	//a new vertex/fragment shader depending on the type given. String is attached and compiled and any
  	//errors that where found are printed
  	//@Return ShaderID - ID of the newly created shader.
  	//@Params file - source-code file name
  	//@Params type - shows whether its a vertex/fragment shader
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }
 
}