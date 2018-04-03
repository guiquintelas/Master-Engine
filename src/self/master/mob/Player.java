package self.master.mob;

import java.util.HashMap;


import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import self.master.audio.RandomSFXGrupo;
import self.master.audio.SoundEffect;
import self.master.graphics.GraphicsM;
import self.master.graphics.animaçao.AnimaçaoSprite;
import self.master.graphics.animaçao.AnimaçaoSpriteGrupo;
import self.master.graphics.light.Luz;
import self.master.input.InputListener;
import self.master.input.ListenerManager;
import self.master.item.Container;
import self.master.item.Item;
import self.master.item.equip.Equip;
import self.master.principal.GameWindow;
import self.master.principal.Principal;
import self.master.projetil.AtkPlayer;
import self.master.tools.Timer;
import self.master.tools.Util;
import self.master.tools.Variator;
import self.master.tools.VariatorNumero;

public final class Player extends Mob implements Ranged{

	//unica referencia ao player ja q só existe um
	private static Player getPlayer;

	public static Player getPlayer() {
		return getPlayer;
	}


	// atributos
	public int pontosLevel = 100;
	private int manaMax = 100;
	public float mana = manaMax;


	// constantes
	private static final int PARADO = 0;
	private static final int WIDTH_HITBOX = 20;
	private static final int HEIGHT_HITBOX = 15;
	private static final int HEIGHT_SPRITE = 55;

	// booleanas
	private boolean isCima = false;
	private boolean isBaixo = false;
	private boolean isDireita = false;
	private boolean isEsquerda = false;

	//Mochila
	public HashMap<Integer, Item> mochilaItens = new HashMap<Integer, Item>();
	public Container mochila;

	//Equipes
	public HashMap<String, Equip> equips = new HashMap<String, Equip>();

	//Luz
	private Luz luz;

	//Variator
	public Variator varGLuz;

	// Timers
	private Timer timerAtaque;

	//Criadores de Particulas

	//Sprite
	private static Image sprites;
	private static AnimaçaoSpriteGrupo grupoAni = new AnimaçaoSpriteGrupo();
	private static AnimaçaoSprite parado;
	private static AnimaçaoSprite esquerda;
	private static AnimaçaoSprite esquerdaCima;
	private static AnimaçaoSprite cima;
	private static AnimaçaoSprite cimaDireita;
	private static AnimaçaoSprite direita;
	private static AnimaçaoSprite direitaBaixo;
	private static AnimaçaoSprite baixo;
	private static AnimaçaoSprite baixoEsquerda;

	//SFX
	private static final RandomSFXGrupo tomarHitSFX  = new RandomSFXGrupo(new String[] { "/SFX/playerHit1.ogg", "/SFX/playerHit2.ogg", "/SFX/playerHit3.ogg", "/SFX/playerHit4.ogg" });
	public static final SoundEffect coraçao 		 = new SoundEffect("/SFX/coraçao.ogg");
	private static final RandomSFXGrupo pegarItemSFX = new RandomSFXGrupo(new String[] { "/SFX/item1.ogg", "/SFX/item1.ogg", "/SFX/item2.ogg", "/SFX/item3.ogg", "/SFX/item4.ogg", "/SFX/item5.ogg", "/SFX/item6.ogg" });
	private static final SoundEffect pegarMochilaSFX = new SoundEffect("/SFX/mochila.ogg");
	private static final RandomSFXGrupo equipSFX 	 = new RandomSFXGrupo(new String[] { "/SFX/equip1.ogg", "/SFX/equip2.ogg", "/SFX/equip3.ogg", "/SFX/equip4.ogg", "/SFX/equip5.ogg" });
	
	//Listener
	private InputListener input;
	
