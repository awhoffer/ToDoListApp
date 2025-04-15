package com.example.todolist.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.data.NoteList;
import com.example.todolist.data.NoteListTitleID;
import com.example.todolist.repository.NoteListRepository;
import com.example.todolist.repository.OperationStates;
import com.example.todolist.repository.WrapperEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class ViewModelList extends ViewModel {

    private final NoteListRepository mNoteListRepository;
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private final List<NoteListTitleID> mEmptyNoteListTitleId = new ArrayList<>();
    private final MutableLiveData<WrapperEvent<List<NoteListTitleID>>> mAllNoteListsTitleIDLiveData = new MutableLiveData<>();
    private final MutableLiveData<WrapperEvent<OperationStates>> mListSavedLiveData = new MutableLiveData<>();
    private final MutableLiveData<WrapperEvent<String>> mListNotesLiveData = new MutableLiveData<>();

    @Inject
    ViewModelList(NoteListRepository pNoteListRepository) {
        this.mNoteListRepository = pNoteListRepository;
    }


    /**
     * LiveData for getting a list of Note titles and ids
     *
     * @return LiveData containing wrapper class with list of NoteListTitleIDs objects
     */
    public LiveData<WrapperEvent<List<NoteListTitleID>>> getNoteAllNoteListsTitleIDLiveData() {
        return mAllNoteListsTitleIDLiveData;
    }


    /**
     * LiveData for when a note db operation has completed
     *
     * @return Live Data containing WrapperEvent containing the operation state which has completed.
     * this could be UPDATED when a list has been updated or SAVED when a new list has been saved.
     */
    public LiveData<WrapperEvent<OperationStates>> getListSavedLiveData() {
        return mListSavedLiveData;
    }

    /**
     * LiveData for getting a List's notes
     *
     * @return LiveData Object containing a WrapperEvent object with the list's notes
     */
    public LiveData<WrapperEvent<String>> getListNotesLiveData() {
        return mListNotesLiveData;
    }

    /**
     * Requests all the title and ID of the NoteLists stored in Room. Live Data is set with a list
     * of NoteListTitleID objects when the operation completes.
     */
    public void requestAllListTitlesAndIDs() {
        mDisposables.add(mNoteListRepository.getAllNoteListsTitleId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(noteListTitleIDS -> mAllNoteListsTitleIDLiveData.setValue(new WrapperEvent<>(noteListTitleIDS)), err -> mAllNoteListsTitleIDLiveData.setValue(new WrapperEvent<>(mEmptyNoteListTitleId))));


    }

    /**
     * Deletes the NoteList stored in Room.
     */
    public void deleteList(int pId) {
        mDisposables.add(mNoteListRepository.deleteNoteList(pId)
                .subscribeOn(Schedulers.io())
                .subscribe());

    }

    /**
     * Gets the notes of a NoteList
     *
     * @param pId NoteList id
     */
    public void getNotes(int pId) {
        mDisposables.add(mNoteListRepository.getNoteListNotes(pId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(noteListNotes -> mListNotesLiveData.setValue(new WrapperEvent<>(noteListNotes.notes))));
    }

    /**
     * Saves a NoteList to Room.
     *
     * @param pTitle title of list
     * @param pNote  the list's notes
     */
    public void saveNote(String pTitle, String pNote) {
        NoteList newNoteList = new NoteList();
        newNoteList.title = pTitle;
        newNoteList.notes = pNote;
        mDisposables.add(mNoteListRepository.saveNoteList(newNoteList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> mListSavedLiveData.setValue(new WrapperEvent<>(OperationStates.SAVED))));
    }

    /**
     * Updates the title,id,and note of NoteList in Room.
     *
     * @param pId    - id of NoteList
     * @param pTitle - title of list
     * @param pNote  - the list's notes
     */
    public void updateNote(int pId, String pTitle, String pNote) {
        NoteList newNoteList = new NoteList();
        newNoteList.id = pId;
        newNoteList.title = pTitle;
        newNoteList.notes = pNote;


        mDisposables.add(mNoteListRepository.updateNoteListTitle(pId, pTitle)
                .andThen(mNoteListRepository.updateNoteListNotes(pId, pNote))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> mListSavedLiveData.setValue(new WrapperEvent<>(OperationStates.UPDATED))));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.dispose();
    }
}
