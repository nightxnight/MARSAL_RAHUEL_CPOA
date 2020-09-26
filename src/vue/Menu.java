package vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

import modele.dao.Persistance;

public abstract class Menu {

	protected static Persistance PERSISTANCE = null;
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
	
	protected boolean confirmRequest() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Confirmer et appliquer les changements ? y/n");
		String input = "";
		input = sc.nextLine();
		input.toLowerCase().trim();
		if(input.equals("y") || input.equals("yes") || input.equals("oui") || input.equals("1")) 
			return true;
		else return false;
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
