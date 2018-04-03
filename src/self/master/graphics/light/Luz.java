package self.master.graphics.light;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import self.master.principal.Dimensional;
import self.master.principal.DimensionalObj;
import self.master.principal.Principal;
import self.master.tools.ActionQueue;
import self.master.tools.Variator;
import self.master.tools.VariatorNumero;

public class Luz {
	protected Dimensional d;
	private int raioFixo;
	protected int ratio = 1;
	protected int raio;
	private int diametro;
	protected int r;
	protected int g;
	protected int b;
	protected double for�a;
	
	protected int xOff = 0;
	protected int yOff = 0;

	private boolean desativando = false;
	protected boolean isInit = false;
	protected boolean isApagada = false;
	private boolean isAtivo = true;

	public Variator raioVar;
	public Variator for�aVar;

	public static ArrayList<Luz> todasLuz = new ArrayList<Luz>();

	public Luz(Dimensional d, int raio, int r, int g, int b, double forca, int fade) {
		this.d = d;
		this.raio = raio;
		this.raioFixo = raio;
		this.r = r;
		this.g = g;
		this.b = b;
		this.for�a = forca;

		getLista().add(this);

		init(fade, true, true, 10, 20);
		updateImgLuz();
	}

	public Luz(Dimensional d, int raio, int r, int g, int b, double forca, int fade, boolean variarRaio, boolean variarFor�a, int oscRaio, int oscFor�a) {
		this.d = d;
		this.raio = raio;
		this.raioFixo = raio;
		this.r = r;
		this.g = g;
		this.b = b;
		this.for�a = forca;

		getLista().add(this);

		init(fade, variarRaio, variarFor�a, oscRaio, oscFor�a);
		updateImgLuz();
	}
	
	public void setRatio(int ratioImpar) {
		if (ratioImpar < 1 || ratioImpar % 2 == 0) {
			System.err.println("RATIO PRECISA SER MAIOR QUE 1 E IMPAR");
		}
		
		this.ratio = ratioImpar;
	}
	
	public int getRaio() {
		if (isAtivo && !isApagada) {
			return raio;
		} else {
			return 0;
		}
	}
	
	public int getDiametro() {
		if (isAtivo && !isApagada) {
			return raio * 2;
		} else {
			return 0;
		}
	}
	
	public static void setLuzesAtivas(boolean ativa) {
		for (int i = 0; i < todasLuz.size(); i++) {
			todasLuz.get(i).for�aVar.setAtivo(ativa);
			todasLuz.get(i).raioVar.setAtivo(ativa);
		}
	}

	private void init(int fade, boolean variarRaio, boolean variarFor�a, final int oscRaio, final int oscFor�a) {
		
		raioVar = new Variator(new VariatorNumero() {
			public void setNumero(double numero) {
				setRaio((int) numero);
			}

			public double getNumero() {
				return getRaio();
			}

			public boolean devoContinuar() {
				return getLista().contains(Luz.this);
			}
		});

		if (variarRaio) {
			
			if (fade != 0) {
				raioVar.fadeInSin(0, raioFixo, fade);
				raioVar.variar(true);
			}
			
			
			if (oscRaio != 0) {
				raioVar.addAcaoNaFila(new ActionQueue() {
					public boolean action() {
						raioVar.oscilar(oscRaio, 400, true);
						raioVar.variar(true);
						return true;
					}
				});
			}
			
		}

		for�aVar = new Variator(new VariatorNumero() {
			public void setNumero(double numero) {
				if (numero > 100) numero = 100;
				setFor�a(numero);
			}

			public double getNumero() {
				return getFor�a();
			}

			public boolean devoContinuar() {
				return getLista().contains(Luz.this);
			}
		});

		if (variarFor�a) {
			
			if (fade != 0) {
				for�aVar.fadeIn(0, for�a - oscFor�a, fade);
				for�aVar.variar(true);
			}			
			
			if (oscFor�a != 0) {
				for�aVar.addAcaoNaFila(new ActionQueue() {
					public boolean action() {
						for�aVar.oscilar(oscFor�a, 400, true);
						for�aVar.variar(true);
						return true;
					}
				});
			}
			
		}
		
		raioVar.setAtivo(Principal.light);
		for�aVar.setAtivo(Principal.light);
	}
	
