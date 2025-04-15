package com.example.todolist;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.todolist.databinding.ActivityMainBinding;
import com.example.todolist.viewmodel.ViewModelList;
import com.google.android.material.appbar.MaterialToolbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ActivityMain extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        MaterialToolbar toolbar = mBinding.MaterialToolbarLists;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_activity_main);
        ViewModelList viewModelList = new ViewModelProvider(this).get(ViewModelList.class);

        //Change Toolbar title based on navigation destination
        navController.addOnDestinationChangedListener((controller, navDestination, bundle) -> {
            final int destination = navDestination.getId();

            if (destination == R.id.fragmentAllLists) {
                toolbar.setVisibility(VISIBLE);
                toolbar.setNavigationIcon(null);
                mBinding.MaterialToolbarLists.setTitle(getString(R.string.TitleViewAllLists));
            } else if (destination == R.id.fragmentViewList) {
                toolbar.setVisibility(GONE);
            }

        });

    }


}