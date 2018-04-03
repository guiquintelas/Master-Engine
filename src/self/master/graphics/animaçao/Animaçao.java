package self.master.graphics.animaçao;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import self.master.graphics.GraphicsM;
import self.master.mob.Mob;
import self.master.principal.Dimensional;
import self.master.principal.Principal;
import self.master.tools.Util;

public class Animaçao extends Dimensional implements AnimaçaoInterface{
	public static final Image explosaoSrite 	   = Util.carregarImg("Sprites/explosoes.png");
	public static final Image particulaAzulSprite  = Util.carregarImg("Sprites/particulaAzul.png");
	public static final Image fumaçaSprite         = Util.carregarImg("Sprites/fumaça.png");
	public static final Image fumaçaClaraSprite    = Util.carregarImg("Sprites/fumaçaClara.png");
	public static final Image particulaEsferica    = Util.carregarImg("Sprites/particulaEsferica.png");
	public static final Image particulasHealSprite = Util.carregarImg("Sprites/heal.png");
	public static final Image magicSprite		   = Util.carregarImg("Sprites/magic.png");
	public static final Image groundedSprite 	   = Util.carregarImg("Sprites/groundedAni.png");
	public static final Image heal2Sprite 		   = Util.carregarImg("Sprites/heal2.png");
	public static final Image raiosSprite 		   = Util.carregarImg("Sprites/raios2.png");
	public static final Image groundProSprite 	   = Util.carregarImg("Sprites/groundAtkPro.png");
	public static final Image venenoGotaSprite 	   = Util.carregarImg("Sprites/venenoGota.png");
	public static final Image druidaAtk 		   = Util.carregarImg("Sprites/atkDruida.png");
	public static final Image peca1 			   = Util.carregarImg("Sprites/peça1.png");
	public static final Image peca3 			   = Util.carregarImg("Sprites/peça3.png");
	public static final Image peca4 			   = Util.carregarImg("Sprites/peça4.png");
	public static final Image fogo 				   = Util.carregarQuickImg("fogo");
	public static final ArrayList<Image> fogoImgs 			= Util.carregarArrayBI(fogo, 0, 0, 128, 128, 16);
	public static final ArrayList<Image> druidaAtkImgs  	= Util.carregarArrayBI(druidaAtk, 0, 0, 15, 15, 7);
	public static final ArrayList<Image> venenoGotaImgs 	= Util.carregarArrayBI(venenoGotaSprite, 0, 0, 12, 12, 14);
	public static final ArrayList<Image> groundProImgs 		= Util.carregarArrayBI(groundProSprite, 0, 0, 12, 12, 7);
	public static final ArrayList<Image> raiosImgs 			= Util.carregarArrayBI(raiosSprite, 0, 0, 192, 192, 12);
	public static final ArrayList<Image> groundedImgs		= Util.carregarArrayBI(groundedSprite, 0, 0, 57, 68, 6);
	public static final ArrayList<Image> magicImgs 			= Util.carregarArrayBI(magicSprite, 192, 0, 192, 192, 26);
	//public static final ArrayList<Image> particulasHealImgs = Util.carregarArrayBI(particulasHealSprite, 0, 0, 192, 192, 25);
	public static final ArrayList<Image> particulasHealImgs = Util.carregarArrayBI(heal2Sprite, 0, 0, 192, 192, 20);
	public static final ArrayList<Image> fumaçaImgs 		= Util.carregarArrayBI(Animaçao.fumaçaClaraSprite, 0, 0, 128, 128, 38);
	public static final ArrayList<Image> fumaçaEscuraImgs 	= Util.carregarArrayBI(Animaçao.fumaçaSprite, 0, 0, 128, 128, 38);
	public static final ArrayList<Image> particulaEsfericaImgs = Util.carregarArrayBI(Animaçao.particulaEsferica, 0, 0, 60, 60, 60);

	private ArrayList<Image> imgs;
	private Image imgAtual;
	
	private HashMap<Integer, AnimaçaoListener> listeners = new HashMap<Integer, AnimaçaoListener>();
	
	private int xOff = 0;
	private int yOff = 0;
	private int delay;
	private int tickAtual;

	private int index = 0;
	
	private float alpha = 1;
	private float alphaVar;
	private int indexAlvo;

