package Homework.seven;

import java.util.Objects;

public class User {
  private final String name;

  public User(String name) {
    this.name = (name == null || name.trim().isEmpty()) ? "Unknown" : name.trim();
  }

  public String getName() {
    return name;
  }

  @Override public String toString() { return name; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User u)) return false;
    return Objects.equals(name, u.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
