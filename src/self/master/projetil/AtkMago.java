package self.master.projetil;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import self.master.graphics.GraphicsM;
import self.master.graphics.animaçao.Animaçao;
import self.master.graphics.light.Luz;
import self.master.mob.Mago;
import self.master.mob.Player;
import self.master.particula.CriadorDeParticulas;



public class AtkMago extends AutoAtkProjetil {
	private Animaçao ani;

	private CriadorDeParticulas particulas;	

	public AtkMago(Mago mago, double angulo, boolean isFurioso) {
		super(mago, angulo);

		if (isFurioso) {
			this.speed = 3;
			this.width = 8;
			this.height = 8;
			particulas = new CriadorDeParticulas(getX(), getY(), width, height, 1, 1, Color.magenta, 30);
			particulas.setAlphaVar(15);
			particulas.setComLuz(4, new Color(160, 32, 240), 100, 5, 10);
		} else {
			this.speed = 3;
			this.width = 5;
			this.height = 5;
			particulas = new CriadorDeParticulas(getX(), getY(), width, height, 1, 1, Color.magenta, 20);
			particulas.setAlphaVar(20, 5);
			particulas.setComLuz(4, new Color(160, 32, 240), 50, 5, 10);
		}

		
		particulas.setProduzindo(true);
		particulas.setSpeed(0);
		particulas.addColor(new Color(160, 32, 240));
		
		
		ani = new Animaçao(getXCentro(), getYCentro(), Animaçao.particulaEsfericaImgs, 2);
		if (isFurioso) {
			ani.setScale(.225f);
		} else {
			ani.setScale(.2f);
		}
		
		ani.setLoop(true);
		ani.setAutoPaint(false);
		ani.setSeguindo(this);
		ani.start();
		
		new Luz(this, 40, 255, 0, 255, 50, 50);

	}

	protected void updateResto() {
		particulas.update(getX(), getY());
	}


	protected void colisao() {
		Player.getPlayer().tomarHit(dano);
		Player.getPlayer().setLentoTrue(100, 25);
		particulas.setProduzindo(false);
	}
	
	protected void removeProjetil() {
		super.removeProjetil();
		ani.stop();
	}

	public void pintar(GraphicsM g) {
		if (ani != null) ani.pintarManual(g);
	}

}
