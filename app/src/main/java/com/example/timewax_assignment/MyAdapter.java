package com.example.timewax_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    ArrayList<Product> list;
//    ArrayList<Product> selectedProducts;
    LayoutInflater inflater;

    public MyAdapter(Context context, ArrayList<Product> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
//        this.selectedProducts = list;
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
    public View getView(final int position, View view, ViewGroup parent) {
        Container container = null;
        if (view == null){
            view = inflater.inflate(R.layout.row, parent,false);
            container = new Container();
            container.photo = view.findViewById(R.id.imageView);
            container.name = view.findViewById(R.id.nameTxtView);
            container.description = view.findViewById(R.id.descrTxtView);
            container.price = view.findViewById(R.id.priceTxtView);
            container.quantity = view.findViewById(R.id.numberTxtView);
            container.morebtn = view.findViewById(R.id.morebtn);
            container.lessbtn = view.findViewById(R.id.lessbtn);
            container.addbtn = view.findViewById(R.id.addbtn);
            container.position = view.findViewById(R.id.position);
            view.setTag(container); //sets the row container
        } else {
            container = (Container) view.getTag(); //gets the row container
        }
        Product p = (Product) getItem(position);
        container.photo.setImageResource(p.image);
        container.name.setText(p.name);
        container.description.setText(p.description);
        container.price.setText(String.format("%.2f", p.price)+"€");
        container.quantity.setText(p.quantity +"");
        container.position.setText(position+"");

        return view;
    }

    class Container {
        ImageView photo;
        TextView position, name,description,price,quantity;
        ImageButton morebtn,lessbtn;
        Button addbtn;
    }
}
