package self.master.gui.ferramentas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import javax.swing.Timer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import self.master.input.InputListener;
import self.master.input.ListenerManager;
import self.master.principal.Dimensional;
import self.master.principal.GameWindow;
import self.master.principal.Principal;

public class Botao extends Dimensional {
	private ActionListener al;
	private ActionListener alDireito;
	private Image img;
	private Image imgSel;
	private Image imgApertado;

	private InputListener mouseInput;
	private InputListener keyInput;


	private int textPosicao = CENTRO;
	public static final int CENTRO = 0;
	public static final int CIMA_ESQUERDA = 1;
	public static final int CIMA_DIREITA = 2;
	public static final int BAIXO_ESQUERDA = 3;
	public static final int BAIXO_DIREITA = 4;

	private float alpha = 0;

	private String text;
	private Font font;

	private boolean isSel = false;
	private boolean isApertado = false;
	private boolean isGui = false;
	private boolean isEnable = true;
	private boolean isTecla = false;
	private boolean isCor = false;
	private boolean hasText = false;
	private boolean hasBack = false;
	private boolean hasSombra = true;
	private boolean isPiscando = false;
	private boolean isCorSelApertadoAtivo = true;

	private Color cor;
	private Color corText;
	private Color corBack;
	private Color corSombra;
	private Color corPiscando = new Color(255, 215, 0);

	private Timer timerAlpha;

	private int teclaCod;

	public Botao(int x, int y, int width, int height, Image imgPadrao, Image imgSelecionado, Image imgApertado, ActionListener al) {
		this.x = x;

		if (y > GameWindow.getHeightHQ()) {
			isGui = true;
		}
		this.y = y;

		this.width = width;
		this.height = height;

		this.img = imgPadrao;
		this.imgSel = imgSelecionado;
		this.imgApertado = imgApertado;

		this.al = al;

		initMouseListener();
	}

	public Botao(int x, int y, int width, int height, Image imgPadrao, Image imgSelecionado, Image imgApertado, ActionListener al, char tecla) {
		this.x = x;

		if (y > GameWindow.getHeightHQ()) {
			isGui = true;
		}
		this.y = y;

		this.width = width;
		this.height = height;

		this.img = imgPadrao;
		this.imgSel = imgSelecionado;
		this.imgApertado = imgApertado;

		this.al = al;

		isTecla = true;
		teclaCod = Character.toUpperCase(tecla);
		initKeyListener();
		initMouseListener();
	}

	public Botao(int x, int y, int width, int height, Color corPadrao, ActionListener al, char tecla) {
		this.x = x;

		if (y > GameWindow.getHeightHQ()) {
			isGui = true;
		}
		this.y = y;

		this.width = width;
		this.height = height;

		isCor = true;

		cor = corPadrao;

		this.al = al;

		isTecla = true;
		teclaCod = Character.toUpperCase(tecla);
		initKeyListener();
		initMouseListener();
	}

	public Botao(int x, int y, int width, int height, Color corPadrao, ActionListener al) {
		this.x = x;

		if (y > GameWindow.getHeightHQ()) {
			isGui = true;
		}
		this.y = y;

		this.width = width;
		this.height = height;

		isCor = true;

		cor = corPadrao;

		this.al = al;
		initMouseListener();
	}

