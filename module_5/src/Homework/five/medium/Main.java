package Homework.five.medium;

public class Main {
  public static void main(String[] args) {
    ArrayList<DayOfWeek> days = new ArrayList<>();
    for (DayOfWeek d : DayOfWeek.values()) {
      days.add(d);
    }

    System.out.println("Все дни недели:");
    for (DayOfWeek d : days) {
      System.out.println(d);
    }

    System.out.println("Is SATURDAY weekend? " + isWeekend(DayOfWeek.SATURDAY));
    System.out.println("Is WEDNESDAY weekend? " + isWeekend(DayOfWeek.WEDNESDAY));
  }
  public static boolean isWeekend(DayOfWeek day) {
    return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
  }
}
