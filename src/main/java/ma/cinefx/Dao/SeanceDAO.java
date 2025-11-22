package ma.cinefx.Dao;

import ma.cinefx.Exception.EntityAlreadyExistsException;
import ma.cinefx.Exception.EntityNotFoundException;
import ma.cinefx.Exception.EntityPersistenceException;
import ma.cinefx.Model.Seance;
import ma.cinefx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class SeanceDAO {

    // Ajouter une séance
    public void addSeance(Seance seance) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Vérifier si la salle a déjà une séance à la même dateHeure
            Query<Seance> query = session.createQuery(
                    "FROM Seance WHERE salle.id = :salleId AND dateHeure = :dateHeure", Seance.class);
            query.setParameter("salleId", seance.getSalle().getId());
            query.setParameter("dateHeure", seance.getDateHeure());

            if (!query.list().isEmpty()) {
                throw new EntityAlreadyExistsException("Seance",
                        "Salle ID=" + seance.getSalle().getId() + " à " + seance.getDateHeure());
            }

            session.save(seance);
            transaction.commit();

        } catch (EntityAlreadyExistsException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new EntityPersistenceException("Erreur lors de l'ajout de la séance", e);
        }
    }

    // Récupérer une séance par ID
    public Seance getSeanceById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Seance seance = session.get(Seance.class, id);
            if (seance == null) {
                throw new EntityNotFoundException("Seance", "ID=" + id);
            }
            return seance;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EntityPersistenceException("Erreur lors de la récupération de la séance", e);
        }
    }

    // Lister toutes les séances
    public List<Seance> getAllSeances() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Seance", Seance.class).list();
        } catch (Exception e) {
            throw new EntityPersistenceException("Erreur lors de la récupération des séances", e);
        }
    }

    // Mettre à jour une séance
    public void updateSeance(Seance seance) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(seance);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new EntityPersistenceException("Erreur lors de la mise à jour de la séance", e);
        }
    }

    // Supprimer une séance
    public void deleteSeance(Long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Seance seance = session.get(Seance.class, id);
            if (seance == null) {
                throw new EntityNotFoundException("Seance", "ID=" + id);
            }
            session.delete(seance);
            transaction.commit();
        } catch (EntityNotFoundException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new EntityPersistenceException("Erreur lors de la suppression de la séance", e);
        }
    }
}