	public Player(int x, int y) throws SlickException {
		super(WIDTH_HITBOX, HEIGHT_HITBOX, HEIGHT_SPRITE, 100, 10, 0, 1.5, 1);

		getPlayer = this;
		this.x = x;
		this.y = y;
		this.angulo = 0;
		
		sprites = new Image("Sprites/playerBitmap.png");

		parado 		  = new AnimaçaoSprite(Util.carregarArrayBI(sprites, 0, 0, 30, 55, 11), 30, grupoAni);
		esquerda	  = new AnimaçaoSprite(Util.carregarArrayBI(sprites, 30, 55, 30, 55, 10), 8, grupoAni);
		esquerdaCima  = new AnimaçaoSprite(Util.carregarArrayBI(sprites, 30, 110, 30, 55, 10), 8, grupoAni);
		cima 		  = new AnimaçaoSprite(Util.carregarArrayBI(sprites, 0, 165, 30, 55, 4), 30, grupoAni);
		cimaDireita   = new AnimaçaoSprite(Util.carregarArrayBI(sprites, 0, 220, 30, 55, 4), 30, grupoAni);
		direita       = new AnimaçaoSprite(Util.carregarArrayBI(sprites, 0, 275, 30, 55, 4), 30, grupoAni);
		direitaBaixo  = new AnimaçaoSprite(Util.carregarArrayBI(sprites, 0, 330, 30, 55, 4), 30, grupoAni);
		baixo         = new AnimaçaoSprite(Util.carregarArrayBI(sprites, 0, 385, 30, 55, 4), 30, grupoAni);
		baixoEsquerda = new AnimaçaoSprite(Util.carregarArrayBI(sprites, 0, 440, 30, 55, 4), 30, grupoAni);

		grupoAni.setOffXBrutoAll(-5);
		grupoAni.setOffYAll(1, -2);
		grupoAni.setOffYAll(2, -1);

		cima.setOffYBruto(-2);
		baixo.setOffYBruto(4);
		direitaBaixo.setOffYBruto(4);
		baixoEsquerda.setOffYBruto(4);

		//esquerdaCima.setScale(4);
		esquerda.setOffYBruto(2);
		esquerda.setOffY(0, -2);
		esquerda.setOffY(1, -1);
		esquerda.setOffY(3, -1);
		esquerda.setOffY(4, -2);
		esquerda.setOffY(5, -2);
		esquerda.setOffY(6, -1);
		esquerda.setOffY(8, -1);
		esquerda.setOffY(9, -2);

		esquerdaCima.setOffXBruto(-6);
		esquerdaCima.setOffYBruto(2);
		esquerdaCima.setOffY(0, -2);
		esquerdaCima.setOffY(1, -1);
		esquerdaCima.setOffY(3, -1);
		esquerdaCima.setOffY(4, -2);
		esquerdaCima.setOffY(5, -2);
		esquerdaCima.setOffY(6, -1);
		esquerdaCima.setOffY(8, -1);
		esquerdaCima.setOffY(9, -2);
		//grupoAni.setOffYAll(3, -2);

		parado.clearOffY();
		parado.setOffYBruto(0);
		
		luz = new Luz(this, 150, 255, 223, 100, 100, 200);

		varGLuz = new Variator(new VariatorNumero() {
			public void setNumero(double numero) {
				if (numero > 255) numero = 255;
				if (numero < 0) numero = 0;
				luz.setRGB(luz.getRed(), (int) numero, luz.getBlue());
			}

			public double getNumero() {
				return luz.getGreen();
			}

			public boolean devoContinuar() {
				return Luz.todasLuz.contains(luz);
			}
		});
		
		initTimers();
		initListener();
		prontoParaPintar();
		
	}

	private void initTimers() {
		timerAtaque = new Timer(getVelAtkTickDelay()) {
			public void acao() {
				isAtkAReady = true;
				
			}
		};
		
	}

	public void updateMob() {
		grupoAni.updateAll(this);
		checaVivo();
		checaMove();
		move();


	}

	protected RandomSFXGrupo getTomarHitSFX() {
		return Player.tomarHitSFX;
	}


	protected boolean checaVivo() {
		if (!super.checaVivo()) {
			Principal.isRodando = false;
			varGLuz.desativar();
			
			return false;
		}
		return true;
	}

	public int quantosItens() {
		int quantos = 0;

		for (int i = 0; i < mochila.getSize(); i++) {
			if (mochilaItens.get(i) != null) {
				quantos++;
			}
		}

		return quantos;
	}

	public void equipar(Equip equip) {
		if (equips.get(equip.getID()) == null) {
			equips.put(equip.getID(), equip);
			equipSFX.play();


		} else {
			System.out.println("Voce ja tem um equip no " + equip.getID());
		}
	}

	public void desequipar(String id) {
		equips.put(id, null);
	}

