package com.example.taskapp_orig.ui.onBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.taskapp_orig.Prefs;
import com.example.taskapp_orig.R;
import com.example.taskapp_orig.interfaces.OnViewClickListener;

public class PageAdapter extends PagerAdapter {

    private String[] titles = {"Привет!", "Готов???", "Начинай!!!"};
    private OnViewClickListener onViewClickListener;

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.page_board, container, false);
        TextView textTitle = view.findViewById(R.id.textTitle);

        Button btnGetStart = view.findViewById(R.id.btn_getStarting);
        textTitle.setText(titles[position]);
        container.addView(view);
        if (position == 2) btnGetStart.setVisibility(View.VISIBLE);
        else btnGetStart.setVisibility(View.GONE);
        btnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                отправляет данные, о том что на кнопку нажали
                 */
                new Prefs(container.getContext()).setShown();
                onViewClickListener.onViewClick();
            }
        });
        return view;
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
