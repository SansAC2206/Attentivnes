package com.example.attentivnes.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attentivnes.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>
{
    private List<String> playersList;

    public ItemsAdapter(List<String> playersList) {
        this.playersList = playersList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        String playerName = playersList.get(position);
        holder.playerName.setText(playerName);
    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    static class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        ItemsViewHolder(View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
        }
    }
}
