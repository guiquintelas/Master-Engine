package self.master.principal.gamestate;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import self.master.graphics.GraphicsM;
import self.master.graphics.cutscene.CutScene;
import self.master.graphics.cutscene.ImageSnap;
import self.master.graphics.light.LuzAmbiente;
import self.master.gui.menus.MenuPrincipal;
import self.master.gui.menus.MenuTeste1;
import self.master.gui.menus.MenuTeste2;
import self.master.input.InputListener;
import self.master.input.ListenerManager;
import self.master.map.MapGenerator;
import self.master.mob.Player;
import self.master.projetil.ProjetilVeneno;

public class TestState extends SlickState {

	
	
	@Override
	protected void gameStateInit() throws SlickException {
		LuzAmbiente.init();
		
		initListeners();
		
		new MenuTeste1();
		new MenuTeste2();
		new MenuPrincipal();
		
		ImageSnap.init();
		CutScene.init();


	}
	
	protected void gameStateInitOnEnter() throws SlickException {
		MapGenerator.criarMapa();
		
		new Player(200, 200);
		//new Mago();
		new ProjetilVeneno(400, 400, 0);
		
		ListenerManager.addInputListener(new InputListener() {
			public void update(Input input) {
				if (input.isKeyPressed(Input.KEY_T)) game.enterState(1);
				
			}
		});
		
		 
	}

	private void initListeners() {
		
		
	}

	public void updateGameState() {
	}

	@Override
	public int getID() {
		return 2;
	}

	@Override
	public void pintarGameState(GraphicsM gLQ, GraphicsM gHQ) {
		gHQ.setColor(Color.black);
		gHQ.setFont(gHQ.fontPadrao);
		gHQ.drawString("TESTE", 0, 0);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		clearAll();
	}
	
	@Override
	public String toString() {
		return "TestState";
	}


}
