/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.google.gson.Gson;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lukas Bjornvad
 */
public class Software {
    int id;
    String description;
    double price;
    String title;
    List<String> reviews;
    LinkedList<String> specifications;

    public Software() {
    }

    public Software(int id, String description, double price, List<String> reviews, String title) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.reviews = reviews;
        this.title = title;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public LinkedList<String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(LinkedList<String> specifications) {
        this.specifications = specifications;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}

