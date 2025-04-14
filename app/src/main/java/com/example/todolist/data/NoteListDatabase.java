package com.example.todolist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = NoteList.class)
public abstract class NoteListDatabase extends RoomDatabase {
//Gets NoteList Dao
public abstract NoteListDao getNoteListDao();

}
