package ma.cinefx.Service;

import ma.cinefx.Dao.FilmDAO;
import ma.cinefx.Exception.EntityAlreadyExistsException;
import ma.cinefx.Exception.EntityNotFoundException;
import ma.cinefx.Exception.EntityPersistenceException;
import ma.cinefx.Model.Film;

import java.util.List;

public class FilmService {

    private final FilmDAO filmDAO;

    public FilmService() {
        this.filmDAO = new FilmDAO();
    }

    // Ajouter un film
    public void ajouterFilm(String titre, String genre, int duree) {
        if (titre == null || titre.isEmpty()) throw new IllegalArgumentException("Le titre du film ne peut pas être vide");
        if (duree <= 0) throw new IllegalArgumentException("La durée doit être supérieure à 0");

        Film film = new Film(titre, genre, duree);

        try {
            filmDAO.addFilm(film);
            System.out.println("Film ajouté avec succès !");
        } catch (EntityAlreadyExistsException e) {
            System.out.println("Impossible d'ajouter le film : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique : " + e.getMessage());
        }
    }

    // Récupérer un film par ID
    public Film recupererFilmParId(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalide");

        try {
            return filmDAO.getFilmById(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Film introuvable : " + e.getMessage());
            return null;
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la récupération : " + e.getMessage());
            return null;
        }
    }

    // Lister tous les films
    public List<Film> listerTousLesFilms() {
        try {
            return filmDAO.getAllFilms();
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la récupération des films : " + e.getMessage());
            return List.of();
        }
    }

    // Mettre à jour un film
    public void modifierFilm(Long id, String nouveauTitre, String nouveauGenre, int nouvelleDuree) {
        try {
            Film film = filmDAO.getFilmById(id);
            if (nouveauTitre != null && !nouveauTitre.isEmpty()) film.setTitre(nouveauTitre);
            if (nouveauGenre != null && !nouveauGenre.isEmpty()) film.setGenre(nouveauGenre);
            if (nouvelleDuree > 0) film.setDuree(nouvelleDuree);
            filmDAO.updateFilm(film);
            System.out.println("Film mis à jour avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Film introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la mise à jour : " + e.getMessage());
        }
    }

    // Supprimer un film
    public void supprimerFilm(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalide");

        try {
            filmDAO.deleteFilm(id);
            System.out.println("Film supprimé avec succès !");
        } catch (EntityNotFoundException e) {
            System.out.println("Film introuvable : " + e.getMessage());
        } catch (EntityPersistenceException e) {
            System.out.println("Erreur technique lors de la suppression : " + e.getMessage());
        }
    }
}