	protected ArrayList<Luz> getLista() {
		return todasLuz;
	}

	protected synchronized void updateImgLuz() {
		diametro = raio * 2;
//		pixelLuzA = new byte[diametro * diametro * 4];
		isInit = true;
//
//		for (int y = ratio/2; y < diametro - (ratio/2); y+=ratio - ratio/2) {
//			for (int x = ratio/2; x < diametro - (ratio/2); x+=ratio - ratio/2) {			
//				
//				double dist = Math.sqrt(((raio - x)*(raio - x)) + ((raio - y)*(raio - y)));
//				//System.out.println(((raio - x)*(raio - x)));
//				int index;
//				
//				if (dist < raio) {
//				//pixelLuzA[index] = (byte) 255;
//					index = (x + y * diametro) * 4 ;
//				
//					pixelLuzA[index ]    = (byte) (((r / (double) raio) * (raio - dist)) / (100 / for�a));
//					pixelLuzA[index + 1] = (byte) (((g / (double) raio) * (raio - dist)) / (100 / for�a));
//					pixelLuzA[index + 2] = (byte) (((b / (double) raio) * (raio - dist)) / (100 / for�a));
//					pixelLuzA[index + 3] = (byte)255;
//				} else {
//					continue;
//				}
//				
//				if (ratio == 1 && ratio > 1) continue;
//				
//				for (int offx = -ratio/2; offx < ratio - (ratio/2)*2; offx++) {
//					for (int offy = -ratio/2; offy < ratio - (ratio/2)*2; offy++) {
//						if (offx == ratio - ratio/2 && offy == ratio - ratio/2) continue;
//						int indexOff = ((x + offx) + (y + offy) * diametro) * 3 ;
//						
//						if (indexOff >= pixelLuzA.length) return;						
//						
//						pixelLuzA[indexOff]     = pixelLuzA[index];
//						pixelLuzA[indexOff + 1] = pixelLuzA[index + 1];
//						pixelLuzA[indexOff + 2] = pixelLuzA[index + 2];
//						pixelLuzA[indexOff + 3] = (byte)255;
//						
//					}
//					
//				}
//				
//			}
			
//			final ByteBuffer bytes = ByteBuffer.wrap(pixelLuzA);
//			//bytes.flip();
//			bytes.rewind();
//			
//			imgLuz = new Image(new ImageData() {
//				public int getWidth() {
//					return diametro;
//				}
//				
//				@Override
//				public int getTexWidth() {
//					return diametro;
//				}
//				
//				@Override
//				public int getTexHeight() {
//					return diametro;
//				}
//				
//				@Override
//				public ByteBuffer getImageBufferData() {
//					return bytes;
//				}
//				
//				@Override
//				public int getHeight() {
//					return diametro;
//				}
//				
//				@Override
//				public int getDepth() {
//					return 0;
//				}
//			});
			
//		}
	}
	
	public void setAtiva(boolean isAtivo) {
		this.isAtivo = isAtivo;
		for�aVar.setAtivo(isAtivo);
		raioVar.setAtivo(isAtivo);
	}

	public void update() {	
		checaAtivo();
		if (!isAtivo) return;	
		updateImgLuz();
	}

	private boolean checaAtivo() {
		if (d instanceof DimensionalObj) {
			if (DimensionalObj.todosDimensionalObjs.contains(d) == false) {
				if (isAtivo) {
					desativar();
				} else {
					desativarTotal();
				}
				return false;
			}
		}

		return true;
	}

