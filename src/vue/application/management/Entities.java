package vue.application.management;

public enum Entities {
	
	PRODUIT(""),
	CATEGORIE(""),
	CLIENT(""), 
	COMMANDE("");
	
	private final String fxmlFileName;
	
	private Entities(String fxmlFileName) {
		this.fxmlFileName = fxmlFileName;
	}
	
	public String getFxmlFileName() {
		return this.fxmlFileName;
	}
}
