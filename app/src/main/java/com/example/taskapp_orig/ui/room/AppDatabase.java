package com.example.taskapp_orig.ui.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskapp_orig.ui.models.Task;

/**
 * следующим образом мы создали самый ново-главный абстрактный класс AppDatabase и унаследуем его от RoomDatabase. Lesson.4:3 --> App.class
 */

/*
    также у Database есть аннотация в котором записывается
     entities = таблицы(внутри в скобках можно добавлять списки при помощи запятой),
     затем version = 1, а после, чтобы ошибок не было(нужно добавить exportSchema  = false)!
 */
@Database(entities = {Task.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    /*
        После этого записали новый метод taskDao();???
        без понятия, зачем!!!?
     */
    public abstract TaskDao taskDao();
}
