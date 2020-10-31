package entities;

public class Produit {
	
	private int id;
	private String nom;
	private String description;
	private double tarif;
	private String visuel;
	private int idCategorie;

	public Produit(String nom, String description, double tarif, String visuel, int idCategorie) {
		this.nom = nom;
		this.description = description;
		this.tarif = tarif;
		this.visuel = visuel;
		this.idCategorie = idCategorie;
	}
	
	public Produit(int id, String nom, String description, double tarif, String visuel, int idCategorie) {
		this(nom, description, tarif, visuel, idCategorie);
		this.id = id;
	}
	
	//Getters and setters!
	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getDescription() {
		return description;
	}

	public double getTarif() {
		return tarif;
	}

	public String getVisuel() {
		return visuel;
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTarif(double tarif) {
		this.tarif = tarif;
	}

	public void setVisuel(String visuel) {
		this.visuel = visuel;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	//ToString
	@Override
	public String toString() {
		return "Produit [id=" + id + ", nom=" + nom + ", description=" + description + ", tarif=" + tarif + ", visuel="
				+ visuel + ", idCategorie=" + idCategorie + "]";
	}

	//Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Produit)) {
			return false;
		}
		Produit other = (Produit) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	public Produit clone() {
		return new Produit(id, nom, description, tarif, visuel, idCategorie);
	}
	
}
