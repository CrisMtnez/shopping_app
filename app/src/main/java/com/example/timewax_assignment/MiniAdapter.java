package com.example.timewax_assignment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MiniAdapter extends BaseAdapter {
    ArrayList<Product> list;
    LayoutInflater inflater;

    public MiniAdapter(Context context, ArrayList<Product> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public ArrayList<Product> getList() {
        return list;
    }

    public void setList(ArrayList<Product> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MiniContainer container = null;
        if (view == null){
            view = inflater.inflate(R.layout.selected_item_row, null);
            container = new MiniContainer();
            container.name = view.findViewById(R.id.nameTxtView);
            container.price = view.findViewById(R.id.priceTxtView);
            container.totalPrice = view.findViewById(R.id.totalPrice);
            container.quantity = view.findViewById(R.id.numberTxtView);
            container.morebtn = view.findViewById(R.id.morebtn);
            container.lessbtn = view.findViewById(R.id.lessbtn);
            container.removebtn = view.findViewById(R.id.removebtn);
            container.position = view.findViewById(R.id.positionOnCart);
            view.setTag(container);
        } else {
            container = (MiniContainer) view.getTag();
        }

        Product p = (Product) getItem(position);
        container.name.setText(p.name);
        container.price.setText(String.format("%.2f", p.price)+"€");
        container.totalPrice.setText(String.format("= %.2f", p.getTotalPrice())+"€");
        container.quantity.setText(p.quantity +"");
        container.position.setText(position+"");

        return view;
    }

    class MiniContainer {
        TextView position, name,price,quantity,totalPrice;
        ImageButton morebtn,lessbtn,removebtn;
    }
}
