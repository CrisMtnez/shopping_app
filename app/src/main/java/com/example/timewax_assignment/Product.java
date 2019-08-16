package com.example.timewax_assignment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Manages the data of each product
 */
public class Product implements Serializable, Cloneable {
    public static ArrayList<Product> products;
    private String name, description;
    private int id, quantity, image;
    private double price, totalPrice;

    public Product(int id, String name, String description, int quantity,
                   int image, double price, double totalPrice) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    /**
     * Creates a list with all the ids of the products at the shop
     * @param products the list of products
     * @return list of ids of products
     */
    public static ArrayList<Integer> getIdsOfProducts(ArrayList<Product>products){
        ArrayList<Integer> idsOfProducts = new ArrayList<>();
        for (Product p:products) {
            idsOfProducts.add(p.id);
        }
        return idsOfProducts;
    }

    /**
     * Initiates the products in the shop
     * @return the list of products
     */
    public static ArrayList<Product> fillProductsList(){

        Product.products = new ArrayList<>();

        Product.products.add(new Product(100,"Banana","Gaan met die banaan!",
                1,R.drawable.banana,0.50,0.50));

        Product.products.add(new Product(101,"Apple","An apple a day keeps the doctor away",
                1, R.drawable.apple,0.75,0.75));

        Product.products.add(new Product(102,"Cookie","That's the way the cookie crumbles",
                1,R.drawable.cookie,1.50,1.50));

        Product.products.add(new Product(103,"Cake","It's a piece of cake",
                1,R.drawable.cake,4.25,4.25));

        Product.products.add(new Product(104,"Cucumber","Cool as a cucumber",
                1,R.drawable.cucumber,1.20,1.20));

        Product.products.add(new Product(105,"Broccoli","They are cute little trees",
                1,R.drawable.broccoli,2,2));

        Product.products.add(new Product(106,"Avocado","The good kind of fat",
                1,R.drawable.avocado,2.50,2.50));

        return Product.products;
    }
}
