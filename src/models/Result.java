package models;

public class Result {
    private int id;
    private int test_id;
    private String name_test;
    private int student_id;
    private String student_name;
    private float score;
    private int max_score;

    public Result(int id, int test_id, String name_test,  int student_id, String student_name, float score, int max_score) {
        this.id = id;
        this.test_id = test_id;
        this.name_test = name_test;
        this.student_id = student_id;
        this.student_name = student_name;
        this.score=score;
        this.max_score=max_score;
    }

    public int getId() {  return id; }
    public void setId(int id) {        this.id = id; }

    public int getTest_id() {        return test_id; }
    public void setTest_id(int test_id) {        this.test_id = test_id; }

    public String getName_test() {        return name_test; }
    public void setName_test(String name_test) {        this.name_test = name_test; }

    public String getStudent_name() {        return student_name; }
    public void setStudent_id(int student_id) {        this.student_id = student_id; }

    public int getStudent_id() {        return student_id; }
    public void setStudent_name(String student_name) {        this.student_name = student_name; }

    public float getScore() {        return score; }
    public void setScore(float score) {        this.score = score; }


    public int getMax_score() {        return max_score; }
    public void setMax_score(int max_score) {        this.max_score = max_score; }
}
