package test;

import java.util.Scanner;
import vue.Menu;

public class MainClass {

	public static void main(String[] args) {
		
		/*
		 * Exemple d'un menu en ligne de commande qui permet d'essayer
		 * Les fonctions li� aux ajouts, modifications, suppressions et obtention des cat�gories
		 * 
		 * Puisque c'est le m�me principe pour les clients ainsi que les produits, seul
		 * un menu des cat�gories est disponible.
		 * 
		 * De plus nous passerons surement par la suite sur une IHM.
		 * 
		 * Cependant si cela est VRAIMENT n�cessaire, la r�alisation de ces menus (et par cons�quent un menu g�n�ral)
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
