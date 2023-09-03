package com.example.weighingscale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weighingscale.Adapters.ChangeAddItemAdapters;
import com.example.weighingscale.Adapters.ItemListAdapter;
import com.example.weighingscale.Models.ItemModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.jar.Attributes;

public class CreateList extends AppCompatActivity {
    ArrayList<ItemModel> itemModels;
    ItemListAdapter itemListAdapter;
    RecyclerView rv;
    TextView name,tv_total;
    EditText input;
    Button add, next, cancel, save;

    String Name, Price, Id, SaleId, Date, Month;


    int current, sum, total, totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        input = findViewById(R.id.input);
        add = findViewById(R.id.add);
        next = findViewById(R.id.next);
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);

        name = findViewById(R.id.itemname);
        tv_total = findViewById(R.id.total);


        rv = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CreateList.this);
        rv.setLayoutManager(layoutManager);
        itemModels = new ArrayList<>();

        Name = getIntent().getExtras().getString("Name");
        Price = getIntent().getExtras().getString("Price");
        Id = getIntent().getExtras().getString("Id");
        SaleId = getIntent().getExtras().getString("SalesId");

        LocalDate dateObj = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            DateTimeFormatter MonthFormat = DateTimeFormatter.ofPattern("MM-yyyy");
            Date = dateObj.format(formatter);
            Month = dateObj.format(MonthFormat);
        }


        name.setText(Name);
        totalAmount = 0;
        sum = 0;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compute();

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compute();
                Intent i = new Intent(CreateList.this, SelectItem.class);
                i.putExtra("SalesId", SaleId);
                startActivity(i);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        loadSale(SaleId);
        displayTotal();
    }
    public void compute() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Sales").child(Month.toString()).child(SaleId);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (String.valueOf(input.getText()).equals("")) {
                    current = 0;
                } else {
                    current = Integer.parseInt(String.valueOf(input.getText()));
                }

                for (DataSnapshot ds : snapshot.getChildren()) {
                    System.out.println(ds.child("Id").getValue());
                    if (!ds.getKey().equals("TotalAmount")) {
                        if (ds.child("Id").getValue().equals(Id)) {
                            sum = Integer.parseInt(String.valueOf(ds.child("Kg").getValue()));
                        }
                    }
                }
                if (!String.valueOf(input.getText()).equals("")) {
                    sum += current;
                    total = sum * Integer.parseInt(Price);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Name", Name);
                    hashMap.put("Kg", String.valueOf(sum));
                    hashMap.put("Price", Price);
                    hashMap.put("Id", Id);
                    hashMap.put("Total", String.valueOf(total));
                    hashMap.put("Date", Date);
                    database.child(Name).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            input.setText("");
                            loadSale(SaleId);
                            displayTotal();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void loadSale(String SaleId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Sales").child(Month).child(SaleId);
        reference.addValueEventListener(new ValueEventListener() {
                @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemModels.clear();
                int total = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (!ds.getKey().equals("TotalAmount")) {
                        ItemModel model = ds.getValue(ItemModel.class);
                        int cost = Integer.valueOf(model.getTotal());
                        total = total + cost;
                        itemModels.add(model);
                    }
                }

                if (total !=0){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("TotalAmount", String.valueOf(total));
                    reference.updateChildren(hashMap);
                }
                itemListAdapter = new ItemListAdapter(CreateList.this, itemModels);
                rv.setAdapter(itemListAdapter);
                itemListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void displayTotal(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Sales").child(Month).child(SaleId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot.child("TotalAmount").getValue());
                tv_total.setText("TOTAL: P"+ snapshot.child("TotalAmount").getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void cancel(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateList.this);
        builder.setTitle("Choose Action");
        builder.setMessage("Are you sure you want to cancel this transaction?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Sales").child(Month).child(SaleId);
                databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        databaseReference.child("TotalAmount").removeValue();
                        Toast.makeText(CreateList.this, "Cancelled", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CreateList.this, Dashboard.class));
                        CreateList.this.finish();
                    }
                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
    @Override
    public void onBackPressed() {
        cancel();
    }
}