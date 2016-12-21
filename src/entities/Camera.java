package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0,5,0);
	//Camera's Rotation around x,y,z axis
	private float pitch = 10;
	//Camera's Left and right aim
	private float yaw;
	//Camera's tilt
	private float roll;
		
	public Camera(){}

	//Moves Camera
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z-=0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z+=0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x+=0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x-=0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y-=0.2f;
        }if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            yaw+=1;
        }if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            yaw-=1;
        }
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}
