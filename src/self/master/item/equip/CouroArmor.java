package self.master.item.equip;


import org.newdawn.slick.Image;

import self.master.graphics.GraphicsM;
import self.master.tools.Tradutor;


public class CouroArmor extends Equip{
	private static final Image img = Equip.armorSheet.getSubImage(32, 0, 32, 32);

	public CouroArmor(int x, int y) {
		super(x, y, 32, 32, Equip.PEITO);
	}

	@Override
	public Image getImg() {
		return img;
	}

	@Override
	protected void updateResto() {	
	}

	@Override
	public String toString() {
		return Tradutor.getText("ITEN9");
	}

	@Override
	public String getText() {
		return "Uma armadura feita da pele de um forte animal. Defesa +3. Speed -0.1";
	}
	
	public double getSpeed() {
		return -.1;
	}
	
	public int getDefesa() {
		return 3;
	}

	@Override
	public void pintar(GraphicsM gLQ) {
		
	}

}
