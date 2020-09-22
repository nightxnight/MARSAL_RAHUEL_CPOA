package modele.dao;

import java.util.ArrayList;

public interface DAO <T>{
	
	public abstract boolean create(T objet);
	public abstract boolean update(int idObjetModifie, T objetRemplacant );
	public abstract boolean delete(T objet);
	public abstract ArrayList<T> getAll();
	

}