	private float scale = 1;

	private boolean loop = false;
	private boolean isInvertido = false;
	private boolean isAumentando = true;
	private boolean isVaiVolta = false;
	private boolean isFadeOut = false;
	private boolean isAutoPaint = true;
	private boolean isSeguindo = false;
	private boolean isRodando = false;
	private boolean jaVoltou = false;
	private boolean jaUsouMetodo = false;
	
	private Dimensional d;
	

	

	public Animaçao(int xCentro, int yCentro, ArrayList<Image> imgs, int tickPorIndex) {
		this.x = xCentro;
		this.y = yCentro;
		this.imgs = imgs;
		this.width = imgs.get(0).getWidth();
		this.height = imgs.get(0).getHeight();
		this.delay = tickPorIndex;
		
		this.tickAtual = Principal.tickTotal;
	}

	public void start() {
		updateXY();
		todasAni.add(this);
		isRodando = true;
	}

	public void stop() {
		todasAni.remove(this);
		isRodando = false;
	}

	public synchronized void pintar(GraphicsM g) {
		if (!isAutoPaint || !isRodando) {
			return;
		}
		
		if (isSeguindo) {
			if (d instanceof Mob) {
				if (((Mob) d).isInvisivel()) {
					return;
				}
			}
		}

		if (alpha == 1) {
			g.drawImage(imgs.get(index), getX() - (int) (width * scale) / 2, getY() - (int) (height * scale) / 2, scale);
		} else {
			g.setAlpha(alpha);
			g.drawImage(imgs.get(index), getX() - (int) (width * scale) / 2, getY() - (int) (height * scale) / 2, scale);
			g.setAlphaPadrao();
		}		
	}
	
	public synchronized void pintarManual(GraphicsM gLQ) {	
		gLQ.setAlpha(alpha);
		gLQ.drawImage(imgs.get(index), getX() - (int) (width * scale) / 2, getY() - (int) (height * scale) / 2, scale);
		gLQ.setAlphaPadrao();
	}
	
	public synchronized void pintarManual(GraphicsM gLQ, double anguloRotacao) {	
//		Image imgAtualMargem = new Image((int) (width * scale) * 2, (int) (height * scale)  * 2, imgs.get(0).getType());
//		imgAtual = new Image((int) (width * scale), (int) (height * scale), imgs.get(0).getType());
//		imgAtual.getGraphics().drawImage(imgs.get(index), 0, 0, (int) (width * scale), (int) (height * scale), null);
//		
//		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(anguloRotacao), (width * scale) / 2, (height * scale) / 2);
//		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//		op.filter(imgAtual, imgAtualMargem);
//		
//		gLQ.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//		
//		gLQ.setColor(new Color(1,1,1,alpha));
//		gLQ.drawImage(imgAtualMargem, getX() - (int) (width * scale) / 2, getY() - (int) (height * scale) / 2, null);
//		gLQ.setColor(new Color(1,1,1,alpha));
	}
	
	public synchronized void pintarManual(GraphicsM g, float anguloRotacao, int xOffRotCentro, int yOffRotCentro) {	
//		Image imgAtualMargem = new Image((int) (width * scale) * 2, (int) (height * scale) *2, imgs.get(0).getType());
//		imgAtual = new Image((int) (width * scale), (int) (height * scale), imgs.get(0).getType());
//		imgAtual.getGraphics().drawImage(imgs.get(index), 0, 0, (int) (width * scale), (int) (height * scale), null);
//		imgAtualMargem.getGraphics().drawImage(imgAtual, (int)(width * scale) - imgAtual.getWidth() / 2 - xOffRotCentro, (int)(height * scale) - imgAtual.getHeight() / 2  - yOffRotCentro, null);
//		
//		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(anguloRotacao), (width * scale), (height * scale));
//		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//		
//		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		
		g.setCurrent();
		imgs.get(index).setFilter(Image.FILTER_NEAREST);
		imgs.get(index).setCenterOfRotation((width * scale)/2 + xOffRotCentro, (height * scale)/2 + yOffRotCentro);
		imgs.get(index).setRotation(anguloRotacao);
		imgs.get(index).draw(getX() - (width * scale)/2  + xOff * scale, getY() - (height * scale)/2 + yOff * scale, (width * scale),(height * scale));
	}

