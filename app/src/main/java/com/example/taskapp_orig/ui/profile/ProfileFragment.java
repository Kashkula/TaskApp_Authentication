package com.example.taskapp_orig.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.taskapp_orig.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ProfileFragment extends Fragment {

    protected NavController navController;
    protected AppBarConfiguration appBarConfiguration;
    protected BottomNavigationView navView;
    private ImageView imageBackBtn, imageAvatar, imageBackground;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        onClick();

    }

    private void onClick() {
        imageBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 22);
            }
        });

        imageBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//
                startActivityForResult(intent, 21);
            }
        });
    }

    private void init(View view) {
        imageBackBtn = view.findViewById(R.id.imageBackBtn);
        imageBackground = view.findViewById(R.id.imageBackground);
        imageAvatar = view.findViewById(R.id.imageAvatar);

        navView = view.findViewById(R.id.profileNavView);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.layout.fragment_profile_photo,
                R.layout.fragment_profile_video,
                R.layout.fragment_profile_settings)
                .build();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_profile_fragment);

        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        Glide.with(this).load(R.drawable.hediedwith).circleCrop().into(imageAvatar);
        Glide.with(this).load(R.drawable.thevigitarian).centerCrop().into(imageBackground);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 22) {
                Glide.with(this).load(data.getData()).circleCrop().into(imageAvatar);
            }
            if (requestCode == 21) {
                Glide.with(this).load(data.getData()).into(imageBackground);
            }
        }
    }
}