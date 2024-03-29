package org.ax.java.jdbc.repository;

import org.ax.java.jdbc.model.Category;
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

        try (Statement stmt = getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT p.*, c.name " +
                "as category FROM products as p INNER JOIN category as c ON (p.category_id=c.id)")) {

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

        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT p.*, c.name " +
                "as category FROM products as p INNER JOIN category as c ON (p.category_id=c.id) WHERE p.id=?")) {

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
            sql = "UPDATE products SET name=?, price=?, category_id=? WHERE id=?";
        } else {
            sql = "INSERT INTO products(name, price, category_id, date_registration) VALUES(?,?,?,?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setLong(2, product.getPrice());
            stmt.setLong(3, product.getCategory().getId());

            if (product.getId() != null && product.getId() > 0) {
                stmt.setLong(4, product.getId());
            } else {
                stmt.setDate(4, new Date(product.getDateRegistry().getTime()));
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
        Category c = new Category();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getInt("price"));
        p.setDateRegistry(rs.getDate("date_registration"));
        c.setId(rs.getLong("category_id"));
        c.setName(rs.getString("category"));
        p.setCategory(c);
        return p;
    }
}
