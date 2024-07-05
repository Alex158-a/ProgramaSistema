package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpresaDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Empresa getCompanyData() {
        Empresa empresa = null;
        String sql = "SELECT * FROM configuracion LIMIT 1";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setRuc(rs.getString("ruc"));
                empresa.setNombre(rs.getString("nombre"));
                empresa.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResources();
        }
        return empresa;
    }

    public void updateCompanyData(Empresa empresa) {
        String sql = "UPDATE configuracion SET ruc=?, nombre=?, telefono=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, empresa.getRuc());
            ps.setString(2, empresa.getNombre());
            ps.setString(3, empresa.getTelefono());
            ps.setInt(4, empresa.getId());
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

