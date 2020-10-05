package test;

import vue.MenuGeneral;

public class MainClass {

	public static void main(String[] args) {
		MenuGeneral m = new MenuGeneral();
		while(m.isRunning()) {
			m.boucle();
		}
	}
}
