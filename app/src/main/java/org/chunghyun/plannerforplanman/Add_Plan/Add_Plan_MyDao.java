package org.chunghyun.plannerforplanman.Add_Plan;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
// 특정 쿼리 작성
@Dao
public interface Add_Plan_MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 중복 ID일경우 교체
    public void insert(Add_Plan_MyEntity entity);

    @Update
    void update(Add_Plan_MyEntity myEntity);

    @Delete
    public void delete(Add_Plan_MyEntity entity);

    @Query("DELETE FROM myDatabase")
    public void deleteAll();

    @Query("SELECT * FROM myDatabase WHERE date LIKE :selectDate")
    public LiveData<List<Add_Plan_MyEntity>> getAll(String selectDate);
}
