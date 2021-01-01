package org.chunghyun.plannerforplanman.Add_Plan;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "myDatabase")
public class Add_Plan_MyEntity {

    @PrimaryKey(autoGenerate =  true)
    int id;
    int isChecked;
    String content;
    String date;

    public Add_Plan_MyEntity(String content, int isChecked, String date){
        this.content = content;
        this.isChecked = isChecked;
        this.date = date;
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

    @NonNull
    @Override
    public String toString() {
        return "RecordData{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", isChecked='" + isChecked + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
