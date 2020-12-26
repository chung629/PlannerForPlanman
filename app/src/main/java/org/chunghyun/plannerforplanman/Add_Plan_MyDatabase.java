package org.chunghyun.plannerforplanman;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// 수정 할 필요 없 - 12/27
@Database(version = 1, entities = {Add_Plan_MyEntity.class}, exportSchema = false)
public abstract class Add_Plan_MyDatabase extends RoomDatabase {

    public abstract Add_Plan_MyDao myDao();

    private static  volatile Add_Plan_MyDatabase INSTANCE;

    // 싱글톤
    public static Add_Plan_MyDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (Add_Plan_MyDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Add_Plan_MyDatabase.class, "myDatabase").build();
                }
            }
        }
        return INSTANCE;
    }

    //DB 객체 제거
    public static void destroyInstance(){
        INSTANCE = null;
    }
}
