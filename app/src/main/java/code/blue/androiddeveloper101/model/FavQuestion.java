package code.blue.androiddeveloper101.model;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "favQuestion")
public class FavQuestion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int id;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "answer")
    private String answer;

    @TypeConverters({TermConverter.class})
    @ColumnInfo(name = "terminologies")
    private List<TerminologyPojo> terminologies;

    @TypeConverters({AssoQuesConverter.class})
    @ColumnInfo(name = "associated_questions")
    private List<AssoQuestionPojo> associatedQuestions;

    public FavQuestion(int id, String question, String answer, List<TerminologyPojo> terminologies, List<AssoQuestionPojo> associatedQuestions){
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.terminologies = terminologies;
        this.associatedQuestions = associatedQuestions;
    }

    @Ignore
    public FavQuestion(String question, String answer, List<TerminologyPojo> terminologies, List<AssoQuestionPojo> associatedQuestions){
        this.question = question;
        this.answer = answer;
        this.terminologies = terminologies;
        this.associatedQuestions = associatedQuestions;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String q){
        question = q;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String a){
        answer = a;
    }

    @TypeConverters({TermConverter.class})
    public List<TerminologyPojo> getTerminologies(){
        return terminologies;
    }

    @TypeConverters({TermConverter.class})
    public void setTerminologies(List<TerminologyPojo> tp){
        terminologies = tp;
    }

    @TypeConverters({AssoQuesConverter.class})
    public List<AssoQuestionPojo> getAssociatedQuestions(){
        return associatedQuestions;
    }

    @TypeConverters({AssoQuesConverter.class})
    public void setAssociatedQuestions(List<AssoQuestionPojo> aq){
        this.associatedQuestions = aq;
    }
}
