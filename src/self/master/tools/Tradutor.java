package self.master.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import self.master.principal.Principal;

public class Tradutor {
	private static HashMap<String, String> ptbr = new HashMap<String, String>();
	private static HashMap<String, String> en = new HashMap<String, String>();
	private static HashMap<Integer, HashMap<String, String>> textoMap = new HashMap<Integer, HashMap<String, String>>();
	
	public static final int PT_BR = 1;
	public static final int ENGLISH = 2;
	
	public static void init() {
		System.out.println("CARREGANDO TXT DE IDIOMA");
		long time = System.nanoTime();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(Tradutor.class.getResourceAsStream("/Idioma/ptbr.txt")));
			
			String line;
			while((line = in.readLine()) != null) {
			    //ptbr.put(line.split("@")[0], line.split("@")[1]);
			}
			
			in.close();
			
			in = new BufferedReader(new InputStreamReader(Tradutor.class.getResourceAsStream("/Idioma/en.txt")));
			
			while((line = in.readLine()) != null) {
				 //en.put(line.split("@")[0], line.split("@")[1]);
			}
			
			in.close();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		textoMap.put(PT_BR, ptbr);
		textoMap.put(ENGLISH, en);
		
		System.out.println("Tempo de carregamento de idiomas: " + (System.nanoTime() - time)/1000000.0 + "ms");
	}
	
	public static String getText(String index) {
		String texto = textoMap.get(Principal.idioma).get(index);
		
		if (texto == null) {
			return "";
		} else {
			return texto;
		}
	}
	
}
