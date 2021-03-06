package self.master.mob;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import self.master.audio.RandomSFXGrupo;
import self.master.graphics.GraphicsM;
import self.master.graphics.animašao.Animašao;
import self.master.graphics.light.Luz;
import self.master.map.TileMap;
import self.master.particula.CriadorDeParticulas;
import self.master.particula.Particula;
import self.master.particula.ParticulaAnimation;
import self.master.principal.DimensionalObj;
import self.master.principal.Game;
import self.master.principal.GameWindow;
import self.master.principal.Principal;
import self.master.tools.ActionQueue;
import self.master.tools.Util;
import self.master.tools.Variator;
import self.master.tools.VariatorNumero;

public abstract class Mob extends DimensionalObj {

	public int heightSprite;

	// atributos
	protected double angulo;
	private int vida;
	private int vidaMax;
	protected double speed;
	private double speedMax;
	protected int foršaPorcentagemLento = 0;
	private int dano;
	private int defesa;
	private double velAtk;

	// booleanas de condišao
	protected boolean isAndando = false;
	protected boolean isInvuKnockBack = false;
	protected boolean isAtkAReady = true;
	protected boolean isGrounded = false;
	protected boolean isLento = false;
	protected boolean isEnvenenado = false;
	protected boolean isInvisivel = false;
	protected boolean isBuffDano = false;
	protected boolean isFire = false;
	public boolean colidindoWall = false;

	//Fogo
	protected int fireSpread;
	protected int fireDano;
	protected int fireTick;
	protected boolean firePronto = false;

	// constantes
	private static final int DELAY_INVU_KNOCKBACK = 10;
	private static final int DELAY_KB_ANI = 5;
	public static final int DELAY_VENENO_TICK = 75;
	public static final int DELAY_FOGO_TICK = 100;
	private static final int DELAY_INVI_PAR = 65;


	// timers
	protected Timer timerInvuKB;
	protected Timer timerAtk;
	protected Timer timerGround;
	protected Timer timerKnockBackAni;
	protected Timer timerLento;
	protected Timer timerVeneno;
	private Timer timerGroundedAni;
	protected Timer timerInvisivel;
	protected Timer timerBuffDano;
	protected Timer timerFire;

	// criadores de particula
	protected CriadorDeParticulas particulasGrounded;
	protected CriadorDeParticulas particulasInvisivel;
	protected CriadorDeParticulas particulasEnvenenado;
	protected CriadorDeParticulas particulasBuffForša;
	protected CriadorDeParticulas particulaTomarDano;

	protected CriadorDeParticulas particulasFogo;
	protected CriadorDeParticulas particulasFumaša;
	protected CriadorDeParticulas particulasFagulha;

	//Luz
	protected Luz luzFogo;

	public static ArrayList<Mob> todosMobs = new ArrayList<Mob>();

	// SFX
	private static final RandomSFXGrupo tomarHitSFX = new RandomSFXGrupo(new String[] { "/SFX/hit2.ogg", "/SFX/hit3.ogg", "/SFX/hit4.ogg" });
	public static final RandomSFXGrupo groundedSFX = new RandomSFXGrupo(new String[] { "/SFX/groundedSfx1.ogg", "/SFX/groundedSfx2.ogg", "/SFX/groundedSfx3.ogg", });
	protected static final RandomSFXGrupo healSFX = new RandomSFXGrupo(new String[] { "/SFX/heal1.ogg", "/SFX/heal2.ogg", "/SFX/heal3.ogg", });
	private static final RandomSFXGrupo envenenadoSFX = new RandomSFXGrupo(new String[] { "/SFX/envenenado.ogg", "/SFX/envenenado2.ogg", "/SFX/envenenado3.ogg" });

	// Sprites
	private static ArrayList<Image> groundedSprite = carregarBitmapGrounded();
	private Image spriteGroundedAtual;

