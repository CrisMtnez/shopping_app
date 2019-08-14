package com.example.timewax_assignment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Collections;

public class CartDialog extends DialogFragment {

    private Button checkOut,removeAll;
    private ListView itemsList;
    TextView totalCart;
    private MiniAdapter adapter;

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
////        Collections.addAll(selectedProducts);
////        selectedProducts = Product.fillProductsList();
//        View v = getActivity().getLayoutInflater().inflate(R.layout.selected_items_list, null);
//        cartList = new MiniAdapter(getContext(),selectedProducts);
//        itemsList = v.findViewById(R.id.selectedListView);
//        itemsList.setAdapter(cartList);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(v);
//        return builder.create();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selected_items_list, container, false);
        adapter = new MiniAdapter(getContext(),MainActivity.selectedProducts);
        itemsList = view.findViewById(R.id.selectedListView);
        itemsList.setAdapter(adapter);
        checkOut = view.findViewById(R.id.checkOutButton);
        removeAll = view.findViewById(R.id.removeAllBtn);
        totalCart = view.findViewById(R.id.totalTxtView);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getDialog().setCanceledOnTouchOutside(true);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCheckOut();
            }
        });

        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.selectedProducts.clear();
                //y eliminar la vista
            }
        });

        if (MainActivity.selectedProducts.size()==0){
            removeAll.setVisibility(View.INVISIBLE);
            totalCart.setText("Your cart is empty!");
            totalCart.setTextSize(24);
            totalCart.setGravity(View.TEXT_ALIGNMENT_CENTER);
        }

        return view;
    }

    public void goToCheckOut(){
        Intent intent = new Intent(getContext(),CheckOutActivity.class);
        startActivity(intent);
        dismiss();
    }

    public void setSelectedProducts(ArrayList<Product> selectedProducts){
        MainActivity.selectedProducts = selectedProducts;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.cart_dialog, container, true);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        return view;
//    }
}
