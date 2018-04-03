package self.master.map;

import java.util.ArrayList;
import java.util.Collections;

import self.master.tools.Util;

public class MapGenerator {
	
	public static void criarMapa() {
		long tempoA = System.currentTimeMillis();
		
		criarGrama();
		
		System.out.println("tempo de criação do mapa: " + (System.currentTimeMillis() - tempoA) + "ms");
	}
	

	private static void criarGrama() {
		int numDGrama = Util.randomInt(500, 1000);
		
		
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		for (int y = 0; y < TileMap.HEIGHT; y++) {
			for (int x = 0; x < TileMap.WIDTH; x++) {
				tiles.add(TileMap.tileMap[x][y]);
			}
		}
		
		for (int x = 0; x < numDGrama; x++) {		
			int randon = Util.randomInt(0, tiles.size() - 1);			
			tiles.get(randon).setGrama();
			tiles.addAll(tiles.get(randon).getTilesCirculo(4));
			tiles.addAll(tiles.get(randon).getTilesCirculo(2));
			tiles.removeAll(Collections.singleton(tiles.get(randon)));
			
		}
		
	}
	
	public static void clearMap() {
		for (int y = 0; y < TileMap.HEIGHT; y++) {
			for (int x = 0; x < TileMap.WIDTH; x++) {
				TileMap.tileMap[x][y].reset();
			}
		}
	}
}