	public Mob(int width, int height, int heightSprite, int vida, int dano, int defesa, double speed, double velAtk) {
		todosMobs.add(this);
		this.width = width;
		this.height = height;
		if (heightSprite == 0) {
			this.heightSprite = height;
		} else {
			this.heightSprite = heightSprite;
		}
		setVida(vida);
		this.vidaMax = vida;
		this.dano = dano;
		this.defesa = defesa;
		setSpeed(speed);
		this.velAtk = velAtk;


		particulasInvisivel = new CriadorDeParticulas(getX(), getYSprite(), width, this.heightSprite, Util.removeFirsts(Animašao.fumašaImgs, 3), .4f, 3, 50);
		//particulasInvisivel = new CriadorDeParticulas(getX(), getYSprite(), with, heightSprite, 2, 6, new Color(190,190,190), 100);
		//particulasInvisivel.setContorno(true);
		particulasInvisivel.setAlphaVar(45, 10);

		particulasEnvenenado = new CriadorDeParticulas(getX(), getYSprite(), width, 5, 3, 3, new Color(0, 100, 0), 10);
		particulasEnvenenado.setContorno(true);
		particulasEnvenenado.setSeguindo(true, this);
		particulasEnvenenado.setAngulo(90, 30);
		particulasEnvenenado.setAlphaVar(75, 30);
		particulasEnvenenado.addColor(new Color(85, 107, 47));

		particulasBuffForša = new CriadorDeParticulas(getX(), getYSprite(), width, this.heightSprite, 1, 7, new Color(210, 105, 30), 30);
		particulasBuffForša.setSeguindo(true, this);

		particulasFogo = new CriadorDeParticulas(getX(), getY(), width, this.heightSprite, Animašao.fogoImgs, .2f, 3, (width * this.heightSprite) / 25);
		particulasFogo.setSpeed(.5);
		particulasFogo.setAngulo(90, 20);
		particulasFogo.addColor(Color.yellow);
		particulasFogo.addColor(Color.orange);
		particulasFogo.addColor(Color.red);
		particulasFogo.setComLuz(7, null, 70, 5, 5);
		particulasFogo.addColorLuz(Color.yellow);
		particulasFogo.setAlphaDelay(40);
		particulasFogo.setAlphaVar(20, 20);
		particulasFogo.setParAni(new ParticulaAnimation() {
			public void setAnimation(final Particula par) {
				Variator varG = new Variator(new VariatorNumero() {
					public void setNumero(double numero) {
						if (numero > 255) numero = 255;
						if (numero < 0) numero = 0;
						par.luz.setRGB(255, (int) numero, 0);
					}

					public double getNumero() {
						return par.luz.getGreen();
					}

					public boolean devoContinuar() {
						return Luz.todasLuz.contains(par.luz);
					}
				});

				varG.fadeOut(255, 100, 50);
				varG.variar(true);

			}
		});
		//particulasFogo.setSeguindo(true, this);

		particulasFumaša = new CriadorDeParticulas(getX(), getYSprite(), width, this.heightSprite, Animašao.fumašaEscuraImgs, .6f, 3, (width * this.heightSprite) / 80);
		particulasFumaša.setSpeed(.25);
		particulasFumaša.setAngulo(90, 20);
		particulasFumaša.setAlphaDelay(30);

		particulasFagulha = new CriadorDeParticulas(getX(), getYSprite(), width, this.heightSprite, 1, 1, Color.orange, (width * this.heightSprite) / 110);
		particulasFagulha.setSpeed(2.5);
		particulasFagulha.setAngulo(90, 40);
		particulasFagulha.setGravidadeRateVar(.05f, .01f);
		particulasFagulha.setComLuz(2, Color.orange, 100, 5, 5, false, true, 0, 0);
		particulasFagulha.setAlphaVar(50, 10);

		luzFogo = new Luz(this, this.heightSprite, 255, 200, 20, 60, 25, false, false, 0, 0);
		luzFogo.setAtiva(false);

	}

