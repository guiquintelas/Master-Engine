package self.master.principal;

import javax.swing.UIManager;

import self.master.audio.BackMusic;
import self.master.tools.Tradutor;


public class Principal {
	public static boolean isRodando       = false;
	private static boolean isPausado      = false;
	public static final boolean debugPF   = false;
	public static boolean monstrosParados = false;
	public static boolean editorTiles 	  = false;
	public static boolean light           = true;
	public static boolean dia             = false;
	public static boolean sempreNoite     = true;
	public static boolean showFPS         = true;
	public static boolean grafico  		  = false;
	public static boolean initAni  		  = false;
	
	private static boolean menuOpçoes = false; //acesso por get set
	public static boolean menuMochila = false;
	public static boolean menuChar = false;
	public static boolean menuLevel = false;
	
	public static int idioma = Tradutor.PT_BR;

	public GameWindow janela;

	public static int tickTotal = 0;
	private static int tickPerSec = 0;
	
	//public static final int TPS = 50;
	//public static final int TARGET_TIME = 1000/TPS;

	public static Game game;
	
	public static BackMusic musicas;
	
	public Principal(boolean initAni) {
		
		
		
		if (initAni) {
			//gameEstate = new AnInitState();
		} else {
			//gameEstate = new TestState();
		}	
		

	}

	
	public static boolean menuOpçoes() {
		return menuOpçoes;
	}
	
	public static void setMenuOpçoes(boolean menu) {
		setPause(menu);
		menuOpçoes = menu;	
		
	}
	
	public static int getTPS() {
		return tickPerSec;
	}
	
	public static void setPause(boolean pausar) {
		if (pausar == isPausado) return;
		
		isPausado = pausar;
	}
	
	public static boolean isPausado() {
		return isPausado;
	}	

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.setProperty("org.lwjgl.librarypath", "C:/Documents and Settings/ESCRITORIO/Meus documentos/Java Projetos/WaveDefender/native");
		System.setProperty("org.lwjgl.librarypath", "E:/Documents/Testes em Java/Master Engine/native");

		//new Principal(false);
		GameWindow.init();
	}

}
