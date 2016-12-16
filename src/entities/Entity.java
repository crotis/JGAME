package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

//An instance of a texture model, contains the textured model and its
//position, rotation and scale in matrix format that we wish to render it by
//
//Entities allow us to have multiple entities that all use the same textured model,
//thus we will be able to render the same texture model many times without having to load the VAO more than once
public class Entity {
	
	private TexturedModel model;
	private Vector3f positions;
	private float rotX, rotY, rotZ;
	private float scale;
	
	public Entity(TexturedModel model, Vector3f positions, float rotX, float rotY, float rotZ, float scale){
		super();
		this.model = model;
		this.positions = positions;
		this.rotY = rotY;
		this.rotX = rotX;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	//Moves Entity
	public void increasePosition(float dx, float dy, float dz){
		this.positions.x += dx;
		this.positions.y += dy;
		this.positions.z += dz;
	}
	
	//Rotates Entity
	public void increaseRotation(float dx, float dy, float dz){
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPositions() {
		return positions;
	}

	public void setPositions(Vector3f positions) {
		this.positions = positions;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
