package models;

public class SuggestionAnswer {
    private int id;
    private String content;

    private int questionId;

    public SuggestionAnswer(int id, String content, int questionId) {
        this.id = id;
        this.content = content;
        this.questionId = questionId;
    }

    public SuggestionAnswer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