	public synchronized void pintar(Graphics g, Image img) {
		
		
		if (!isInit || isApagada) return;
		int getX = d.getXCentro() + xOff;
		int getY = d.getYCentro() + yOff;
//		int indexLuz = -3;
//
//		for (int y = getY - raio; y < getY + raio; y++) {
//			for (int x = getX - raio; x < getX + raio; x++) {
//				indexLuz += 3;
//
//				if (y < 0 || y >= imgJogo.getHeight()) continue;
//				if (x < 0 || x >= imgJogo.getWidth()) continue;
//
//				int index = (x + y * GameWindow.getWidthLQ()) * 3;
//
//				int blue  = LuzAmbiente.pixelLuz[index ] & 0xFF;
//				int green = LuzAmbiente.pixelLuz[index + 1] & 0xFF;
//				int red   = LuzAmbiente.pixelLuz[index + 2] & 0xFF;
//
//				int blueLuz  = pixelLuzA[indexLuz] & 0xFF;
//				int greenLuz = pixelLuzA[indexLuz + 1] & 0xFF;
//				int redLuz   = pixelLuzA[indexLuz + 2] & 0xFF;
//
//				int redFinal   = red   + redLuz;
//				int greenFinal = green + greenLuz;
//				int blueFinal  = blue  + blueLuz;
//
//				if (redFinal > 255)   redFinal   = 255;
//				if (greenFinal > 255) greenFinal = 255;
//				if (blueFinal > 255)  blueFinal  = 255;
//
//				LuzAmbiente.pixelLuz[index]     = (byte) blueFinal;
//				LuzAmbiente.pixelLuz[index + 1] = (byte) greenFinal;
//				LuzAmbiente.pixelLuz[index + 2] = (byte) redFinal;
//			}
//		}
		
		g.setDrawMode(Graphics.MODE_ADD);
		g.drawImage(img, getX-diametro/2, getY-diametro/2,getX + diametro/2, getY + diametro/2, getX-diametro/2, getY-diametro/2, getX + diametro/2, getY + diametro/2);
	}

	public void setRaio(int raio) {
		if (raio == this.raio) {
			return;
		}
		this.raio = raio;
		diametro = raio * 2;
	}

	public void desativar() {
		if (desativando) return;
		desativando = true;
		raioVar.variar(false);
		for�aVar.variar(false);
		
		raioVar.fadeOutSin(raio, 0, 25);
		raioVar.variar(true);
		for�aVar.fadeOutSin(for�a, 0, 25);
		for�aVar.variar(true);
		raioVar.addAcaoNaFila(new ActionQueue() {
			public boolean action() {
				desativarTotal();
				return true;
			}
		});
	}
	
	public void desativar(int fadeOut) {
		if (desativando) return;
		if (fadeOut == 0) {
			desativarTotal();
			return;
		}
		desativando = true;
		raioVar.variar(false);
		for�aVar.variar(false);

		raioVar.fadeOutSin(raio, 0, fadeOut);
		raioVar.variar(true);
		for�aVar.fadeOutSin(for�a, 0, fadeOut);
		for�aVar.variar(true);
		raioVar.addAcaoNaFila(new ActionQueue() {
			public boolean action() {
				desativarTotal();			
				return true;
			}
		});
	}

	public void desativarTotal() {
		raioVar.desativar();
		for�aVar.desativar();
		getLista().remove(this);
	}

	public double getFor�a() {
		return for�a;
	}

	public void setFor�a(double for�a) {
		if (this.for�a == for�a) return;
		if (for�a > 100) for�a = 100;
		if (for�a < 0) for�a = 0;
		this.for�a = for�a;
	}
	
	public void setRGB(int r, int g, int b) {
		if (this.r == r && this.g == g && this.b == b) return;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public int getRed() {
		return r;
	}
	
	public int getGreen() {
		return g;
	}
	
	public int getBlue() {
		return b;
	}

	public void setXOff(int xOff) {
		this.xOff = xOff;
	}

	public void setYOff(int yOff) {
		this.yOff = yOff;
	}
	
	public void setApagada(boolean apagar) {
		isApagada = apagar;
	}
	
	public int getRatio() {
		return ratio;
	}
	
	public int getX() {
		return  d.getX() + xOff;
	}
	
	public int getY() {
		return  d.getY() + yOff;
	}
	
	public int getXCentro() {
		return  d.getXCentro() + xOff;
	}
	
	public int getYCentro() {
		return  d.getYCentro() + yOff;
	}

}
