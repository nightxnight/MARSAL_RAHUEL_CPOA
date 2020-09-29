package dao;

import java.util.ArrayList;

public interface DAO <T>{
	
	public abstract boolean create(T objet);
	public abstract boolean update(T objet);
	public abstract boolean delete(T objet);
	//On renvoie un type objet afin de pouvoir obtenir des ArrayList lors de cl� compos�es
	//Inconv�nient : cast au retour de la fonction
	public abstract Object getById(int id);
	public abstract ArrayList<T> getAll();
}
