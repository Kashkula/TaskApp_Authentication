package com.example.taskapp_orig.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.taskapp_orig.R;
import com.example.taskapp_orig.ui.models.Task;

public class FormFragment extends Fragment {

    private EditText editText;
    protected Task task;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        task = (Task) requireArguments().getSerializable("task");
        if (task != null) {
            editText.setText(task.getTitle());
        }
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }

    private void save() {
        String text = editText.getText().toString().trim();
        if (!text.isEmpty()) {
            if (task != null) {
                task.setTitle(text);
                task.setUpdateTime(System.currentTimeMillis());
                App.getInstance().getDatabase().taskDao().update(task);
            } else {
                task = new Task(text, System.currentTimeMillis());
                task.setUpdateTime(System.currentTimeMillis());
                App.getInstance().getDatabase().taskDao().insert(task);
            }
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigateUp();
        } else {
            editText.setError("Заполните это поле!");
        }
    }
}