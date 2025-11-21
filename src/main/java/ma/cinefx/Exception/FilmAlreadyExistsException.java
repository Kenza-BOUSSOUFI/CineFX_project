package ma.cinefx.Exception;

public class FilmAlreadyExistsException extends RuntimeException {
    public FilmAlreadyExistsException(String message) {
        super(message);
    }
}