	public synchronized void pegarItem(Item item) {
		if (item instanceof Container) {
			if (mochila == null || mochila.getSize() < ((Container) item).getSize()) {

				pegarMochilaSFX.play();
				mochila = ((Container) item);
				item.removerDoChao();
				return;
			}


		}  else {
			if (quantosItens() < mochila.getSize()) {

				for (int i = 0; i < mochila.getSize(); i++) {
					if (mochilaItens.get(i) == null) {
						mochilaItens.put(i, item);
						break;
					}
				}

				item.removerDoChao();
				pegarItemSFX.play();
			}
		}

		System.out.println(mochilaItens);
	}



	public String toString() {
		return "Player";
	}


	private void checaMove() {
		isAndando = true;

		if (isCima) {
			if (isDireita) {
				angulo = 45;
				grupoAni.stopAll(cimaDireita);
				cimaDireita.start(this);
				return;
			}

			if (isEsquerda) {
				grupoAni.stopAll(esquerdaCima);
				esquerdaCima.start(this);
				angulo = 135;
				return;
			}

			grupoAni.stopAll(cima);
			cima.start(this);
			angulo = 90;
			return;
		}

		if (isBaixo) {
			if (isDireita) {
				grupoAni.stopAll(direitaBaixo);
				direitaBaixo.start(this);
				angulo = 315;
				return;
			}

			if (isEsquerda) {
				grupoAni.stopAll(baixoEsquerda);
				baixoEsquerda.start(this);
				angulo = 225;
				return;
			}

			grupoAni.stopAll(baixo);
			baixo.start(this);
			angulo = 270;
			return;
		}

		if (isDireita) {
			grupoAni.stopAll(direita);
			direita.start(this);
			angulo = 360;
			return;
		}

		if (isEsquerda) {
			grupoAni.stopAll(esquerda);
			esquerda.start(this);
			angulo = 180;
			return;
		}

		grupoAni.stopAll(parado);
		parado.start(this);
		angulo = PARADO;
		isAndando = false;
	}

	private void move() {
		if (isAndando && !isGrounded) {
			x += Math.cos(Math.toRadians(angulo)) * speed;
			y -= Math.sin(Math.toRadians(angulo)) * speed;
		}

		if (x < 0) {
			x = 0;
		}

		if (x > (GameWindow.getWidthLQ() - width)) {
			x = (GameWindow.getWidthLQ()- width);
		}

		if (y < 0) {
			y = 0;
		}

		if (y > (GameWindow.getHeightLQ() - height)) {
			y = (GameWindow.getHeightLQ() - height);
		}

	}
	
	private void initListener() {
		
		if (input != null) {
			ListenerManager.removeInputListener(input);
		}
		
		ListenerManager.addInputListener(new InputListener() {
			public void update(Input input) {
				if (input.isKeyDown(Input.KEY_W)) {
					isCima = true;
				} else {
					isCima = false;
				}

				if (input.isKeyDown(Input.KEY_S)) {
					isBaixo = true;
				} else {
					isBaixo = false;
				}

				if (input.isKeyDown(Input.KEY_D)) {
					isDireita = true;
				} else {
					isDireita = false;
				}

				if (input.isKeyDown(Input.KEY_A)) {
					isEsquerda = true;
				} else {
					isEsquerda = false;
				}
				
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					autoAtk();
				}
				
				
			}

		});
	}
	
	private void autoAtk() {
		
		if (isAtkAReady) {
			double anguloAtk = Math.toDegrees(Math.atan2(ListenerManager.xMouse() - getXCentro(), ListenerManager.yMouse() - getYSpriteCentro())) - 90;
			if (anguloAtk < 0) {
				anguloAtk += 360;
			}
			new AtkPlayer(anguloAtk, false);
			isAtkAReady = false;
			if (!timerAtaque.isRodando()) timerAtaque.reset();
		}
		
		
		
	}


	public void pintarMob(GraphicsM g) {
		grupoAni.pintarAll(g);
		

		//alcance
		//g.drawOval(getX() - (getAlcance() - width / 2), getYSpriteCentro() - (getAlcance() - height / 2), getAlcance() * 2, getAlcance() * 2);


	}

	public Luz getLuz() {
		return luz;
	}

	@Override
	public Color getCor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAlcance() {
		return 150;
	}


}
