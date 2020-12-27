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

    public Add_Plan_MyEntity(String content, int isChecked){
        this.content = content;
        this.isChecked = isChecked;
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

    @NonNull
    @Override
    public String toString() {
        return "RecordData{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", isChecked='" + isChecked + '\'' +
                '}';
    }
}
