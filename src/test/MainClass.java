package test;

import java.util.Scanner;
import vue.Menu;

public class MainClass {

	public static void main(String[] args) {
		
		/*
		 * Exemple d'un menu en ligne de commande qui permet d'essayer
		 * Les fonctions lié aux ajouts, modifications, suppressions et obtention des catégories
		 * 
		 * Puisque c'est le même principe pour les clients ainsi que les produits, seul
		 * un menu des catégories est disponible.
		 * 
		 * De plus nous passerons surement par la suite sur une IHM.
		 * 
		 * Cependant si cela est VRAIMENT nécessaire, la réalisation de ces menus (et par conséquent un menu général)
		 * est possible.
		 */

		Menu m = new Menu();
		Scanner sc = new Scanner(System.in);
		while(m.isRunning()) {
			m.boucle();
			sc.nextLine();//Permet d'attendre le prochain input de l'utilisateur
		}
		
	}

}
