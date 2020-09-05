package com.example.taskapp_orig.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp_orig.R;
import com.example.taskapp_orig.interfaces.OnItemClickListener;
import com.example.taskapp_orig.ui.models.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> list;
    protected OnItemClickListener onItemClickListener;


    public TaskAdapter(ArrayList<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle,textTime,textViewUpdateTime;
        private RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textView);
            textTime = itemView.findViewById(R.id.textTime);
            relativeLayout = itemView.findViewById(R.id.relative_layout_main);
            textViewUpdateTime = itemView.findViewById(R.id.textViewUpdateTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(Task task) {
            if (task.getTitle().length()>5)
                relativeLayout.setBackgroundColor(Color.GRAY);
            else relativeLayout.setBackgroundColor(Color.WHITE);
            textTitle.setText(task.getTitle());
            textTime.setText(getDate(task.getCreatedAt()));
            textViewUpdateTime.setText(getUpdateDate(task.getUpdateTime()));
        }

        private String getDate(long time) {
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm zzzz", Locale.getDefault());
            return dateFormat.format(time);
        }

        private String getUpdateDate(long time) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());
            return dateFormat.format(time);
        }
    }
}
