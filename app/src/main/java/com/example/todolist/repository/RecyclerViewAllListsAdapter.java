package com.example.todolist.repository;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.data.NoteListTitleID;

import java.util.List;

import com.example.todolist.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * Recyclerview adapter class to show lists that have been created by the user.
 */
public class RecyclerViewAllListsAdapter extends RecyclerView.Adapter<RecyclerViewAllListsAdapter.ViewHolder> {
    private final List<NoteListTitleID> mAllNoteListTitleId;
    private final InterfaceRecyclerViewNoteListItem mInterfaceRecyclerViewItem;
    private final MaterialAlertDialogBuilder mMaterialDialogBuilderDelete;

    /**
     * @param pAllNoteListTitleID               the list of NoteListTitleID
     * @param pMaterialAlertDialogDeleteBuilder material dialog builder to appear before item is deleted
     * @param pInterfaceRecyclerViewItem        InterfaceRecyclerViewItem to to use for item click and delete events
     */
    public RecyclerViewAllListsAdapter(List<NoteListTitleID> pAllNoteListTitleID, MaterialAlertDialogBuilder pMaterialAlertDialogDeleteBuilder, InterfaceRecyclerViewNoteListItem pInterfaceRecyclerViewItem) {
        this.mAllNoteListTitleId = pAllNoteListTitleID;
        this.mInterfaceRecyclerViewItem = pInterfaceRecyclerViewItem;
        this.mMaterialDialogBuilderDelete = pMaterialAlertDialogDeleteBuilder;
    }


    @NonNull
    @Override
    public RecyclerViewAllListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_show_list_meta, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.getTextViewNoteListTitle().setOnLongClickListener(v -> {

            holder.getOnLongClickListenerForNoteListTitle().onClick();
            return true;

        });
        holder.getTextViewNoteListTitle().setOnClickListener(v -> holder.getOnClickListenerForNoteListTitle().onClick());
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAllListsAdapter.ViewHolder holder, int position) {
        NoteListTitleID noteListTitleID = mAllNoteListTitleId.get(position);
        holder.getTextViewNoteListTitle().setText(noteListTitleID.title);
        holder.setOnClickListenerForNoteListTitle(() -> mInterfaceRecyclerViewItem.onClick(noteListTitleID.id, noteListTitleID.title));

        holder.setOnLongClickListenerForListTitle(() -> {
            //make user wants to delete by showing dialog. If user hits the positive button, then the list will get deleted
            mMaterialDialogBuilderDelete.setPositiveButton(R.string.FragmentAllListsDeleteDialogDelete, (dialog, which) -> {
                int index = mAllNoteListTitleId.indexOf(noteListTitleID);
                mAllNoteListTitleId.remove(index);
                notifyItemRemoved(index);
                mInterfaceRecyclerViewItem.onDelete(noteListTitleID.id);
            }).setNegativeButton(R.string.FragmentAllListsDeleteDialogCancel, (dialog, which) -> dialog.dismiss());
            mMaterialDialogBuilderDelete.show();
        });
    }

    @Override
    public int getItemCount() {
        return mAllNoteListTitleId.size();
    }

    /// ViewHolder class for RecyclerViewAllListsAdapter.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private InterfaceOnClickListener mOnLongClickListenerForListTitle;
        private InterfaceOnClickListener mOnClickListenerForNoteListTitle;
        private final TextView mTextViewNoteListTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTextViewNoteListTitle = itemView.findViewById(R.id.TextVieRecShowListMeta);
        }

        /**
         * Used to set the interface for when the ViewHolder's title is long clicked.
         *
         * @param pOnLongClickListenerForListTitle - InterfaceOnClickListener
         */
        public void setOnLongClickListenerForListTitle(InterfaceOnClickListener pOnLongClickListenerForListTitle) {
            this.mOnLongClickListenerForListTitle = pOnLongClickListenerForListTitle;
        }

        /**
         * Used to set the interface for when the ViewHolder's title is clicked.
         *
         * @param pOnClickListenerForNoteListTitle - InterfaceOnClickListener
         */
        public void setOnClickListenerForNoteListTitle(InterfaceOnClickListener pOnClickListenerForNoteListTitle) {
            this.mOnClickListenerForNoteListTitle = pOnClickListenerForNoteListTitle;
        }

        /// Gets the interface for when the list title is long clicked.
        public InterfaceOnClickListener getOnLongClickListenerForNoteListTitle() {
            return this.mOnLongClickListenerForListTitle;
        }

        /// Gets the interface for when the list title is clicked.
        public InterfaceOnClickListener getOnClickListenerForNoteListTitle() {
            return mOnClickListenerForNoteListTitle;
        }


        public TextView getTextViewNoteListTitle() {
            return mTextViewNoteListTitle;
        }


    }
}

