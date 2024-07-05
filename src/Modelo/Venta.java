package Modelo;

public class Venta {
    private int id;
    private String cliente;
    private double total;
    private String fech;

    public Venta() {}

    public Venta(int id, String cliente, double total, String fech) {
        this.id = id;
        this.cliente = cliente;
        this.total = total;
        this.fech = fech;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFech() {
        return fech;
    }

    public void setFech(String fech) {
        this.fech = fech;
    }
}
