package org.chunghyun.plannerforplanman;

public class Home_Note {
    int _id;
    String startDate;   // 시작 날짜
    String curDate;     // 현재 진행 일수
    String contents;    // 내용

    public Home_Note(int _id, String startDate, String curDate, String contents){
        this._id = _id;
        this.startDate = startDate;
        this.curDate = curDate;
        this.contents = contents;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int get_id() {
        return _id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getCurDate() {
        return curDate;
    }

    public String getContents() {
        return contents;
    }
}
