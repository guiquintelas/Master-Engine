package self.master.tools;

import java.util.ArrayList;

import self.master.principal.Principal;



public abstract class Timer {
	private int tickCriado;
	private int delay;
	
	private boolean loop = false; 
	
	public static ArrayList<Timer> todosTimers = new ArrayList<Timer>();
	
	public Timer(int delay) {
		this.delay = delay;
		tickCriado = Principal.tickTotal;
		todosTimers.add(this);
	}
	
	public final void update() {
		acaoTick();
		if (Principal.tickTotal >= tickCriado + delay) {
			acao();
			if (loop) {
				reset();
			} else {
				todosTimers.remove(this);
			}
		}
	}
	
	protected void acaoTick() {}

	public abstract void acao();
	
	public final void reset() {
		tickCriado = Principal.tickTotal;
		
		if (!todosTimers.contains(this)) {
			todosTimers.add(this);
		}
	}
	
	public final void reset(int novoDelay) {
		delay = novoDelay;
		reset();
	}
	
	public final void delete() {
		todosTimers.remove(this);
	}
	
	public final boolean isRodando() {
		return todosTimers.contains(this);
	}
	
	public final void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	
}
