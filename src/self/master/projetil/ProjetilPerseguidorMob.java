package self.master.projetil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.newdawn.slick.Graphics;

import self.master.graphics.GraphicsM;
import self.master.graphics.animašao.Animašao;
import self.master.mob.Player;
import self.master.principal.Principal;


public abstract class ProjetilPerseguidorMob extends Projetil{
	private double distanciaTemp;
	
	private static final int DELAY_AUTOD = 25;
	
	private Timer timerAutoD;

	public ProjetilPerseguidorMob(int xCentro, int yCentro, int dano, int withPro, int heightPro, double speed) {
		super(xCentro, yCentro, 0, dano);
		this.speed = speed;
		this.width = withPro;
		this.height = heightPro;
		
		init();
	}
	
	protected void init() {
		distanciaTemp = Math.sqrt(Math.pow(getXCentro() - Player.getPlayer().getXCentro(), 2) + Math.pow(getYCentro() - Player.getPlayer().getYCentro(), 2));
		double anguloNovo = Math.toDegrees(Math.atan2(Player.getPlayer().getXCentro() - getXCentro(), Player.getPlayer().getYCentro() - getYCentro())) - 90;
		if (anguloNovo < 0) {
			anguloNovo += 360;
		}
		angulo = anguloNovo;
	
	}

	public void updatePri() {
		updateAngulo();
		move();
		checaColisaoPlayer();
		checaAutoDestruišao();
	}

	protected void checaAutoDestruišao() {
		double novaD = Math.sqrt(Math.pow(getXCentro() - Player.getPlayer().getXCentro(), 2) + Math.pow(getYCentro() - Player.getPlayer().getYCentro(), 2));
		if (distanciaTemp + 3 < novaD && distanciaTemp < 50) {
			if (timerAutoD != null) {
				return;
			}
			
			final int tickAtual = Principal.tickTotal;
			timerAutoD = new Timer(5, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (Principal.tickTotal >= tickAtual + DELAY_AUTOD) {
						autoDestruišao();
						removeProjetil();
						timerAutoD.stop();
					}
					
				}
			});
			timerAutoD.start();
		}
		
		distanciaTemp = novaD;
	}
	
	protected void autoDestruišao() {
		Animašao.aniFumašaPadrao(getXCentro(), getYCentro());
	}

	private void move() {
		x += Math.cos(Math.toRadians(angulo)) * speed;
		y -= Math.sin(Math.toRadians(angulo)) * speed;
	}

	protected void checaColisaoPlayer() {
		if (Math.abs(getXCentro() - Player.getPlayer().getXCentro()) < ((width / 2) + (Player.getPlayer().getWidth() / 2))) {
			if (Math.abs(getYCentro() - Player.getPlayer().getYCentro()) < ((height / 2) + (Player.getPlayer().getHeight() / 2))) {
				colisao();
				removeProjetil();
			}
		}
	}
	
	protected abstract void colisao();
	
	@Override
	protected void colisaoParedeRes() {
		removeProjetil();
		
	}

	
	protected void updateAngulo() {
		// - 90 para acertar com as cordenadas do campo, 0 para direita, 90 para cima, 180 para direita e 270 para baixo
		double anguloNovo = Math.toDegrees(Math.atan2(Player.getPlayer().getXCentro() - getXCentro(), Player.getPlayer().getYCentro() - getYCentro())) - 90;
		if (anguloNovo < 0) {
			anguloNovo += 360;
		}
		
		double diferenca = anguloNovo - angulo;
		if (diferenca > 180) {
			diferenca -= 360;
		}
		
		if (diferenca < -180) {
			diferenca += 360;
		}
		
		angulo += diferenca/15;	
		if ( angulo > 360) angulo -= 360;
		if ( angulo < 0) angulo += 360;
		
	}

	public abstract void pintar(GraphicsM g);

}
