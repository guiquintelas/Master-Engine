package self.master.principal.gamestate;


import org.newdawn.slick.Graphics;

import self.master.audio.SoundEffect;
import self.master.graphics.animaçao.Animaçao;
import self.master.graphics.animaçao.AnimaçaoInterface;
import self.master.graphics.cutscene.CutScene;
import self.master.graphics.light.Luz;
import self.master.graphics.light.LuzAmbiente;
import self.master.graphics.light.LuzBruta;
import self.master.gui.DisplayNomes;
import self.master.gui.GUI;
import self.master.item.Item;
import self.master.map.MapGenerator;
import self.master.map.TileMap;
import self.master.map.trap.Trap;
import self.master.mob.Mob;
import self.master.mob.Monstro;
import self.master.particula.Particula;
import self.master.principal.DimensionalObj;
import self.master.principal.Principal;
import self.master.projetil.Projetil;
import self.master.tools.Variator;

public abstract class GameState {
	
	private Object sync = new Object();
	
	public GameState() {
		init();
	}
	
	public final void update() {
		synchronized (sync) {
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
		}
		
		updateGameState();
	}
	
	public abstract void updateGameState();
	
	public abstract void pintarGUI(Graphics g);
	
	public abstract void pintar(Graphics g);
	
	protected abstract void gameStateInit();
	
	private final void init() {
		
		long tempoA = System.currentTimeMillis();
		System.out.println("inicio");
		//LuzAmbiente.init();	
		
		gameStateInit();
		
		System.out.println("Tempo de Inicialização: " + (System.currentTimeMillis() - tempoA) + "ms");
	}
	
	public abstract void closeGameState();
	
	public final void close() {
		MapGenerator.clearMap();
		Item.todosItens.clear();
		DimensionalObj.todosDimensionalObjs.clear();
		Particula.todasParticulas.clear();
		AnimaçaoInterface.todasAni.clear();
		Mob.todosMobs.clear();
		Monstro.todosMontros.clear();
		Projetil.todosProjetils.clear();
		GUI.reset();
		Trap.todasTraps.clear();
		
		if (Principal.light) LuzAmbiente.cleanUp();
		Luz.todasLuz.clear();
		LuzBruta.todasLuzBruta.clear();
		
		closeGameState();
	}
	


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
	
	private void updateVariator() {
		for (int x = 0; x < Variator.todosVariator.size(); x++) {
			Variator.todosVariator.get(x).update();
		}
	}
	
}
