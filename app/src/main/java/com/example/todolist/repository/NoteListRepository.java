package com.example.todolist.repository;


import com.example.todolist.data.NoteList;
import com.example.todolist.data.NoteListDatabase;
import com.example.todolist.data.NoteListNotes;
import com.example.todolist.data.NoteListTitleID;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

/// Repository class that handles the deleting, updating, or adding of NoteLists
public class NoteListRepository {
    private final NoteListDatabase mNoteListDatabase;

    public NoteListRepository(NoteListDatabase pNoteListDatabase) {
        this.mNoteListDatabase = pNoteListDatabase;
    }

    /**
     * Gets all the NoteLists's Title and id's
     *
     * @return List of NoteListTitleID objects
     */
    public Single<List<NoteListTitleID>> getAllNoteListsTitleId() {
        return mNoteListDatabase.getNoteListDao().getAllNoteListTitleId();
    }

    /**
     * Deletes A NoteList Entry from Room
     *
     * @param pId the id of the NoteList entry
     * @return Single containing number of rows effected
     */
    public Single<Integer> deleteNoteList(int pId) {
        return mNoteListDatabase.getNoteListDao().deleteNoteList(pId);
    }

    /**
     * Inserts a NoteList entry into Room
     *
     * @param pNoteList the NoteList Object to insert as an entry
     * @return Completable that completes when the work is done
     */
    public Completable saveNoteList(NoteList pNoteList) {
        return mNoteListDatabase.getNoteListDao().insertNoteList(pNoteList);
    }

    /**
     * Updates a NoteList entry's title in Room
     *
     * @param pId    the NoteList Object id to update
     * @param pTitle the new title
     * @return Completable that completes when the work is done
     */
    public Completable updateNoteListTitle(int pId, String pTitle) {
        return mNoteListDatabase.getNoteListDao().updateNoteListTitle(pId, pTitle);
    }

    /**
     * Updates a NoteList entry's notes in Room
     *
     * @param pId    the NoteList Object id
     * @param pNotes the notes for the list
     * @return Completable that completes when the work is done
     */
    public Completable updateNoteListNotes(int pId, String pNotes) {
        return mNoteListDatabase.getNoteListDao().updateNoteListNotes(pId, pNotes);
    }

    /**
     * Gets a NoteList entry's notes from Room
     *
     * @param pId - NoteList id
     * @return Single with notes
     */
    public Single<NoteListNotes> getNoteListNotes(int pId) {
        return mNoteListDatabase.getNoteListDao().getNotes(pId);
    }


}
