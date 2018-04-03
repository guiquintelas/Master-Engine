package self.master.gui.menus;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

final public class ControladorMenu {
	public static ArrayList<Menu> menus = new ArrayList<Menu>();
	public static HashMap<Integer, Menu> grupos = new HashMap<Integer, Menu>();
	private static boolean menuPrincipalAberto = false;
	
	public static boolean addMenu(Menu menu) {
		System.out.println("add menu " + menu.nome + " ...");
		for (int i = 0; i< menus.size(); i++) {
			if (menus.get(i).keyCode == menu.keyCode) {
				System.err.println("Esta tecla ja esta associada a um menu!!!");
				return false;
			}
		}
		
		menus.add(menu);
		System.out.println("Menu " + menu.nome + " add com sucesso!");
		return true;
	} 
	
	public static boolean abrirMenu(Menu menu) {
		if (menuPrincipalAberto) return false;
		
		if (grupos.get(menu.grupo) != null) {
			if (menu.prioridade > grupos.get(menu.grupo).prioridade) {
				grupos.get(menu.grupo).fechar();
				grupos.put(menu.grupo, menu);
				grupos.get(menu.grupo).abrir();
				return true;
			} else {
				return false;
			}
			
		} else {
			grupos.put(menu.grupo, menu);
			grupos.get(menu.grupo).abrir();
			return true;
		}
	}
	
	public static void abrirMenuPrincipal(Menu menu) {
		if (menu.isPrincipal() == false) return;
		
		for (int i = 0; i < menus.size(); i++) {
			if (menus.get(i).aberto)menus.get(i).fechar();
		}

		menuPrincipalAberto = true;
	}
	
	public static void fecharMenuPrincipal(Menu menu) {
		if (menu.isPrincipal() == false) return;
		
		menuPrincipalAberto = false;
	}
	
	public static void fecharMenu(Menu menu) {
		grupos.put(menu.grupo, null);
	}
	
	public static void pintarMenus(Graphics2D g) {
		for (int i = 0; i < menus.size(); i++) {
			menus.get(i).pintar(g);
		}
	}
	
	public static void updateMenus() {
		for (int i = 0; i < menus.size(); i++) {
			menus.get(i).update();
		}
	}
 	
	
}
