package com.example.timewax_assignment;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable, Cloneable {
    public static ArrayList<Product> products;
    String name, description;
    int quantity, image;
    double price, totalPrice;

    public Product(String name, String description, int quantity,
                   int image, double price, double totalPrice) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.image = image;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    @Override
    protected Object clone()
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return price*quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static ArrayList<String> getNamesOfProducts(ArrayList<Product>products){
        ArrayList<String> namesOfProducts = new ArrayList<>();
        for (Product p:products) {
            namesOfProducts.add(p.name);
        }
        return namesOfProducts;
    }

    public static ArrayList<Product> fillProductsList(){

        Product.products = new ArrayList<>();

        Product.products.add(new Product("Banana","Gaan met die banaan!",
                1,R.drawable.banana,0.50,0.50));

        Product.products.add(new Product("Apple","An apple a day keeps the doctor away",
                1, R.drawable.apple,0.75,0.75));

        Product.products.add(new Product("Cookie","That's the way the cookie crumbles",
                1,R.drawable.cookie,1.50,1.50));

        Product.products.add(new Product("Cake","It's a piece of cake",
                1,R.drawable.cake,4.25,4.25));

        Product.products.add(new Product("Cucumber","Cool as a cucumber",
                1,R.drawable.cucumber,1.20,1.20));

        Product.products.add(new Product("Broccoli","They are cute little trees",
                1,R.drawable.broccoli,2,2));

        Product.products.add(new Product("Avocado","The good kind of fat",
                1,R.drawable.avocado,2.50,2.50));

        return Product.products;
    }
}
