package models;

public class StudentAccount {
    public static StudentAccount LoggedAccount;

    private int id;
    private String login;
    private String password;
    private String username;
    private String className;

    public StudentAccount(int id, String login, String password, String username, String className) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.username = username;
        this.className = className;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
