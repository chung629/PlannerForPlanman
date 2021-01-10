package org.chunghyun.plannerforplanman.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

// 수정 할 필요 없 - 12/27
@Database(version = 1, entities = {Add_Plan_MyEntity.class, Home_Plan_Entity.class}, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MyDatabase extends RoomDatabase {

    public abstract MyDao myDao();
    public abstract Home_Plan_dao home_plan_dao();

    private static  volatile MyDatabase INSTANCE;

    // 싱글톤
    public static MyDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MyDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "myDatabase").build();
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
