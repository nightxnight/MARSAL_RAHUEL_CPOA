package vue.application.management;

import java.util.ArrayList;

import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public interface Management<T>{
	
	public abstract TableView<T> getTableModel();
	
	public abstract ArrayList<T> getDatas();
	//FIXME changer l'entete, objet recherche en parametre
	public abstract ArrayList<T> research(T objet);
	
	public abstract Pane getActionPane(T objet, boolean bool);
	public abstract Pane getResearchPane();
	
	public abstract boolean delete(T objet);
}
