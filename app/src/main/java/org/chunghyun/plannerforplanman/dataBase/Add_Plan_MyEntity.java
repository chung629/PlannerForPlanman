package org.chunghyun.plannerforplanman.dataBase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "addPlanDatabase")
public class Add_Plan_MyEntity {

    @PrimaryKey(autoGenerate =  true)
    int id;
    int isChecked;
    String content;
    int page;
    String date;

    public Add_Plan_MyEntity(String content, int isChecked, String date, int page){
        this.content = content;
        this.isChecked = isChecked;
        this.date = date;
        this.page = page;
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

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @NonNull
    @Override
    public String toString() {
        return "RecordData{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", isChecked='" + isChecked + '\'' +
                ", date='" + date + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
