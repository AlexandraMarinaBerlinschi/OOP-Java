package Services;

import User.NormalUser;
import User.Admin;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, Admin> admini;
    private Map<String, NormalUser> users;


    public UserService() {
        admini = new HashMap<>();
        users = new HashMap<>();
    }
    public void addAdmin(Admin admin) {
        if(admin !=  null && !admini.containsKey(admin.getUsername()))
        {
            admini.put(admin.getUsername(),admin);
            System.out.println("Adminul " + admin.getUsername() + " a fost adaugat");
        }else {
            System.out.println("Nu se poate adauga adminul, va rog sa completati un username valid");
        }
    }

    public boolean addUser(NormalUser user) {
        if (user != null && !users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
            return true;
        } else {
            return false;
        }
    }

        public void applyRedeemCode(String redeemCode) {
            for (NormalUser user : users.values()) {
                if (user.getCodUnic().equals(redeemCode)) {
                    user.incrementReferinte();
                    break;
                }
            }
        }

    public boolean login(String username, String password) {
        if(admini.containsKey(username)) {
            return admini.get(username).verifyPassword(password);
        } else if(users.containsKey(username)) {
            return users.get(username).verifyPassword(password);
        }
        return false;
    }


    }
