package entities.commande;

import java.sql.Date;
import java.time.LocalDate;

public class Commande {
	
	private int idCommande;
	private LocalDate dateCommande;
	private int idClient;
	
	//Constructor
	public Commande(int idCommande, Date dateCommandeSQL, int idClient) {
		
		this.idCommande = idCommande;
		this.dateCommande = dateCommandeSQL.toLocalDate();
		this.idClient = idClient;
	}

	
	//Getters and Setters
	public int getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}

	public LocalDate getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(LocalDate dateCommande) {
		this.dateCommande = dateCommande;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	//ToString
	@Override
	public String toString() {
		return "Commande [idCommande=" + idCommande + ", dateCommande=" + dateCommande + ", idClient=" + idClient + "]";
	}

	//Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Commande)) {
			return false;
		}
		Commande other = (Commande) obj;
		if (dateCommande == null) {
			if (other.dateCommande != null) {
				return false;
			}
		} else if (!dateCommande.equals(other.dateCommande)) {
			return false;
		}
		if (idClient != other.idClient) {
			return false;
		}
		if (idCommande != other.idCommande) {
			return false;
		}
		return true;
	}
	
	
	
}
