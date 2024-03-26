package Interfaces;

public interface IUser {
    void updatePassword(String password);
    boolean verifyPassword(String password);
}