	private static ArrayList<Image> carregarBitmapGrounded() {
		ArrayList<Image> arrayCarregado = new ArrayList<Image>();
		Image bitmap = null;
		try {
			bitmap = new Image("Sprites/groundedAni.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int x = 0; x < bitmap.getWidth(); x += 57) {
			arrayCarregado.add(bitmap.getSubImage(x, 0, 57, 68));
		}

		return arrayCarregado;
	}

	private void updateSpriteGrounded(final int durašao) {
		if (isGrounded) {

			if (timerGroundedAni != null) {
				if (timerGroundedAni.isRunning()) {
					return;
				}
			}

			timerGroundedAni = new Timer(5, new ActionListener() {
				int tickInicial = Principal.tickTotal;
				int tickAtual = Principal.tickTotal;
				int indexAtual = 0;

				public void actionPerformed(ActionEvent e) {
					if (Principal.tickTotal >= tickAtual + 2) {

						spriteGroundedAtual = groundedSprite.get(indexAtual);

						if (Principal.tickTotal - tickInicial >= durašao - 10) {
							indexAtual--;
							if (indexAtual < 0) {
								indexAtual = 0;
							}
						} else {
							indexAtual++;
							if (indexAtual > 5) {
								indexAtual = 5;
							}
						}
						tickAtual += 2;
					}

					if (!isGrounded) {
						timerGroundedAni.stop();
					}

				}
			});
			timerGroundedAni.start();

		}
	}

	protected RandomSFXGrupo getTomarHitSFX() {
		return tomarHitSFX;
	}

	protected void setSpeed(double speed) {
		this.speedMax = speed;
		this.speed = speed;
	}

	protected void setInvisivel(boolean isInvisivel) {
		this.isInvisivel = isInvisivel;
	}

	public boolean isInvisivel() {
		return isInvisivel;
	}

	public int getYSprite() {
		return (getY() + height) - heightSprite;

	}

	public int getYSpriteCentro() {
		return getYSprite() + heightSprite / 2;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	public void setDefesa(int defesa) {
		this.defesa = defesa;
	}

	public void setVidaMax(int vidaMax) {
		this.vidaMax = vidaMax;
	}

	public void setVelAtkMax(double velAtk) {
		this.velAtk = velAtk;
	}

	public int getDefesa() {
		return defesa;
	}

	public double getVelAtk() {
		return velAtk;
	}

	public double getSpeed() {
		return speed;
	}

	public int getVidaMax() {
		return vidaMax;
	}

	public final int getDefesaCrua() {
		return defesa;
	}

	public final int getDanoCru() {
		return dano;
	}

	public final double getVelAtkCru() {
		return velAtk;
	}

	public final double getSpeedMaxCru() {
		return speedMax;
	}

	public final int getVidaMaxCrua() {
		return vidaMax;
	}

	public boolean isVivo() {
		if (todosMobs.contains(this)) {
			return true;
		} else {
			return false;
		}
	}

	protected Color sangueCor() {
		return Color.red;
	}

	public boolean isGrounded() {
		return isGrounded;
	}

	public boolean isLento() {
		return isLento;
	}

	public boolean isEnvenenado() {
		return isEnvenenado;
	}

	public boolean isBuffDano() {
		return isBuffDano;
	}

	public double getSpeedMax() {
		return speedMax;
	}

	public int getForšaPorcentagemLento() {
		return foršaPorcentagemLento;
	}

	protected int getVelAtkTickDelay() {
		return (int) (Game.FPS / getVelAtk());

	}

	protected boolean checaForaCampo() {
		boolean isForadoCampo = false;

		if (x < 0) {
			x = 0;
			isForadoCampo = true;
		}

		if (x > (GameWindow.getWidthLQ() - width)) {
			x = (GameWindow.getWidthLQ() - width);
			isForadoCampo = true;
		}

		if (y < 0) {
			y = 0;
			isForadoCampo = true;
		}

		if (y > (GameWindow.getHeightLQ() - height)) {
			y = (GameWindow.getHeightLQ() - height);
			isForadoCampo = true;
		}

		return isForadoCampo;
	}

	public int getDanoVar() {
		int danoJaVariado = (getDano() - 2) + (int) (Math.random() * 4);
		if (danoJaVariado < 0) danoJaVariado = 0;
		return danoJaVariado;
	}

	public int tomarHit(int hit) {
		int hitDefesa = hit - getDefesa();
		if (hitDefesa < hit / 3) hitDefesa = hit / 3;

		setVida(vida - hitDefesa);
		System.out.println(toString() + " tomou " + hitDefesa + " de dano");
		getTomarHitSFX().play();
		//new ModValorAnimašao(getX(), getYSprite(), hitDefesa, ModValorAnimašao.COR_DANO);
		//new ControladorSangue(getXCentro(), getYCentro(), sangueCor(), hitDefesa);

		setInvisivel(false);
		return hitDefesa;
	}

	public int tomarHitSemDefesa(int hit) {
		setVida(vida - hit);
		System.out.println(toString() + " tomou " + hit + " de dano");
		getTomarHitSFX().play();
		//new ModValorAnimašao(getX(), getYSprite(), hit, ModValorAnimašao.COR_DANO);
		//new ControladorSangue(getXCentro(), getYCentro(), sangueCor(), hit);

		setInvisivel(false);
		return hit;
	}

	public void tomarHitPassivo(int hit) {
		setVida(vida - hit);
		System.out.println(toString() + " tomou " + hit + " de dano");
		//new ModValorAnimašao(getX(), getYSprite(), hit, ModValorAnimašao.COR_DANO);
	}

	public void tomarHitFogo(int hit) {
		setVida(vida - hit);
		System.out.println(toString() + " tomou " + hit + " de dano com fogo");
		//new ModValorAnimašao(getX(), getYSprite(), hit, Color.RED);
	}

	public void setGroundedTrue(final int durašao) {
		if (!isGrounded) {
			spriteGroundedAtual = groundedSprite.get(0);
		}
		isGrounded = true;
		updateSpriteGrounded(durašao);
		TileMap.getTile(getXCentro(), getYCentro()).setGrama();

		groundedSFX.play();
		//new NovoStatusAnimašao(getX(), getYSprite(), "GROUNDED!");
		final int tickAtual = Principal.tickTotal;

		if (timerGround != null) {
			if (timerGround.isRunning()) {
				timerGround.stop();
			}
		}

		timerGround = new Timer(5, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Principal.tickTotal >= (tickAtual + durašao)) {
					isGrounded = false;
					timerGround.stop();
				}

			}
		});
		timerGround.start();
	}

	public void setInvisivel(final int durašao) {
		setInvisivel(true);

		if (timerInvisivel != null) {
			if (timerInvisivel.isRunning()) {
				timerInvisivel.stop();
			}
		}

		final int tickAtual = Principal.tickTotal;
		timerInvisivel = new Timer(5, new ActionListener() {
			int tickParInvisivel = tickAtual;

			public void actionPerformed(ActionEvent e) {
				if (!isInvisivel || !isVivo()) {
					timerInvisivel.stop();
				}

				if (Principal.tickTotal >= tickAtual + durašao) {
					setInvisivel(false);
					timerInvisivel.stop();
				}

				if (Principal.tickTotal >= tickParInvisivel + DELAY_INVI_PAR) {
					tickParInvisivel += DELAY_INVI_PAR;
					particulasInvisivel.setProduzindo(true, Util.randomInt(20, 30));
				}
			}
		});
		timerInvisivel.start();
	}

	public void setLentoTrue(final int durašao, int foršaPorcentagem) {
		if (foršaPorcentagemLento > foršaPorcentagem) {
			return;
		}

		setLento(foršaPorcentagem);
		if (this instanceof Player) {
			//new StatusLentoGUI(durašao);
		}

		if (timerLento != null) {
			if (timerLento.isRunning()) {
				timerLento.stop();
			}
		}

		final int tickAtual = Principal.tickTotal;
		timerLento = new Timer(5, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Principal.tickTotal >= (tickAtual + durašao)) {
					setLento(0);
					timerLento.stop();
				}

			}
		});
		timerLento.start();
	}

	private void setLento(int porcentagem) {
		if (porcentagem == 0) {
			this.isLento = false;
			speed = getSpeedMax();
			this.foršaPorcentagemLento = 0;

		} else {
			this.isLento = true;
			this.foršaPorcentagemLento = porcentagem;
			speed = getSpeedMax() * ((100.0 - porcentagem) / 100.0);
			//new NovoStatusAnimašao(getX(), getYSprite(), "SPEED DOWN");
		}
	}

	public void setFire(final int dano, int ticks, int spread) {
		isFire = true;
		firePronto = false;
		fireSpread = spread;
		fireTick = ticks;
		this.fireDano = dano;

		particulasFogo.setProduzindo(true);
		particulasFumaša.setProduzindo(true);
		particulasFagulha.setProduzindo(true);

		luzFogo.foršaVar.clearFila();
		luzFogo.foršaVar.variar(false);
		luzFogo.setAtiva(true);
		luzFogo.foršaVar.fadeInSin(0, 60, 50);
		luzFogo.foršaVar.variar(true);
		luzFogo.foršaVar.addAcaoNaFila(new ActionQueue() {
			public boolean action() {
				luzFogo.foršaVar.oscilar(20, 100, true);
				luzFogo.foršaVar.variar(true);
				return true;
			}
		});

		if (timerFire != null && timerFire.isRunning()) {
			timerFire.stop();
		}

		final int tickFinal = Principal.tickTotal + (ticks * DELAY_FOGO_TICK);
		timerFire = new Timer(5, new ActionListener() {
			int tickAtual = Principal.tickTotal;

			public void actionPerformed(ActionEvent e) {
				if (Principal.tickTotal >= tickAtual + DELAY_FOGO_TICK) {
					tickAtual = Principal.tickTotal;

					tomarHitFogo(randomVenenoHit(dano));

					firePronto = true;
					fireSpread();

					particulasFogo.setPorcentagem(particulasFogo.getPorcentagemInicial() * 12);
					particulasFogo.setAngulo(90, 70);
					particulasFogo.setSpeed(2);
					particulasFogo.setAlphaDelay(0);

					particulasFumaša.setPorcentagem(particulasFumaša.getPorcentagemInicial() * 3);
				}

				if (Principal.tickTotal >= tickAtual + 10) {
					particulasFogo.setPorcentagem(particulasFogo.getPorcentagemInicial());
					particulasFogo.setAngulo(90, 20);
					particulasFogo.setSpeed(.5);
					particulasFogo.setAlphaDelay(40);

					particulasFumaša.setPorcentagem(particulasFumaša.getPorcentagemInicial());
				}

				if (Principal.tickTotal >= tickFinal || !isVivo() || !isFire) {
					timerFire.stop();
					particulasFogo.setProduzindo(false);
					particulasFumaša.setProduzindo(false);
					particulasFagulha.setProduzindo(false);
					isFire = false;

					luzFogo.foršaVar.variar(false);
					luzFogo.foršaVar.addAcaoNaFila(new ActionQueue() {
						public boolean action() {
							luzFogo.foršaVar.fadeOutSin(luzFogo.getForša(), 0, 50);
							luzFogo.foršaVar.variar(true);

							luzFogo.foršaVar.addAcaoNaFila(new ActionQueue() {
								public boolean action() {
									luzFogo.setAtiva(false);
									return true;
								}
							});
							return true;
						}
					});
				}
			}
		});
		timerFire.start();

	}

	public void setBuffDano(final int buff, final int durašao) {
		if (isBuffDano) return;

		this.dano += buff;
		isBuffDano = true;
		particulasBuffForša.setProduzindo(true);

		timerBuffDano = new Timer(5, new ActionListener() {
			int tickAtual = Principal.tickTotal;

			public void actionPerformed(ActionEvent e) {
				if (Principal.tickTotal >= tickAtual + durašao) {
					dano -= buff;
					isBuffDano = false;
					particulasBuffForša.setProduzindo(false);
					timerBuffDano.stop();
				}

				if (!isVivo()) {
					particulasBuffForša.setProduzindo(false);
					timerBuffDano.stop();
				}

			}
		});
		timerBuffDano.start();
	}

	public void setEnvenenadoTrue(int ticksVeneno, final int danoBase) {

		isEnvenenado = true;
		particulasEnvenenado.setProduzindo(true);
		envenenadoSFX.play();
		//new NovoStatusAnimašao(getX(), getYSprite(), "POISONED");

		if (this instanceof Player) {
			//new StatusEnvenenadoGUI(ticksVeneno * DELAY_VENENO_TICK, (Player) this);
		}

		if (timerVeneno != null) {
			if (timerVeneno.isRunning()) {
				timerVeneno.stop();
			}
		}

		final int tickFinal = Principal.tickTotal + (ticksVeneno * DELAY_VENENO_TICK);
		timerVeneno = new Timer(5, new ActionListener() {
			int tickAtual = Principal.tickTotal;

			public void actionPerformed(ActionEvent e) {
				if (Principal.tickTotal >= (tickAtual + DELAY_VENENO_TICK)) {
					tomarHitPassivo(randomVenenoHit(danoBase));
					tickAtual += DELAY_VENENO_TICK;
				}

				if (Principal.tickTotal >= tickFinal || !isVivo()) {
					particulasEnvenenado.setProduzindo(false);
					timerVeneno.stop();
				}

			}
		});
		timerVeneno.start();
	}

	private int randomVenenoHit(int danoBase) {
		int danoJaVariado = (danoBase - 2) + (int) (Math.random() * 4);
		if (danoJaVariado <= 0) danoJaVariado = 1;
		return danoJaVariado;
	}

	public void curar(int curaValor, boolean sfx) {
		int vidaAntes = vida;
		setVida(vida + curaValor);
		if (vida > getVidaMax()) {
			setVida(getVidaMax());
		}
		int mudanšaVida = vida - vidaAntes;

		if (mudanšaVida > 0) {
			System.out.println(toString() + " healou " + mudanšaVida + " de vida");
			//new ModValorAnimašao(getX(), getYSprite(), mudanšaVida, Color.GREEN);
			if (sfx) healSFX.play();
		}

	}

	public void curar(int curaValor) {
		int vidaAntes = vida;
		setVida(vida + curaValor);
		if (vida > getVidaMax()) {
			vida = getVidaMax();
		}
		int mudanšaVida = vida - vidaAntes;

		if (mudanšaVida > 0) {
			System.out.println(toString() + " healou " + mudanšaVida + " de vida");
			//new ModValorAnimašao(getX(), getYSprite(), mudanšaVida, Color.GREEN);
			healSFX.play();
		}

	}

	public void tomarKnockBack(final double anguloKB) {
		if (!isInvuKnockBack) {
			if (timerKnockBackAni != null) {
				if (timerKnockBackAni.isRunning()) {
					timerKnockBackAni.stop();
				}
			}

			if (particulaTomarDano != null) {
				if (anguloKB < 45 || anguloKB > 315) {
					particulaTomarDano.setAngulo(anguloKB + 40, 20);

				} else if (anguloKB > 135 && anguloKB < 225) {
					particulaTomarDano.setAngulo(anguloKB - 40, 20);

				} else {
					particulaTomarDano.setAngulo(anguloKB, 20);
				}
			}

			final int tickAtual = Principal.tickTotal;
			timerKnockBackAni = new Timer(5, new ActionListener() {
				int proximoTick = Principal.tickTotal + 1;

				public void actionPerformed(ActionEvent e) {
					if (Principal.tickTotal >= proximoTick) {
						x += Math.cos(Math.toRadians(anguloKB)) * 5;
						y -= Math.sin(Math.toRadians(anguloKB)) * 5;

						if (checaForaCampo() || colidindoWall) {
							colidindoWall = false;
							timerKnockBackAni.stop();
						}

						proximoTick++;
					}

					if (Principal.tickTotal >= (tickAtual + DELAY_KB_ANI)) {
						timerKnockBackAni.stop();
					}

				}
			});
			timerKnockBackAni.start();

			isInvuKnockBack = true;

			if (timerInvuKB != null) {
				if (timerInvuKB.isRunning()) {
					return;
				}
			}

			final int tickAlvo = Principal.tickTotal;
			timerInvuKB = new Timer(5, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (Principal.tickTotal >= (tickAlvo + DELAY_INVU_KNOCKBACK)) {
						isInvuKnockBack = false;
						timerInvuKB.stop();
					}
				}
			});
			timerInvuKB.start();
		}

	}

	protected boolean checaVivo() {
		if (vida <= 0) {
			todosMobs.remove(this);
			DimensionalObj.todosDimensionalObjs.remove(this);
			return false;
		} else {
			return true;
		}
	}

	public abstract Color getCor();

	public void update() {
		if (!checaVivo()) return;
		
		updateMob();
		particulasInvisivel.update(getX(), getYSprite());
		particulasEnvenenado.update(getX(), getYSprite());
		particulasBuffForša.update(getX(), getYSprite());
		particulasFogo.update(getX(), getYSprite());
		particulasFumaša.update(getX(), getYSprite());
		particulasFagulha.update(getX(), getYSprite());
	}

	private void fireSpread() {
		if (isFire && firePronto && fireSpread > 0) {
			for (int i = 0; i < Monstro.todosMontros.size(); i++) {
				Monstro ma = Monstro.todosMontros.get(i);
				if (ma == this || ma.isFire) continue;
				
				double dist = Math.sqrt(((ma.getXCentro() - getXCentro())*(ma.getXCentro() - getXCentro())) + ((ma.getYCentro() - getYCentro())*(ma.getYCentro() - getYCentro())));
				
				if (dist < height * 3) {
					ma.setFire(fireDano, fireTick, fireSpread -= 1);
					if (fireSpread == 0) return;
				}
			}
		}
		
		
	}

	protected abstract void updateMob();

	protected abstract void pintarMob(GraphicsM g);

	public void pintar(GraphicsM g) {
		if (isInvisivel && !(this instanceof Player)) return;
		pintarMob(g);
	}



	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

}
