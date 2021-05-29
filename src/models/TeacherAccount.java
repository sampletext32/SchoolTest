package models;

public class TeacherAccount {
    public static TeacherAccount LoggedAccount;

    private int id;
    private String login;
    private String password;
    private String username;

    public TeacherAccount(int id, String login, String password, String username) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
