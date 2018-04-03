package self.master.tools;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import self.master.input.InputListener;
import self.master.input.ListenerManager;

public class RelatorioDelay {
	private ArrayList<ArrayList<Long>> medias = new ArrayList<ArrayList<Long>>();
	private ArrayList<Long> valores = new ArrayList<Long>();
	private ArrayList<String> titulos = new ArrayList<String>();
	
	private int maiorTitulo = 0;
	//private int numMedias = 0;
	
	private boolean gerarMedia = false;
	private boolean mediaAtiva = false;
	private boolean relatorioAtivo = false;
	private boolean valorAberto = false;
	private boolean relatorioAberto = false;
	private boolean erro;
	
	long tempAtual;
	
	long delayRelatorio = 0;
	
	public RelatorioDelay(final char keyChar, final char keyCharMedia) {
		
		ListenerManager.addInputListener(new InputListener() {
			public void update(Input input) {
				if (input.isKeyPressed(Character.getNumericValue(keyChar))  && !mediaAtiva) {
					relatorioAtivo = true;
				}
				
				if (input.isKeyPressed(Character.getNumericValue(keyCharMedia))) {
					mediaAtiva = !mediaAtiva;
					
					if (mediaAtiva) {
						
						System.out.println("Média de Relatorio medindo...");
					} else {
						System.out.println("Média finalizada!");
						gerarMedia = true;
					}
				}
				
			}
		});
	}
	
	public void abrir() {
		if (mediaAtiva || gerarMedia) relatorioAtivo = true;
		
		if (!relatorioAtivo) return;
		
		if (relatorioAberto) {
			System.err.println("Relatorio não foi fechado!!!!");
			erro = true;
			return;
		}
		delayRelatorio = 0;
		
		long temp = System.nanoTime();
		
		if (mediaAtiva || gerarMedia) {
			medias.add(valores);
		}
		
		valores.clear();
		titulos.clear();
		relatorioAberto = true;
		relatorioAtivo = false;
		delayRelatorio += System.nanoTime() - temp;
	}
	
	public void abrirValor() {
		if (!relatorioAberto)return;
		
		if (valorAberto) {
			System.err.println("Valor não foi fechado!");
			return;
		}
		if (erro) return;
		
		long temp = System.nanoTime();
		tempAtual = System.nanoTime();
		valorAberto = true;
		delayRelatorio += System.nanoTime() - temp;
	}
	
	public void fecharValor(String titulo) {
		if (!relatorioAberto)return;
		if (erro) return;
		
		long temp = System.nanoTime();
		valores.add(System.nanoTime() - tempAtual);
		titulos.add(titulo);
		if (titulo.length() > maiorTitulo) maiorTitulo = titulo.length();
		valorAberto = false;
		delayRelatorio += System.nanoTime() - temp;
	}
	
	public void fechar() {
		if (!relatorioAberto) return;
		
		if (valorAberto) {
			System.err.println("Valor não foi fechado!!");
		}
		
		gerarRelatorio();
		
		relatorioAberto = false;
	}
	
	private void gerarRelatorio() {
		if (mediaAtiva && !gerarMedia) return;
		
		long temp = System.nanoTime();
		System.out.println();
		System.out.println("RELATORIO");
		if(gerarMedia)System.out.println("NUMERO DE MEDIAS: " + medias.size());
		for (int i = 0; i < valores.size(); i++) {
			String pontos = "..";
			
			for (int p = 0; p < maiorTitulo - titulos.get(i).length(); p++) {
				pontos = pontos.concat(".");
			}
			
			if (valores.get(i) < 1000) valores.set(i, (long) 0);
			
			if (!gerarMedia) {
				System.out.println(titulos.get(i) + pontos + valores.get(i)/1000000.0 + "ms");
			} else {
				long media = 0;
				
				for (int m = 0; m < medias.size(); m++) {
					media += medias.get(m).get(i);
				}
				media /= medias.size();
				if (media < 1000) media = 0;

				System.out.println(titulos.get(i) + pontos + media/1000000.0 + "ms");
			}
			
		}
		delayRelatorio += System.nanoTime() - temp;
		
		String pontos = "..";
		for (int p = 0; p < maiorTitulo - 9; p++) {
			pontos = pontos.concat(".");
		}
		
		System.out.println("Relatorio" + pontos + delayRelatorio/1000000.0 + "ms");
		
		System.out.println();
		
		gerarMedia = false;
	}
	
}


