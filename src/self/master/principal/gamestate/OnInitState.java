package self.master.principal.gamestate;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import self.master.graphics.GraphicsM;
import self.master.principal.GameWindow;
import self.master.principal.Principal;
import self.master.tools.ActionQueue;
import self.master.tools.Util;
import self.master.tools.Variator;
import self.master.tools.VariatorNumero;

public class OnInitState extends SlickState {
	
	private static int ultimoIndex;
	private static int index = -1;
	public static final ArrayList<Image> imgs = new ArrayList<Image>();
	public static Image img1;
	public static Image img2;
	public static final int IMG_DELAY = 300;
	public static final int IMG_FADE_DELAY = 100;
	private float alpha = 0;
	
	private Variator varAlpha;


	@Override
	public void updateGameState() {
		if (Principal.tickTotal >= ultimoIndex + IMG_DELAY + IMG_FADE_DELAY*2) {
			if (index == imgs.size() - 1) {
				game.enterState(1);
				return;
			}
			
			passarImg();
			ultimoIndex = Principal.tickTotal;
		}
	}

	private void passarImg() {
		index++;
		
		varAlpha.fadeInSin(0.0f, 1.0f, IMG_FADE_DELAY);
		varAlpha.variar(true);
		varAlpha.addEsperaNaFila(IMG_DELAY);
		varAlpha.addAcaoNaFila(new ActionQueue() {
			public boolean action() {
				varAlpha.fadeOutSin(1.0f, 0.0f, IMG_FADE_DELAY);
				varAlpha.variar(true);
				return true;
			}
		});
		
	}


	@Override
	protected void gameStateInit() {
		img1 = Util.carregarQuickImg("armor2");
		img2 = Util.carregarQuickImg("balaoDeFala");
		
		imgs.add(img1);
		imgs.add(img2);
		
		
		varAlpha = new Variator(new VariatorNumero() {
			public void setNumero(double numero) {
				alpha = Util.limita((float)numero, 0, 1);
				
			}

			public double getNumero() {
				return alpha;
			}

			public boolean devoContinuar() {
				return true;
			}
		});
	}
	

	@Override
	protected void gameStateInitOnEnter() throws SlickException {
		index = -1;
		ultimoIndex = Principal.tickTotal;
		passarImg();
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void pintarGameState(GraphicsM gLQ, GraphicsM gHQ) {
		gHQ.setColor(Color.black);
		gHQ.fillRect(0, 0, GameWindow.getWidthHQ(), GameWindow.getHeightHQ());
		
		gHQ.setAlpha(alpha);
		gHQ.drawImage(imgs.get(index), GameWindow.getWidthHQ()/2 - imgs.get(index).getWidth()/2, GameWindow.getHeightHQ()/2 - imgs.get(index).getHeight()/2);
		gHQ.setAlphaPadrao();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		
	}
	
	@Override
	public String toString() {
		return "OnInitState";
	}



}
