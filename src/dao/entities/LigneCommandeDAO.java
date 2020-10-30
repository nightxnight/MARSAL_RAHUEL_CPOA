package dao.entities;

import java.util.ArrayList;

import entities.LigneCommande;

public interface LigneCommandeDAO {

	public abstract boolean create(LigneCommande objet);
	public abstract boolean update(LigneCommande objet);
	public abstract boolean delete(LigneCommande objet);
	public abstract ArrayList<LigneCommande> getById(int id);
	public abstract ArrayList<LigneCommande> getAll();
}
