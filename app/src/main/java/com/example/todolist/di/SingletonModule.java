package com.example.todolist.di;

import android.content.Context;

import androidx.room.Room;

import com.example.todolist.data.NoteListDatabase;
import com.example.todolist.repository.NoteListRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/// Hilt dependency injection class that installs bindings into the Singleton Component
@Module
@InstallIn(SingletonComponent.class)
public class SingletonModule {
    @Singleton
    @Provides
    public NoteListDatabase getNoteListDatabase(@ApplicationContext Context pContext) {
        return Room.databaseBuilder(pContext, NoteListDatabase.class, "NoteListDatabase").build();
    }

    @Provides
    public NoteListRepository getNoteListRepository(NoteListDatabase pNoteListDatabase) {
        return new NoteListRepository(pNoteListDatabase);
    }

}
