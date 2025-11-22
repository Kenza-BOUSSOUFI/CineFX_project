package ma.cinefx.Service;

import ma.cinefx.Dao.TarifDAO;
import ma.cinefx.Exception.EntityAlreadyExistsException;
import ma.cinefx.Exception.EntityNotFoundException;
import ma.cinefx.Exception.EntityPersistenceException;
import ma.cinefx.Model.Tarif;

import java.util.List;

public class TarifService {

    private final TarifDAO tarifDAO;

    public TarifService() {
        this.tarifDAO = new TarifDAO();
    }

    // Ajouter un tarif
    public void ajouterTarif(String libelle, double prix) {
        if (libelle == null || libelle.isEmpty()) throw new IllegalArgumentException("Le libellé ne peut pas être vide");
        if (prix <= 0) throw new IllegalArgumentException("Le prix doit être supérieur à 0");

        Tarif tarif = new Tarif(libelle, prix);

        try {
            tarifDAO.addTarif(tarif);
            System.out.println("Tarif ajouté avec succès !");
        } catch (EntityAlreadyExistsException e) {
            System.out.println("Impossible d'ajouter le tarif : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de l'ajout du tarif : " + e.getMessage());
        }
    }

    // Récupérer un tarif par ID
    public Tarif recupererTarifParId(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalide");

        try {
            return tarifDAO.getTarifById(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Tarif introuvable : " + e.getMessage());
            return null;
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la récupération du tarif : " + e.getMessage());
            return null;
        }
    }

    // Lister tous les tarifs
    public List<Tarif> listerTousLesTarifs() {
        try {
            return tarifDAO.getAllTarifs();
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la récupération des tarifs : " + e.getMessage());
            return List.of();
        }
    }

    // Mettre à jour un tarif
    public void modifierTarif(Long id, String nouveauLibelle, double nouveauPrix) {
        try {
            Tarif tarif = tarifDAO.getTarifById(id);
            if (nouveauLibelle != null && !nouveauLibelle.isEmpty()) tarif.setLibelle(nouveauLibelle);
            if (nouveauPrix > 0) tarif.setPrix(nouveauPrix);
            tarifDAO.updateTarif(tarif);
            System.out.println("Tarif mis à jour avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Tarif introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la mise à jour : " + e.getMessage());
        }
    }

    // Supprimer un tarif
    public void supprimerTarif(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalide");

        try {
            tarifDAO.deleteTarif(id);
            System.out.println("Tarif supprimé avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Tarif introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la suppression : " + e.getMessage());
        }
    }
}
