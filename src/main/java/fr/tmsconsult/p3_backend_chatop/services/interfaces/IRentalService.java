package fr.tmsconsult.p3_backend_chatop.services.interfaces;

import fr.tmsconsult.p3_backend_chatop.entities.Rental;

import java.util.List;
/**
 * Interface pour la gestion des locations (Rental).
 */
public interface IRentalService {
    /**
     * Recherche une location (Rental) par son identifiant.
     *
     * @param id l'identifiant unique de la location à rechercher
     * @return la location correspondante à l'identifiant fourni, ou {@code null} si aucune location n'est trouvée
     */
    Rental findRentalById(Integer id);
    /**
     * Récupère la liste de toutes les locations disponibles.
     *
     * @return une liste contenant toutes les locations (Rental)
     */
    List<Rental> getAllRentals();
    /**
     * Ajoute une nouvelle location dans le système.
     *
     * @param rental la location à ajouter
     */
    void addRental(Rental rental);
    /**
     * Met à jour les informations d'une location existante.
     *
     * @param rentalToUpdate la location avec les nouvelles informations à mettre à jour
     */
    void updateRental(Rental rentalToUpdate);
}
