# CineFX

## Description
CineFX est une application de gestion d'un cinéma.  
Elle permet de gérer les films, les salles, les séances, les réservations et les tarifs.

## Fonctionnalités
- Programmer des séances par salle
- Gérer les réservations
- Suivre les recettes par film et par séance
- Gestion des utilisateurs (si applicable)

## Technologies utilisées
- **Java**
- **MySQL** pour la base de données
- **Hibernate** pour la connexion à la base
- **IntelliJ IDEA** comme IDE


## Installation et exécution
1. Cloner le projet :

git clone https://github.com/Kenza-BOUSSOUFI/CineFX_project.git
Ouvrir le projet avec IntelliJ IDEA.

2. Créer une base de données MySQL vide "CineFX_project" (modifier hibernate.cfg.xml si nécessaire).

3. Exécuter la classe App.java pour tester la connexion et créer les tables automatiquement.


## Structure du projet
- `src/main/java` : code source Java
- `src/main/resources` : fichiers de configuration et ressources
  - `hibernate.cfg.xml` : configuration Hibernate
  - `view/` : interface utilisateur (FXML) et styles (CSS)
- `out/` : dossiers de compilation (ignorés par Git)



Auteur
Kenza Boussoufi