package ma.cinefx.Service;

import ma.cinefx.Dao.ReservationDAO;
import ma.cinefx.Model.Reservation;
import ma.cinefx.Exception.EntityAlreadyExistsException;
import ma.cinefx.Exception.EntityNotFoundException;
import ma.cinefx.Exception.EntityPersistenceException;

import java.util.List;

public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService() {
        this.reservationDAO = new ReservationDAO();
    }

    // Ajouter une réservation
    public void ajouterReservation(Reservation reservation) {
        try {
            reservationDAO.addReservation(reservation);
            System.out.println("Réservation ajoutée avec succès !");
        } catch (EntityAlreadyExistsException e) {
            System.out.println("Impossible d'ajouter la réservation : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de l'ajout : " + e.getMessage());
        }
    }

    // Récupérer une réservation par ID
    public Reservation recupererReservationParId(Long id) {
        try {
            return reservationDAO.getReservationById(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Réservation introuvable : " + e.getMessage());
            return null;
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
            return null;
        }
    }

    // Lister toutes les réservations
    public List<Reservation> listerToutesLesReservations() {
        try {
            return reservationDAO.getAllReservations();
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur lors de la récupération des réservations : " + e.getMessage());
            return List.of();
        }
    }

    // Mettre à jour une réservation
    public void modifierReservation(Reservation reservation) {
        try {
            reservationDAO.updateReservation(reservation);
            System.out.println("Réservation mise à jour avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Réservation introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    // Supprimer une réservation
    public void supprimerReservation(Long id) {
        try {
            reservationDAO.deleteReservation(id);
            System.out.println("Réservation supprimée avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Réservation introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
