package com.example.weighingscale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    Button create, price,inventory,settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        create = findViewById(R.id.create);
        price = findViewById(R.id.changePrice);
        inventory = findViewById(R.id.inventory);
        settings = findViewById(R.id.settings);

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, ChangeAddItem.class));
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, SelectItem.class);
                String Sale_id = String.valueOf(System.currentTimeMillis());
                intent.putExtra("SalesId", Sale_id);
                startActivity(intent);
            }
        });
    }
}