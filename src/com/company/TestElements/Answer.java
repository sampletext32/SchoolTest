package com.company.TestElements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Answer {
    private String text;
    private boolean correct;
    @JsonCreator
    public Answer(@JsonProperty("text") String text,@JsonProperty("isCorrect") boolean isCorrect) throws Exception {
        setText(text);
        this.correct = isCorrect;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) throws Exception {
        if (text.length() <= 0) throw new Exception("Вы ввели пустую строку");
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }
}
