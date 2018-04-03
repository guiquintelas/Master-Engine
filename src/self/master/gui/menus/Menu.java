package self.master.gui.menus;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.newdawn.slick.Input;

import self.master.input.InputListener;
import self.master.input.ListenerManager;
import self.master.principal.Dimensional;

public abstract class Menu extends Dimensional {
	protected int keyCode;
	protected String nome;
	protected boolean aberto = false;
	protected int prioridade = 0; //para abrir por cima de outro menu
	protected int grupo = 0;
	
	protected static int grupoPrincipal = 0;
	
	
	public Menu(int x, int y, int width, int height, final int keyCode, String nome,int grupo) {
		setX(x);
		setY(y);
		this.width = width;
		this.height = height;
		
		this.keyCode = keyCode;
		this.nome = nome;
		this.grupo = grupo;

		
		ListenerManager.addInputListener(new InputListener() {
			public void update(Input input) {
				if (input.isKeyDown(keyCode)) {
					if (aberto) {
						fechar();
					} else {
						abrir();	
					}	
				}
				
			}
		});
		
		ControladorMenu.addMenu(this);	
	}
	
	protected void setPrioridade(int pri) {
		this.prioridade = pri;
	}
	
	protected boolean isPrincipal() {
		return grupo == grupoPrincipal;
	}
	
	protected void setGrupoPrincipal(Menu menu) {
		if (grupoPrincipal != 0) {
			System.err.println("MENU PRINCIPAL JA ESCOLHIDO");
			return;
		}
		grupoPrincipal = menu.grupo;
	}
	
	public abstract void init();
	
	public final void pintar(Graphics2D g) {
		if (aberto)pintarMenu(g);
	}
	
	protected abstract void pintarMenu(Graphics2D g);
	
	
	public final void update() {
		if (aberto)updateMenu();
	}
	
	protected abstract void updateMenu();
	
	public final void abrir() {
		if (isPrincipal()) {
			ControladorMenu.abrirMenuPrincipal(this);
		} else {
			if (!ControladorMenu.abrirMenu(this)) return;
		}
		
		abrirMenu();
		aberto = true;
	}
	
	protected abstract void abrirMenu();

	public final void fechar() {
		if (isPrincipal()) {
			ControladorMenu.fecharMenuPrincipal(this);
		} else {
			ControladorMenu.fecharMenu(this);
		}	
		
		fecharMenu();
		aberto = false;
	}

	protected abstract void fecharMenu();
	
}
