package dao;

import tourism.TouristPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TouristPackageDao {
    private final Map<Integer, TouristPackage> packages = new HashMap<>();
    private static int nextId = 1;

    public boolean addPackage(TouristPackage touristPackage) {
        if (touristPackage == null) {
            return false;
        }
        int id = generateId();
        TouristPackage packageWithId = new TouristPackage(touristPackage.getNume(), touristPackage.getPret(), touristPackage.getDurata(), touristPackage.getRating(), touristPackage.getDestinatie(), touristPackage.getNrPersoane());
        packageWithId.setReviews(touristPackage.getReviews());
        packages.put(id, packageWithId);
        return true;
    }

    public TouristPackage getPackageById(int id) {
        return packages.get(id);
    }

    public List<TouristPackage> getAllPackages() {
        return new ArrayList<>(packages.values());
    }

    public boolean updatePackage(TouristPackage touristPackage) {
        if (touristPackage == null || !packages.containsKey(touristPackage.getId())) {
            return false;
        }
        packages.put(touristPackage.getId(), touristPackage);
        return true;
    }

    public boolean deletePackage(int id) {
        TouristPackage deletedPackage = packages.remove(id);
        if (deletedPackage != null) {
            return true;
        }
        return false;
    }

    private static int generateId() {
        return nextId++;
    }

}