	public void update() {
		if (isRodando) updateSprite();
		
		if (isRodando) {	
			updateAlpha();
			updateXY();
			executarMetodo();
		} else {
			stop();
		}
		
	}

	private void updateXY() {
		if (isSeguindo) {
			this.x = d.getXCentro() + xOff;
			if (d instanceof Mob) {
				this.y = ((Mob) d).getYSpriteCentro() + yOff;
			} else {
				this.y = d.getYCentro() + yOff;
			}
			
		}
		
	}



	private void updateAlpha() {
		if (isFadeOut) {
			if (isInvertido) {
				if (index > indexAlvo) {
					return;
				}
			} else {
				if (index < indexAlvo) {
					return;
				}
			}
			
			alpha -= alphaVar;
			if (alpha < 0) {
				alpha = 0;
				stop();
			}
		}
		
	}
	
	public boolean isRodando() {
		return isRodando;
	}

	private void updateSprite() {
		if (Principal.tickTotal >= tickAtual + delay) {
			
			
			if (isAumentando) {
				index++;
			} else {
				index--;
			}

			if (index > imgs.size() - 1) {
				if (loop) {
					if (isVaiVolta) {
						index = imgs.size() - 1;
						isAumentando = false;
						jaVoltou = true;
					} else {
						index = 0;
					}
					
				} else if (isVaiVolta && !jaVoltou){
					index = imgs.size() - 1;
					isAumentando = false;
					jaVoltou = true;
				} else {
					index = imgs.size() - 1;
					stop();
					return;
				}

			}
			
			if (index < 0) {
				if (loop) {
					if (isVaiVolta) {
						index = 0;
						isAumentando = true;
						jaVoltou = false;
					} else {
						index = imgs.size() - 1;
					}
				} else if (isVaiVolta && !jaVoltou){			
					index = 0;
					isAumentando = true;					
					jaVoltou = true;
				} else {
					index = 0;
					stop();
					return;
				}

			}
			
			jaUsouMetodo = false;
			tickAtual = Principal.tickTotal;
		}
	}
	
	public static void aniFumaçaPadrao(int xCentro, int yCentro) {
		Animaçao ani = new Animaçao(xCentro, yCentro, fumaçaImgs, 6);
		ani.setScale(.6f);
		ani.setFadeOut(140);
		ani.start();
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public boolean isInvertido() {
		return isInvertido;
	}
	
	private void executarMetodo() {
		if (listeners.get(index) != null && !jaUsouMetodo) {
			listeners.get(index).metodo();
			jaUsouMetodo = true;
		}
		
	}

	public void setVoid(int index, AnimaçaoListener aniListener) {
		listeners.put(index, aniListener);
	}

	public void setInvertido(boolean invertido) {
		this.isInvertido = invertido;
		this.isAumentando = !isInvertido;
		if (isInvertido)
			index = imgs.size() - 1;

	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public int getWidth() {
		return (int)(width * scale);
	}
	
	public int getHeight() {
		return (int)(height * scale);
	}
	
	
	public int getXCentro() {
		return getX();
	}
	
	public int getYCentro() {
		return getY();
	}

	public void setVaiVolta(boolean vaiVolta) {
		this.isVaiVolta = vaiVolta;
	}
	
	public void setFadeOut(int duraçao) {
		isFadeOut = true;
		
		if (isInvertido) {
			indexAlvo = imgs.size() - 1;
		} else {
			indexAlvo = 0;
		}
		
		alpha = 1;
		alphaVar = alpha/(float)duraçao;
	}
	
	public void setFadeOut(int indexInicio, float alphaFinal) {
		isFadeOut = true;
		this.indexAlvo = indexInicio;
		alpha = 1;
		alphaVar = Math.abs(alpha - alphaFinal)/(float)((imgs.size() - 1) - indexInicio);
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public void setAutoPaint(boolean auto) {
		this.isAutoPaint = auto;
	}
	
	public void setSeguindo(Dimensional d) {
		isSeguindo = true;
		this.d = d;
	}
	
	public void setSeguindo(Dimensional d, int xOff, int yOff) {
		isSeguindo = true;
		this.d = d;
		this.xOff = xOff;
		this.yOff = yOff;
	}
}
