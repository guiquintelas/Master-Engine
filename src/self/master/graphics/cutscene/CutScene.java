package self.master.graphics.cutscene;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import self.master.graphics.GraphicsM;
import self.master.input.InputListener;
import self.master.input.ListenerManager;
import self.master.principal.GameWindow;
import self.master.principal.Principal;
import self.master.tools.ActionQueue;
import self.master.tools.Util;
import self.master.tools.Variator;
import self.master.tools.VariatorNumero;

public class CutScene {
	protected int delay = 300; //minimo = delay de tran fade out
	private int index = 0;
	protected static final int DELAY_TRAN = 20;
	
	private int tickAtual;
	
	private static int alphaTran = 0;
	private Variator varAlphaTran;
	private static boolean passando = false;
	
	private static float alpha = 1;
	private Variator varAlpha;
	private static boolean fadeOut = false;
	
	private ArrayList<Slide> slides = new ArrayList<Slide>();
	
	private static final int VAZIO = -3;
	
	public static CutScene atual = null;
	private static int atualID = VAZIO;
	
	private static int idCount = 0;
	private final int id = idCount++;
	
	public CutScene(ArrayList<Image> imgs) {
		
		for (int i = 0; i < imgs.size();i++) {
			slides.add(new Slide(imgs.get(i), this));
		}
		
		initCons();
	}
	
	private void initCons() {
		
		varAlphaTran = new Variator(new VariatorNumero() {
			public void setNumero(double numero) {
				alphaTran = Util.limita((int) numero, 0, 255);			
			}
			
			public double getNumero() {
				return alphaTran;
			}
			
			public boolean devoContinuar() {				
				return atualID != VAZIO;
			}
		});
		
		varAlpha = new Variator(new VariatorNumero() {
			public void setNumero(double numero) {
				alpha = Util.limita((float) numero, 0.0f, 1.0f);			
			}
			
			public double getNumero() {
				return alpha;
			}
			
			public boolean devoContinuar() {				
				return atualID != VAZIO;
			}
		});
		
	}
	
	public static void init() {
		
		ListenerManager.addInputListener(new InputListener() {
			public void update(Input input) {
				if (input.isKeyDown(Input.KEY_C)) {
					new CutScene(ImageSnap.imgs).rodar();		
				}
				
			}
		});
	}
	
	public void addSlide(Image img) {
		slides.add(new Slide(img,this));
	}
	
	private void setAtual(CutScene cs) {
		if (cs == null) {
			atual = null;
			atualID = VAZIO;
		} else {
			atual = cs;
			atualID = cs.id;
		}
	}
	
	public void rodar() {
		if (atualID == VAZIO && slides.size() > 0) {
			alpha = 1;
			setAtual(this);
			tickAtual = Principal.tickTotal;
			System.out.println("Cutscene iniciada! " + slides.size() + " imgs")   ;
		}
	}
	
	public void parar() {
		fadeOut = true;
		varAlpha.fadeOutSin(1.0f, 0.0f, 20);
		varAlpha.variar(true);
		varAlpha.addAcaoNaFila(new ActionQueue() {
			public boolean action() {
				if (atualID == id) setAtual(null);
				fadeOut = false;
				return atualID == VAZIO;
			}
		});
		
		
	}
	
	private void updateAtual() {
		passarImgs();
		slides.get(index).update();
	}
	
	public static void update() {
			if (atualID != VAZIO && !fadeOut) {
				atual.updateAtual();
			}
	}
	
	private void passarImgs() {
		if (Principal.tickTotal >= tickAtual + delay) {
			if (index + 1 < slides.size()) {
				passarSlide();
			} else {
				parar();
			}
			
			
		}
	}
	
	private void passarSlide() {
		if (passando) return;
		passando = true;

		varAlphaTran.fadeInSin(0, 255, DELAY_TRAN);
		varAlphaTran.variar(true);
		varAlphaTran.addAcaoNaFila(new ActionQueue() {
			public boolean action() {
				index++;
				tickAtual = Principal.tickTotal;
				return true;
			}
		});
		varAlphaTran.addAcaoNaFila(new ActionQueue() {
			public boolean action() {
				varAlphaTran.fadeOutSin(255, 0, DELAY_TRAN);
				varAlphaTran.variar(true);
				return true;
			}
		});
		varAlphaTran.addAcaoNaFila(new ActionQueue() {
			public boolean action() {
				passando = false;
				return true;
			}
		});
		
		delay += DELAY_TRAN * 2;
		

	}

	private void pintarAtual(GraphicsM gHQ) {
		if (fadeOut) {
			gHQ.setAlpha(alpha);
			gHQ.drawImage(slides.get(index).img, slides.get(index).getX(), slides.get(index).getY());
			gHQ.setAlphaPadrao();
		} else {
			gHQ.drawImage(slides.get(index).img, slides.get(index).getX(), slides.get(index).getY());
		}
	}
	
	public static void pintar(GraphicsM gHQ) {
		if (atualID != VAZIO) {
			
			atual.pintarAtual(gHQ);
			if (passando) {
				gHQ.setAlpha(alphaTran);
				gHQ.fillRect(0, 0, GameWindow.getWidthHQ(), GameWindow.getHeightHQ());
				gHQ.setAlphaPadrao();
			}
			
		}
	}
	
	
}

final class Slide {
	private double x;
	private double y;
	
	private float speed;
	private double angulo;
	
	public Image img;
	
	private static boolean lado = true;
	
	public Slide(Image img, CutScene cs) {
		this.img = img;
		this.speed = Util.randomFloat(0.15f, .3f);
		
		if (lado) {
			this.angulo = Util.randomDouble(0, 180);
		} else {
			this.angulo = Util.randomDouble(180, 359);
		}
		
		lado = !lado;
		
		x = Util.mudaX(this.angulo + 180, speed * cs.delay /2);
		y = Util.mudaY(this.angulo + 180, speed * cs.delay /2);
	
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public void update() {
		mover();
	}

	private void mover() {
		x += Util.mudaX(angulo, speed);
		y += Util.mudaY(angulo, speed);
	}
	
}
