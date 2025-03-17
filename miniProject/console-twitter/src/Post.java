import java.util.UUID;

public class Post {
    private final String id;
    private final User author;
    private final String content;
    private int likes;
    private int reposts;
    private final long timestamp;

    public Post(User author, String content) {
        this.id = UUID.randomUUID().toString().substring(0, 5);
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.reposts = 0;
        this.timestamp = System.currentTimeMillis();
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

    public void like() {
        likes++;
    }

    public void repost() {
        reposts++;
    }

    @Override
    public String toString() {
        return "Post{id=" + id + ", author=" + author.getName() + ", content='" + content + "', likes=" + likes + ", reposts=" + reposts + "}";
    }
}
