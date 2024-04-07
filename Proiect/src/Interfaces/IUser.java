package interfaces;

import tourism.TouristPackage;

public interface IUser {
    void updatePassword(String password);
    boolean verifyPassword(String password);


}