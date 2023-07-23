package com.example.weighingscale.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weighingscale.Models.ItemModel;
import com.example.weighingscale.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.HolderAdapter> {
    Context context;
    ArrayList<ItemModel> ItemModels;

    public ItemListAdapter(Context context , ArrayList<ItemModel> ItemModels){
        this.context = context;
        this.ItemModels = ItemModels;
    }
    @NonNull
    @Override
    public ItemListAdapter.HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent,false);

        return new ItemListAdapter.HolderAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.HolderAdapter holder, int position) {
        ItemModel model = ItemModels.get(position);
        String name = model.getName();
        String price = model.getPrice();
        String amount = model.getKg();
        String total = model.getTotal();

        holder.name.setText(name);
        holder.price.setText("Price: "+price+" * ");
        holder.amount.setText(amount+"kg"+" = ");
        holder.total.setText(total);
    }

    @Override
    public int getItemCount() {
        return ItemModels.size();
    }

    public class HolderAdapter extends RecyclerView.ViewHolder {
        TextView price,amount,name, total;
        public HolderAdapter(@NonNull View itemView) {
            super(itemView);
            price= itemView.findViewById(R.id.price);
            amount = itemView.findViewById(R.id.amount);
            name = itemView.findViewById(R.id.name);
            total = itemView.findViewById(R.id.total);

        }
    }
}
