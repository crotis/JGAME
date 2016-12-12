package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
 
//Represents a generic shader program.
public abstract class ShaderProgram {
     
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
     
  //Takes source code files and generates our shader Objects
    public ShaderProgram(String vertexFile,String fragmentFile){
        vertexShaderID = loadShader(vertexFile,GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,GL20.GL_FRAGMENT_SHADER);
        
        //Creates new program that ties the vertex and fragment shaders together
        programID = GL20.glCreateProgram();
         
        //Attaches shaders' to the program 
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
    }
     
    public void start(){
        GL20.glUseProgram(programID);
    }
     
    public void stop(){
        GL20.glUseProgram(0);
    }
     
    //Memory Management: Deletes shaders when they're no longer needed
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