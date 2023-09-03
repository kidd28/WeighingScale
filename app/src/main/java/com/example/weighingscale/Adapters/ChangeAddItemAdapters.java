package com.example.weighingscale.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weighingscale.Models.ItemModel;
import com.example.weighingscale.R;

import java.util.ArrayList;

public class ChangeAddItemAdapters extends RecyclerView.Adapter<ChangeAddItemAdapters.HolderAdapter> {
    Context context;
    ArrayList<ItemModel> changeAddItemModels;

    public ChangeAddItemAdapters(Context context , ArrayList<ItemModel> ChangeAddItemModels){
        this.context = context;
        this.changeAddItemModels = ChangeAddItemModels;
    }
    @NonNull
    @Override
    public ChangeAddItemAdapters.HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_add_item, parent,false);

        return new ChangeAddItemAdapters.HolderAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeAddItemAdapters.HolderAdapter holder, int position) {
        ItemModel model = changeAddItemModels.get(position);
        String name = model.getName();
        String price = model.getPrice();

        holder.name.setText(name);
        holder.price.setText("Price: "+price);
    }

    @Override
    public int getItemCount() {
        return changeAddItemModels.size();
    }

    public class HolderAdapter extends RecyclerView.ViewHolder{
        RelativeLayout layout;
        TextView name, price;
        public HolderAdapter(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
