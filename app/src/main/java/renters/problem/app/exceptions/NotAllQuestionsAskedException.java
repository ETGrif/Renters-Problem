package renters.problem.app.exceptions;

public class NotAllQuestionsAskedException extends RuntimeException{
    String message;

    public NotAllQuestionsAskedException(String message){
        this.message = message;
    }

    public NotAllQuestionsAskedException(){
        this.message = "Not all divisions were answered when they were expected to be.";
    }
}
