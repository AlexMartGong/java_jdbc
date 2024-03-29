package org.ax.java.jdbc;

import org.ax.java.jdbc.model.Category;
import org.ax.java.jdbc.model.Product;
import org.ax.java.jdbc.repository.ProductRepository;
import org.ax.java.jdbc.repository.Repository;
import org.ax.java.jdbc.util.ConnectionDatabase;

import java.sql.*;
import java.util.Date;

public class ExampleJdbc {
    public static void main(String[] args) {

        try (Connection conn = ConnectionDatabase.getInstance()) {

            Repository<Product> repository = new ProductRepository();
            System.out.println("====================Listar======================");
            repository.listAll().forEach(System.out::println);
            System.out.println("====================Obtener por id==============");
            System.out.println(repository.byId(2L));
            System.out.println("====================insert new product==============");
            Product p = new Product();
            Category c = new Category();
            p.setName("Base para laptop");
            p.setPrice(350);
            p.setDateRegistry(new Date());
            c.setId(3L);
            p.setCategory(c);
            repository.save(p);
            System.out.println("Product save");
            repository.listAll().forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
