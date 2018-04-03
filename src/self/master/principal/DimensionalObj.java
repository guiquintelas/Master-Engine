package self.master.principal;

import java.util.ArrayList;

import self.master.graphics.GraphicsM;


public abstract class DimensionalObj extends Dimensional {
	public static ArrayList<DimensionalObj> todosDimensionalObjs = new ArrayList<DimensionalObj>();
	
	public void prontoParaPintar() {
		if (!todosDimensionalObjs.contains(this)) {
			todosDimensionalObjs.add(this);
		}	
	}
	
	public void remove() {
		todosDimensionalObjs.remove(this);
	}
	
	public abstract String toString();
	
	public abstract void pintar(GraphicsM gLQ);
	
	public abstract void update();
}
