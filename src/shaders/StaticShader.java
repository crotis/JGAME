package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{
     
    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
    
    private int location_transformationMatrix;
 
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        //Links textureColour to attribute 1 of the VAO
        super.bindAttribute(1, "textureCoords");
    }

	@Override
	protected void getAllUnbiformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}
	
	//Loads a transformation matrix to a uniform variable
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
}
     
     
 