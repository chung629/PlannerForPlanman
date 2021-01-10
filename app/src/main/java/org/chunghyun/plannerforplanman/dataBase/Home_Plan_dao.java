package org.chunghyun.plannerforplanman.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Home_Plan_dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 중복 ID일경우 교체
    public void insert(Home_Plan_Entity entity);

    @Update
    void update(Home_Plan_Entity myEntity);

    @Delete
    public void delete(Home_Plan_Entity entity);

    @Query("DELETE FROM homeDatabase")
    public void deleteAll();

    @Query("SELECT * FROM homeDatabase")
    public LiveData<List<Home_Plan_Entity>> getAll();

    @Query("SELECT totalUnit FROM homeDatabase WHERE bookName=:name")
    public int getPage(String name);

    @Query("UPDATE homeDatabase SET curUnit=:unitNum WHERE bookName=:name")
    void curUnitUpdate(String name, int unitNum);

    @Query("SELECT bookName FROM homeDatabase")
    public List<String> getBookName();

    //test
    @Query("SELECT curUnit FROM homeDatabase WHERE bookName=:name")
    public int getCur(String name);

    @Query("DELETE FROM addPlanDatabase WHERE content LIKE   :name || '%'")
    public void deleteAddPlan(String name);
}
