package Homework.seven;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/** Сервис: CRUD постов, лайки, репосты, комментарии, загрузка/сохранение. */
public class TwitterService {
  private final List<Post> posts = new ArrayList<>();
  private final AtomicLong seq = new AtomicLong(0);

  // ====== Persistence ======
  public boolean loadFromFile(String path) {
    File f = new File(path);
    if (!f.exists()) return false;
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
      Object obj = in.readObject();
      @SuppressWarnings("unchecked")
      List<Post> loaded = (List<Post>) obj;
      posts.clear();
      posts.addAll(loaded);
      // восстановим корректный счётчик id
      long maxId = posts.stream().mapToLong(Post::getId).max().orElse(0L);
      seq.set(maxId);
      return true;
    } catch (Exception e) {
      System.err.println("Не удалось загрузить данные: " + e.getMessage());
      return false;
    }
  }

  public boolean saveToFile(String path) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
      out.writeObject(posts);
      return true;
    } catch (IOException e) {
      System.err.println("Не удалось сохранить данные: " + e.getMessage());
      return false;
    }
  }

  public boolean isEmpty() { return posts.isEmpty(); }

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

  // ====== Базовые операции ======
  public Post createPost(User author, String content) {
    if (author == null || content == null) return null;
    String trimmed = content.trim();
    if (trimmed.isEmpty() || trimmed.length() > 280) return null;

    Post p = new Post(seq.incrementAndGet(), author, trimmed, LocalDateTime.now());
    posts.add(p);
    return p;
  }

  public boolean likePost(long id) {
    return findById(id).map(p -> { p.like(); return true; }).orElse(false);
  }

  public boolean repost(long id) {
    return findById(id).map(p -> { p.repost(); return true; }).orElse(false);
  }

  public boolean addComment(long postId, User author, String text) {
    return findById(postId).map(p -> { p.addComment(author, text); return true; }).orElse(false);
  }

  /** Удалять разрешаем только автору поста */
  public boolean deletePost(long id, User requester) {
    Optional<Post> opt = findById(id);
    if (opt.isEmpty()) return false;
    Post p = opt.get();
    if (!Objects.equals(p.getAuthor(), requester)) return false;
    return posts.remove(p);
  }

  public List<Post> findAllSortedByDateDesc() {
    return posts.stream()
            .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
            .collect(Collectors.toList());
  }

  public List<Post> findTopLiked(int limit) {
    if (limit <= 0) return Collections.emptyList();
    return posts.stream()
            .sorted(Comparator.comparingInt(Post::getLikes).reversed()
                    .thenComparing(Post::getCreatedAt).reversed())
            .limit(limit)
            .collect(Collectors.toList());
  }

  public List<Post> findByAuthor(User author) {
    return posts.stream()
            .filter(p -> Objects.equals(p.getAuthor(), author))
            .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
            .collect(Collectors.toList());
  }

  public Optional<Post> findById(long id) {
    return posts.stream().filter(p -> p.getId() == id).findFirst();
  }
}
