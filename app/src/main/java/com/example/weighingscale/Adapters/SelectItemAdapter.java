package com.example.weighingscale.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weighingscale.CreateList;
import com.example.weighingscale.Models.ItemModel;
import com.example.weighingscale.R;

import java.util.ArrayList;

public class SelectItemAdapter extends RecyclerView.Adapter<SelectItemAdapter.HolderAdapter> {
    Context context;
    ArrayList<ItemModel> ItemModels;
    String SalesId;

    public SelectItemAdapter(Context context, ArrayList<ItemModel> changeAddItemModels,String salesId){
        this.context = context;
        this.ItemModels = changeAddItemModels;
        this.SalesId = salesId;
    }
    @NonNull
    @Override
    public SelectItemAdapter.HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_add_item, parent,false);

        return new SelectItemAdapter.HolderAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectItemAdapter.HolderAdapter holder, int position) {
        ItemModel model = ItemModels.get(position);
        String name = model.getName();
        String price = model.getPrice();

        holder.name.setText(name);
        holder.price.setText("Price: "+price);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CreateList.class);
                i.putExtra("Name",name);
                i.putExtra("Price", price);
                i.putExtra("Id", model.getId());
                i.putExtra("SalesId", SalesId);
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ItemModels.size();
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
