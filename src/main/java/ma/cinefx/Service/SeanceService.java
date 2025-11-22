package ma.cinefx.Service;

import ma.cinefx.Dao.SeanceDAO;
import ma.cinefx.Exception.EntityAlreadyExistsException;
import ma.cinefx.Exception.EntityNotFoundException;
import ma.cinefx.Exception.EntityPersistenceException;
import ma.cinefx.Model.Seance;

import java.util.List;

public class SeanceService {

    private final SeanceDAO seanceDAO;

    public SeanceService() {
        this.seanceDAO = new SeanceDAO();
    }

    // Ajouter une séance
    public void ajouterSeance(Seance seance) {
        if (seance == null) throw new IllegalArgumentException("Séance invalide");

        try {
            seanceDAO.addSeance(seance);
            System.out.println("Séance ajoutée avec succès !");
        } catch (EntityAlreadyExistsException e) {
            System.out.println("Impossible d'ajouter la séance : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de l'ajout de la séance : " + e.getMessage());
        }
    }

    // Récupérer une séance par ID
    public Seance recupererSeanceParId(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalide");

        try {
            return seanceDAO.getSeanceById(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Séance introuvable : " + e.getMessage());
            return null;
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la récupération de la séance : " + e.getMessage());
            return null;
        }
    }

    // Lister toutes les séances
    public List<Seance> listerToutesLesSeances() {
        try {
            return seanceDAO.getAllSeances();
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la récupération des séances : " + e.getMessage());
            return List.of();
        }
    }

    // Mettre à jour une séance
    public void modifierSeance(Seance seance) {
        if (seance == null) throw new IllegalArgumentException("Séance invalide");

        try {
            seanceDAO.updateSeance(seance);
            System.out.println("Séance mise à jour avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Séance introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la mise à jour : " + e.getMessage());
        }
    }

    // Supprimer une séance
    public void supprimerSeance(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalide");

        try {
            seanceDAO.deleteSeance(id);
            System.out.println("Séance supprimée avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Séance introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la suppression : " + e.getMessage());
        }
    }
}
