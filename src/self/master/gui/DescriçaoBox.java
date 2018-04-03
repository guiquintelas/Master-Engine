package self.master.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import self.master.input.ListenerManager;
import self.master.principal.Dimensional;
import self.master.principal.GameWindow;
import self.master.tools.Util;

public class Descri�aoBox extends Dimensional {
	
	private static final float ALPHA = 0.8f;
	private static final int MAX_WIDTH_LINHA = 300;
	private static final Color COR_FUNDO = new Color(238, 221, 130);
	private static final Font font = Util.carregarFonte("Bookman Old Style", Util.BOLD, 12);
	
	private static int x;
	private static int y;
	
	private static int width;
	private static int height;
	
	private static boolean isVisivel = false;
	
	private static ArrayList<String> linhas = new ArrayList<String>();
	public static String descri�ao;
	
	public static void criar(String descri�ao) {
		if (Descri�aoBox.descri�ao == descri�ao) {
			updateXY();
		} else {
			Descri�aoBox.descri�ao = descri�ao;		
			setWidthHeight();
			updateXY();
		}
		
		setVisible(true);
	}
	
	public static String getDescri�ao() {
		return descri�ao;
	}
	
	
	private synchronized static void updateXY() {
		x = (int)ListenerManager.xMouse();
		y = (int)(ListenerManager.yMouse() - GameWindow.getHeightHQ()- height);	
	}

	private static void setWidthHeight() {
		linhas.clear();
		
		if (font.getWidth(descri�ao) < MAX_WIDTH_LINHA) {
			width = font.getWidth(descri�ao) + 10;
			height = 20;
			linhas.add(descri�ao);
			return;
		}
		
		
		ArrayList<String> palavras = new ArrayList<String>();
		String linhaAtual = "";
		
		while (descri�ao.length() > 0) {
			
			if (!descri�ao.contains(" ")) {
				palavras.add(descri�ao);
				descri�ao = "";
				
			} else {
				palavras.add(descri�ao.substring(0, descri�ao.indexOf(" ") + 1));
				descri�ao = descri�ao.substring(descri�ao.indexOf(" ") + 1, descri�ao.length());
				
			}
			
		}
		
		
		while (!palavras.isEmpty()) {
			String linhaAtualTemp = "";
			
			while(true) {				
				if (palavras.isEmpty()) break;
				
				linhaAtualTemp = linhaAtualTemp.concat(palavras.get(0));

				if (font.getWidth(linhaAtualTemp) <= MAX_WIDTH_LINHA) {
					linhaAtual = linhaAtualTemp;
				} else {
					break;
				}
				
				palavras.remove(0);
			}
			
			linhas.add(linhaAtual);			
		}
		
		width = MAX_WIDTH_LINHA;	
		height = linhas.size() * 20;		
		
		if ((x + width) > GUI.WIDTH) {
			x -= width;
		}
	}
	
	public synchronized static void setVisible(boolean visivel) {
		Descri�aoBox.isVisivel = visivel;
	}
	
	public static boolean isVisivel() {
		return isVisivel;
	}
	
	public synchronized static void pintarNaGUI(Graphics gGUI) {
		if (!isVisivel) return;
		gGUI.setColor(COR_FUNDO);
		gGUI.setColor(new Color(1,1,1,ALPHA));
		//gGUI.fillRect(x, y, width, height);
		gGUI.fillRoundRect(x, y, width, height, 7, 7);
		gGUI.setColor(new Color(1,1,1,1));
		
		gGUI.setColor(Color.black);
		//gGUI.drawRect(x, y, width, height);
		gGUI.drawRoundRect(x, y, width, height, 7, 7);
		
		gGUI.setColor(Color.black);
		gGUI.setFont(font);
		for (int i = 0; i < linhas.size(); i++) {
			gGUI.drawString(linhas.get(i), x + 5, y + (20 * (i + 1) - 5));
		}
 	}
	
	public synchronized static void pintarNoJogo(Graphics g) {		
		if (!isVisivel) return;
		
		g.setColor(COR_FUNDO);
		g.setColor(new Color(1,1,1,ALPHA));
		//g.fillRect(x,GameWindow.HEIGHT + y, width, height);
		g.fillRoundRect(x, GameWindow.getHeightHQ() + y, width, height, 7,7);
		g.setColor(new Color(1,1,1,1));
		
		g.setColor(Color.black);
		//g.drawRect(x, GameWindow.HEIGHT + y, width, height);
		g.drawRoundRect(x, GameWindow.getHeightHQ() + y, width, height, 7, 7);
		
		g.setColor(Color.black);
		g.setFont(font);
		for (int i = 0; i < linhas.size(); i++) {
			g.drawString(linhas.get(i), x + 5, GameWindow.getHeightHQ() + y + (20 * (i + 1) - 5));
		}
 	}
	
	
}
