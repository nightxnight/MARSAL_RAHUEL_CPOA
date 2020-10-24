package vue.application.management;

public enum Entities {
	
	PRODUIT("Produit"),
	CATEGORIE("Categorie"),
	CLIENT("Client"), 
	COMMANDE("Commande");
	
	private String libelle;
	
	private Entities(String libelle) {
		this.libelle = libelle;
	}
	
	public String getLibelle() {
		return this.libelle;
	}

}
