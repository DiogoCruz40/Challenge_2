package pt.cm.challenge_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<Note> notes;
    private ClickListener mClickListener;
    private LongClickListener mLongClickListener;

    // RecyclerView recyclerView;
    public ListAdapter(ArrayList<Note> notes, ClickListener clickListener, LongClickListener longClickListener) {
        this.notes = notes;
        this.mClickListener = clickListener;
        this.mLongClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem, mClickListener, mLongClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Note note = notes.get(position);
        holder.textView.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public ArrayList<Note> getNotes() {
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

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public interface LongClickListener {
        void onLongItemClick(int position, View v);
    }
}


