package com.example.timewax_assignment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Screen with a wider view of the cart to finish the purchase
 */
public class CheckOutActivity extends AppCompatActivity {

    MiniAdapter adapter;
    ListView finalList;
    Button purchase,discountBtn;
    MainActivity m;
    double totalPriceDouble;
    TextView totalCosts;
    EditText discountInput;
    boolean discountApplied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        adapter = new MiniAdapter(this,MainActivity.selectedProducts);
        finalList = findViewById(R.id.shoppingListFinal);
        finalList.setAdapter(adapter);
        purchase = findViewById(R.id.finishPurchaseBtn);
        m = new MainActivity();
        totalCosts = findViewById(R.id.totalCosts);
        totalPriceDouble = updateTotalPrice();
        discountApplied = false;
        discountBtn = findViewById(R.id.applydiscountbtn);

        /**
         * Possible discounts applicable -only once on the purchase.
         * Switch is used given that we only introduced 3 discounts for a test.
         */
        discountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountInput = findViewById(R.id.discountInput);
                String code = discountInput.getText().toString();
                if (!discountApplied){
                    switch (code.toUpperCase()){
                        case "SUPER50":
                            totalCosts.setText(String.format("%.2f €", totalPriceDouble/2));
                            MainActivity.eventManager(null,Event.EventCodes.Discount50,0,false);
                            break;
                        case "SALES20":
                            totalCosts.setText(String.format("%.2f €", (totalPriceDouble/5)*4));
                            MainActivity.eventManager(null,Event.EventCodes.Discount20,0,false);
                            break;
                        case "GIFT10OFF":
                            totalCosts.setText(String.format("%.2f €", (totalPriceDouble/10)*9));
                            MainActivity.eventManager(null,Event.EventCodes.Discount10,0,false);
                            break;
                    }
                }
            }
        });

        /**
         * Sends the client to what would be the payment gateway in a future implementation
         */
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.eventManager(view,Event.EventCodes.Payment,0,false);
                Intent intent = new Intent(CheckOutActivity.this,EndActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Updates the total price of the purchase after some event
     * @return the total price
     */
    public double updateTotalPrice(){
        double t = 0;
        for (Product p : MainActivity.selectedProducts) {
            t += p.getTotalPrice();
        }
        totalCosts.setText(String.format("%.2f €", t));
        return t;
    }

    /**
     * Sends to the handler of the MainActivity to manage the moreBtnCartOnClick Event
     * @param v the button clicked
     */
    public void moreBtnCartHandler(View v){
        m.moreBtnCartHandler(v);
        totalPriceDouble = updateTotalPrice();
    }

    /**
     * Sends to the handler of the MainActivity to manage the lessBtnCartOnClick Event
     * @param v the button clicked
     */
    public void lessBtnCartHandler(View v){
        m.lessBtnCartHandler(v);
        totalPriceDouble = updateTotalPrice();
    }

    //TODO borra siempre el item del último index
    public void removeBtnHandler(View v){
        m.removeBtnHandler(v);
        adapter.notifyDataSetChanged();
        totalPriceDouble = updateTotalPrice();
    }

    /**
     * Inflates de menu
     * @param menu the menu to inflate
     * @return if the event was managed
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        menu.removeItem(R.id.logout);
        return true;
    }

    /**
     * Manages the options selected on the menu
     * @param item the item selected
     * @return if the event was managed, or sends to the superclass to manage it
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
