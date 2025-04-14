package com.example.todolist.data;

import androidx.room.ColumnInfo;
/// Simple object for retrieving only the id and title of the NoteList from Room.
public class NoteListTitleID{
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "title")
    public String title;
}
