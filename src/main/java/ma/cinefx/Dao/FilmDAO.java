package ma.cinefx.Dao;

import ma.cinefx.Model.Film;
import ma.cinefx.Exception.*;
import ma.cinefx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FilmDAO {

    // Ajouter un film
    public void addFilm(Film film) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Vérifier si un film avec le même titre existe déjà
            Query<Film> query = session.createQuery("FROM Film WHERE titre = :titre", Film.class);
            query.setParameter("titre", film.getTitre());
            if (!query.list().isEmpty()) {
                throw new FilmAlreadyExistsException("Le film \"" + film.getTitre() + "\" existe déjà !");
            }

            session.save(film);
            transaction.commit();
        } catch (FilmAlreadyExistsException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new FilmPersistenceException("Erreur lors de l'ajout du film", e);
        }
    }

    // Trouver un film par ID
    public Film getFilmById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Film film = session.get(Film.class, id);
            if (film == null) {
                throw new FilmNotFoundException("Film avec l'ID " + id + " non trouvé");
            }
            return film;
        } catch (FilmNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new FilmPersistenceException("Erreur lors de la récupération du film", e);
        }
    }

    // Lister tous les films
    public List<Film> getAllFilms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Film", Film.class).list();
        } catch (Exception e) {
            throw new FilmPersistenceException("Erreur lors de la récupération des films", e);
        }
    }
    public void updateFilm(Film film) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(film);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new FilmPersistenceException("Erreur lors de la mise à jour du film", e);
        }
    }


    // Supprimer un film
    public void deleteFilm(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Film film = session.get(Film.class, id);
            if (film == null) {
                throw new FilmNotFoundException("Film avec l'ID " + id + " non trouvé");
            }
            session.delete(film);
            transaction.commit();
        } catch (FilmNotFoundException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new FilmPersistenceException("Erreur lors de la suppression du film", e);
        }
    }
}
