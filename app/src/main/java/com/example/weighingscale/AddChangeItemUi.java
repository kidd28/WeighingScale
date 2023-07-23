package com.example.weighingscale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddChangeItemUi extends AppCompatActivity {
    EditText name,price;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_change_item_ui);

        name = findViewById(R.id.itemName);
        price = findViewById(R.id.price);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name, Price;
                Name = name.getText().toString();
                Price = price.getText().toString();
                String id = String.valueOf(System.currentTimeMillis());

                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Items");
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("Name", Name);
                        hashMap.put("Price", Price);
                        hashMap.put("Id", id);
                        database.child(Name).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddChangeItemUi.this, "Added", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AddChangeItemUi.this, ChangeAddItem.class);
                                startActivity(intent);
                                AddChangeItemUi.this.finish();
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}