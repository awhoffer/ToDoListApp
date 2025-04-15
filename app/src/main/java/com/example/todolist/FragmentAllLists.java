package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.data.NoteListTitleID;
import com.example.todolist.databinding.FragmentAllListsBinding;
import com.example.todolist.repository.InterfaceRecyclerViewNoteListItem;
import com.example.todolist.repository.RecyclerViewAllListsAdapter;
import com.example.todolist.viewmodel.ViewModelList;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentAllLists extends Fragment {

    private FragmentAllListsBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = FragmentAllListsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        ViewModelList viewModelList = new ViewModelProvider(requireActivity()).get(ViewModelList.class);
        ImageButton imageButtonAddList = mBinding.ImageButtonAddList;
        RecyclerView recyclerViewAllLists = mBinding.RecyclerViewAllLists;
        recyclerViewAllLists.setLayoutManager(new LinearLayoutManager(requireContext()));
        viewModelList.requestAllListTitlesAndIDs();

        //Create material dialog to ask user if they are sure before deleting list.
        MaterialAlertDialogBuilder materialAlertDialogDeleteBuilder = new MaterialAlertDialogBuilder(requireContext())
                .setMessage(R.string.FragmentAllListsDeleteDialogMessage);


        viewModelList.getNoteAllNoteListsTitleIDLiveData().observe(getViewLifecycleOwner(), wrapperEventNoteListTitleIDS -> {
            if (wrapperEventNoteListTitleIDS.contentIsNotHandled()) {
                List<NoteListTitleID> noteListTitleIDS = wrapperEventNoteListTitleIDS.getContent();

                RecyclerViewAllListsAdapter recyclerViewAllListsAdapter = new RecyclerViewAllListsAdapter(noteListTitleIDS, materialAlertDialogDeleteBuilder, new InterfaceRecyclerViewNoteListItem() {
                    @Override
                    public void onDelete(int pId) {

                        viewModelList.deleteList(pId);
                    }

                    @Override
                    public void onClick(int pId, String pTitle) {
                        //sets the action argument which will pass the id of the note list to FragmentViewList
                        FragmentAllListsDirections.ActionFragmentAllListsToFragmentViewList action = FragmentAllListsDirections
                                .actionFragmentAllListsToFragmentViewList(pTitle)
                                .setListIdArg(pId);
                        //query for the notes of the note list with specific id which will displayed in FragmentViewList
                        viewModelList.getNotes(pId);
                        navController.navigate(action);
                    }

                });
                recyclerViewAllLists.setAdapter(recyclerViewAllListsAdapter);
            }
        });

        //On Click listener for the add list button. When navigating, this list will be a newly created list so we will add the default title as an argument.
        imageButtonAddList.setOnClickListener(v -> navController.navigate(FragmentAllListsDirections.actionFragmentAllListsToFragmentViewList(getString(R.string.FragmentViewListEnterTitle))));


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

}