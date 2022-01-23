package com.example.journeyjournal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.journeyjournal.Interface.EntryRecyclerViewInterface;
import com.example.journeyjournal.Model.JournalEntry;
import com.example.journeyjournal.R;

import java.util.ArrayList;

public class JournalEntryRecViewAdapter extends RecyclerView.Adapter<JournalEntryRecViewAdapter.ViewHolder> {

    private final EntryRecyclerViewInterface entryRecyclerViewInterface;
    private ArrayList<JournalEntry> entries = new ArrayList<>();

    public JournalEntryRecViewAdapter(EntryRecyclerViewInterface entryRecyclerViewInterface) {
        this.entryRecyclerViewInterface = entryRecyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view, entryRecyclerViewInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.entryTitle.setText(entries.get(position).getTitle());
        holder.entryDescription.setText(entries.get(position).getDescription());
        holder.entryDate.setText(entries.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void setEntries(ArrayList<JournalEntry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView entryImage;
        private TextView entryTitle, entryDescription, entryDate;
        public ViewHolder(@NonNull View itemView,EntryRecyclerViewInterface entryRecyclerViewInterface) {
            super(itemView);
            entryImage = itemView.findViewById(R.id.entry_background);
            entryTitle = itemView.findViewById(R.id.entry_title);
            entryDescription = itemView.findViewById(R.id.entry_description);
            entryDate = itemView.findViewById(R.id.entry_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(entryRecyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            entryRecyclerViewInterface.onEntryClick(position);
                        }
                    }
                }
            });
        }
    }
}
