import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ваше имя: ");
        String name = scanner.nextLine();
        User user = new User(name);
        System.out.println("Добро пожаловать, " + user.getName() + "!");

        List<Post> posts = PostDB.loadPosts();

        TwitterConsole app = new TwitterConsole(posts, user);
        app.start();
    }
}
