package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public int createNewSale(String cliente, double total) {
        int id = -1;
        String sql = "INSERT INTO ventas (cliente, total) VALUES (?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente);
            ps.setDouble(2, total);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
        return id;
    }

    public void updateTotal(int id, double total) {
        String sql = "UPDATE ventas SET total=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDouble(1, total);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
    }

    public void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
