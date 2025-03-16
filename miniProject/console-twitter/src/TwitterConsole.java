import java.util.*;

public class TwitterConsole {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Post> posts;
    private User currentUser;

    public TwitterConsole(List<Post> posts, User currentUser) {
        this.posts = posts;
        this.currentUser = currentUser;
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Twitter Console ===");
            System.out.println("1. Написать пост");
            System.out.println("2. Лайкнуть пост");
            System.out.println("3. Сделать репост");
            System.out.println("4. Комментировать пост");
            System.out.println("5. Показать все посты");
            System.out.println("6. Показать мои посты");
            System.out.println("7. Выход");
            System.out.print("Выберите действие: ");

            switch (getIntInput()) {
                case 1 -> createPost();
                case 2 -> likePost();
                case 3 -> repostPost();
                case 4 -> commentOnPost();
                case 5 -> showAllPosts();
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
        System.out.print("Введите текст поста: ");
        String content = scanner.nextLine();
        Post newPost = new Post(currentUser, content);
        posts.add(newPost);
        PostDB.savePosts(posts);
        System.out.println("Пост опубликован!");
    }

    private void likePost() {
        Post post = findPostById();
        if (post != null) {
            post.like();
            PostDB.savePosts(posts);
            System.out.println("Лайк добавлен!");
        }
    }

    private void repostPost() {
        Post post = findPostById();
        if (post != null) {
            post.repost();
            PostDB.savePosts(posts);
            System.out.println("Репост сделан!");
        }
    }

    private void commentOnPost() {
        Post post = findPostById();
        if (post != null) {
            System.out.print("Введите комментарий: ");
            String commentText = scanner.nextLine();
            post.addComment(currentUser, commentText);
            PostDB.savePosts(posts);
            System.out.println("Комментарий добавлен!");
        }
    }

    private void showAllPosts() {
        if (posts.isEmpty()) {
            System.out.println("Нет доступных постов.");
            return;
        }
        posts.stream()
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .forEach(this::displayPost);
    }

    private void showUserPosts() {
        List<Post> userPosts = posts.stream()
                .filter(post -> post.getAuthor().equals(currentUser))
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .toList();

        if (userPosts.isEmpty()) {
            System.out.println("У вас пока нет постов.");
        } else {
            userPosts.forEach(this::displayPost);
        }
    }

    private void displayPost(Post post) {
        System.out.println(post);
        if (!post.getComments().isEmpty()) {
            System.out.println("  Комментарии:");
            post.getComments().forEach(comment -> System.out.println("    " + comment));
        }
    }

    private Post findPostById() {
        System.out.print("Введите ID поста: ");
        String postId = scanner.nextLine().trim();
        return posts.stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Пост с таким ID не найден.");
                    return null;
                });
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
