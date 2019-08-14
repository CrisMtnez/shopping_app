package com.example.timewax_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> productsList;
    public static ArrayList<Product> selectedProducts;
    private MyAdapter adapter;
    private ListView lv;
//    private MiniAdapter cartList;
    private CartDialog cartDialog;
    private ImageButton more,less;
    private TextView numberShown;
    private Button yourCart;
    public int itemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productsList = Product.fillProductsList();
        selectedProducts = new ArrayList<>();
        adapter = new MyAdapter(this,productsList);
//        cartList = new MiniAdapter(this,selectedProducts);
        lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);
//        ListView lvSel = findViewById(R.id.selectedListView);
//        lvSel.setAdapter(cartList);
        more = findViewById(R.id.morebtn);
        less = findViewById(R.id.lessbtn);
        yourCart = findViewById(R.id.checkCartBtn);
        numberShown = findViewById(R.id.numberTxtView);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                itemIndex = position;
//                //Log.e("**** onItemClick","index changed to " +position);
//            }
//        });
    }

    public void quantityChange(int newQuantity, View v){
        if (newQuantity<=1000 && newQuantity>0){
            ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
            numberShown = (TextView)parentRow.getChildAt(6); //numOfItems
            productsList.get(itemIndex).setQuantity(newQuantity);
            numberShown.setText(newQuantity+"");
//            Log.e("**** quantityChange","index = " +itemIndex+ "  | new quantity = " + newQuantity);
        }
    }

    public void addBtnHandler(View v){

        //The row the clicked button is in
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
        itemIndex = Integer.parseInt(((TextView)parentRow.getChildAt(9)).getText().toString());

        ArrayList <String> itemsOnCart = Product.getNamesOfProducts(selectedProducts);
        if (itemsOnCart.contains(productsList.get(itemIndex).name)){
            int i = itemsOnCart.indexOf(productsList.get(itemIndex).name);
            Log.e("**** index",i+"");
            selectedProducts.get(i).setQuantity(selectedProducts.get(i).getQuantity()+productsList.get(itemIndex).getQuantity());
        }else{
            selectedProducts.add((Product)productsList.get(itemIndex).clone());
        }
//        Log.e("**** itemindex",itemIndex+"");
//        Log.e("**** selectedProducts",selectedProducts.toString());
//        Log.e("**** selProductsNames",itemsOnCart.toString());

//        Log.e("**** quantity",productsList.get(itemIndex).getQuantity()+"");
//        Log.e("**** addBtnHandler","actual index = " +itemIndex);
        yourCart.setText("Your cart ("+selectedProducts.size()+")");
        quantityChange(1,v);
    }

    public void moreBtnHandler(View v){
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();

//        Log.e("**** parentRow","child at 0 = " +parentRow.getChildAt(0));
//        Log.e("**** parentRow","child at 1 = " +parentRow.getChildAt(1));
//        Log.e("**** parentRow","child at 2 = " +parentRow.getChildAt(2));
//        Log.e("**** parentRow","child at 3 = " +parentRow.getChildAt(3));
//        Log.e("**** parentRow","child at 4 = " +parentRow.getChildAt(4));
//        Log.e("**** parentRow","child at 5 = " +parentRow.getChildAt(5));
//        Log.e("**** parentRow","child at 6 = " +parentRow.getChildAt(6));
//        Log.e("**** parentRow","child at 7 = " +parentRow.getChildAt(7));
//        Log.e("**** parentRow","child at 8 = " +parentRow.getChildAt(8));

//        more = (ImageButton)parentRow.getChildAt(4); //morebtn
//        MyAdapter ma = ((MyAdapter)((ListView)parentRow.getParent()).getAdapter());
//        Log.e("**** parent of row",((ListView)parentRow.getParent()).getAdapter()+"");

        itemIndex = Integer.parseInt(((TextView)parentRow.getChildAt(9)).getText().toString());
//        Log.e("**** moreBtnHandler","actual index = " +itemIndex);
        quantityChange(productsList.get(itemIndex).getQuantity()+1,v);
    }

    public void lessBtnHandler(View v){
        ConstraintLayout parentRow = (ConstraintLayout) v.getParent();
        itemIndex = Integer.parseInt(((TextView)parentRow.getChildAt(9)).getText().toString());
//        less = (ImageButton)parentRow.getChildAt(4); //lessbtn
//        Log.e("**** lessBtnHandler","actual index = " +itemIndex);
        quantityChange(productsList.get(itemIndex).getQuantity()-1,v);
    }

    public void checkCartHandler(View v){
      cartDialog = new CartDialog();
      cartDialog.show(getSupportFragmentManager(),"");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
//        MenuItem item = menu.findItem(R.id.logout);
//        CartDialog list = new CartDialog();
//        list.show(getSupportFragmentManager(),"");
//        s.setOnItemSelectedListener(onItemSelectedListener); // set the listener, to perform actions based on item selection
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.logout:
                finish();
                System.exit(0);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
