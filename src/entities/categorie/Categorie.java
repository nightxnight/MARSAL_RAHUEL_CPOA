package entities.categorie;

public class Categorie {

	private int idCategorie;
	private String titre;
	private String visuel;
	
	public Categorie(String titre, String visuel) {
		this.titre = titre;
		this.visuel = visuel;
	}
	
	//Constructor
	public Categorie(int idCategorie, String titre, String visuel) {
		this(titre, visuel);
		this.idCategorie = idCategorie;

	}

	//Getters and Setters!
	public int getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getVisuel() {
		return visuel;
	}

	public void setVisuel(String visuel) {
		this.visuel = visuel;
	}

	//ToString
	@Override
	public String toString() {
		return "Categorie [idCategorie=" + idCategorie + ", titre=" + titre + ", visuel=" + visuel + "]";
	}

	//Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Categorie)) {
			return false;
		}
		Categorie other = (Categorie) obj;
		if (idCategorie != other.idCategorie) {
			return false;
		}
		return true;
	}
	
	
	
	
}
