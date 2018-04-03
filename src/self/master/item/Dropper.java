package self.master.item;

import java.util.ArrayList;

import self.master.mob.Mob;
import self.master.tools.Util;

public class Dropper {
	public static final String MOCHILA = "Mochila";


	private int xCentro;
	private int yCentro;
	
	private int quantos = 0;
	private int capacidade = 0;
	private boolean isCapped = false;

	private Mob mob;

	private ArrayList<String> itens = new ArrayList<String>();
	private ArrayList<Item> itensD = new ArrayList<Item>();

	private static final int DIST_MIN = 20;

	public Dropper(int xCentro, int yCentro) {
		this.xCentro = xCentro;
		this.yCentro = yCentro;
	}

	public Dropper(Mob mob) {
		this.mob = mob;
		this.xCentro = mob.getXCentro();
		this.yCentro = mob.getYCentro();
	}
	
	public void setCap(int cap) {
		isCapped = true;
		capacidade = cap;
	}
	

	public boolean addItem(String item, double chanceEm100) {
		double chance = Util.randomDouble(0, 100);
		if (chance <= chanceEm100 && (!isCapped || (quantos < capacidade))) {
			itens.add(item);
			quantos++;
			return true;
		}
		
		return false;
	}
	
	public boolean addItem(String item, double chanceEm100, Void semCap) {
		if (Util.randomDouble(0, 100) <= chanceEm100) {
			itens.add(item);
			return true;
		}
		
		return false;
	}

	public void removeAll() {
		itensD.clear();
	}

	public void droppar() {
		if (mob != null) {
			this.xCentro = mob.getXCentro();
			this.yCentro = mob.getYCentro();
		}

		for (int i = 0; i < itens.size(); i++) {
			int randX = 0;
			int randY = 0;

			boolean liberado = false;
			while (!liberado) {
				int margem = itens.size() * 8;
				randX = Util.randomInt(-margem, margem);
				randY = Util.randomInt(-margem, margem);
				if (i == 0)
					break;
				for (int j = 0; j < itensD.size(); j++) {
					double dist = Math.sqrt(Math.pow(xCentro + randX - itensD.get(j).getX(), 2) + Math.pow(yCentro + randY - itensD.get(j).getY(), 2));

					if (dist > DIST_MIN && j == itensD.size() - 1) {
						liberado = true;
					}

					if (dist < DIST_MIN) {
						break;
					}

				}
			}

//			switch (itens.get(i)) {
//
//			case MOCHILA:
//				itensD.add(new Mochila(xCentro + randX, yCentro + randY));
//				break;
//
//			default:
//				System.out.println("erro NAO EXISTE ESSE ITEM");
//
//			}

		}
	}
}
