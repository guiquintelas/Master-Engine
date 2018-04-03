package self.master.gui;


import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;

import self.master.graphics.GraphicsM;
import self.master.input.ListenerManager;
import self.master.item.Item;
import self.master.mob.Mob;
import self.master.mob.Monstro;
import self.master.mob.Player;
import self.master.principal.DimensionalObj;

public class DisplayNomes {
	private static final Font font = new TrueTypeFont(new java.awt.Font("Bookman Old Style", java.awt.Font.PLAIN, 13), false);
	private static int x;
	private static int y;

	private static DimensionalObj dSelecionado;

	public static void update() {
		checaColisaoDimensionalObjMouse();
		updateXY();
	}

	private static void updateXY() {
		if (dSelecionado != null) {
			int fontWidth = font.getWidth(dSelecionado.toString());

			x = dSelecionado.getXCentro() - fontWidth / 2;
			y = dSelecionado.getY() + dSelecionado.getHeight();
		}

	}

	private synchronized static void checaColisaoDimensionalObjMouse() {
		for (int x = 0; x < DimensionalObj.todosDimensionalObjs.size(); x++) {
			DimensionalObj dAtual = DimensionalObj.todosDimensionalObjs.get(x);

			if (Math.abs(ListenerManager.xMouse() - dAtual.getXCentro()) < (dAtual.getWidth() / 2)) {
				if (dAtual instanceof Mob) {
					Mob dAtualM = (Mob)dAtual;
					if (Math.abs(ListenerManager.yMouse() - dAtualM.getYSpriteCentro()) < (dAtualM.heightSprite / 2)) {
						if (dAtualM.isInvisivel() && !(dAtual instanceof Player)) {
							return;
						}
						dSelecionado = dAtual;
						return;
					}
				} else {
					if (Math.abs(ListenerManager.yMouse() - dAtual.getYCentro()) < (dAtual.getHeight() / 2)) {
						dSelecionado = dAtual;
						return;
					}
				}

			}
		}

		dSelecionado = null;

	}

	public synchronized static void pintar(GraphicsM gHQ) {
		if (dSelecionado != null) {
			
			gHQ.setFont(font);
			if (dSelecionado instanceof Monstro) {
				gHQ.setColor(Color.red);
			} else if ( dSelecionado instanceof Item) {
				gHQ.setColor(Color.yellow);
			} else {
				gHQ.setColor(Color.lightGray);
			}
			
			
			gHQ.drawString(dSelecionado.toString(), x-1, y-1);
			gHQ.setColor(Color.black);
			gHQ.drawString(dSelecionado.toString(), x, y);
			
		}
	}
}
