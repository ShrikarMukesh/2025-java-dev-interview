package com.elevens.newer;
import java.util.
class Product {
    String name;
    int price;
    String category;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }

    public Product(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
public class Interview {
    public static void main(String[] args) {
        Product product1 = new Product();
    }
}
