package Homework.seven;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Post {
    private final long id;
    private final User author;
    private final String content;            // до 280
    private int likes;
    private int reposts;
    private final LocalDateTime createdAt;

    public Post(long id, User author, String content, LocalDateTime createdAt) {
        this.id = id;
        this.author = Objects.requireNonNull(author, "author");
        this.content = content == null ? "" : content;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public long getId() { return id; }
    public User getAuthor() { return author; }
    public String getContent() { return content; }
    public int getLikes() { return likes; }
    public int getReposts() { return reposts; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void like() { likes++; }
    public void repost() { reposts++; }

    @Override
    public String toString() {
        String ts = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "Post{id=" + id +
                ", author=" + author +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", reposts=" + reposts +
                ", time=" + ts +
                '}';
    }
}
