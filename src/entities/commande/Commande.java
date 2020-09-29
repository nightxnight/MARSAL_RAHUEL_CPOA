package entities.commande;

import java.sql.Date;
import java.time.LocalDate;

public class Commande {
	
	private int idCommande;
	private LocalDate dateCommande;
	private int idClient;
	
	//Constructor
	public Commande(LocalDate dateCommande, int idClient) {
		this.dateCommande = dateCommande;
		this.idClient = idClient;
	}
	
	public Commande(Date dateCommande, int idClient) {
		this(dateCommande.toLocalDate(), idClient);
	}
	
	public Commande(int idCommande, LocalDate dateCommande, int idClient) {
		this(dateCommande, idClient);
		this.idCommande = idCommande;
	}
	
	public Commande(int idCommande, Date dateCommandeSQL, int idClient) {
		this(idCommande, dateCommandeSQL.toLocalDate(), idClient);
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
		if (idClient != other.idClient) {
			return false;
		}
		return true;
	}
	
	
	
}
