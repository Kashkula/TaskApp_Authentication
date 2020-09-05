package com.example.taskapp_orig.ui.profileFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskapp_orig.R;
import com.example.taskapp_orig.ui.adapters.PhotoAdapter;

public class ProfileFragmentPhoto extends Fragment {

    private int[] images = {R.drawable.hediedwith, R.drawable.ifg, R.drawable.mariasemples, R.drawable.privacy, R.drawable.thevigitarian, R.drawable.thewildrobot
            , R.drawable.utl};
    private String[] imagesName = {"Samuel Jackson", "Katy Perry", "Alika", "Sazhi", "Lover", "Peri", "Peter"};

    protected RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_profilePhoto);
        layoutManager = new GridLayoutManager(requireActivity(), 3);
        PhotoAdapter adapter = new PhotoAdapter(images, imagesName);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}