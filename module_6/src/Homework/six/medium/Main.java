package Homework.six.medium;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    String filePath = (args.length > 0) ? args[0] : "text.txt";

    String text = readWholeFile(filePath);

    String[] tokens = text.toLowerCase().split("[^\\p{L}]+");

    Set<String> uniqueWords = new HashSet<>();
    Map<String, Integer> freq = new HashMap<>();

    for (String w : tokens) {
      if (w.isEmpty()) continue;
      uniqueWords.add(w);
      freq.put(w, freq.getOrDefault(w, 0) + 1);
    }

    System.out.println("Уникальных слов: " + uniqueWords.size());

    List<String> uniqueSorted = new ArrayList<>(uniqueWords);
    Collections.sort(uniqueSorted);
    System.out.println("Список уникальных слов:");
    System.out.println(uniqueSorted);

    List<Map.Entry<String, Integer>> byFreq = new ArrayList<>(freq.entrySet());
    byFreq.sort((a, b) -> {
      int cmp = Integer.compare(b.getValue(), a.getValue());
      if (cmp != 0) return cmp;
      return a.getKey().compareTo(b.getKey());
    });

    System.out.println("\nЧастоты слов:");
    for (Map.Entry<String, Integer> e : byFreq) {
      System.out.printf("%s -> %d%n", e.getKey(), e.getValue());
    }
  }

  private static String readWholeFile(String filePath) {
    try {
      List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
      return String.join(" ", lines);
    } catch (IOException e) {
      System.err.println("Не удалось прочитать файл: " + filePath);
      throw new RuntimeException(e);
    }
  }
}
