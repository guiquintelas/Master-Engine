package self.master.item;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import self.master.audio.RandomSFXGrupo;
import self.master.graphics.light.Luz;
import self.master.input.InputListener;
import self.master.input.ListenerManager;
import self.master.principal.DimensionalObj;
import self.master.principal.GameWindow;

public abstract class Item extends DimensionalObj {
	public static final RandomSFXGrupo drinkSFX = new RandomSFXGrupo(new String[]{"/SFX/potDrink1.ogg", "/SFX/potDrink2.ogg", "/SFX/potDrink3.ogg"});
	private static final RandomSFXGrupo dropSFX = new RandomSFXGrupo(new String[]{"/SFX/drop1.ogg", "/SFX/drop2.ogg", "/SFX/drop3.ogg"});
	
	public static ArrayList<Item> todosItens = new ArrayList<Item>();
	//protected Image imgSel = criarImgSel();
	
	protected boolean isSel = false;
	protected boolean isNoChao = true;
	
	protected Luz luz;
	
	//listener
	private InputListener mousePressed;
	
	public Item(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		this.width = width; 
		this.height = height;
		if (x > GameWindow.getWidthLQ() - width) this.x = GameWindow.getWidthLQ() - width;
		if (y > GameWindow.getHeightLQ()- height) this.y = GameWindow.getHeightLQ() - height;
		
		todosItens.add(this);
		
		ListenerManager.addInputListener(mousePressed = new InputListener() {
			public void update(Input input) {
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || input.isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON) 
						|| input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
					mousePressed();
				}
				
			}
		});
		
		prontoParaPintar();
		luz = initLuz();
		
	}
	
	protected Luz initLuz() {
		return new Luz(this, 30, 200, 200, 200, 50, 100, true, false, 5, 0);
	}
	
	public final void update() {
		checaSel();
		updateResto();
	}
	
	protected void mousePressed() {
		
	}
	
	protected abstract void updateResto();
	
	public void pintar(Graphics g) {
		if (isSel) {
			//g.drawImage(getImgSel(), getX(),  getY());
		} else {
			g.drawImage(getImg(), getX(),  getY());
		}
	}
	
	public abstract String toString();
	
	public abstract String getText(); 
	
	protected void checaSel() {
		if (ListenerManager.xMouse() > getX() && ListenerManager.xMouse() < getX() + width) {
			if (ListenerManager.yMouse() > getY() && ListenerManager.yMouse() < getY() + height) {
				if (!isSel) {
					luz.forçaVar.variar(false);
					luz.forçaVar.fadeIn(luz.getForça(), 100, 25);
					luz.forçaVar.variar(true);
				}
				isSel = true;
				
				return;
			}
		}
		
		if (isSel) {
			luz.forçaVar.variar(false);
			luz.forçaVar.fadeOut(luz.getForça(), 50, 25);
			luz.forçaVar.variar(true);
		}
		isSel = false;
	}
	
	public void removerDoChao() {
		if (isNoChao) {
			todosItens.remove(this);
			DimensionalObj.todosDimensionalObjs.remove(this);
			ListenerManager.removeInputListener(mousePressed);
			isNoChao = false;
		}
		
	}
	
	public void botarNoChao(int x, int y) {
		if (!isNoChao) {
			this.x = x;
			this.y = y;
			
			if (x < 0) x = 0;
			if (y < 0) y = 0;
			if (x > GameWindow.getWidthLQ() - width) this.x = GameWindow.getWidthLQ() - width;
			if (y > GameWindow.getHeightLQ() - height) this.y = GameWindow.getHeightLQ() - height;
		
			dropSFX.play();
			todosItens.add(this);
			DimensionalObj.todosDimensionalObjs.add(this);
			ListenerManager.addInputListener(mousePressed);
			isNoChao = true;
		}
		
	}
	
	
	public static void clear() {	
		while (todosItens.size() != 0) {
			todosItens.get(0).removerDoChao();
		}
	}
	
	public abstract Image getImg();
//	
//	public final Image getImgSel() {
//		return imgSel;
//	}
	
//	private Image criarImgSel() {
//		BufferedImage imgSel = new BufferedImage(getImg().getWidth(), getImg().getHeight(), getImg().getType());
//		imgSel.getGraphics().drawImage(getImg(), 0, 0, null);
//		for (int y = 0; y < imgSel.getHeight(); y++) {
//			for (int x = 0; x < imgSel.getWidth(); x++) {
//				int cor = imgSel.getRGB(x, y);
//				int alpha = (cor>>24) & 0xff;
//				
//				if (alpha == 0) {
//					
//					
//					if (x - 1 > 0) {
//						int alphaEsquerda = (imgSel.getRGB(x - 1, y)>>24) & 0xff;
//						if (alphaEsquerda > 100) imgSel.setRGB(x - 1, y, 0xffffff00);
//					}
//					
//					if (x + 1 < imgSel.getWidth()) {
//						int alphaDireita = (imgSel.getRGB(x + 1, y)>>24) & 0xff;
//						if (alphaDireita > 100) imgSel.setRGB(x + 1, y, 0xffffff00);
//					}
//					
//					if (y + 1 < imgSel.getHeight()) {
//						int alphaBaixo = (imgSel.getRGB(x, y + 1)>>24) & 0xff;
//						if (alphaBaixo > 100) imgSel.setRGB(x, y + 1, 0xffffff00);
//					}
//					
//					if (y - 1 > 0) {
//						int alphaCima = (imgSel.getRGB(x, y - 1)>>24) & 0xff;
//						if (alphaCima > 100) imgSel.setRGB(x, y - 1, 0xffffff00);
//					}
//				} else {
//					if (x == 0 || y == 0 || x == imgSel.getWidth() - 1 || y == imgSel.getHeight() - 1) {
//						imgSel.setRGB(x, y, 0xffffff00);
//					}
//				}
//				
//			}
//		}
//		return imgSel;
//		
//	}
}
