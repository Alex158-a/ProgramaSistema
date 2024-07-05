package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Producto> getAllProducts() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio(rs.getDouble("precio"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
        return productos;
    }

    public Producto getProductByCodigo(String codigo) {
        Producto producto = null;
        String sql = "SELECT * FROM productos WHERE codigo=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio(rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
        return producto;
    }

    public Producto getProductById(int id) {
        Producto producto = null;
        String sql = "SELECT * FROM productos WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio(rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
        return producto;
    }

    public void addProduct(Producto producto) {
        String sql = "INSERT INTO productos (codigo, nombre, stock, precio) VALUES (?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setInt(3, producto.getStock());
            ps.setDouble(4, producto.getPrecio());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
    }

    public void updateProduct(Producto producto) {
        String sql = "UPDATE productos SET codigo=?, nombre=?, stock=?, precio=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setInt(3, producto.getStock());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM productos WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}

