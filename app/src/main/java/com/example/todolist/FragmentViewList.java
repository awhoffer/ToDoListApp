package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.todolist.databinding.FragmentViewListBinding;
import com.example.todolist.repository.OperationStates;
import com.example.todolist.viewmodel.ViewModelList;
import com.google.android.material.textfield.TextInputLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentViewList extends Fragment {

    private FragmentViewListBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentViewListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        ViewModelList viewModelList = new ViewModelProvider(requireActivity()).get(ViewModelList.class);
       TextInputLayout textInputLayoutNoteTitle = mBinding.TextInputLayoutNoteTitle;
       Button buttonDone = mBinding.ButtonListSave;
       TextInputLayout textInputLayoutNotes = mBinding.TextInputLayoutNotes;
        //gets the argument from the nav action which determines if there is a new list being created(default value of -1)
        //or if a list is getting updated(id of the note list which will always be greater than -1)
        int updateNoteId = FragmentViewListArgs.fromBundle(getArguments()).getListIdArg();
        String titleText = FragmentViewListArgs.fromBundle(getArguments()).getListTitle();
        //set the title text
        if(textInputLayoutNoteTitle.getEditText()!=null) {
            textInputLayoutNoteTitle.getEditText().setText(titleText);
        }

        viewModelList.getListNotesLiveData().observe(getViewLifecycleOwner(), wrapperEventNotes -> {
            if(wrapperEventNotes.contentIsNotHandled()) {
                String notes = wrapperEventNotes.getContent();
                if (textInputLayoutNotes.getEditText() != null) {
                    textInputLayoutNotes.getEditText().setText(notes);
                }
            }
        });


        viewModelList.getListSavedLiveData().observe(getViewLifecycleOwner(), wrapperEventOperationStates -> {
            if(wrapperEventOperationStates.contentIsNotHandled()){
                OperationStates operationState = wrapperEventOperationStates.getContent();
                if(operationState == OperationStates.SAVED || operationState == OperationStates.UPDATED){
                navController.navigate(FragmentViewListDirections.actionFragmentViewListToFragmentAllLists());
            }
            }
        });

        buttonDone.setOnClickListener(v->{
            EditText editTextTitle = textInputLayoutNoteTitle.getEditText();
            EditText editTextNotes = textInputLayoutNotes.getEditText();

            if(editTextTitle!=null && editTextNotes!=null)

                if(updateNoteId != -1) {

                    viewModelList.updateNote(updateNoteId,editTextTitle.getText().toString(), editTextNotes.getText().toString());

                }else{
                    viewModelList.saveNote(editTextTitle.getText().toString(), editTextNotes.getText().toString());

                }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

}