	private void initMouseListener() {
		
		mouseInput = new InputListener() {
			public void update(Input input) {
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					if (ListenerManager.xMouse() > x && ListenerManager.xMouse() < x + width) {
						if (ListenerManager.yMouse() > y && ListenerManager.yMouse() < y + height) {
							isApertado = true;
						}
					}
				} else {
					if (ListenerManager.xMouse() > x && ListenerManager.xMouse() < x + width) {
						if (ListenerManager.yMouse() > y && ListenerManager.yMouse() < y + height && isApertado) {
							if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && alDireito != null) {
								alDireito.actionPerformed(null);
							} else {
								al.actionPerformed(null);
							}

						}
					}
					
					isApertado = false;
				}
				
				
				
			}
		};

		ListenerManager.addInputListener(mouseInput);
	}

	private void initKeyListener() {
		
		keyInput = new InputListener() {
			public void update(Input input) {
				if (isTecla && input.isKeyDown(teclaCod)) {
					isApertado = true;
				} else {
					al.actionPerformed(null);
					isApertado = false;
				}
				
			}
		};
		
		
		ListenerManager.addInputListener(keyInput);
	}

	public void setActionListenerDireito(ActionListener al) {
		alDireito = al;
	}

	public void piscar(boolean piscar) {
		if (timerAlpha != null) {
			if (timerAlpha.isRunning()) {
				if (piscar) {
					return;
				} else {
					timerAlpha.stop();
				}
			}
		}

		if (!piscar) {
			isPiscando = false;
			return;
		}

		isPiscando = true;

		timerAlpha = new Timer(5, new ActionListener() {
			int tickAtual = Principal.tickTotal;
			boolean isAlphaUp = true;

			public void actionPerformed(ActionEvent e) {

				if (Principal.tickTotal >= tickAtual + 1 && isAlphaUp) {
					alpha += .025f;
					tickAtual++;
					if (alpha >= .7f) {
						alpha = .7f;
						isAlphaUp = false;
					}
				}

				if (Principal.tickTotal >= tickAtual + 1 && !isAlphaUp) {
					alpha -= .025f;
					tickAtual++;
					if (alpha <= 0) {
						alpha = 0;
						isAlphaUp = true;
					}
				}

			}
		});
		timerAlpha.start();
	}

	public int getY() {
		if (isGui) {
			return (int) y - GameWindow.getHeightHQ();
		} else {
			return (int) y;
		}
	}

	public void setText(String texto, Font font, Color corText) {
		this.text = texto;
		this.font = font;
		this.corText = corText;
		hasText = true;
	}

	public void setText(String texto) {
		if (hasText) {
			this.text = texto;
		}
	}

	public void setTextPosicao(int pos) {
		textPosicao = pos;
	}

	public void setBG(Color cor) {
		corBack = cor;
		hasBack = true;
	}

	public void setAllImg(Image img) {
		this.img = img;
		this.imgSel = img;
		this.imgApertado = img;
	}

	public void setSombra(Color cor) {
		corSombra = cor;
		hasSombra = true;
	}

	public void setSombraFalse() {
		hasSombra = false;
	}

	public void setEnable(boolean enable) {
		if (enable && !isEnable) {
			isEnable = true;
			
			ListenerManager.addInputListener(mouseInput);
			
			if (isTecla) {				
				ListenerManager.addInputListener(keyInput);
			}
			return;
		}

		if (!enable && isEnable) {
			isEnable = false;
			
			ListenerManager.removeInputListener(mouseInput);
			
			if (isTecla) {			
				ListenerManager.removeInputListener(keyInput);
			}
			return;
		}
	}

	public void pintar(Graphics g) {
		pintarSombra(g);
		pintarBG(g);
		if (isCor) {
			if (isApertado) {
				g.setColor(cor);
				g.fillRect(getX() + 1, getY() + 1, width, height);
				pintarApertado(g);
				pintarPisca(g);
				pintarText(g);
				return;
			}

			if (isSel) {
				g.setColor(cor);
				g.fillRect(getX(), getY(), width, height);
				pintarSel(g);
				pintarPisca(g);
				pintarText(g);
				return;
			}

			g.setColor(cor);
			g.fillRect(getX(), getY(), width, height);
			pintarPisca(g);
			pintarText(g);

		} else {
			if (isApertado) {
				g.drawImage(imgApertado, getX() + 1, getY() + 1);
				pintarApertado(g);
				pintarPisca(g);
				pintarText(g);
				return;
			}

			if (isSel) {
				g.drawImage(imgSel, getX(), getY());
				pintarSel(g);
				pintarPisca(g);
				pintarText(g);
				return;
			}

			g.drawImage(img, getX(), getY());
			pintarPisca(g);
			pintarText(g);
		}

		if (!isEnable) {
			g.setColor(Color.black);
			g.setColor(new Color(1,1,1, .3f));
			g.fillRect(getX(), getY(), width, height);
			g.setColor(new Color(1,1,1,1));
		}

	}

	private void pintarSel(Graphics g) {
		if (isCorSelApertadoAtivo && isEnable) {
			if (isCorSelApertadoAtivo) {
				g.setColor(Color.white);
				g.setColor(new Color(1,1,1, .2f));
				g.fillRect(getX(), getY(), width, height);
				g.setColor(new Color(1,1,1,1));
			}
		}
	}

	private void pintarApertado(Graphics g) {
		if (isCorSelApertadoAtivo) {
			if (isCorSelApertadoAtivo) {
				g.setColor(Color.black);
				g.setColor(new Color(1,1,1, .3f));
				g.fillRect(getX() + 1, getY() + 1, width, height);
				g.setColor(new Color(1,1,1,1));
			}
		}
	}

	private void pintarText(Graphics g) {
		if (!hasText) return;
		g.setFont(font);
		g.setColor(corText);
		if (isApertado) {
			switch (textPosicao) {
				case CENTRO:
					g.drawString(text, getX() + width / 2 - font.getWidth(text) / 2 + 1, getY() + height / 2 + font.getHeight(text) / 2 - 1);
					break;
				case BAIXO_DIREITA:
					g.drawString(text, getX() + width - font.getWidth(text) - 2 + 1, getY() + height - 2 - 1);
					break;
				default:
					g.drawString(text, getX() + width / 2 - font.getWidth(text) / 2 + 1, getY() + height / 2 + font.getHeight(text) / 2 - 1);
			}

		} else {
			switch (textPosicao) {
				case CENTRO:
					g.drawString(text, getX() + width / 2 - font.getWidth(text) / 2, getY() + height / 2 + font.getHeight(text) / 2 - 2);
					break;
				case BAIXO_DIREITA:
					g.drawString(text, getX() + width - font.getWidth(text) - 2, getY() + height - 2 - 2);
					break;
				default:
					g.drawString(text, getX() + width / 2 - font.getWidth(text) / 2, getY() + height / 2 + font.getHeight(text) / 2 - 2);
			}

		}
	}

	private void pintarBG(Graphics g) {
		if (hasBack) {
			g.setColor(corBack);
			g.fillRect(getX(), getY(), width, height);
		}
	}

	private void pintarSombra(Graphics g) {
		if (hasSombra) {
			g.setColor(corSombra);
			g.fillRect(getX() + 1, getY() + 1, width, height);
		}
	}

	private void pintarPisca(Graphics g) {
		if (isPiscando) {
			g.setColor(new Color(1,1,1, alpha));
			g.setColor(corPiscando);
			g.fillRect(getX(), getY(), width, height);
			g.setColor(new Color(1,1,1,1));
		}
	}

	public void update() {
		checaSel();
	}

	private void checaSel() {
		if (ListenerManager.xMouse() > x && ListenerManager.xMouse() < x + width) {
			if (ListenerManager.yMouse() > y && ListenerManager.yMouse() < y + height) {
				isSel = true;
				return;
			}
		}
		isSel = false;

	}

}
