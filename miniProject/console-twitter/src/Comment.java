import java.io.Serializable;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private final User author;
    private final String content;

    public Comment(User author, String content) {
        this.author = author;
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return author.getName() + ": " + content;
    }
}
