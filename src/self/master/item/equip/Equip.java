package self.master.item.equip;


import org.newdawn.slick.Image;

import self.master.item.Item;
import self.master.tools.Util;

public abstract class Equip extends Item{
	protected String ID;
	
	public Equip(int x, int y, int width, int height, String ID) {
		super(x, y, width, height);
		this.ID = ID;
	}
	public static Image armorSheet = Util.carregarImg("/Sprites/armor2.png");
	public static final String CABEÇA = "cabeça";
	public static final String PEITO = "peito";
	public static final String PERNA = "perna";
	public static final String PE = "pe";
	
	public String getID() {
		return ID;
	}
	
	public int getVida() {
		return 0;
	}
	
	public int getDano() {
		return 0;
	}
	
	public int getDefesa() {
		return 0;
	}
	
	public double getSpeed() {
		return 0;
	}
	
	public double getVelAtk() {
		return 0;
	}
	
	public float getManaRegen() {
		return 0;
	}
	
	public int getMana() {
		return 0;
	}

	public abstract Image getImg();
}
