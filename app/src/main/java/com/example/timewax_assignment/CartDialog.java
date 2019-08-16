package com.example.timewax_assignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * The small screen displayed when the client checks the cart
 */
public class CartDialog extends DialogFragment {

    private Button checkOut,removeAll;
    private ListView itemsList;
    private TextView totalCart;
    public MiniAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selected_items_list, container, false);
        adapter = new MiniAdapter(getContext(),MainActivity.selectedProducts);
        itemsList = view.findViewById(R.id.selectedListView);
        itemsList.setAdapter(adapter);
        checkOut = view.findViewById(R.id.checkOutButton);
        removeAll = view.findViewById(R.id.removeAllBtn);
        totalCart = view.findViewById(R.id.totalPriceOfCart);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getDialog().setCanceledOnTouchOutside(true);
        /**
         * Goes to the checkout to finish the purchase
         */
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCheckOut();
            }
        });
        /**
         * Sends to a confirmation dialog for emptying the cart
         */
        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmRemove();
            }
        });
        /**
         * Changes the message displayed depending on wheter the cart is empty or not
         */
        if (MainActivity.selectedProducts.size()==0){
            emptyCart();
        } else {
            updateTotalPrice();
        }

        return view;
    }

    /**
     * Asks for confirmation to emptying the cart. The dialog is cancelled if the client clicks no
     */
    public void confirmRemove(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Emptying cart").setMessage("Do you want to remove all items from your cart?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.selectedProducts.clear();
                        adapter.notifyDataSetChanged();
                        emptyCart();
                        MainActivity.eventManager(null, Event.EventCodes.RemoveAll,0,false);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).create().show();
    }

    /**
     * Removes all the elements from the cart
     */
    public void emptyCart(){
        removeAll.setVisibility(View.INVISIBLE);
        totalCart.setText("Your cart is empty!");
        totalCart.setTextSize(24);
        totalCart.setGravity(View.TEXT_ALIGNMENT_CENTER);
    }

    /**
     * Updates the total price of the cart
     */
    public void updateTotalPrice(){
        double t = 0;
        for (Product p : MainActivity.selectedProducts) {
            t += p.getTotalPrice();
        }
        totalCart.setText(String.format("TOTAL = %.2f €", t));
    }

    /**
     * Goes to the Checkout Screen and closes the dialog
     */
    public void goToCheckOut(){
        MainActivity.eventManager(null, Event.EventCodes.CheckOut,0,false);
        Intent intent = new Intent(getContext(),CheckOutActivity.class);
        startActivity(intent);
        dismiss();
    }
}
