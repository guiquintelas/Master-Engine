package self.master.graphics.light;


import org.newdawn.slick.SlickException;
import self.master.principal.Principal;
import self.master.tools.ActionQueue;
import self.master.tools.Variator;
import self.master.tools.VariatorNumero;

public class LuzAmbiente {
	public static int luzARed   = 255;
	public static int luzAGreen = 255;
	public static int luzABlue  = 255;
	
	public static final int LUZ_RED_DIA   = 255;
	public static final int LUZ_GREEN_DIA = 255;
	public static final int LUZ_BLUE_DIA  = 255;
	
	
	public static final int LUZ_RED_NOITE 	= 45;
	public static final int LUZ_GREEN_NOITE = 45;
	public static final int LUZ_BLUE_NOITE 	= 90;
	
	
	public static final int DELAY_DIA_NOITE_FADE = 1600;//1600
	public static final int DIA_DURACAO 		 = 2400;//2400
	public static final int NOITE_DURACAO 		 = 3000;//3000
	
	private static ActionQueue cicloDiaNoiteRedGreen = null;
	private static ActionQueue cicloDiaNoiteBlue = null;
	
	private static Variator diaNoiteRedGreen;
	private static Variator diaNoiteBlue;
	

	
	public static void cleanUp() {
		if (Principal.sempreNoite) return;
		diaNoiteBlue.desativar();
		diaNoiteRedGreen.desativar();
		luzARed   = LUZ_RED_DIA;
		luzAGreen = LUZ_GREEN_DIA;
		luzABlue  = LUZ_BLUE_DIA;
	}
	
	public static void init() throws SlickException {
		if (Principal.sempreNoite) {
			luzARed   = LUZ_RED_NOITE;
			luzAGreen = LUZ_GREEN_NOITE;
			luzABlue  = LUZ_BLUE_NOITE;
			return;
		}
		diaNoiteRedGreen = new Variator(new VariatorNumero() {
			public void setNumero(double numero) {
				if (numero > 255) numero = 255;
				if (numero < 0) numero = 0;
				luzARed = (int)numero;
				luzAGreen = (int)numero;
			}
			
			public double getNumero() {
				return luzARed;
			}
			
			public boolean devoContinuar() {
				return true;
			}
		});
		
		diaNoiteBlue = new Variator(new VariatorNumero() {
			public void setNumero(double numero) {
				if (numero > 255) numero = 255;
				if (numero < 0) numero = 0;
				luzABlue = (int) numero;
			}

			public double getNumero() {
				return luzABlue;
			}
			
			public boolean devoContinuar() {
				return true;
			}
		});
		
		
		cicloDiaNoiteRedGreen = new ActionQueue() {
			public boolean action() {
				Principal.dia = false;
				diaNoiteRedGreen.fadeOut(LUZ_RED_DIA, LUZ_RED_NOITE, DELAY_DIA_NOITE_FADE);
				diaNoiteRedGreen.variar(true);
				
				diaNoiteRedGreen.addAcaoNaFila(new ActionQueue() {
					public boolean action() {
						System.out.println("foi");
						diaNoiteRedGreen.esperar(NOITE_DURACAO);
						
						diaNoiteRedGreen.addAcaoNaFila(new ActionQueue() {
							public boolean action() {
								diaNoiteRedGreen.fadeIn(LUZ_RED_NOITE, LUZ_RED_DIA, DELAY_DIA_NOITE_FADE);
								diaNoiteRedGreen.variar(true);
								
								diaNoiteRedGreen.addAcaoNaFila(new ActionQueue() {
									public boolean action() {
										Principal.dia = true;
										diaNoiteRedGreen.esperar(DIA_DURACAO);
										
										diaNoiteRedGreen.addAcaoNaFila(cicloDiaNoiteRedGreen);
										return true;
									}
								});
								return true;
							}
						});
						return true;
					}
				});
				return true;
			}
		};
		
		cicloDiaNoiteBlue = new ActionQueue() {
			public boolean action() {
				diaNoiteBlue.fadeOut(LUZ_BLUE_DIA, LUZ_BLUE_NOITE, DELAY_DIA_NOITE_FADE);
				diaNoiteBlue.variar(true);
				
				diaNoiteBlue.addAcaoNaFila(new ActionQueue() {
					public boolean action() {
						
						diaNoiteBlue.esperar(NOITE_DURACAO);
						
						diaNoiteBlue.addAcaoNaFila(new ActionQueue() {
							public boolean action() {
								diaNoiteBlue.fadeIn(LUZ_BLUE_NOITE, LUZ_BLUE_DIA, DELAY_DIA_NOITE_FADE);
								diaNoiteBlue.variar(true);
								
								diaNoiteBlue.addAcaoNaFila(new ActionQueue() {
									public boolean action() {
										diaNoiteBlue.esperar(DIA_DURACAO);
										
										diaNoiteBlue.addAcaoNaFila(cicloDiaNoiteBlue);
										return true;
									}
								});
								return true;
							}
						});
						return true;
					}
				});
				return true;
			}
		};
		
		diaNoiteBlue.addAcaoNaFila(cicloDiaNoiteBlue);
		diaNoiteRedGreen.addAcaoNaFila(cicloDiaNoiteRedGreen);
	}



}
