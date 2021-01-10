package org.chunghyun.plannerforplanman.dataBase;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "homeDatabase")
public class Home_Plan_Entity {

    @PrimaryKey(autoGenerate =  true)
    int id;
    String bookName;
    int totalUnit;
    int curUnit;


    public Home_Plan_Entity(String bookName, int totalUnit, int curUnit){
        this.bookName = bookName;
        this.totalUnit = totalUnit;
        this.curUnit = curUnit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getTotalUnit() {
        return totalUnit;
    }

    public void setTotalUnit(int totalUnit) {
        this.totalUnit = totalUnit;
    }

    public int getCurUnit() {
        return curUnit;
    }

    public void setCurUnit(int curUnit) {
        this.curUnit = curUnit;
    }

    @NonNull
    @Override
    public String toString() {
        return "RecordData{" +
                "id='" + id + '\'' +
                ", bookName='" + bookName + '\'' +
                ", totalUnit='" + totalUnit + '\'' +
                ", curUnit='" + curUnit + '\'' +
                '}';
    }
}
