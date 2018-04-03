package self.master.projetil;


import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import self.master.audio.RandomSFXGrupo;
import self.master.graphics.GraphicsM;
import self.master.graphics.animaçao.Animaçao;
import self.master.graphics.animaçao.AnimaçaoListener;
import self.master.graphics.light.Luz;
import self.master.mob.Monstro;
import self.master.mob.Player;
import self.master.particula.CriadorDeParticulas;
import self.master.tools.ActionQueue;
import self.master.tools.Util;

public class AtkPlayer extends AutoAtkProjetil {
	private static final ArrayList<Image> imgsColisao = Util.carregarArrayBI(Animaçao.explosaoSrite, 0, 256, 64, 64, 39);
	private static final Image spriteSheet = Util.carregarImg("Sprites/playerAtkAni.png");
	private static final Image img = spriteSheet.getSubImage(85, 0, 17, 17);
	private static final Image imgVeneno = img;
	private static final ArrayList<Image> aniImgs = Util.carregarArrayBI(spriteSheet, 0, 0, 17, 17, 6);
	private static final ArrayList<Image> aniImgsVeneno = aniImgs;
	private Animaçao ani;
	
	private boolean isVeneno = false;
	
	//private SombraDinamica sombraD;
	private CriadorDeParticulas particulas;
	private static RandomSFXGrupo sfxs = new RandomSFXGrupo(new String[]{"/SFX/atk1.ogg", "/SFX/atk2.ogg", "/SFX/atk3.ogg"});

	public AtkPlayer(double angulo, boolean sfx) {
		super(Player.getPlayer(), angulo);
		this.width = 5;
		this.height = 5;
		this.speed = 0.75;
		
		particulas = new CriadorDeParticulas(getX(), getY(), width, height, 1, 1, Color.black, 20);
		particulas.setSpeed(0);
		
		
		if (Player.getPlayer().isBuffDano()) particulas.addColor(new Color(139, 69, 19));
		
		
		int random = Util.randomInt(1, 100);
		if (random < 50) {
			//random < MenuLevel.getChanceVenenoMod()
			isVeneno = true;
			particulas.addColor(Color.green);
			particulas.removeColor(Color.black);
			particulas.setPorcentagem(40);
			particulas.setComLuz(5, Color.green, 100, 5, 5);
			particulas.setAlphaVar(10);
			new Luz(this, 20, 40, 200, 40, 80, 25);
		} else {
			new Luz(this, 20, 255, 255, 255, 50, 25);
			particulas.setComLuz(5, Color.gray, 50, 5, 10);
			particulas.setAlphaVar(10);
		}
		
		if (isVeneno) {
			ani = new Animaçao(getXCentro(), getYCentro(), aniImgsVeneno, 6);
		} else {
			ani = new Animaçao(getXCentro(), getYCentro(), aniImgs, 6);
		}
		
		ani.setSeguindo(this);
		ani.setAutoPaint(false);
		ani.setVoid(5, new AnimaçaoListener() {
			public void metodo() {
				speed = 3;
				particulas.setProduzindo(true);
			}
		});
		ani.start();
		
		//sfxs.setVolume(0.5f);
		//sombraD = new SombraDinamica(this, getXCentro(), getYCentro(), Player.getPlayer().getAlcance());
		//sombra = sombraD.sombra;
		//sombra.setHeightOff(8);
		if (sfx) sfxs.play();
	}

	protected void checaColisao() {
		for (int x = 0; x < Monstro.todosMontros.size(); x++) {
			Monstro monstroAtual = Monstro.todosMontros.get(x);

			if (Math.abs(getXCentro() - monstroAtual.getXCentro()) < ((width / 2) + (monstroAtual.getWidth() / 2))) {
				if (Math.abs(getYCentro() - monstroAtual.getYCentro()) < ((height / 2) + (monstroAtual.getHeight() / 2))) {
					int danoDado = monstroAtual.tomarHit(dano);

					if (isVeneno) {
						monstroAtual.setEnvenenadoTrue(3, dano/3);
					}
					
					monstroAtual.tomarKnockBack(angulo);
					monstroAtual.setFurioso(true);
					particulas.setProduzindo(false);
					
					Animaçao ani = new Animaçao(getXCentro(), getYCentro(), imgsColisao, 1);
					ani.setScale(getScalePorDano(danoDado));
					ani.start();
					
					final Luz luzAni = new Luz(ani, ani.getWidth() / 2 + 5, 255, 220, 200, 100, 15, true, true, 0, 0);
					luzAni.forçaVar.addAcaoNaFila(new ActionQueue() {
						public boolean action() {
							luzAni.desativar(15);
							return true;
						}
					});
					
					removeProjetil();
					
				}
			}
		}

	}
	
	private float getScalePorDano(int dano) {
		float scale = (dano * (3/6.0f))/10f;
		if (scale > .75f) {
			return .75f;
		}
		
		if (scale < .4f) {
			return .4f;
		}
		
		return scale;
	}

	public void pintar(GraphicsM g) {
		//g.setColor(Color.BLACK);
	//	g.fillOval(getX() - width / 2, getY() - height / 2, width, height);
		
		if (ani.isRodando()) {
			ani.pintarManual(g);
		} else {
			if (isVeneno) {
				g.drawImage(imgVeneno, getXCentro() - img.getWidth() / 2, getYCentro() - img.getHeight() / 2);
			} else {
				g.drawImage(img, getXCentro() - img.getWidth() / 2, getYCentro() - img.getHeight() / 2);
			}
		}
		
		//g.drawImage(Filter.soVerde(spriteSheet, 0), 50, 50, spriteSheet.getWidth() * 4, spriteSheet.getHeight() * 4, null);
		
	}

	protected void updateResto() {
		if (particulas != null) {
			particulas.update(getX(), getY());
		}
	//	sombraD.update();
		
	}
	
	@Override
	protected void colisao() {
		// TODO Auto-generated method stub
		
	}





}
