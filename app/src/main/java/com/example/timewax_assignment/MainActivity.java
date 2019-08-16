package com.example.timewax_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> productsList;
    public static ArrayList<Product> selectedProducts;
    public static ArrayList<Event> events;
    private MyAdapter adapter;
    private ListView lv;
    private CartDialog cartDialog;
    private TextView numberShown,totalProduct;
    private Button yourCart;
    public int itemIndex, productId;
    public static final int MAX_QUANTITY=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productsList = Product.fillProductsList();
        selectedProducts = new ArrayList<>();
        adapter = new MyAdapter(this,productsList);
        lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);
        yourCart = findViewById(R.id.checkCartBtn);
        numberShown = findViewById(R.id.numberTxtView);
        events = new ArrayList<>();
    }

    /**
     * Manages all the events that could be stored
     * @param v the element that fires the event
     * @param event the name of the event
     * @param idIndex the index in the row with the id of the product
     * @param product whether the event is about a particular product of not
     */
    public static void eventManager(View v, Event.EventCodes event, int idIndex, boolean product){
        Date date = new Date();
        int id =-1;
        if (product) {
            ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
            id = Integer.parseInt(((TextView) parentRow.getChildAt(idIndex)).getText().toString());
        }
        //For a future implementation with a database an id for the event should be added
        events.add(new Event(id,event,date));
        Log.i("EventAdded: ", String.format("%s - pid %d - %s",event.name(),id,date));
    }

    /**
     * Updates the number (SKU) displayed when it's modified
     * @param newQuantity the new quantity to display
     * @param productsList the list of products where the quantity changed
     * @param indexOfnShown the index in the row where this number is
     * @param v the element that fires the event
     */
    public void quantityChange(int newQuantity, ArrayList<Product> productsList, int indexOfnShown, View v){
        if (newQuantity<=MAX_QUANTITY && newQuantity>0){
            ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
            numberShown = (TextView)parentRow.getChildAt(indexOfnShown); //numOfItems
            productsList.get(itemIndex).setQuantity(newQuantity);
            numberShown.setText(newQuantity+"");
        }
    }

    /**
     * Displays the price of the product multiplied by the number of units selected
     * @param p the product
     * @param indexOfnShown the index of the number to change
     * @param v the element that fires the event
     */
    public void totalPriceOfProduct(Product p, int indexOfnShown, View v){
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
        totalProduct = (TextView)parentRow.getChildAt(indexOfnShown);
        totalProduct.setText(String.format("= %.2f€", p.getTotalPrice()));
    }

    /**
     * Handles the click event on the addBtn, changing the quantity of the product stored in the cart
     * if it's less that the maximum allowed
     * @param v the button clicked
     */
    public void addBtnHandler(View v){
        //The row the clicked button is in
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
        itemIndex = Integer.parseInt(((TextView)parentRow.getChildAt(9)).getText().toString());
        productId = Integer.parseInt(((TextView)parentRow.getChildAt(11)).getText().toString());
        ArrayList <Integer> itemsOnCart = Product.getIdsOfProducts(selectedProducts);
        int quantity;
        if (itemsOnCart.contains(productsList.get(itemIndex).getId())){
            int i = itemsOnCart.indexOf(productsList.get(itemIndex).getId());
            quantity = selectedProducts.get(i).getQuantity()+productsList.get(itemIndex).getQuantity();
            if (quantity<=MAX_QUANTITY){
                selectedProducts.get(i).setQuantity(quantity);
                eventManager(v, Event.EventCodes.Added,11,true);
                displayToast(true);
            }else {
                selectedProducts.get(i).setQuantity(MAX_QUANTITY);
                displayToast(false);
            }
        }else{
            quantity = productsList.get(itemIndex).getQuantity();
            if (quantity<=MAX_QUANTITY) {
                selectedProducts.add((Product) productsList.get(itemIndex).clone());
                eventManager(v, Event.EventCodes.FirstAdded, 11, true);
                displayToast(true);
            }else {
                selectedProducts.add((Product) productsList.get(itemIndex).clone());
                selectedProducts.get(selectedProducts.size()-1).setQuantity(MAX_QUANTITY);
                displayToast(false);
            }

        }
        quantityChange(1, productsList, 6, v);
        yourCart.setText("Your cart ("+selectedProducts.size()+")");
    }

    public void displayToast(boolean wasAdded){
        if (wasAdded)
            Toast.makeText(this,String.format("%d %s(s) added to your cart",
                productsList.get(itemIndex).getQuantity(),productsList.get(itemIndex).getName()),Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,String.format("You cannot add more than %d %ss!",
                    MAX_QUANTITY, productsList.get(itemIndex).getName()),Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the click event on the moreBtn, changing the quantity of the product displayed in the main page
     * @param v the button clicked
     */
    public void moreBtnHandler(View v){
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
        itemIndex = Integer.parseInt(((TextView)parentRow.getChildAt(9)).getText().toString());
        quantityChange(productsList.get(itemIndex).getQuantity()+1,productsList,6,v);
    }

    /**
     * Handles the click event on the lessBtn, changing the quantity of the product displayed in the main page
     * @param v the button clicked
     */
    public void lessBtnHandler(View v){
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
        itemIndex = Integer.parseInt(((TextView)parentRow.getChildAt(9)).getText().toString());
        quantityChange(productsList.get(itemIndex).getQuantity()-1,productsList,6,v);
    }

    /**
     * Handles the click event on the removeBtn, removing the product from the cart
     * @param v the button clicked
     */
    public void removeBtnHandler(View v){
        //event
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
        itemIndex = Integer.parseInt(((TextView)parentRow.getChildAt(8)).getText().toString());
        productId = Integer.parseInt(((TextView)parentRow.getChildAt(10)).getText().toString());
        eventManager(v, Event.EventCodes.ItemRemoved,10,true);
        selectedProducts.remove(itemIndex);
        if (cartDialog!=null){
            cartDialog.adapter.notifyDataSetChanged();
            cartDialog.updateTotalPrice();
            if (selectedProducts.size()==0)
                cartDialog.emptyCart();
            yourCart.setText("Your cart ("+selectedProducts.size()+")");
        }
    }

    /**
     * Handles the click event on the moreBtnCart, changing the quantity of the product stored/displayed in the cart
     * @param v the button clicked
     */
    public void moreBtnCartHandler(View v){
        //event
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
        itemIndex = Integer.parseInt(((TextView)parentRow.getChildAt(8)).getText().toString());
        eventManager(v, Event.EventCodes.MoreQuantity,10,true);
        quantityChange(selectedProducts.get(itemIndex).getQuantity()+1,selectedProducts,4,v);
        totalPriceOfProduct(selectedProducts.get(itemIndex),6,v);
        if (cartDialog!=null)
            cartDialog.updateTotalPrice();
    }

    /**
     * Handles the click event on the lessBtnCart, changing the quantity of the product stored/displayed in the cart
     * @param v the button clicked
     */
    public void lessBtnCartHandler(View v){
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
        itemIndex = Integer.parseInt(((TextView)parentRow.getChildAt(8)).getText().toString());
        eventManager(v, Event.EventCodes.LessQuantity,10,true);
        quantityChange(selectedProducts.get(itemIndex).getQuantity()-1,selectedProducts,4,v);
        totalPriceOfProduct(selectedProducts.get(itemIndex),6,v);
        if (cartDialog!=null)
            cartDialog.updateTotalPrice();
    }

    /**
     * Opens the FragmentDialog with the client's cart
     * @param v the button that fires the event
     */
    public void checkCartHandler(View v){
        eventManager(v, Event.EventCodes.CartChecked,0,false);
        cartDialog = new CartDialog();
        cartDialog.show(getSupportFragmentManager(),"");
    }

    /**
     * Updates the text displayed in the checkCart button
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            yourCart.setText("Your cart ("+selectedProducts.size()+")");
        }
    }

    /**
     * Inflates the menu
     * @param menu the menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Manages the options selected on the menu
     * @param item the item selected
     * @return if the event was managed, or sends to the superclass to manage it
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.logout:
                confirmLeaving();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Asks for confirmation to leave the app. The dialog is cancelled if the client clicks no
     */
    public void confirmLeaving(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Leaving Kwik-E-Smart").setMessage("Are you sure you want to close Kwik-E-Smart?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent backHome = new Intent(Intent.ACTION_MAIN);
                        backHome.addCategory( Intent.CATEGORY_HOME );
                        backHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(backHome);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).create().show();
    }
}