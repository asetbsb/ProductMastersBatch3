package Homework.seven;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
public class TwitterService {
  private final List<Post> posts = new ArrayList<>();
  private final AtomicLong seq = new AtomicLong(0);

  /** Стартовые посты от разных пользователей */
  public void initializePosts() {
    User alice = new User("Alice");
    User bob = new User("Bob");
    User charlie = new User("Charlie");

    createPost(alice, "Привет, мир!");
    createPost(bob, "Сегодня отличный день!");
    createPost(charlie, "Люблю программировать на Java.");

    System.out.println("Добавлены стартовые посты.");
  }

  /** Создать пост */
  public Post createPost(User author, String content) {
    if (author == null || content == null) return null;
    String trimmed = content.trim();
    if (trimmed.isEmpty() || trimmed.length() > 280) return null;

    Post p = new Post(seq.incrementAndGet(), author, trimmed, LocalDateTime.now());
    posts.add(p);
    return p;
  }

  /** Лайк по id */
  public boolean likePost(long id) {
    Optional<Post> opt = findById(id);
    opt.ifPresent(Post::like);
    return opt.isPresent();
  }

  /** Репост по id */
  public boolean repost(long id) {
    Optional<Post> opt = findById(id);
    opt.ifPresent(Post::repost);
    return opt.isPresent();
  }

  /** Все посты: новые → старые */
  public List<Post> findAllSortedByDateDesc() {
    return posts.stream()
            .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
            .collect(Collectors.toList());
  }

  /** Топ по лайкам (limit) */
  public List<Post> findTopLiked(int limit) {
    if (limit <= 0) return Collections.emptyList();
    return posts.stream()
            .sorted(Comparator.comparingInt(Post::getLikes).reversed()
                    .thenComparing(Post::getCreatedAt).reversed())
            .limit(limit)
            .collect(Collectors.toList());
  }

  /** Посты конкретного автора */
  public List<Post> findByAuthor(User author) {
    return posts.stream()
            .filter(p -> Objects.equals(p.getAuthor(), author))
            .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
            .collect(Collectors.toList());
  }

  private Optional<Post> findById(long id) {
    return posts.stream().filter(p -> p.getId() == id).findFirst();
  }
}
