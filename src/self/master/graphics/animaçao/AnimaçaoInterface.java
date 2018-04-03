package self.master.graphics.animaçao;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import self.master.graphics.GraphicsM;


public interface AnimaçaoInterface {
	public static final ArrayList<AnimaçaoInterface> todasAni = new ArrayList<AnimaçaoInterface>();
	
	public void update();
	public void pintar(GraphicsM gLQ);
	
	public void start();
	public void stop();
}
