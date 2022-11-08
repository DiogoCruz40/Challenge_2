package pt.cm.challenge_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pt.cm.challenge_2.Interfaces.ClickListener;
import pt.cm.challenge_2.Interfaces.LongClickListener;
import pt.cm.challenge_2.database.entities.Note;
import pt.cm.challenge_2.dtos.NoteDTO;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<NoteDTO> notes;
    private List<NoteDTO> filteredNotes;
    private ClickListener mClickListener;
    private LongClickListener mLongClickListener;

    // RecyclerView recyclerView;
    public ListAdapter(List<NoteDTO> notes, ClickListener clickListener, LongClickListener longClickListener) {
        this.notes = notes;
        this.filteredNotes = notes;
        this.mClickListener = clickListener;
        this.mLongClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem, mClickListener, mLongClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NoteDTO note = filteredNotes.get(position);
        holder.textView.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return filteredNotes != null ? filteredNotes.size() : 0;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public  void setFilteredNotes(List<NoteDTO> filteredList){
        this.filteredNotes = filteredList;
        notifyDataSetChanged();
    }

    public List<NoteDTO> getFilteredNotes() {
        return filteredNotes;
    }
    public List<NoteDTO> getNotes() {
        return notes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView textView;
        public RelativeLayout relativeLayout;
        ClickListener clickListener;
        LongClickListener longClickListener;

        public ViewHolder(View itemView, ClickListener clickListener, LongClickListener longClickListener) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            this.clickListener = clickListener;
            this.longClickListener = longClickListener;

            itemView.setOnClickListener(this::onClick);
            itemView.setOnLongClickListener(this::onLongClick);
        }


        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            longClickListener.onLongItemClick(getAdapterPosition(), view);
            return true;
        }
    }
}


