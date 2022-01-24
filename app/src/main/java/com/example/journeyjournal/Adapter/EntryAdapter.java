package com.example.journeyjournal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.journeyjournal.Model.JournalEntry;
import com.example.journeyjournal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class EntryAdapter extends FirestoreRecyclerAdapter<JournalEntry, EntryAdapter.EntryHolder> {

    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EntryAdapter(@NonNull FirestoreRecyclerOptions<JournalEntry> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EntryHolder holder, int position, @NonNull JournalEntry model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDate.setText(model.getDate());
        Picasso.get().load(model.getImageUrl()).into(holder.imageViewBackground);
//        holder.imageViewBackground.setBackground(model.getImageUrl());
        holder.textViewDescription.setText(model.getDescription());
    }

    @NonNull
    @Override
    public EntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_list_item, parent, false);
        return new EntryHolder(v);
    }

    class EntryHolder extends RecyclerView.ViewHolder{
        ImageView imageViewBackground;
        TextView textViewDate;
        TextView textViewTitle;
        TextView textViewDescription;

        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.entry_background);
            textViewDate = itemView.findViewById(R.id.entry_date);
            textViewTitle = itemView.findViewById(R.id.entry_title);
            textViewDescription = itemView.findViewById(R.id.entry_description);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    
    public interface OnItemClickListener{
        //these are elements passed to the activity from the adapter
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    
    //this is used in the main activity to set it as the onclick listener for the adapter
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    
}
