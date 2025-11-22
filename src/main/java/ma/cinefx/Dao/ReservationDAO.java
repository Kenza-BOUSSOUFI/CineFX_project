package ma.cinefx.Dao;

import ma.cinefx.Exception.EntityAlreadyExistsException;
import ma.cinefx.Exception.EntityNotFoundException;
import ma.cinefx.Exception.EntityPersistenceException;
import ma.cinefx.Model.Reservation;
import ma.cinefx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ReservationDAO {

    // Ajouter une réservation
    public void addReservation(Reservation reservation) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Optionnel : vérifier si la réservation existe déjà pour le même client et séance
            Query<Reservation> query = session.createQuery(
                    "FROM Reservation WHERE seance.id = :seanceId AND clientNom = :clientNom", Reservation.class);
            query.setParameter("seanceId", reservation.getSeance().getId());
            query.setParameter("clientNom", reservation.getClientNom());

            if (!query.list().isEmpty()) {
                throw new EntityAlreadyExistsException("Reservation", reservation.getClientNom());
            }

            session.save(reservation);
            transaction.commit();

        } catch (EntityAlreadyExistsException e) {
            if (transaction != null) transaction.rollback();
            throw e;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new EntityPersistenceException("Erreur lors de l'ajout de la réservation", e);
        }
    }

    // Trouver une réservation par ID
    public Reservation getReservationById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Reservation reservation = session.get(Reservation.class, id);

            if (reservation == null) {
                throw new EntityNotFoundException("Reservation", "ID=" + id);
            }

            return reservation;

        } catch (EntityNotFoundException e) {
            throw e;

        } catch (Exception e) {
            throw new EntityPersistenceException("Erreur lors de la récupération de la réservation", e);
        }
    }

    // Lister toutes les réservations
    public List<Reservation> getAllReservations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Reservation", Reservation.class).list();
        } catch (Exception e) {
            throw new EntityPersistenceException("Erreur lors de la récupération des réservations", e);
        }
    }

    // Mettre à jour une réservation
    public void updateReservation(Reservation reservation) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(reservation);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new EntityPersistenceException("Erreur lors de la mise à jour de la réservation", e);
        }
    }

    // Supprimer une réservation
    public void deleteReservation(Long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Reservation reservation = session.get(Reservation.class, id);

            if (reservation == null) {
                throw new EntityNotFoundException("Reservation", "ID=" + id);
            }

            session.delete(reservation);
            transaction.commit();

        } catch (EntityNotFoundException e) {
            if (transaction != null) transaction.rollback();
            throw e;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new EntityPersistenceException("Erreur lors de la suppression de la réservation", e);
        }
    }
}
