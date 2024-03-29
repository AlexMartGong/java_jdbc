package org.ax.java.jdbc;

import org.ax.java.jdbc.model.Product;
import org.ax.java.jdbc.repository.ProductRepository;
import org.ax.java.jdbc.repository.Repository;
import org.ax.java.jdbc.util.ConnectionDatabase;

import java.sql.Connection;
import java.sql.SQLException;

public class ExampleJdbcDelete {
    public static void main(String[] args) {

        try (Connection conn = ConnectionDatabase.getInstance()) {

            Repository<Product> repository = new ProductRepository();
            System.out.println("====================Listar======================");
            repository.listAll().forEach(System.out::println);
            System.out.println("====================Obtener por id==============");
            System.out.println(repository.byId(2L));
            System.out.println("====================Delete product==============");
            repository.delete(3L);
            System.out.println("Product delete");
            repository.listAll().forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
