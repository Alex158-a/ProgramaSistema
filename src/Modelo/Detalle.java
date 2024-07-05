package Modelo;

public class Detalle {
    private int id;
    private int productoId;
    private String codProducto;
    private int cantidad;
    private double precio;
    private int ventaId;

    public Detalle() {}

    public Detalle(int id, int productoId, String codProducto, int cantidad, double precio, int ventaId) {
        this.id = id;
        this.productoId = productoId;
        this.codProducto = codProducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.ventaId = ventaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }
}

