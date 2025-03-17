public class Test {
    public static void main(String[] args) {
        User user = new User("Abdussalam");
        Post post = new Post(user, "This is a test post");
        System.out.println(post);
    }
}
