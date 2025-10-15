// src/Homework/six/hard/Main.java
package Homework.six.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    // Демонстрация SafeList
    SafeList<String> list = new SafeList<>();
    list.add("apple");
    list.add("banana");
    list.add(null);      // не добавится
    list.add("apple");   // дубликат — не добавится
    list.add(5, "pear"); // индекс вне диапазона — аккуратно добавит в конец

    System.out.println("list: " + list);
    System.out.println("get(0) -> " + list.get(0));  // apple
    System.out.println("get(5) -> " + list.get(5));  // null (без ошибки)

    // set: безопасно (дубликат/ооб/ null — игнорируется, вернёт null)
    System.out.println("set(1, \"banana\") -> " + list.set(1, "banana")); // тот же элемент
    System.out.println("set(1, \"apple\")  -> " + list.set(1, "apple"));  // станет дубликатом -> null
    System.out.println("set(10, \"grape\") -> " + list.set(10, "grape")); // ооб -> null
    System.out.println("после set: " + list);

    // remove по индексу
    System.out.println("remove(100) -> " + list.remove(100)); // null, тихо
    System.out.println("remove(0)   -> " + list.remove(0));   // удалит apple
    System.out.println("после remove: " + list);

    // remove(Object)
    System.out.println("remove(\"banana\") -> " + list.remove("banana"));
    System.out.println("remove(null)      -> " + list.remove(null));
    System.out.println("итог: " + list);

    // Демонстрация removeDuplicates (из условия предыдущего шаблона)
    ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(1,2,2,3,1,4,4,5));
    System.out.println("Исходный:   " + nums);
    System.out.println("Без дублей: " + removeDuplicates(nums));
  }

  /**
   * Удаляет дубликаты, сохраняя порядок первых вхождений.
   */
  public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
    if (list == null || list.isEmpty()) return new ArrayList<>();
    ArrayList<Integer> result = new ArrayList<>(list.size());
    Set<Integer> seen = new HashSet<>();
    for (Integer x : list) {
      if (x == null) continue;       // можно игнорировать null'ы
      if (seen.add(x)) result.add(x);
    }
    return result;
  }
}
