package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Proveedor> getAllSuppliers() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt("id"));
                proveedor.setRuc(rs.getString("ruc"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
        return proveedores;
    }

    public void addSupplier(Proveedor proveedor) {
        String sql = "INSERT INTO proveedor (ruc, nombre, telefono) VALUES (?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, proveedor.getRuc());
            ps.setString(2, proveedor.getNombre());
            ps.setString(3, proveedor.getTelefono());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
    }

    public void updateSupplier(Proveedor proveedor) {
        String sql = "UPDATE proveedor SET ruc=?, nombre=?, telefono=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, proveedor.getRuc());
            ps.setString(2, proveedor.getNombre());
            ps.setString(3, proveedor.getTelefono());
            ps.setInt(4, proveedor.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
    }

    public void deleteSupplier(int id) {
        String sql = "DELETE FROM proveedor WHERE id=?";
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
