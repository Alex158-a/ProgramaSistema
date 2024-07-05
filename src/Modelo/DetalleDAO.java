package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Detalle> getAllDetails() {
        List<Detalle> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Detalle detalle = new Detalle();
                detalle.setId(rs.getInt("id"));
                detalle.setProductoId(rs.getInt("producto_id"));
                detalle.setCodProducto(rs.getString("cod_producto"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecio(rs.getDouble("precio"));
                detalle.setVentaId(rs.getInt("venta_id"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
        return detalles;
    }

    public List<Detalle> getDetailsByVentaId(int ventaId) {
        List<Detalle> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle WHERE venta_id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ventaId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Detalle detalle = new Detalle();
                detalle.setId(rs.getInt("id"));
                detalle.setProductoId(rs.getInt("producto_id"));
                detalle.setCodProducto(rs.getString("cod_producto"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecio(rs.getDouble("precio"));
                detalle.setVentaId(rs.getInt("venta_id"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
        return detalles;
    }

    public void addDetail(Detalle detalle) {
        String sql = "INSERT INTO detalle (producto_id, cod_producto, cantidad, precio, venta_id) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, detalle.getProductoId());
            ps.setString(2, detalle.getCodProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecio());
            ps.setInt(5, detalle.getVentaId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
    }

    public void updateDetail(Detalle detalle) {
        String sql = "UPDATE detalle SET cantidad=?, precio=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getPrecio());
            ps.setInt(3, detalle.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
    }

    public void deleteDetail(int id) {
        String sql = "DELETE FROM detalle WHERE id=?";
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

    public Detalle getDetailById(int id) {
        Detalle detalle = null;
        String sql = "SELECT * FROM detalle WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                detalle = new Detalle();
                detalle.setId(rs.getInt("id"));
                detalle.setProductoId(rs.getInt("producto_id"));
                detalle.setCodProducto(rs.getString("cod_producto"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecio(rs.getDouble("precio"));
                detalle.setVentaId(rs.getInt("venta_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
        return detalle;
    }

    public void clearDetailsByVentaId(int ventaId) {
        String sql = "DELETE FROM detalle WHERE venta_id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ventaId);
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

