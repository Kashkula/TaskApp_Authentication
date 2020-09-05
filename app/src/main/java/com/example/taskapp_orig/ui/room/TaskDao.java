package com.example.taskapp_orig.ui.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskapp_orig.ui.models.Task;

import java.util.List;

/**
 * мы создали новый абстрактный класс TaskDao,
 * который используется для списков операций!
 * Lesson.4:2 --> AppDatabase
 */
/*
    Далее записываем @Dao - аннотация!
 */
@Dao
public interface TaskDao {

    /*
        Query = запрос, - метод, который показывает данные!!!! --> HomeFragment.class
     */

    @Query("SELECT * FROM task")
    List<Task> getAll();

    /*
        ОТЛИЧИЯ МЕЖДУ ТЕМ И ЭТИМ МЕТОДАМИ, - ЭТОТ ОБНОВЛЯЕТ СВЕЖИМИ ДАННЫМИ, КАК БЫ само слово Live!!!! --> HomeFragment.class
     */

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllLive();

    /*
        для TaskDao мы включаем функцию записи "Insert"!!
     */
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task ORDER BY CASE WHEN :isAsc = 1 THEN task.title END ASC, CASE WHEN :isAsc = 0 THEN task.title END DESC")
    List<Task> getPersonsAlphabetically(boolean isAsc);

    @Query("SELECT * FROM task ORDER BY CASE WHEN :isAsc = 1 THEN task.createdAt END ASC, " +
            "CASE WHEN :isAsc = 0 THEN task.createdAt END DESC")
    List<Task> getTaskDateAlphabetically(boolean isAsc);

    @Query("DELETE FROM task")
    void nukeTable();
}
