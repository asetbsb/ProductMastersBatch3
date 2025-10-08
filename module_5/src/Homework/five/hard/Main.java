package Homework.five.hard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    // 1) считываем n целых и добавляем в список
    int n = sc.nextInt();
    ArrayList<Integer> numbers = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      numbers.add(sc.nextInt());
    }

    ArrayList<Integer> unique = removeDuplicates(numbers);

    // вывод исходного и результата (по желанию)
    System.out.println("Исходный:   " + numbers);
    System.out.println("Без дублей: " + unique);
  }

  // 2) удаляем дубликаты, сохраняя порядок
  public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
    ArrayList<Integer> result = new ArrayList<>(list.size());
    Set<Integer> seen = new HashSet<>();
    for (Integer x : list) {
      if (seen.add(x)) {
        result.add(x);
      }
    }
    return result;
  }
}
