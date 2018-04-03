package self.master.graphics.light;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUseProgram;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import self.master.graphics.GraphicsM;
import self.master.gui.GUI;
import self.master.principal.GameWindow;
import self.master.shader.Shader;
import self.master.shader.ShaderProgram;
import self.master.tools.RelatorioDelay;

public class LuzManager {
	public static Image imgLuz;
	public static Graphics g;
	
	private static Image buffer;
	private static Graphics gb;
	
	private static ShaderProgram renderLuzShader;
	private static ShaderProgram pintarLuzShader;
	
	
	public static void init() throws SlickException {
		imgLuz = new Image(GameWindow.getWidthHQ(), GameWindow.getHeightHQ() - GUI.HEIGHT);
		g = imgLuz.getGraphics();
		
		buffer = new Image(GameWindow.getWidthHQ(), GameWindow.getHeightHQ() - GUI.HEIGHT);
		gb = buffer.getGraphics();	
		
		renderLuzShader = ShaderProgram.loadProgram("padraoPassar.vrt", "renderLuz.frg");
		pintarLuzShader = ShaderProgram.loadProgram("padraoPassar.vrt", "pintarLuz.frg");
		ShaderProgram.setStrictMode(false);
	}
	
	private synchronized static void resetarPixelPadrao() {		
		g.setColor(new Color(LuzAmbiente.luzARed, LuzAmbiente.luzAGreen, LuzAmbiente.luzABlue));
		g.fillRect(0, 0, imgLuz.getWidth(), imgLuz.getHeight());
		
		gb.setColor(new Color(LuzAmbiente.luzARed, LuzAmbiente.luzAGreen, LuzAmbiente.luzABlue));
		gb.fillRect(0, 0, imgLuz.getWidth(), imgLuz.getHeight());
	}
	
	public static void resetImgLuz() {
		resetarPixelPadrao();
	}
	
	public static void pintarLuz(Luz luz) throws SlickException {	
		pintarLuzShader.bind();
		
		int luzXCentro = luz.getXCentro();
		int luzYCentro = luz.getYCentro();
		int luzRaio = luz.getRaio();
		
		pintarLuzShader.setUniform1f("raio", luz.getRaio());	
		pintarLuzShader.setUniform2f("centro", luz.getXCentro(), luz.getYCentro());
		pintarLuzShader.setUniform4f("corLuz", new Color(luz.r/255.0f, luz.g/255.0f, luz.b/255.0f, (float)luz.força/100.0f));	
		
		
		
		gb.drawImage(imgLuz, luzXCentro - luzRaio,
							 luzYCentro - luzRaio,
							 luzXCentro + luzRaio,
							 luzYCentro + luzRaio,
							 luzXCentro - luzRaio,
							 luzYCentro - luzRaio,
							 luzXCentro + luzRaio,
							 luzYCentro + luzRaio);
		
		pintarLuzShader.unbind();
		glActiveTexture(GL_TEXTURE0);
		
		g.drawImage(buffer, luzXCentro - luzRaio,
							luzYCentro - luzRaio,
							luzXCentro + luzRaio,
							luzYCentro + luzRaio,
							luzXCentro - luzRaio,
							luzYCentro - luzRaio,
							luzXCentro + luzRaio,
							luzYCentro + luzRaio);
	}
	
	public static void renderLuz(GraphicsM gLQ, Image imgJogo) {
		Graphics.setCurrent(gb);
	    
	    renderLuzShader.bind();
		
		renderLuzShader.setUniform1i("luz", 4);
		glActiveTexture(GL_TEXTURE0 +4);
		glBindTexture(GL_TEXTURE_2D, imgLuz.getTexture().getTextureID());
		
		renderLuzShader.setUniform1i("tex0", 0);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, imgJogo.getTexture().getTextureID());
		
		gb.drawImage(imgJogo, 0, 0);
		
		renderLuzShader.unbind();
		glActiveTexture(GL_TEXTURE0);
		
		gLQ.drawImage(buffer, 0, 0);
	    

	}
}
