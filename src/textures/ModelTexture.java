package textures;

//Represents the texture we can use to texture our models
public class ModelTexture {

	private int textureID;
	
	//The following two variables collectively determine an objects Specultar Lighting
	//Determines how much light a surface reflects, works in addition to pixel-lighting 
	private float reflectivity = 0;
	//Determines how close to the camera needs to be to the reflected light to see any chance in the brightness of the object
	private float shineDamper = 1;
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getId() {
		return this.textureID;
	}
	
	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}
}
