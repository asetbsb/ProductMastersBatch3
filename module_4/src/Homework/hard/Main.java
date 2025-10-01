package Homework.hard;
class Box<T> {
  private T item;

  public Box(T item) {
    this.item = item;
  }

  public void setItem(T item) {
    this.item = item;
  }

  public T getItem() {
    return item;
  }

  public void showType() {
    if (item == null) {
      System.out.println("Тип: null (элемент отсутствует)");
    } else {
      System.out.println("Тип: " + item.getClass().getName());
    }
  }

  @Override
  public String toString() {
    return "Box{item=" + item + "}";
  }
}
class MyData {
  private final int id;
  private final String value;

  public MyData(int id, String value) {
    this.id = id; this.value = value;
  }

  @Override
  public String toString() {
    return "MyData{id=" + id + ", value='" + value + "'}";
  }
}

public class Main {

  public static void main(String[] args) {
    Box<String> strBox = new Box<>("Hello");
    strBox.showType();
    System.out.println("Содержимое: " + strBox.getItem());

    Box<MyData> dataBox = new Box<>(new MyData(42, "answer"));
    dataBox.showType();
    System.out.println("Содержимое: " + dataBox.getItem());

    // Замена значения
    dataBox.setItem(new MyData(7, "lucky"));
    dataBox.showType();
    System.out.println("Содержимое после setItem: " + dataBox.getItem());
  }

}
