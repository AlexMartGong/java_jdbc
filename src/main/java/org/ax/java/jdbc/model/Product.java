package org.ax.java.jdbc.model;

import java.util.Date;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Date dateRegistry;

    public Product() {
    }

    public Product(Long id, String name, Integer price, Date dateRegistry) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.dateRegistry = dateRegistry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDateRegistry() {
        return dateRegistry;
    }

    public void setDateRegistry(Date dateRegistry) {
        this.dateRegistry = dateRegistry;
    }

    @Override
    public String toString() {
        return id +
                " | " +
                name +
                " | " +
                price +
                " | " +
                dateRegistry;
    }
}
