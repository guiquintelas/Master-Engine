package self.master.principal;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import self.master.audio.BackMusic;
import self.master.graphics.light.LuzManager;
import self.master.input.ListenerManager;
import self.master.map.TileMap;
import self.master.principal.gamestate.SlickState;
import self.master.tools.Tradutor;

public final class Game extends StateBasedGame {

	private SlickState[] states;
	private int stateAtualID;
	
	public int stateID = -1;
	
	public boolean isInit = false;
	public boolean requestStateInit = false;
	
	public static final int FPS = 100;

	public Game(SlickState state, int stateAtualID) {
		super("Engine Master");
		states = new SlickState[1];
		states[0] = state;
		this.stateAtualID = stateAtualID;
	}
	
	public Game(SlickState[] states,int stateAtualID) {
		super("Engine Master");
		this.states = states;
		this.stateAtualID = stateAtualID;
	}
	
	@Override
	public void enterState(int id) {
		if (isInit)stateID = id;
		
		requestStateInit = true;
		super.enterState(id);
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(100);
		gc.setAlwaysRender(true);

		for (int i = 0; i < states.length; i++) {
			addState(states[i]);
		}		
		
		isInit = true;
		this.enterState(stateAtualID);
			
		
		init();
		
		
	}
	
	
	private void init() throws SlickException {
		System.out.println("\nCarregando o Jogo");
		long tempoA = System.currentTimeMillis();
			
		LuzManager.init();
		//ListenerManager.init();
		BackMusic.setMute(true);
		Tradutor.init();
		TileMap.init();
		
		
		
		System.out.println("Jogo Carregado em: " + (System.currentTimeMillis() - tempoA) + "ms");
	}

}
