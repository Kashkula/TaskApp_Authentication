package com.example.taskapp_orig.ui;

import android.app.Application;

import androidx.room.Room;

import com.example.taskapp_orig.ui.room.AppDatabase;

/**
 * Куда мы дошли, ахх точно!
 * Мы создали класс унаследовали его от Application - он самый главный из классов,
 * даже главнее MainActivity! Lesson.4:4 --> AndroidManifest - continued... для того,
 * чтобы использовать App.class, заходим в манифест внутри <application> захерачим name = ".App"!!!
 * <p>
 * После Манифеста наши дальнейшие действия будут проводится определенно в этом классе!!!
 *
 * после всего переходим --> FormFragment.class
 */
public class App extends Application {

    public static App instance;
    private AppDatabase database;



    /*
            у Application, - есть свой жизненный цикл!!!
         */
    @Override
    public void onCreate() {
        super.onCreate();
        /*
         следовательно этот instance равен этой же App-ке.
         */
        instance = this;
        /*
            дальше походу совсем жопа, но я справлюсь, так не отвлекаемся!!!
            мы взяли database впихнули туда ЭТУ строку, то, что "database" - это название файла,
            где и будет сохранятся база данных!!! И сразу добавляем метод allowMainThreadQueries()
             - по умолчанию чтение либо запись на главном потоке не разрешается Room-ом, без понятия для чего в дальнейшем пойму!!!
         */
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
