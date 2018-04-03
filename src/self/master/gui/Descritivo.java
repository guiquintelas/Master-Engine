package self.master.gui;

import java.util.ArrayList;

import self.master.input.ListenerManager;
import self.master.principal.Dimensional;

public abstract class Descritivo extends Dimensional{
	public static final ArrayList<Descritivo> todosDescritivos = new ArrayList<Descritivo>();
	
	//pre requisitos
	// getX()
	// getY()
	// getWidth()
	// getHeight()
	
	public Descritivo() {
		todosDescritivos.add(this);
	}
	
	public abstract String getDescri�ao();
	
	
	public boolean checaColisao() {
		if (!isAtivo()) return false;
		
		if (ListenerManager.xMouse() > getX() && ListenerManager.xMouse() < getX() + getWidth()) {
			if (ListenerManager.yMouse() > getY() && ListenerManager.yMouse() < getY() + getHeight()) {
				Descri�aoBox.criar(getDescri�ao());
				return true;
			}
		}
		
		return false;
	}
	
	public static void updateAll() {
		for (int i = 0; i < todosDescritivos.size(); i++) {
			if (todosDescritivos.get(i).checaColisao()) return;
		}
		
		Descri�aoBox.setVisible(false);
	}
	
	public boolean isAtivo() {
		//padrao
		return true; 
	}
}
