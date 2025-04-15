package com.example.todolist.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/// Entity for creating NoteList rows in Room
@Entity
public class NoteList {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String notes;
}
