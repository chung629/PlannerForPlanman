package org.chunghyun.plannerforplanman;

public class Add_Plan_Note {

    boolean isComplete; //완료
    String content; //내용

    public Add_Plan_Note(boolean isComplete, String content){
        this.isComplete = isComplete;
        this.content = content;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public String getContent() {
        return content;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
