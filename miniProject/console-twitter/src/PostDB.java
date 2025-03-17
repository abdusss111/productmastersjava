import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PostDB {
    private static final String FILE_NAME = "posts.ser";

    public static void savePosts(List<Post> posts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(posts);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении постов: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Post> loadPosts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Post>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке постов: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
