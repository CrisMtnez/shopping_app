package com.example.timewax_assignment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Final screen with a thank you message for the order and a button to go back
 */
public class EndActivity extends AppCompatActivity {

    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        goBack = findViewById(R.id.backToShopBtn);
        ActionBar aB = getSupportActionBar();
        aB.hide();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
