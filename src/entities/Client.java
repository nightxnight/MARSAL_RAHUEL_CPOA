package entities;

public class Client implements Comparable<Client> {
	
	private int idClient;
	private String nom;
	private String prenom;
	private String identifiant;
	private String motDePasse;
	private String adrNumero;
	private String adrVoie;
	private String adrCodePostal;
	private String adrVille;
	private String adrPays;
	
	//Constructor
	public Client(String nom, String prenom, String identifiant, String motDePasse, String adrNumero,
			String adrVoie, String adrCodePostal, String adrVille, String adrPays) {
		this.nom = nom;
		this.prenom = prenom;
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
		this.adrNumero = adrNumero;
		this.adrVoie = adrVoie;
		this.adrCodePostal = adrCodePostal;
		this.adrVille = adrVille;
		this.adrPays = adrPays;
	}
	
	public Client(int idClient, String nom, String prenom, String identifiant, String motDePasse, String adrNumero,
			String adrVoie, String adrCodePostal, String adrVille, String adrPays) {
		this(nom, prenom, identifiant, motDePasse, adrNumero, adrVoie, adrCodePostal, adrVille, adrPays);
		this.idClient = idClient;
		
	}

	//Getters and Setters
	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getAdrNumero() {
		return adrNumero;
	}

	public void setAdrNumero(String adrNumero) {
		this.adrNumero = adrNumero;
	}

	public String getAdrVoie() {
		return adrVoie;
	}

	public void setAdrVoie(String adrVoie) {
		this.adrVoie = adrVoie;
	}

	public String getAdrCodePostal() {
		return adrCodePostal;
	}

	public void setAdrCodePostal(String adrCodePostal) {
		this.adrCodePostal = adrCodePostal;
	}

	public String getAdrVille() {
		return adrVille;
	}

	public void setAdrVille(String adrVille) {
		this.adrVille = adrVille;
	}

	public String getAdrPays() {
		return adrPays;
	}

	public void setAdrPays(String adrPays) {
		this.adrPays = adrPays;
	}
	
	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", nom=" + nom + ", prenom=" + prenom + ", identifiant=" + identifiant
				+ ", motDePasse=" + motDePasse + ", adrNumero=" + adrNumero + ", adrVoie=" + adrVoie
				+ ", adrCodePostal=" + adrCodePostal + ", adrVille=" + adrVille + ", adrPays=" + adrPays + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Client)) {
			return false;
		}
		Client other = (Client) obj;
		if (idClient != other.idClient) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Client objet) {
		int comparaisonNom = nom.toLowerCase().compareTo(objet.getNom().toLowerCase());
		if(comparaisonNom == 0) {
			int comparaisonPrenom = prenom.toLowerCase().compareTo(objet.getPrenom().toLowerCase());
			if(comparaisonPrenom == 0) return adrCodePostal.toLowerCase().compareTo(objet.getAdrVille().toLowerCase());
			else return comparaisonPrenom;
		} else return comparaisonNom;
	}
}
