package ma.cinefx.Service;

import ma.cinefx.Dao.FilmDAO;
import ma.cinefx.Model.Film;
import ma.cinefx.Exception.*;

import java.util.List;

public class FilmService {

    private final FilmDAO filmDAO;

    public FilmService() {
        this.filmDAO = new FilmDAO();
    }

    // Ajouter un film (avec vérification de règles métier si besoin)
    public void ajouterFilm(String titre, String genre, int duree) {
        if (titre == null || titre.isEmpty()) {
            throw new IllegalArgumentException("Le titre du film ne peut pas être vide");
        }
        if (duree <= 0) {
            throw new IllegalArgumentException("La durée doit être supérieure à 0");
        }

        Film film = new Film(titre, genre, duree);

        try {
            filmDAO.addFilm(film);
            System.out.println("Film ajouté avec succès !");
        } catch (FilmAlreadyExistsException e) {
            System.out.println("Impossible d'ajouter le film : " + e.getMessage());
        }
    }


    // Récupérer un film par ID
    public Film recupererFilmParId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        return filmDAO.getFilmById(id);
    }

    // Lister tous les films
    public List<Film> listerTousLesFilms() {
        return filmDAO.getAllFilms();
    }

    // Supprimer un film par ID
    public void supprimerFilm(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        filmDAO.deleteFilm(id);
    }

    // Mettre à jour un film
    public void modifierFilm(Long id, String nouveauTitre, String nouveauGenre, int nouvelleDuree) {
        Film film = filmDAO.getFilmById(id);
        if (nouveauTitre != null && !nouveauTitre.isEmpty()) film.setTitre(nouveauTitre);
        if (nouveauGenre != null && !nouveauGenre.isEmpty()) film.setGenre(nouveauGenre);
        if (nouvelleDuree > 0) film.setDuree(nouvelleDuree);
        filmDAO.updateFilm(film);
    }
}
