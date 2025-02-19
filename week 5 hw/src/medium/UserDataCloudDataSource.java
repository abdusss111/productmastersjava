package medium;

public class UserDataCloudDataSource {
    public UserData fetchUserData(int userId) {
        System.out.println("Fetching user data from the cloud...");
        return new UserData(userId, "User" + userId, "user" + userId + "@example.com");
    }
}
