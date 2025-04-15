package com.example.todolist.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

/// Data Access Object for retrieving NoteList from Room.
@Dao
public interface NoteListDao {
    @Query("SELECT id, title FROM NoteList")
    /// Gets all note list's titles and ids from Room
    Single<List<NoteListTitleID>> getAllNoteListTitleId();

    /// Gets the notes field of a note list given the id
    @Query("SELECT notes FROM NoteList WHERE id=:id")
    Single<NoteListNotes> getNotes(int id);

    /// Inserts a NoteList entry into Room
    @Insert
    Completable insertNoteList(NoteList noteList);

    /// Deletes NoteList entry based on id
    @Query("DELETE FROM NoteList WHERE id=:id")
    Single<Integer> deleteNoteList(int id);

    /// Updates a NoteList entry's title
    @Query("UPDATE NoteList SET title=:title WHERE id=:id")
    Completable updateNoteListTitle(int id, String title);

    /// Updates a NoteList entry's notes
    @Query("UPDATE NoteList SET notes=:notes WHERE id=:id")
    Completable updateNoteListNotes(int id, String notes);


}

