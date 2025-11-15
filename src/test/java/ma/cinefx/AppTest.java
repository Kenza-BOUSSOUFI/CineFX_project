package ma.cinefx;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.cinefx.util.HibernateUtil;
import ma.cinefx.Model.Film; // importe tes entités

public class AppTest {
    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;

        try {
            // Ouvrir une session
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            System.out.println("✅ Connexion à la base réussie !");

            // Test : créer un film pour vérifier que la table est bien créée
            Film filmTest = new Film();
            filmTest.setTitre("Avengers");
            filmTest.setGenre("Action");
            filmTest.setDuree(150);
            session.persist(filmTest);

            transaction.commit();
            System.out.println("✅ Film ajouté à la base, tables créées si nécessaire !");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        // Fermer la SessionFactory
        HibernateUtil.shutdown();
    }
}