package ma.cinefx.Service;

import ma.cinefx.Dao.SalleDAO;
import ma.cinefx.Model.Salle;
import ma.cinefx.Exception.EntityAlreadyExistsException;
import ma.cinefx.Exception.EntityNotFoundException;
import ma.cinefx.Exception.EntityPersistenceException;

import java.util.List;

public class SalleService {

    private final SalleDAO salleDAO;

    public SalleService() {
        this.salleDAO = new SalleDAO();
    }

    // Ajouter une salle
    public void ajouterSalle(String nom, int capacite) {
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException("Le nom de la salle ne peut pas être vide");
        }
        if (capacite <= 0) {
            throw new IllegalArgumentException("La capacité doit être supérieure à 0");
        }

        Salle salle = new Salle(nom, capacite);

        try {
            salleDAO.addSalle(salle);
            System.out.println("Salle ajoutée avec succès !");
        } catch (EntityAlreadyExistsException e) {
            System.out.println("Impossible d'ajouter la salle : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur lors de l'ajout de la salle : " + e.getMessage());
        }
    }

    // Récupérer une salle par ID
    public Salle recupererSalleParId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        try {
            return salleDAO.getSalleById(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Salle introuvable : " + e.getMessage());
            return null;
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur lors de la récupération de la salle : " + e.getMessage());
            return null;
        }
    }

    // Lister toutes les salles
    public List<Salle> listerToutesLesSalles() {
        try {
            return salleDAO.getAllSalles();
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur lors de la récupération des salles : " + e.getMessage());
            return List.of();
        }
    }

    // Mettre à jour une salle
    public void modifierSalle(Long id, String nouveauNom, int nouvelleCapacite) {
        try {
            Salle salle = salleDAO.getSalleById(id);
            if (nouveauNom != null && !nouveauNom.isEmpty()) salle.setNom(nouveauNom);
            if (nouvelleCapacite > 0) salle.setCapacite(nouvelleCapacite);
            salleDAO.updateSalle(salle);
            System.out.println("Salle mise à jour avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Salle introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur lors de la mise à jour de la salle : " + e.getMessage());
        }
    }

    // Supprimer une salle
    public void supprimerSalle(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        try {
            salleDAO.deleteSalle(id);
            System.out.println("Salle supprimée avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Salle introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur lors de la suppression de la salle : " + e.getMessage());
        }
    }
}
