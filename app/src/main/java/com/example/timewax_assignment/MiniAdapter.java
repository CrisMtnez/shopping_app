package com.example.timewax_assignment;

import android.content.Context;
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
            container.name = view.findViewById(R.id.nameTxtViewCart);
            container.price = view.findViewById(R.id.priceTxtViewCart);
            container.totalPrice = view.findViewById(R.id.totalPriceOfRowCart);
            container.quantity = view.findViewById(R.id.numberTxtViewCart);
            container.morebtn = view.findViewById(R.id.morebtnCart);
            container.lessbtn = view.findViewById(R.id.lessbtnCart);
            container.removebtn = view.findViewById(R.id.removebtnCart);
            container.position = view.findViewById(R.id.positionOnCart);
            view.setTag(container);
        } else {
            container = (MiniContainer) view.getTag();
        }

        Product p = (Product) getItem(position);
        container.name.setText(p.name);
        container.price.setText(String.format("%.2f€", p.price));
        container.totalPrice.setText(String.format("= %.2f€", p.getTotalPrice()));
        container.quantity.setText(p.quantity +"");
        container.position.setText(position+"");

        return view;
    }

    class MiniContainer {
        TextView position, name,price,quantity,totalPrice;
        ImageButton morebtn,lessbtn,removebtn;
    }
}
