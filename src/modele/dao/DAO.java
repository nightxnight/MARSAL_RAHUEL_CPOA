package modele.dao;

public interface DAO <T>{
	
	public abstract boolean create(T objet);
	public abstract boolean update(T objetModife, T objetRemplacant );
	public abstract boolean delete(T objet);

}
