package self.master.projetil;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import self.master.graphics.GraphicsM;
import self.master.graphics.animaçao.Animaçao;
import self.master.graphics.light.Luz;
import self.master.mob.Player;
import self.master.particula.CriadorDeParticulas;
import self.master.tools.Util;


public class ProjetilVeneno extends ProjetilPerseguidorMob{
	private Animaçao ani;
	private static ArrayList<Image> img = Util.carregarArrayBI(Animaçao.explosaoSrite, 64, 576, 64, 64, 30);
	
	private CriadorDeParticulas particulas;

	public ProjetilVeneno(int xCentro, int yCentro, int dano) {
		super(xCentro, yCentro, dano, 10, 10,1);
		//3.5
		
		particulas = new CriadorDeParticulas(getX(), getY(), width, height, 2, 2, new Color(0, 100, 0), 20);
		particulas.setAlphaVar(25, 15);
		particulas.setSpeed(.5);
		//particulas.setProduzindo(true);
		particulas.setComLuz(7, new Color(0, 255, 0), 100, 5, 25);
		
		ani = new Animaçao(getXCentro(), getYCentro(), Animaçao.venenoGotaImgs, 8);
		ani.setScale(Util.randomInt(15, 20)/10f);
		ani.setLoop(true);
		ani.setSeguindo(this, 1, 2);
		ani.setAutoPaint(false);
		ani.start();
		
		new Luz(this, 30, 0, 255, 120, 70, 100);
	}
	
	public void updatePri() {
		super.updatePri();
		particulas.update(getX(), getY());
		particulas.setAngulo(angulo + 180);
	}

	protected void colisao() {
		Player.getPlayer().setEnvenenadoTrue(4, dano / 2);
		Player.getPlayer().setLentoTrue(125, 40);
		particulas.setProduzindo(false);
		
		new Animaçao(getXCentro(), getYCentro(), img, 4).start();
	}

	public void pintar(GraphicsM g) {
		//g.setColor(new Color(124, 252, 0));
		//g.fillOval(getX(), getY(), width, height);
		//g.setColor(new Color(0, 100, 0));
		//g.drawOval(getX(), getY(), width, height);
		ani.pintarManual(g, (float)(-angulo + 90), 0, -5);
	}
	
	protected void removeProjetil() {
		super.removeProjetil();
		ani.stop();
	}

}
