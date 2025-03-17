import java.util.*;

class TwitterConsole {
    private final Scanner scanner;
    private final User currentUser;
    private final List<Post> posts;

    public TwitterConsole(Scanner scanner, User currentUser) {
        this.scanner = scanner;
        this.currentUser = currentUser;
        this.posts = PostDB.loadPosts();
        if (posts.isEmpty()) initializeSamplePosts();
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Twitter Console ===");
            System.out.println("1. Написать пост");
            System.out.println("2. Лайкнуть пост");
            System.out.println("3. Сделать репост");
            System.out.println("4. Показать все посты");
            System.out.println("5. Показать популярные посты");
            System.out.println("6. Показать мои посты");
            System.out.println("7. Выход");
            System.out.print("Выберите действие: ");

            int choice = getI();
            switch (choice) {
                case 1 -> createPost();
                case 2 -> likePost();
                case 3 -> repostPost();
                case 4 -> showAllPosts();
                case 5 -> showPopularPosts();
                case 6 -> showUserPosts();
                case 7 -> {
                    PostDB.savePosts(posts);
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Некорректный ввод, попробуйте снова.");
            }
        }
    }

    private void createPost() {
        System.out.print("Введите текст поста (макс. 280 символов): ");
        String content = scanner.nextLine();
        if (content.length() > 280) {
            System.out.println("Ошибка: пост не должен превышать 280 символов.");
            return;
        }
        posts.add(new Post(currentUser, content));
        PostDB.savePosts(posts);
        System.out.println("Пост добавлен!");
    }

    private void likePost() {
        System.out.print("Введите ID поста, который хотите лайкнуть: ");
        String postId = getIntInput();
        posts.stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .ifPresentOrElse(post -> {
                    post.like();
                    PostDB.savePosts(posts);
                    System.out.println("Лайк добавлен!");
                }, () -> System.out.println("Пост с таким ID не найден."));
    }

    private void repostPost() {
        System.out.print("Введите ID поста, который хотите репостнуть: ");
        int postId = getIntInput();
        posts.stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .ifPresentOrElse(post -> {
                    post.repost();
                    posts.add(new Post(currentUser, "[Репост] " + post.getContent()));
                    PostDB.savePosts(posts);
                    System.out.println("Репост добавлен!");
                }, () -> System.out.println("Пост с таким ID не найден."));
    }

    private void showAllPosts() {
        posts.stream()
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .forEach(System.out::println);
    }

    private void showPopularPosts() {
        System.out.print("Сколько популярных постов показать? ");
        int count = getIntInput();
        posts.stream()
                .sorted(Comparator.comparing(Post::getLikes).reversed())
                .limit(count)
                .forEach(System.out::println);
    }

    private void showUserPosts() {
        posts.stream()
                .filter(post -> post.getAuthor().equals(currentUser))
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .forEach(System.out::println);
    }

    private void initializeSamplePosts() {
        posts.add(new Post(new User("Alice"), "Привет, мир!"));
        posts.add(new Post(new User("Bob"), "Сегодня отличный день!"));
        posts.add(new Post(new User("Charlie"), "Люблю программировать на Java."));
        PostDB.savePosts(posts);
    }

    private String getID() {
        while (true) {
            try {
                return scanner.nextLine().trim();
            } catch (NumberFormatException e) {
                System.out.print("Ошибка ввода! Введите ID: ");
            }
        }
    }
}
