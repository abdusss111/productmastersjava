package medium;

import java.util.HashMap;
import java.util.Map;

class UserRepository {
    private final UserDataCloudDataSource cloudDataSource;
    private final Map<Integer, UserData> cache;

    public UserRepository(UserDataCloudDataSource cloudDataSource) {
        this.cloudDataSource = cloudDataSource;
        this.cache = new HashMap<>();
    }

    public UserData getUserData(int userId) {
        if (cache.containsKey(userId)) {
            System.out.println("Returning cached user data...");
            return cache.get(userId);
        }
        UserData userData = cloudDataSource.fetchUserData(userId);
        cache.put(userId, userData);
        return userData;
    }
}