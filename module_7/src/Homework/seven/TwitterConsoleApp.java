package Homework.seven;

import java.util.List;
import java.util.Scanner;

/** Консольное меню с доп. опциями: комментарии, удаление, сохранение. */
public class TwitterConsoleApp {
  private static final Scanner scanner = new Scanner(System.in);
  private static final TwitterService twitterService = new TwitterService();
  private static final String DATA_FILE = "posts.dat";

  public static void main(String[] args) {
    new TwitterConsoleApp().run();
  }

  public void run() {
    // загрузка из файла; если ничего не загрузилось — стартовые посты
    boolean loaded = twitterService.loadFromFile(DATA_FILE);
    if (!loaded || twitterService.isEmpty()) {
      twitterService.initializePosts();
    } else {
      System.out.println("Данные загружены из файла.");
    }

    System.out.print("\nВведите ваше имя: ");
    String userName = scanner.nextLine().trim();
    User currentUser = new User(userName);
    System.out.println("Добро пожаловать, " + currentUser.getName() + "!\n");

    while (true) {
      showMenu();
      int choice = getIntInput();
      switch (choice) {
        case 1 -> handleCreatePost(currentUser);
        case 2 -> handleLike();
        case 3 -> handleRepost();
        case 4 -> handleShowAll();
        case 5 -> handleShowPopular();
        case 6 -> handleShowMyPosts(currentUser);
        case 7 -> handleComment(currentUser);
        case 8 -> handleDelete(currentUser);
        case 9 -> handleSave();
        case 0 -> { handleExit(); return; }
        default -> System.out.println("Некорректный ввод. Попробуйте снова.");
      }
    }
  }

  // ====== Handlers ======
  private void handleCreatePost(User currentUser) {
    System.out.print("Введите текст поста (макс. 280 символов): ");
    String content = scanner.nextLine();
    Post p = twitterService.createPost(currentUser, content);
    System.out.println(p != null ? "Пост добавлен!" : "Пост не добавлен (пустой или длиннее 280 символов).");
  }

  private void handleLike() {
    long id = askLong("ID поста для лайка: ");
    System.out.println(twitterService.likePost(id) ? "Лайк засчитан." : "Пост с таким ID не найден.");
  }

  private void handleRepost() {
    long id = askLong("ID поста для репоста: ");
    System.out.println(twitterService.repost(id) ? "Сделан репост." : "Пост с таким ID не найден.");
  }

  private void handleShowAll() {
    System.out.println("\nВсе посты (новые → старые):");
    printPosts(twitterService.findAllSortedByDateDesc(), true);
  }

  private void handleShowPopular() {
    int n = askInt("Сколько популярных постов показать? ");
    System.out.println("\nПопулярные посты:");
    printPosts(twitterService.findTopLiked(n), true);
  }

  private void handleShowMyPosts(User currentUser) {
    System.out.println("\nМои посты:");
    printPosts(twitterService.findByAuthor(currentUser), true);
  }

  private void handleComment(User currentUser) {
    long id = askLong("ID поста для комментария: ");
    System.out.print("Текст комментария: ");
    String text = scanner.nextLine();
    boolean ok = twitterService.addComment(id, currentUser, text);
    System.out.println(ok ? "Комментарий добавлен." : "Пост не найден или текст пустой.");
  }

  private void handleDelete(User currentUser) {
    long id = askLong("ID поста для удаления: ");
    boolean ok = twitterService.deletePost(id, currentUser);
    System.out.println(ok ? "Пост удалён." : "Нельзя удалить: пост не найден или вы не автор.");
  }

  private void handleSave() {
    boolean ok = twitterService.saveToFile(DATA_FILE);
    System.out.println(ok ? "Сохранено." : "Ошибка сохранения.");
  }

  private void handleExit() {
    twitterService.saveToFile(DATA_FILE);
    System.out.println("Выход…");
  }

  // ====== IO helpers ======
  private static void showMenu() {
    System.out.println("\n=== Twitter Console ===");
    System.out.println("1. Написать пост");
    System.out.println("2. Лайкнуть пост");
    System.out.println("3. Сделать репост");
    System.out.println("4. Показать все посты");
    System.out.println("5. Показать популярные посты");
    System.out.println("6. Показать мои посты");
    System.out.println("7. Оставить комментарий к посту");
    System.out.println("8. Удалить мой пост");
    System.out.println("9. Сохранить сейчас");
    System.out.println("0. Сохранить и выйти");
    System.out.print("Выберите действие: ");
  }

  private int getIntInput() {
    try {
      return Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  private int askInt(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        int v = Integer.parseInt(scanner.nextLine().trim());
        return Math.max(0, v);
      } catch (NumberFormatException e) {
        System.out.println("Введите число.");
      }
    }
  }

  private long askLong(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        return Long.parseLong(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.println("Введите число.");
      }
    }
  }

  private void printPosts(List<Post> list, boolean showComments) {
    if (list.isEmpty()) {
      System.out.println("(пусто)");
      return;
    }
    for (Post p : list) {
      System.out.println(p);
      if (showComments && !p.getComments().isEmpty()) {
        System.out.println("  Комментарии:");
        for (Post.Comment c : p.getComments()) {
          System.out.println("  " + c);
        }
      }
    }
  }
}
