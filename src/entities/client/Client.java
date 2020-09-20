package entities.client;

public class Client {
	
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
	
	//ToString
	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", nom=" + nom + ", prenom=" + prenom + ", identifiant=" + identifiant
				+ ", motDePasse=" + motDePasse + ", adrNumero=" + adrNumero + ", adrVoie=" + adrVoie
				+ ", adrCodePostal=" + adrCodePostal + ", adrVille=" + adrVille + ", adrPays=" + adrPays + "]";
	}

	//Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Client)) {
			return false;
		}
		Client other = (Client) obj;
		if (adrCodePostal == null) {
			if (other.adrCodePostal != null) {
				return false;
			}
		} else if (!adrCodePostal.equals(other.adrCodePostal)) {
			return false;
		}
		if (adrNumero == null) {
			if (other.adrNumero != null) {
				return false;
			}
		} else if (!adrNumero.equals(other.adrNumero)) {
			return false;
		}
		if (adrPays == null) {
			if (other.adrPays != null) {
				return false;
			}
		} else if (!adrPays.equals(other.adrPays)) {
			return false;
		}
		if (adrVille == null) {
			if (other.adrVille != null) {
				return false;
			}
		} else if (!adrVille.equals(other.adrVille)) {
			return false;
		}
		if (adrVoie == null) {
			if (other.adrVoie != null) {
				return false;
			}
		} else if (!adrVoie.equals(other.adrVoie)) {
			return false;
		}
		if (idClient != other.idClient) {
			return false;
		}
		if (identifiant == null) {
			if (other.identifiant != null) {
				return false;
			}
		} else if (!identifiant.equals(other.identifiant)) {
			return false;
		}
		if (motDePasse == null) {
			if (other.motDePasse != null) {
				return false;
			}
		} else if (!motDePasse.equals(other.motDePasse)) {
			return false;
		}
		if (nom == null) {
			if (other.nom != null) {
				return false;
			}
		} else if (!nom.equals(other.nom)) {
			return false;
		}
		if (prenom == null) {
			if (other.prenom != null) {
				return false;
			}
		} else if (!prenom.equals(other.prenom)) {
			return false;
		}
		return true;
	}	
	
}
