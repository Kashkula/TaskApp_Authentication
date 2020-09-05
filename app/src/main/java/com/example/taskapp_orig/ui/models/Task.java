package com.example.taskapp_orig.ui.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * мы записываем Entity для того, чтобы Task стал таблицей. Lesson.4:1 --> TaskDao
 */
@Entity


public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    /*
        затем мы добавляем long id - уникальный ключ,
        записываем сверху через собачку "уникальный ключ" и внутри автогенератор.
        А затем запишем ему геттер и сеттер
         */
    private String title;
    private long createdAt;
    private long updateTime;

    public long getUpdateTime() {
        return updateTime;
    }

    public Task(String title, long createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
