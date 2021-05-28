package com.company.TestElements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@JsonAutoDetect
public class Quest {

    private String text;
    private final List<Answer> answers;

    public Quest(String text) throws Exception {
        setText(text);
        this.answers = new ArrayList<>();
    }
    @JsonCreator
    public Quest(@JsonProperty("text") String text,@JsonProperty("answers") List<Answer> answers) throws Exception {
        setText(text);
        this.answers = answers;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) throws Exception {
        if (text.length() <= 0) throw new Exception("Вы ввели пустую строку");
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public double checkAnswer(List<Integer> answers) throws Exception {
        double max = 0;
        double correct = 0;
        for (Answer answer : this.answers) {
            if (answer.isCorrect()) {
                max++;
            }
        }
        for (Integer answer : answers) {
            if (this.answers.get(answer).isCorrect()) {
                correct++;
            }
        }
        return correct / max;
    }
}
