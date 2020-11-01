# MARSAL_RAHUEL_CPOA

Qu'est ce qui fonctionne ? Absolument tout.

Impréçision : 
"Sur la fenêtre des clients, l'affichage est trié par ordre alphabétique des noms des clients, ou par ville"
  -> L'affichage des clients est par défaut trié par Nom, prénom puis par ville.

"Un bouton d'ajout activé lorsqu'aucune sélection n'est en cours permet d'ajouter un nouvel élément."
  -> Qu'est ce qui m'empêche de créer un élément si j'en ai sélectionner un ? Nous avons décidé de laisser l'option de création disponible.

Addons : 
  -Fichier de configuration utilisateur
  -Possibilité de basculer l'application d'un thème clair à un thème sombre et inversement

Réalisation des tâches : 

Modèle : 
  DAOFactory, MySQLDAOFactory et ListeMemoireDAOFactory : MARSAL Rémi
  ProduitDAO, CategorieDAO, ClientDAO, CommandeDAO, LigneCommandeDAO (listeMemoire et MySQL) : MARSAL Rémi et Victor RAHUEL

Test unitaire : MARSAL Rémi

Classe métier : MARSAL Rémi et Victor RAHUEL

Utils : 
  Regex : MARSAL Rémi et Victor RAHUEL
  XML, Converter : MARSAL Rémi
  

Controleur : 
  MainControleur : MARSAL Rémi et Victor RAHUEL
  ManagementControleur : MARSAL Rémi
  ControleurProduit et ControleurCommande : MARSAL Rémi 
  ControleurClient et ControleurCategorie : Victor RAHUEL

Vue : 
  CustomComponents : MARSAL Rémi
  UIManagement et Entities : MARSAL Rémi
  ProduitUIMangement, CategorieUIManagement, CommandeUIManagement, ClientUIManagement : MARSAL Rémi et Victor RAHUEL

Resources : 
  FXML :
    ManagementPane, ProduitForm, Commande Form + panelRecherche correspondant : MARSAL Rémi
    ClientForm, Categorie Form + panelRecherche correspondant : Victor RAHUEL
    Application.fxml : MARSAL Rémi et Victor RAHUEL

  CSS :
    Theme clair : Victor RAHUEL
    Theme sombre : MARSAL Rémi

ADDONS : MARSAL Rémi

Tests de l'application : MARSAL Rémi et Victor RAHUEL

Investissements en % dans le projet :
  MARSAL Rémi 60%
  RAHUEL Victor 40%
