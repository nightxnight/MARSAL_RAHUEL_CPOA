package vue;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Menu {
	
	protected int etat;
	protected boolean running;
	
	protected final int ETAT_ACCUEIL = 0;
	
	public Menu() {
		etat = ETAT_ACCUEIL;
		running = false;
	}
	
	public abstract void boucle();
	
	protected void changerEtat() {
		Scanner sc = new Scanner(System.in);
		int inputEtat;
		try {
			inputEtat = sc.nextInt();
		} catch(InputMismatchException ime) {
			inputEtat = ETAT_ACCUEIL;
		}		
		etat = inputEtat;
	}
	
	public void start() {
		running = true;
	}
	public void quit() {
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
}
