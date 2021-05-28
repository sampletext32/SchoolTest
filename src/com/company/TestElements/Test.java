package com.company.TestElements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@JsonAutoDetect
public class Test {
    @JsonInclude
    private final List<Quest> quests;
    @JsonInclude
    private String name;
    @JsonInclude
    private String classesName;
    @JsonInclude
    private String subject;
    private double score = 0;
    @JsonInclude
    private int maxScore;

    public String getGroupName() {
        return classesName;
    }

    public void setGroupName(String groupName) {
        this.classesName = groupName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Test(String name) throws Exception {
        setName(name);
        this.quests = new ArrayList<>();
        this.maxScore = 0;
    }
    public Test(String name, String groupName) throws Exception {
        setName(name);
        this.classesName = groupName;
        this.quests = new ArrayList<>();
        this.maxScore = 0;
    }
    public Test(String name, String groupName, String subject) throws Exception {
        setName(name);
        this.classesName = groupName;
        this.subject = subject;
        this.quests = new ArrayList<>();
        this.maxScore = 0;
    }
    @JsonCreator
    public Test(@JsonProperty("name") String name,
                @JsonProperty("classesName") String classesName,
                @JsonProperty("subject") String subject,
                @JsonProperty("quests") List<Quest> quests) throws Exception {
        setName(name);
        this.classesName = classesName;
        this.subject = subject;
        this.quests = quests;
        this.maxScore = quests.size();
    }

    public void setName(String name) throws Exception {
        if (name.length() <= 0) throw new Exception("Вы ввели пустую строку");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Quest> getQuests() {
        return Collections.unmodifiableList(quests);
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
        this.maxScore++;
    }

    public double getScore() {
        return score;
    }
    public void addScores(double score) {
        this.score += score;
    }
    public String finish() {
        return score >= (double) (maxScore / 2) ? "Тест пройден" : "Тест не пройден";
    }
    public int getMaxScore() {
        return maxScore;
    }

}
