package self.master.graphics.cutscene;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import self.master.graphics.GraphicsM;
import self.master.input.InputListener;
import self.master.input.ListenerManager;
import self.master.principal.GameWindow;
import self.master.principal.gamestate.SlickState;
import self.master.tools.ActionQueue;
import self.master.tools.Util;
import self.master.tools.Variator;
import self.master.tools.VariatorNumero;

public class ImageSnap {
	
	private static boolean isSnapping = false;
	private static float alpha = 0;
	
	private static Variator varAlpha;

	
	public static ArrayList<Image> imgs = new ArrayList<Image>();
	
	public static void init() {
		
		ListenerManager.addInputListener(new InputListener() {
			public void update(Input input) {
				if (input.isMouseButtonDown(0)) {
					snap();
				}
				
			}
		});
		
		varAlpha = new Variator(new VariatorNumero() {
			public void setNumero(double numero) {
				alpha = Util.limita((float)numero, 0.0f, 1.0f);

				
			}
			
			public double getNumero() {
				return alpha;
			}
			
			public boolean devoContinuar() {
				return true;
			}
		});
	}

	public static void snap() {
		if (isSnapping) return;
		
		isSnapping = true;
		imgs.add(Util.copyImage(SlickState.getImgJogo()));
		System.out.println("num imgs " + imgs.size());
		

		varAlpha.fadeInSin(0, 1, 100);
		varAlpha.variar(true);
		varAlpha.addAcaoNaFila(new ActionQueue() {
			public boolean action() {
				isSnapping = false;
				alpha = 0;
				
				return true;
			}
		});

	}
	
	public static void pintar(GraphicsM gHQ) {
		if (isSnapping) {
			//gHQ.g.setBackground(new Color(0,0,0,0));
			gHQ.setColor(Color.white);
			gHQ.setAlpha(alpha);
			
			gHQ.fillRect(50 * alpha, 50 * alpha, GameWindow.getWidthHQ() - 100, GameWindow.getHeightHQ() - 100);
			
			
		}
	}
}
