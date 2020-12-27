package org.chunghyun.plannerforplanman.Home_Plan;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 2, entities = {Home_Plan_Entity.class}, exportSchema = false)
public abstract class Home_Plan_homeDatabase extends RoomDatabase {

    public abstract Home_Plan_dao myDao();

    private static  volatile Home_Plan_homeDatabase INSTANCE;

    // 싱글톤
    public static Home_Plan_homeDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (Home_Plan_homeDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Home_Plan_homeDatabase.class, "homeDatabase").build();
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
