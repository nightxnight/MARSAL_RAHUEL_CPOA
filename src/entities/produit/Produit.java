package entities.produit;
/*
 * On cr�e au pr�alable un package entities.produit puisque
 * nous aurons surement besoin par la suite de cr�er des instances de
 * diff�rentes classes "enfant" de produit
 * Produit deviendra alors abstraite
 */
public class Produit {
	
	private int id;
	private String nom;
	private String description;
	private double tarif;
	private String visuel;
	private int idCategorie;
	
	/*
	 * Constructor :
	 * On en a deux puisqu'il y a 2 cas :
	 * 		- Soit on cr�e un nouveau produit et alors dans ce cas il n'y a pas encore d'id attribu�
	 * 		- Soit le produit existe d�j� et par cons�quent un id lui est d�j� attribu�
	 */
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
	
	
	
	
	
}
