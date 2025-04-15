package com.example.todolist.data;

import androidx.room.ColumnInfo;

/// Simple object for retrieving only the notes of the NoteList from Room.
public class NoteListNotes{
    @ColumnInfo(name = "notes")
    public String notes;
}
