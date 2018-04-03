package self.master.principal.gamestate;


import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import self.master.audio.SoundEffect;
import self.master.graphics.GraphicsM;
import self.master.graphics.animaçao.Animaçao;
import self.master.graphics.animaçao.AnimaçaoInterface;
import self.master.graphics.cutscene.CutScene;
import self.master.graphics.cutscene.ImageSnap;
import self.master.graphics.light.LuzManager;
import self.master.graphics.light.Luz;
import self.master.graphics.light.LuzAmbiente;
import self.master.graphics.light.LuzBruta;
import self.master.gui.DisplayNomes;
import self.master.gui.GUI;
import self.master.input.ListenerManager;
import self.master.map.MapGenerator;
import self.master.map.TileMap;
import self.master.mob.Mob;
import self.master.mob.Monstro;
import self.master.mob.Player;
import self.master.particula.Particula;
import self.master.principal.Dimensional;
import self.master.principal.DimensionalObj;
import self.master.principal.Game;
import self.master.principal.GameWindow;
import self.master.principal.Principal;
import self.master.projetil.Projetil;
import self.master.tools.RelatorioDelay;
import self.master.tools.Timer;
import self.master.tools.Util;
import self.master.tools.Variator;

public abstract class SlickState extends BasicGameState {
	
	private static Image imgJogoHQ;
	private static GraphicsM gHQ;
	
	private static Image imgJogoLQ;
	private static GraphicsM gLQ;
	
	private static Image imgJogo;
	private static GraphicsM g;
	
	private BufferedImage imgJogoPadrao;
	private Graphics2D gP;
	
	private BufferedImage imgGUI;
	private Graphics2D gGUI;
	
	protected StateBasedGame game;
	
	private static boolean initBasico = false;
	private boolean isInit = false;
	private boolean initOnEnter = false;

	
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		long tempoA = System.currentTimeMillis();
		System.out.println("\nCarregando " + toString());
		
		this.game = game;
		initBasico();
		gameStateInit();
		
		
		
		System.out.println("Tempo de Carregamento de " + toString() +": " + (System.currentTimeMillis() - tempoA) + "ms\n");
	}
	
	private static void initBasico() throws SlickException {
		if (initBasico) return;
		initBasico = true;
		imgJogo = new Image(GameWindow.getWidthHQ(), GameWindow.getHeightHQ() - GUI.HEIGHT);
		g = new GraphicsM(imgJogo.getGraphics());

//		imgJogoPadrao = new BufferedImage(GameWindow.getWidthLQ(), GameWindow.getHeightLQ(), BufferedImage.TYPE_4BYTE_ABGR);
//		gP = (Graphics2D) imgJogoPadrao.getGraphics();
//		gP.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		gP.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
//		
//
//		imgGUI = new BufferedImage(GameWindow.getWidthHQ(), GUI.HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
//		gGUI = (Graphics2D) imgGUI.getGraphics();
//		gGUI.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		gGUI.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		
		imgJogoHQ = new Image(GameWindow.WIDTH, GameWindow.HEIGHT - GUI.HEIGHT);
		gHQ = new GraphicsM(imgJogoHQ.getGraphics());
		gHQ.setAntiAlias(true);

		
		imgJogoLQ = new Image(GameWindow.getWidthLQ(), GameWindow.getHeightLQ() - GUI.HEIGHT/GameWindow.SCALE);
		gLQ = new GraphicsM(imgJogoLQ.getGraphics());
		gLQ.setAntiAlias(true);	
	}
 

	RelatorioDelay relatorio = new RelatorioDelay('r', 'm');
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		SlickState.gHQ.g = g;
		
		
		relatorio.abrir();
		
		relatorio.abrirValor();
		LuzManager.resetImgLuz();
		relatorio.fecharValor("Resetar Luz Ambiente");
		
		relatorio.abrirValor();
		resetarImg();	
		relatorio.fecharValor("Resetar Imagem");
		
		relatorio.abrirValor();
		pintarTiles();	
		relatorio.fecharValor("Pintar Tiles");
		
//		relatorio.abrirValor();
//		pintarProjetils();
//		relatorio.fecharValor("Pintar Projetil");
//		
		relatorio.abrirValor();
		pintarEmOrdem();
		relatorio.fecharValor("Pintar Ordem");
//		
		relatorio.abrirValor();
		pintarParticulas();
		relatorio.fecharValor("Pintar Particulas");
		
		relatorio.abrirValor();
		pintarAni();	
		relatorio.fecharValor("Pintar Ani");
//		
		relatorio.abrirValor();
	    DisplayNomes.pintar(gHQ);
		relatorio.fecharValor("Pintar DisplayNomes");
			
		if (Principal.light && !Principal.dia) {
			
			relatorio.abrirValor();
			pintarLuz();
			relatorio.fecharValor("Pintar Luz");
			
			relatorio.abrirValor();
			LuzManager.renderLuz(gLQ, imgJogoLQ);
			relatorio.fecharValor("Pintar Luz Ambiente");
		}
		
