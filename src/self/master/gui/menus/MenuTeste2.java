package self.master.gui.menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MenuTeste2 extends Menu {

	public MenuTeste2() {
		super(60, 0, 50, 50, KeyEvent.VK_2, "Menu2", 1);
		setPrioridade(1);
	}

	@Override
	public void init() {
	}

	@Override
	protected void pintarMenu(Graphics2D g) {
		g.setColor(Color.RED);
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