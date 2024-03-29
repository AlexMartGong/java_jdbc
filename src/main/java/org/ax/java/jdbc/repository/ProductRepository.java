package org.ax.java.jdbc.repository;

import org.ax.java.jdbc.model.Product;
import org.ax.java.jdbc.util.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository<Product> {
    private Connection getConnection() throws SQLException {
        return ConnectionDatabase.getInstance();
    }

    @Override
    public List<Product> listAll() {
        List<Product> productList = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                Product p = createProduct(rs);
                productList.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productList;
    }

    @Override
    public Product byId(Long id) {
        Product product = null;

        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM products WHERE id=?")) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    product = createProduct(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public void save(Product product) {
        String sql;
        if (product.getId() != null && product.getId() > 0) {
            sql = "UPDATE products SET name=?, price=? WHERE id=?";
        } else {
            sql = "INSERT INTO products(name, price, date_registration) VALUES(?,?,?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setLong(2, product.getPrice());

            if (product.getId() != null && product.getId() > 0) {
                stmt.setLong(3, product.getId());
            } else {
                stmt.setDate(3, new Date(product.getDateRegistry().getTime()));
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM products WHERE id=?")) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Product createProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getInt("price"));
        p.setDateRegistry(rs.getDate("date_registration"));
        return p;
    }
}
