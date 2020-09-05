package com.example.taskapp_orig;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    protected SharedPreferences preferences;

    public Prefs(Context context) {
        /*
        этот метод создаёт папку и сохраняет там данные
         */
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }
    public void setShown() {
        /*
        после того как пользователь нажмёт на GET STARTING (Btn)
        preferences редактирует и отправляет данные о том, что пользователь просмотрет все ViewPager-ы и нажал на GET STARTING Btn.
         */
        preferences.edit().putBoolean("isShown",true).apply();
    }
    public boolean isShown() {
        /*
       этот метод в MainActivity сработает до условного конструктора
       там в условке должно быть true || false,
       и если этот метод не найдёт "isShown" то возвратит false
         */
        return preferences.getBoolean("isShown", false);
    }
}