//		relatorio.abrirValor();
//		pintarLuzBruta();				
//		relatorio.fecharValor("Pintar Luz Bruta");
		
//		relatorio.abrirValor();
//		ControladorMenu.pintarMenus(gHQ);	
//		relatorio.fecharValor("Pintar Menus");
		
		relatorio.abrirValor();
		ImageSnap.pintar(gHQ);
		relatorio.fecharValor("Pintar ImageSnap");
		
		relatorio.abrirValor();
		CutScene.pintar(gHQ);
		relatorio.fecharValor("Pintar CutScene");
		
		if (initOnEnter)pintarGameState(gLQ, gHQ);
		
		//pintarImgLQ();
		//pintarImgHQ();
		
		//g.drawImage(imgJogo, 0, 0);		
		
	}
	



	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {	
		Principal.tickTotal++;
		
		//ListenerManager.input = gc.getInput();
		
		if (gc.getInput().isMousePressed(0)) {
			ImageSnap.snap();
			
		}

		
		//ListenerManager.updateInputs();
		updateTimer();
		updateDimiObjs();
		updateVariator();
		TileMap.update();
		updateSFX();
		updateAni();
		updateParticulas();	
		DisplayNomes.update();
		//Principal.musicas.play(); 
		if (Principal.light && !Principal.dia) updateLuz();
		updateLuzBruta();
		CutScene.update();
		
		if (initOnEnter) updateGameState();
		
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Dimensional temp = new Dimensional() {};
			//temp.setX(ListenerManager.input.getMouseX());
			//temp.setY(ListenerManager.input.getMouseY());
			
			//new Luz(temp, 25, Util.randomInt(50, 255),Util.randomInt(50, 255), Util.randomInt(50, 255), 80, 100, false, true, 0, 20);
		}
	}
	
	public void clearAll() {
		MapGenerator.clearMap();
		DimensionalObj.todosDimensionalObjs.clear();
		Particula.todasParticulas.clear();
		AnimaçaoInterface.todasAni.clear();
		Mob.todosMobs.clear();
		Monstro.todosMontros.clear();
		Projetil.todosProjetils.clear();
		GUI.reset();
		ListenerManager.todosInputs.clear();
		Luz.todasLuz.clear();
	}
	
	public abstract String toString();

	public abstract int getID();
	
	public abstract void updateGameState();
	
	protected abstract void gameStateInit() throws SlickException;
	
	protected abstract void gameStateInitOnEnter() throws SlickException;
	
	
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		
		if (((Game)game).stateID == getID() && ((Game)game).requestStateInit) {

			System.out.println("\nEntrando no state " + toString());
			gameStateInitOnEnter();
			System.out.println("Entrou no state " + toString() + "\n");
			initOnEnter = true;
			((Game)game).requestStateInit = false;
		}
		
	}
	
	public abstract void leave(GameContainer container, StateBasedGame game);
	
	public abstract void pintarGameState(GraphicsM gLQ2, GraphicsM gHQ2);
	
	
//	private void pintarProjetils() {
//		for (int x = 0; x < Projetil.todosProjetils.size(); x++) {
//			try {
//				Projetil.todosProjetils.get(x).pintar(gLQ);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		}
//
//	}

	// pinta os mobs ordenados pelos seus Y, o quanto menor primeiro ele vai ser
	// pintado
	private void pintarEmOrdem() {
		ArrayList<DimensionalObj> dsEmOrdem = new ArrayList<DimensionalObj>();
		ArrayList<DimensionalObj> dsNaoOrdem = new ArrayList<DimensionalObj>();
		//dsNaoOrdem.addAll(Mob.todosMobs);
		//dsNaoOrdem.addAll(Item.todosItens);
		dsNaoOrdem.addAll(DimensionalObj.todosDimensionalObjs);

		for (int y = 0; y < dsNaoOrdem.size(); y++) {
			for (int i = 0; i < dsNaoOrdem.size(); i++) {

				if (dsEmOrdem.contains(dsNaoOrdem.get(i))) {
					continue;
				}

				if (dsEmOrdem.size() == y) {
					dsEmOrdem.add(dsNaoOrdem.get(i));
					continue;
				}
				
				if (dsNaoOrdem.get(i).getY() + dsNaoOrdem.get(i).getHeight() < dsEmOrdem.get(y).getY() + dsEmOrdem.get(y).getHeight()) {
					dsEmOrdem.set(y, dsNaoOrdem.get(i));
				}
				
			}
		}

		for (int x = 0; x < dsEmOrdem.size(); x++) {
			dsEmOrdem.get(x).pintar(gLQ);
		}
	}
	
