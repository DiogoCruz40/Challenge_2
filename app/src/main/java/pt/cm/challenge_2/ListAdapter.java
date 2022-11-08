package pt.cm.challenge_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pt.cm.challenge_2.Interfaces.FragmentOneInterface;
import pt.cm.challenge_2.dtos.NoteDTO;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<NoteDTO> notes;
    private List<NoteDTO> filteredNotes;
    private FragmentOneInterface fragmentOneInterface;

    // RecyclerView recyclerView;
    public ListAdapter(List<NoteDTO> notes, FragmentOneInterface fragmentOneInterface) {
        this.notes = notes;
        this.filteredNotes = notes;
        this.fragmentOneInterface = fragmentOneInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem, fragmentOneInterface);
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
        FragmentOneInterface fragmentOneInterface;

        public ViewHolder(View itemView, FragmentOneInterface fragmentOneInterface) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            this.fragmentOneInterface = fragmentOneInterface;

            itemView.setOnClickListener(this::onClick);
            itemView.setOnLongClickListener(this::onLongClick);
        }


        @Override
        public void onClick(View view) {
            fragmentOneInterface.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            fragmentOneInterface.onLongItemClick(getAdapterPosition(), view);
            return true;
        }
    }
}


