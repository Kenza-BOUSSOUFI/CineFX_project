package ma.cinefx;

import ma.cinefx.Service.SalleService;
import ma.cinefx.Model.Salle;

import java.util.List;

public class AppTestSalle {

    public static void main(String[] args) {
        SalleService salleService = new SalleService();

        // 1️⃣ Ajouter une salle
        salleService.ajouterSalle("Salle 1", 100);
        salleService.ajouterSalle("Salle 2", 50);

        // 2️⃣ Lister toutes les salles
        System.out.println("\nListe des salles :");
        List<Salle> salles = salleService.listerToutesLesSalles();
        for (Salle s : salles) {
            System.out.println("ID: " + s.getId() + ", Nom: " + s.getNom() + ", Capacité: " + s.getCapacite());
        }

        // 3️⃣ Modifier une salle
        if (!salles.isEmpty()) {
            Salle premiereSalle = salles.get(0);
            salleService.modifierSalle(premiereSalle.getId(), "Salle Principale", 120);
            System.out.println("\nSalle modifiée : " + salleService.recupererSalleParId(premiereSalle.getId()).getNom());
        }

        // 4️⃣ Supprimer une salle
        if (salles.size() > 1) {
            Salle deuxiemeSalle = salles.get(1);
            salleService.supprimerSalle(deuxiemeSalle.getId());
            System.out.println("\nSalle supprimée : " + deuxiemeSalle.getNom());
        }

        // 5️⃣ Vérifier la liste finale
        System.out.println("\nListe finale des salles :");
        List<Salle> sallesFinales = salleService.listerToutesLesSalles();
        for (Salle s : sallesFinales) {
            System.out.println("ID: " + s.getId() + ", Nom: " + s.getNom() + ", Capacité: " + s.getCapacite());
        }
    }
}
