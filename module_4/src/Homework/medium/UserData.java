package Homework.medium;

public class UserData {
    private final int id;
    private final String name;
    private final String email;

    public UserData(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "medium.lesson.UserData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
