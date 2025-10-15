package Homework.six.easy;

import java.util.*;

public class NumberList {
    private final List<Integer> numbers;

    public NumberList(List<Integer> numbers) {
        if (numbers == null) throw new IllegalArgumentException("numbers is null");
        this.numbers = new ArrayList<>(numbers);
    }

    /** 1) Минимум */
    public int min() {
        if (numbers.isEmpty()) throw new NoSuchElementException("Список пуст");
        int m = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) < m) m = numbers.get(i);
        }
        return m;
    }

    /** 2) Максимум */
    public int max() {
        if (numbers.isEmpty()) throw new NoSuchElementException("Список пуст");
        int m = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) > m) m = numbers.get(i);
        }
        return m;
    }

    /** 3) Сортировка по возрастанию (новый список) */
    public List<Integer> sortAsc() {
        List<Integer> copy = new ArrayList<>(numbers);
        Collections.sort(copy);
        return copy;
    }

    /** 4) Сортировка по убыванию (новый список) */
    public List<Integer> sortDesc() {
        List<Integer> copy = new ArrayList<>(numbers);
        copy.sort(Comparator.reverseOrder());
        return copy;
    }

    /** 5) Поиск элемента */
    public boolean contains(int value) {
        return numbers.contains(value);
    }

    /** 6) Фильтр: оставить только элементы > threshold (новый список) */
    public List<Integer> filterGreaterThan(int threshold) {
        List<Integer> result = new ArrayList<>();
        for (int x : numbers) {
            if (x > threshold) result.add(x);
        }
        return result;
    }

    /** 7) Сумма всех чисел */
    public long sum() {
        long s = 0;
        for (int x : numbers) s += x;
        return s;
    }

    @Override public String toString() {
        return numbers.toString();
    }

    public static void main(String[] args) {
        NumberList nl = new NumberList(Arrays.asList(5, -2, 7, 7, 0, 12, 3));
        System.out.println("Исходный:        " + nl);
        System.out.println("Минимум:         " + nl.min());
        System.out.println("Максимум:        " + nl.max());
        System.out.println("По возрастанию:  " + nl.sortAsc());
        System.out.println("По убыванию:     " + nl.sortDesc());
        System.out.println("Есть 7?          " + nl.contains(7));
        System.out.println("> 4:             " + nl.filterGreaterThan(4));
        System.out.println("Сумма:           " + nl.sum());
    }
}
