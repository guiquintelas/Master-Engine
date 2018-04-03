package self.master.graphics.light;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import self.master.principal.Dimensional;

public class LuzBruta extends Luz{
	private BufferedImage imgLuz;
	private int[] pixelLuzABruto;
	
	public static ArrayList<Luz> todasLuzBruta = new ArrayList<Luz>();

	public LuzBruta(Dimensional d, int raio, int r, int g, int b, double forca, int fade) {
		super(d, raio, r, g, b, forca, fade);
		forçaVar.setAtivo(true);
		raioVar.setAtivo(true);
	}
	
	public LuzBruta(Dimensional d, int raio, int r, int g, int b, double forca, int fade, boolean variarRaio, boolean variarForça, int oscRaio, int oscForça) {
		super(d, raio, r, g, b, forca, fade, variarRaio, variarForça, oscRaio, oscRaio);
		forçaVar.setAtivo(true);
		raioVar.setAtivo(true);
	}
	
	protected ArrayList<Luz> getLista() {
		return todasLuzBruta;
	}
	
	protected synchronized void updateImgLuz() {
		
	}
	
	public synchronized void pintar(BufferedImage img) {		
		if (!isInit || isApagada) return;
		int getX = d.getXCentro() + xOff;
		int getY = d.getYCentro() + yOff;	
		img.getGraphics().drawImage(imgLuz, getX - raio, getY - raio , null);
	}

}
