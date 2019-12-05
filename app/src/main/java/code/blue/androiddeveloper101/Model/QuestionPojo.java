package code.blue.androiddeveloper101.Model;

public class QuestionPojo {

    private String question;
    private String answer;
    private String number;

    public QuestionPojo(String question, String answer, String number) {
        this.question = question;
        this.answer = answer;
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
