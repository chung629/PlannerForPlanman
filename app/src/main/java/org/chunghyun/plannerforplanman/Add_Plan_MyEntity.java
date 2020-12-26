package org.chunghyun.plannerforplanman;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "myDatabase")
public class Add_Plan_MyEntity {

    @PrimaryKey(autoGenerate =  true)
    int id;
    String content;

    public Add_Plan_MyEntity(String content){
        this.content = content;
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

    @NonNull
    @Override
    public String toString() {
        return "RecordData{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
