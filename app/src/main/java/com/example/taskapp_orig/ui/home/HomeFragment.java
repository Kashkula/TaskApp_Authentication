package com.example.taskapp_orig.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp_orig.R;
import com.example.taskapp_orig.interfaces.OnItemClickListener;
import com.example.taskapp_orig.ui.App;
import com.example.taskapp_orig.ui.models.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private TaskAdapter adapter;
    private ArrayList<Task> list;
    protected int currentPosition;
    protected Task task;
    protected boolean sortCharBoolean = false, sortDateBoolean = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view)  {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        list = new ArrayList<>();

        App.getInstance().getDatabase().taskDao().getAllLive().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(App.getInstance().getDatabase().taskDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                currentPosition = position;
                task = list.get(position);
                openForm(task);
            }

            @Override
            public void onItemLongClick(int position) {
                showAlert(list.get(position));
            }

            private void showAlert(final Task task) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Удалить запись " + task.getTitle() + "?");
                builder.setNegativeButton("Отмена", null);
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.getInstance().getDatabase().taskDao().delete(task);
                    }
                });
                builder.show();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) openForm(null);
        if (item.getItemId() == R.id.updateAt)
            App.getInstance().getDatabase().taskDao().nukeTable();
        list.clear();
        if (item.getItemId() == R.id.sortChar) {
            if (sortCharBoolean) {
                list.addAll(App.getInstance().getDatabase().taskDao().getPersonsAlphabetically(true));
                sortCharBoolean = false;
            } else {
                list.addAll(App.getInstance().getDatabase().taskDao().getPersonsAlphabetically(false));
                sortCharBoolean = true;
            }
        }
        if (item.getItemId() == R.id.sortDate) {
            if (sortDateBoolean) {
                list.addAll(App.getInstance().getDatabase().taskDao().getTaskDateAlphabetically(true));
                sortDateBoolean = false;
            } else {
                list.addAll(App.getInstance().getDatabase().taskDao().getTaskDateAlphabetically(false));
                sortDateBoolean = true;
            }
        }
        adapter.notifyDataSetChanged();

        return super.onOptionsItemSelected(item);

    }

    private void openForm(Task task) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_navigation_home_to_formFragment, bundle);
    }
}