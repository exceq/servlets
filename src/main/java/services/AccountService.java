package services;

import models.UserProfile;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    private static Map<String, UserProfile> loginToProfile;
    private static Map<String, UserProfile> sessionIdToProfile;

    static {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();

        loginToProfile.put("admin", new UserProfile("admin", "admin", "admin@mail.ru"));
        loginToProfile.put("egor", new UserProfile("egor","cool","egor@mail.ru"));
    }

    public static void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public static UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public static UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public static void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public static void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}