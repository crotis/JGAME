package textures;

//Represents the texture we can use to texture our models
public class ModelTexture {

	private int textureID;
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getId() {
		return this.textureID;
	}
}
