package self.master.principal;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import self.master.principal.gamestate.OnInitState;
import self.master.principal.gamestate.SlickState;
import self.master.principal.gamestate.TestState;

public class GameWindow {
	public static AppGameContainer janela;
	public static final int WIDTH = 1820;
	public static final int HEIGHT = 920;
	public static final int SCALE = 1;
	
	public static void init() {
		
		try {
			Principal.game = new Game(new SlickState[]{new OnInitState(), new TestState()}, 2);
			janela = new AppGameContainer(Principal.game);
			janela.setDisplayMode(WIDTH, HEIGHT, false);
			janela.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public static int getWidthHQ() {
		return WIDTH * SCALE;
	}
	
	public static int getWidthLQ() {
		return WIDTH;
	}
	
	public static int getHeightHQ() {
		return HEIGHT * SCALE;
	}
	
	public static int getHeightLQ() {
		return HEIGHT;
	}
	
}
