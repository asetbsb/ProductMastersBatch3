package Homework.seven;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** Твит (пост) + поддержка комментариев. */
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Простой комментарий как вложенный класс, чтобы не плодить файлы. */
    public static class Comment implements Serializable {
        private static final long serialVersionUID = 1L;
        private final User author;
        private final String text;
        private final LocalDateTime createdAt;

        public Comment(User author, String text, LocalDateTime createdAt) {
            this.author = Objects.requireNonNull(author, "author");
            this.text = Objects.requireNonNullElse(text, "");
            this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
        }
        public User getAuthor() { return author; }
        public String getText() { return text; }
        public LocalDateTime getCreatedAt() { return createdAt; }

        @Override public String toString() {
            String ts = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return "- " + author + ": " + text + " (" + ts + ")";
        }
    }

    private final long id;
    private final User author;
    private final String content;            // до 280 символов
    private int likes;
    private int reposts;
    private final LocalDateTime createdAt;
    private final List<Comment> comments = new ArrayList<>();

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
    public List<Comment> getComments() { return Collections.unmodifiableList(comments); }

    public void like() { likes++; }
    public void repost() { reposts++; }

    public void addComment(User author, String text) {
        if (author == null || text == null || text.isBlank()) return;
        comments.add(new Comment(author, text.trim(), LocalDateTime.now()));
    }

    @Override
    public String toString() {
        String ts = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "Post{id=" + id +
                ", author=" + author +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", reposts=" + reposts +
                ", time=" + ts +
                (comments.isEmpty() ? "" : (", comments=" + comments.size())) +
                '}';
    }
}
