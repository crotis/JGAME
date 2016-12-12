package shaders;
 
public class StaticShader extends ShaderProgram{
     
    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
 
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        //Links textureColour to attribute 1 of the VAO
        super.bindAttribute(1, "textureCoords");
    }
}
     
     
 