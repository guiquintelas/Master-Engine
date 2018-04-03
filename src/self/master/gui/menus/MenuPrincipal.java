package self.master.gui.menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MenuPrincipal extends Menu{

	public MenuPrincipal() {
		super(100, 100, 100, 100, KeyEvent.VK_SPACE, "Menu Principal", 3);
		setGrupoPrincipal(this);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void pintarMenu(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(getX(), getY(), width, height);
		g.setColor(Color.WHITE);
		g.drawString("MENU PRINCIPAL", getXCentro(), getYCentro());
		
	}

	@Override
	protected void updateMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void abrirMenu() {
	}

	@Override
	protected void fecharMenu() {
	}

}
