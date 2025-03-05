package medium;

public class Main {
    public static void main(String[] args) {
        UserDataCloudDataSource cloudDataSource = new UserDataCloudDataSource();
        UserRepository userRepository = new UserRepository(cloudDataSource);

        System.out.println(userRepository.getUserData(1)); // Fetches from cloud
        System.out.println(userRepository.getUserData(1)); // Retrieves from cache
        System.out.println(userRepository.getUserData(2)); // Fetches from cloud
    }
}
