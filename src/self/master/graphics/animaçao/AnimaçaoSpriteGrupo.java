package self.master.graphics.anima�ao;

import java.awt.Graphics2D;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import self.master.graphics.GraphicsM;
import self.master.mob.Mob;

public class Anima�aoSpriteGrupo {
	private ArrayList<Anima�aoSprite> anis = new ArrayList<Anima�aoSprite>();
	
	public Anima�aoSpriteGrupo() {
		
	}
	
	public void add(Anima�aoSprite ani) {
		anis.add(ani);
	}
	
	public void remove(Anima�aoSprite ani) {
		anis.remove(ani);
	}
	
	public void setOffXBrutoAll(int off) {
		for (int i = 0; i < anis.size(); i++) {
			anis.get(i).setOffXBruto(off);
		}
	}
	
	public void setOffYBrutoAll(int off) {
		for (int i = 0; i < anis.size(); i++) {
			anis.get(i).setOffYBruto(off);
		}
	}
	
	public void setOffXAll(int index, int off) {
		for (int i = 0; i < anis.size(); i++) {
			anis.get(i).setOffX(index, off);
		}
	}
	
	public void setOffYAll(int index, int off) {
		for (int i = 0; i < anis.size(); i++) {
			anis.get(i).setOffY(index, off);
		}
	}
	
	public void stopAll() {
		for (int i = 0; i < anis.size(); i++) {
			if (anis.get(i).isAtivo()) {
				anis.get(i).stop();
			}
		}
	}
	
	public void stopAll(Anima�aoSprite ani) {
		for (int i = 0; i < anis.size(); i++) {
			if (anis.get(i).isAtivo() && anis.get(i) != ani) {
				anis.get(i).stop();
			}
		}
	}
	
	public void updateAll(Mob mob) {
		for (int i = 0; i < anis.size(); i++) {
			anis.get(i).update(mob);
		}
	}
	
	public void pintarAll(GraphicsM g) {
		for (int i = 0; i < anis.size(); i++) {
			anis.get(i).pintar(g);
		}
	}
}
