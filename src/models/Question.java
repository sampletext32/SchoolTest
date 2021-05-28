package models;

public class Question {
    private int id;

    private String title;

    private int testId;

    public Question(int id, String title, int testId) {
        this.id = id;
        this.title = title;
        this.testId = testId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }
}
