package self.master.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class GraphicsM {
	public Graphics g;
	private Font font;
	public Font fontPadrao;
	private Color cor;
	private Color filter = new Color(1.0f, 1.0f, 1.0f);
	private float alpha = 1f;
	
	public GraphicsM(Graphics g) {
		this.g = g;
		fontPadrao = g.getFont();
	}
	
	public void setAntiAlias(boolean anti) {
		g.setAntiAlias(anti);
	}
	
	public void setFont(Font font){
		this.font = font;
		g.setFont(font);
	}
	
	public Font getFont() {
		return font;
	}
	
	public void setAlpha(float alpha) {
		if (alpha > 1) alpha /= 255.0f;
		this.alpha = alpha;
		g.setColor(cor);
	}
	
	public void setAlphaPadrao() {
		setAlpha(1);
	}
	
	public void setFilter(float r, float g, float b) {
		filter = new Color(r, g, b);
	}
	
	public void setColor(Color c) {
		cor = new Color(c.r, c.g, c.b, alpha);
		g.setColor(cor);
	}
	
	public void drawString(String texto, int x, int y) {
		setCurrent();
		g.drawString(texto, x, y);
	}
	
	public void setCurrent() {
		Graphics.setCurrent(g);
	}
	
	public void drawImage(Image img, float x, float y) {
		setCurrent();
		img.setAlpha(alpha);
		img.draw(x, y, filter);
		img.setAlpha(1);
	}
	
	public void drawImage(Image img, float x, float y, float scale) {
		setCurrent();
		img.setAlpha(alpha);
		img.draw(x, y, scale, filter);
		img.setAlpha(1);
	}
	
	public void fillRect(float x, float y, float width, float height) {
		//setCurrent();
		g.fillRect(x, y, width, height);
	}
	
	public void drawOval(float x, float y, float width, float height) {
		setCurrent();
		g.drawOval(x, y, width, height);
	}
	
	public void drawLine(float x1, float y1, float x2, float y2) {
		setCurrent();
		g.drawLine(x1, y2, x1, x2);
	}
	
	public void clearAll() {
		g.setBackground(new Color(0,0,0,0));
		g.clear();
		g.setBackground(new Color(1,1,1,1));
	}

	public void fillOval(float x, float y, float width, float height) {
		setCurrent();
		g.fillOval(x, y, width, height);
	}
	
	
	 
	
}
