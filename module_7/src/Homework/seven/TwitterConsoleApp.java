package Homework.seven;

import java.util.List;
import java.util.Scanner;

public class TwitterConsoleApp {
  private static final Scanner scanner = new Scanner(System.in);
  private static final TwitterService twitterService = new TwitterService();

  public static void main(String[] args) {
    new TwitterConsoleApp().run();
  }

  public void run() {
    System.out.print("Введите ваше имя: ");
    String userName = scanner.nextLine().trim();
    User currentUser = new User(userName);
    System.out.println("Добро пожаловать, " + currentUser.getName() + "!\n");

    twitterService.initializePosts();

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
        case 7 -> { System.out.println("Выход..."); return; }
        default -> System.out.println("Некорректный ввод. Попробуйте снова.");
      }
    }
  }

  // ====== Handlers ======
  private void handleCreatePost(User currentUser) {
    System.out.print("Введите текст поста (макс. 280 символов): ");
    String content = scanner.nextLine();
    Post p = twitterService.createPost(currentUser, content);
    if (p != null) System.out.println("Пост добавлен!");
    else System.out.println("Пост не добавлен (пустой или длиннее 280 символов).");
  }

  private void handleLike() {
    long id = askLong("ID поста для лайка: ");
    boolean ok = twitterService.likePost(id);
    System.out.println(ok ? "Лайк засчитан." : "Пост с таким ID не найден.");
  }

  private void handleRepost() {
    long id = askLong("ID поста для репоста: ");
    boolean ok = twitterService.repost(id);
    System.out.println(ok ? "Сделан репост." : "Пост с таким ID не найден.");
  }

  private void handleShowAll() {
    System.out.println("\nВсе посты (новые → старые):");
    printPosts(twitterService.findAllSortedByDateDesc());
  }

  private void handleShowPopular() {
    int n = askInt("Сколько популярных постов показать? ");
    System.out.println("\nПопулярные посты:");
    printPosts(twitterService.findTopLiked(n));
  }

  private void handleShowMyPosts(User currentUser) {
    System.out.println("\nМои посты:");
    printPosts(twitterService.findByAuthor(currentUser));
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
    System.out.println("7. Выход");
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

  private void printPosts(List<Post> list) {
    if (list.isEmpty()) {
      System.out.println("(пусто)");
      return;
    }
    for (Post p : list) System.out.println(p);
  }
}