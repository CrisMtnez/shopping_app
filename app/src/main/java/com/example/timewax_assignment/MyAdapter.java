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

/**
 * Custom adapter that manages the main listview of the shop
 */
public class MyAdapter extends BaseAdapter {
    ArrayList<Product> list;
    Context context;
    LayoutInflater inflater;

    public MyAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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

    /**
     * Gets the listview managed
     * @param position the position of a row
     * @param view the row inflated
     * @param parent the parent ViewGroup
     * @return view
     */
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
            container.productId = view.findViewById(R.id.refIdTextView);
            view.setTag(container); //sets the row container
        } else {
            container = (Container) view.getTag(); //gets the row container
        }
        Product p = (Product) getItem(position);
        container.photo.setImageResource(p.getImage());
        container.name.setText(p.getName());
        container.description.setText(p.getDescription());
        container.price.setText(String.format("%.2f€", p.getPrice()));
        container.quantity.setText(p.getQuantity() +"");
        container.position.setText(position+"");
        container.productId.setText(p.getId()+"");

        return view;
    }

    /**
     * Contains all the elements from each row
     */
    class Container {
        ImageView photo;
        TextView productId, position, name,description,price,quantity;
        ImageButton morebtn,lessbtn;
        Button addbtn;
    }
}
