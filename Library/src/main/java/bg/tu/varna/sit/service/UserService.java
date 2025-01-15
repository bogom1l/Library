package bg.tu.varna.sit.service;

import bg.tu.varna.sit.dao.JAXBParser;
import bg.tu.varna.sit.data.User;
import bg.tu.varna.sit.data.UsersWrapper;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static final String USERS_XML_PATH = "users.xml"; // Path to your users XML file
    private final UsersWrapper usersWrapper;

    public UserService() {
        usersWrapper = new UsersWrapper();
    }


    // Adds a user to the users.xml file
    public void addUser(String username, String password) {
        UsersWrapper usersWrapper = JAXBParser.loadObjectFromXML(USERS_XML_PATH, UsersWrapper.class);
        if (usersWrapper == null) {
            usersWrapper = new UsersWrapper();
        }
        User newUser = new User(username, password, false); // false means user is not logged in by default
        usersWrapper.getUsers().add(newUser);
        JAXBParser.saveObjectToXML(USERS_XML_PATH, usersWrapper);
        System.out.println("User " + username + " added.");
    }

    // Removes a user from the users.xml file
    public void removeUser(String username) {
        UsersWrapper usersWrapper = JAXBParser.loadObjectFromXML(USERS_XML_PATH, UsersWrapper.class);
        if (usersWrapper != null) {
            Optional<User> userToRemove = usersWrapper.getUsers().stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst();
            userToRemove.ifPresent(user -> {
                usersWrapper.getUsers().remove(user);
                JAXBParser.saveObjectToXML(USERS_XML_PATH, usersWrapper);
                System.out.println("User " + username + " removed.");
            });
        } else {
            System.out.println("User " + username + " not found.");
        }
    }

    // Get all users (for display or validation purposes)
    public List<User> getAllUsers() {
        UsersWrapper usersWrapper = JAXBParser.loadObjectFromXML(USERS_XML_PATH, UsersWrapper.class);
        return usersWrapper != null ? usersWrapper.getUsers() : List.of();
    }

    // Find a user by username (for login, etc.)
    public Optional<User> getUserByUsername(String username) {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
}