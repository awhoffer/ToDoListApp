package com.example.todolist.repository;

/// Interface for use in a Recycler View to handle item click or delete events.
public interface InterfaceRecyclerViewNoteListItem {
    void onDelete(int pId);

    void onClick(int pId, String pTitle);

}
