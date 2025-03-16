import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final User author;
    private final String content;
    private int likes;
    private int reposts;
    private final long timestamp;
    private final List<Comment> comments;  // New field for storing comments


    public Post(User author, String content) {
        this.id = UUID.randomUUID().toString().substring(0, 5);
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.reposts = 0;
        this.timestamp = System.currentTimeMillis();
        this.comments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public int getReposts() {
        return reposts;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void like() {
        likes++;
    }

    public void repost() {
        reposts++;
    }

    public void addComment(User user, String content) {
        comments.add(new Comment(user, content));
    }

    @Override
    public String toString() {
        return "id: " + id + " " + author.getName() + ": " + content +
                "\n❤️ " + likes + " | 🔁 " + reposts +
                " | 💬 " + comments.size(); // Avoid NullPointerException
    }
}
