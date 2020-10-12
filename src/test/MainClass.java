package test;

import vue.console.MenuGeneral;

public class MainClass {

	public static void main(String[] args) {
		MenuGeneral m = new MenuGeneral();
		while(m.isRunning()) {
			m.boucle();
		}
	}
}
