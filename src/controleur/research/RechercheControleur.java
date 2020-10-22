package controleur.research;

import dao.Persistance;

public interface RechercheControleur<T>{
	
	public abstract T getResearchParameters();
	public abstract void setPersistance(Persistance persistance);
}
