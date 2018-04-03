package self.master.gui.menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MenuTeste1 extends Menu {

	public MenuTeste1() {
		super(0, 0, 50, 50, KeyEvent.VK_1, "Menu1", 2);
		setPrioridade(2);
	}

	@Override
	public void init() {
	}

	@Override
	protected void pintarMenu(Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.fillRect(getX(), getY(), width, height);
		
		g.setColor(Color.BLACK);
		g.drawString(nome, getXCentro(), getYCentro());
	}

	@Override
	protected void updateMenu() {
	}

	@Override
	protected void abrirMenu() {
		
	}

	@Override
	protected void fecharMenu() {
		
	}

}
