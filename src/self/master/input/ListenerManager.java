package self.master.input;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import self.master.principal.Dimensional;


public class ListenerManager {
	public static Input input;
	public static Dimensional mouse;
	
	
	public static ArrayList<InputListener> todosInputs = new ArrayList<InputListener>();
	
	public static void init() {
		mouse = new Dimensional() {
				public int getX() {
					return input.getMouseX();
				}
				
				public int getY() {
					return input.getMouseY();
				}
				
				public int getXCentro() {
					return input.getMouseX();
				}
				
				public int getYCentro() {
					return input.getMouseY();
				}
			
				
				
				
			};
	}

	public static void updateInputs(){
		for (int i = 0; i< todosInputs.size(); i++) {
			todosInputs.get(i).update(input);
		}
	}
	
	public static void addInputListener(InputListener input) {
		todosInputs.add(input);
	}
	
	public static void removeInputListener(InputListener input) {
		todosInputs.remove(input);
	}
	
	public static int xMouse() {
		//return input.getMouseX();
		return 0;
	}
	
	public static int yMouse() {
		//return input.getMouseY();
		return 0;
	}

}