//	private void pintarFPSTPS(Graphics2D g) {
//		g.setFont(new Font("Arial", Font.BOLD, 12));
//		if (Principal.light) {
//			g.setColor(Color.WHITE);
//		} else {
//			g.setColor(Color.DARK_GRAY);
//		}
//		g.drawString("TPS: " + Principal.getTPS() + "   FPS: " + getFPS(), 5, GameWindow.getHeightHQ() - 10);
//	}
	
	private void pintarImgLQ() {
		g.drawImage(imgJogoLQ, 0, 0, GameWindow.SCALE);
	}
	
	private void pintarImgHQ() {
		//g.setCurrent();
		imgJogoHQ.draw();
		//g.drawImage(imgJogoHQ, 0, 0);
	}
	



	private void resetarImg() {
		gLQ.setColor(new Color(222, 184, 135));
		gLQ.fillRect(0, 0, GameWindow.getWidthLQ(), GameWindow.getHeightLQ() - GUI.HEIGHT);
		
		g.setColor(new Color(1,1,1));
		g.fillRect(0, 0, GameWindow.getWidthHQ(), GameWindow.getHeightHQ());
		
		//gLQ.drawImage(imgJogoPadrao, 0, 0, this);
//		if (Principal.editorTiles) {
//			g.setColor(Color.BLACK);
//			g.setFont(new Font("Arial", Font.BOLD, 12));
//			g.drawString("Editor Mode: ON", 5, 15);
//		}
		
//		Composite compHQ = null;
//		if (gHQ.getComposite() != null) compHQ  = gHQ.getComposite();
//		gHQ.setComposite(AlphaComposite.Clear);
//		gHQ.fillRect(0, 0, GameWindow.getWidthHQ(), GameWindow.getHeightHQ());
//		if (gHQ.getComposite() != null) gHQ.setComposite(compHQ);
		gHQ.clearAll();

	}

//
//	private void pintarDescriçaoBox() {
//			DescriçaoBox.pintarNaGUI(gGUI);
//			DescriçaoBox.pintarNoJogo(gHQ);
//		
//	}

	private void pintarParticulas() {
		for (int x = 0; x < Particula.todasParticulas.size(); x++) {
			try {
				Particula.todasParticulas.get(x).pintar(gLQ);
			} catch (NullPointerException e) {
				System.out.println("Erro particulas, particula removida na hora de ser pintada");
			}
			
		}
	}





	private void pintarTiles() {
		for (int y = 0; y < TileMap.HEIGHT; y++) {
			for (int x = 0; x < TileMap.WIDTH; x++) {
				TileMap.tileMap[x][y].pintar(gLQ);
			}
		}
	}
	
	private void pintarAni() {
		for (int x = 0; x < Animaçao.todasAni.size(); x++) {
			AnimaçaoInterface.todasAni.get(x).pintar(gLQ);
		}
	}
	

	
	private void pintarLuz() {
		for (int x = 0; x < Luz.todasLuz.size(); x++) {
			try {
				LuzManager.pintarLuz(Luz.todasLuz.get(x));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
//	private void pintarLuzBruta() {
//		for (int x = 0; x < LuzBruta.todasLuzBruta.size(); x++) {
//			try {
//				LuzBruta.todasLuzBruta.get(x).pintar(imgJogo);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		}
//	}



//	public void resetarImgPadrao() {
//		//gP.setColor(Color.BLACK);
//		gP.setColor(new Color(222, 184, 135));
//		gP.fillRect(0, 0, GameWindow.getWidthLQ(), GameWindow.getHeightLQ());
//		
//	}
	
	private void updateParticulas() {
		for (int x = 0; x < Particula.todasParticulas.size(); x++) {
			Particula.todasParticulas.get(x).update();
		}
	}



//	private void updateSFX2D() {
//		for (int x = 0; x < SoundEffect2D.sfxs2d.size(); x++) {
//			SoundEffect2D.sfxs2d.get(x).update();
//		}
//	}

	private void updateAni() {
		for (int x = 0; x < Animaçao.todasAni.size(); x++) {
			AnimaçaoInterface.todasAni.get(x).update();
		}
	}
	
	private void updateDimiObjs() {
		for (int x = 0; x < DimensionalObj.todosDimensionalObjs.size(); x++) {
			DimensionalObj.todosDimensionalObjs.get(x).update();
		}
	}
	
	
	private void updateSFX() {
		for (int x = 0; x < SoundEffect.sfxs.size(); x++) {
			SoundEffect.sfxs.get(x).update();			
		}
	}
	
	private void updateLuz() {
		for (int x = 0; x < Luz.todasLuz.size(); x++) {
			Luz.todasLuz.get(x).update();
		}
	}
	
	private void updateLuzBruta() {
		for (int x = 0; x < LuzBruta.todasLuzBruta.size(); x++) {
			LuzBruta.todasLuzBruta.get(x).update();
		}
	}
	
	private void updateTimer() {
		for (int x = 0; x < Timer.todosTimers.size(); x++) {
			Timer.todosTimers.get(x).update();
		}
	}
	
	private void updateVariator() {
		for (int x = 0; x < Variator.todosVariator.size(); x++) {
			Variator.todosVariator.get(x).update();
		}
	}
	
	public static Image getImgJogo() {
		return imgJogo;
	}

}
