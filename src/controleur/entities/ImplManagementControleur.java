package controleur.entities;

public interface ImplManagementControleur<T> {

	public abstract void setFormMode(T objet, boolean bool);
	public abstract void fillForm(T objet);
	
	public abstract void create();
	public abstract void update();
	
	public abstract boolean checkErrors();
	public abstract void retourPage();	
}
