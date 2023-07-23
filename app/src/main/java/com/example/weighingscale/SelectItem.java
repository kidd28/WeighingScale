package com.example.weighingscale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weighingscale.Adapters.ChangeAddItemAdapters;
import com.example.weighingscale.Adapters.SelectItemAdapter;
import com.example.weighingscale.Models.ItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectItem extends AppCompatActivity {

    ArrayList<ItemModel> changeAddItemModel;
    SelectItemAdapter selectItemAdapter;
    RecyclerView rv;
    String SalesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);

        rv = findViewById(R.id.rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectItem.this);
        rv.setLayoutManager(layoutManager);
        changeAddItemModel = new ArrayList<>();

        SalesId = getIntent().getExtras().getString("SalesId");


        loadItem();
    }

    private void loadItem() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                changeAddItemModel.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ItemModel model = ds.getValue(ItemModel.class);
                    changeAddItemModel.add(model);
                }
                selectItemAdapter = new SelectItemAdapter(SelectItem.this, changeAddItemModel,SalesId);
                rv.setAdapter(selectItemAdapter);
                selectItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}