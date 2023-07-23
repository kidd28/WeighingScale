package com.example.weighingscale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weighingscale.Adapters.ChangeAddItemAdapters;
import com.example.weighingscale.Models.ItemModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChangeAddItem extends AppCompatActivity {
    ArrayList<ItemModel> changeAddItemModel;
    ChangeAddItemAdapters changeAddItemAdapters;
    RecyclerView rv;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_add_item);
        rv = findViewById(R.id.rv);
        fab = findViewById(R.id.addBtn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChangeAddItem.this);
        rv.setLayoutManager(layoutManager);
        changeAddItemModel = new ArrayList<>();

        loadItem();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangeAddItem.this, AddChangeItemUi.class));
            }
        });
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
                changeAddItemAdapters = new ChangeAddItemAdapters(ChangeAddItem.this, changeAddItemModel);
                rv.setAdapter(changeAddItemAdapters);
                changeAddItemAdapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  startActivity(new Intent(Read.this, Dashboard.class));
        // Read.this.finish();
    }

}