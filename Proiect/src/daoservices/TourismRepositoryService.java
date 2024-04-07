package daoservices;

import dao.TouristPackageDao;
import tourism.TouristPackage;
import tourism.Destination;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TourismRepositoryService {
    private final TouristPackageDao packageDao;

    public TourismRepositoryService() {
        this.packageDao = new TouristPackageDao();
    }

    public void addPackage(TouristPackage touristPackage) {
        packageDao.addPackage(touristPackage);
    }

    public List<TouristPackage> getAllPackages() {
        return packageDao.getAllPackages();
    }

    public TouristPackage getPackageById(int id) {
        return packageDao.getPackageById(id);
    }

    public boolean updatePackage(TouristPackage updatedPackage) {
        return packageDao.updatePackage(updatedPackage);
    }

    public boolean deletePackage(int id) {
        return packageDao.deletePackage(id);
    }

    public List<TouristPackage> searchByName(String name) {
        return packageDao.getAllPackages().stream()
                .filter(p -> p.getNume().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<TouristPackage> searchByDestination(String destination) {
        return packageDao.getAllPackages().stream()
                .filter(p -> p.getDestinatie().getNumeDestinatie().toLowerCase().contains(destination.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<TouristPackage> filterPackagesByPrice(float minPrice, float maxPrice) {
        return packageDao.getAllPackages().stream()
                .filter(p -> p.getPret() >= minPrice && p.getPret() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<TouristPackage> filterPackagesByRating(float minRating, float maxRating) {
        return packageDao.getAllPackages().stream()
                .filter(p -> p.getRating() >= minRating && p.getRating() <= maxRating)
                .collect(Collectors.toList());
    }

}
