package self.master.graphics.anima�ao;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import self.master.graphics.GraphicsM;


public interface Anima�aoInterface {
	public static final ArrayList<Anima�aoInterface> todasAni = new ArrayList<Anima�aoInterface>();
	
	public void update();
	public void pintar(GraphicsM gLQ);
	
	public void start();
	public void stop();
}
