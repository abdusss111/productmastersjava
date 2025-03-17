import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ваше имя: ");
        String name = scanner.nextLine();
        User user = new User(name);
        System.out.println("Добро пожаловать, " + user.getName() + "!");
        TwitterConsole app = new TwitterConsole(scanner, user);
        app.start();
    }
}
