package entities.ligneCommande;

public class LigneCommande {
	private int idCommande;
	private int idProduit;
	private int quantite;
	private double tarifUnitaire;
	
	//Constructor
	public LigneCommande(int idCommande, int idProduit, int quantite, double tarifUnitaire) {
		super();
		this.idCommande = idCommande;
		this.idProduit = idProduit;
		this.quantite = quantite;
		this.tarifUnitaire = tarifUnitaire;
	}

	//Getters and setters!
	public int getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}

	public int getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getTarifUnitaire() {
		return tarifUnitaire;
	}

	public void setTarifUnitaire(double tarifUnitaire) {
		this.tarifUnitaire = tarifUnitaire;
	}
	
	//ToString
	@Override
	public String toString() {
		return "LigneCommande [idCommande=" + idCommande + ", idProduit=" + idProduit + ", quantite=" + quantite
				+ ", tarifUnitaire=" + tarifUnitaire + "]";
	}

	//Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof LigneCommande)) {
			return false;
		}
		LigneCommande other = (LigneCommande) obj;
		if (idCommande != other.idCommande) {
			return false;
		}
		if (idProduit != other.idProduit) {
			return false;
		}
		return true;
	}
	
	
	
	
}
