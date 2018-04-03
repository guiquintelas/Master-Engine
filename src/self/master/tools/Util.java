package self.master.tools;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;





import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;



public class Util {
	public static final int BOLD = java.awt.Font.BOLD;
	public static final int PLAIN = java.awt.Font.PLAIN;
	public static final int ITALIC = java.awt.Font.ITALIC;
	
	
	public static Image carregarImg(String path) {
		System.out.println("Carregando.. " + path);
		Image img = null;
		try {
			img = new Image(path);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return img;
	}
	
	public static Image carregarQuickImg(String soNome) {
		return carregarImg("Sprites/" + soNome + ".png");
	}
	
	public static Font carregarFonte(String name, int style, int size) {
		return new TrueTypeFont(new java.awt.Font(name, style, size), false);
	}
	
	public static Color setAlpha(float alpha) {
		return new Color(1,1,1, alpha);
	}

	public static ArrayList<Image> carregarArrayBI(Image sprites, int xInicial, int yInicial, int width, int height, int quantasBI) {
		ArrayList<Image> imgs = new ArrayList<Image>();
		boolean inicial = true;

		for (int y = yInicial; y < sprites.getHeight(); y += height) {
			for (int x = 0; x < sprites.getWidth(); x += width) {
				if (inicial) {
					x = xInicial;
					inicial = false;
				}
				
				imgs.add(sprites.getSubImage(x, y, width, height));
				if (imgs.size() >= quantasBI) {
					return imgs;
				}
			}
		}
		
		System.out.println("deu ruim");
		return imgs;
	}
	
	public static ArrayList<BufferedImage> carregarArrayBIVertical(BufferedImage spriteSheet, int xInicial, int yInicial, int width, int height, int quantasBI) {
		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		boolean inicial = true;

		for (int x = xInicial; x < spriteSheet.getWidth(); x += width) {
			for (int y = 0; y < spriteSheet.getHeight(); y += height) {
				if (inicial) {
					y = yInicial;
					inicial = false;
				}
				
				imgs.add(spriteSheet.getSubimage(x, y, width, height));
				if (imgs.size() >= quantasBI) {
					return imgs;
				}
			}
		}
		
		System.out.println("deu ruim");
		return imgs;
	}
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static double randomDouble(double min, double max) {
		return min + (Math.random() * (max - min));
	}
	
	public static float randomFloat(float min, float max) {
		return min + (float)(Math.random() * (max - min));
	}

	public static int randomInt(int min, int max) {
		return min + (int) (Math.random() * (max - min) + 0.5);
	}
	
	public static int limita(int num,int min, int max) {
		if (num > max) num = max;
		if (num < min) num = min;
		
		return num;
	}
	
	public static float limita(float num,float min, float max) {
		if (num > max) num = max;
		if (num < min) num = min;
		
		return num;
	}
	
	public static double limita(double num,double min, double max) {
		if (num > max) num = max;
		if (num < min) num = min;
		
		return num;
	}
	
	public static Image copyImage(Image img) {
		Image retorna = null;
		
		try {
			retorna = new Image(img.getWidth(),img.getHeight());
			img.getGraphics().copyArea(retorna, 0, 105);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		return retorna;
	}
	
	public static double mudaX(double angulo, float speed) {
		return Math.cos(Math.toRadians(angulo)) * speed;
	}
	
	public static double mudaY(double angulo, float speed) {
		return -Math.sin(Math.toRadians(angulo)) * speed;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Image> removeFirsts(ArrayList<Image> imgs, int indexs) {
		ArrayList<Image> imgsTemp = (ArrayList<Image>)imgs.clone();
		for (int i = 0; i < indexs; i++) {
			imgsTemp.remove(0);
		}
		return imgsTemp;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<BufferedImage> removeLasts(ArrayList<BufferedImage> imgs, int indexs) {
		ArrayList<BufferedImage> imgsTemp = (ArrayList<BufferedImage>)imgs.clone();
		for (int i = 0; i < indexs; i++) {
			imgsTemp.remove(imgs.size() - 1);
		}
		return imgsTemp;
	}
	
	public static boolean compararAngulos(double angulo1, double angulo2) {
		while (angulo1 > 180)angulo1 -= 360;
		while (angulo1 < -180)angulo1 += 360;
		while (angulo2 > 180)angulo2 -= 360;
		while (angulo2 < -180)angulo2 += 360;
		
		return angulo1 > angulo2;
	}
 }
