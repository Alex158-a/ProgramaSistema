package programasistema;

import Modelo.Producto;
import Modelo.ProductoDAO;
import Modelo.Detalle;
import Modelo.DetalleDAO;
import Modelo.VentaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NewSale extends JFrame {
    private JPanel panel;
    private JTextField codigoText;
    private JTextField cantidadText;
    private JTextField precioText;
    private JTextField clienteText;
    private JButton addButton;
    private JButton finishButton;
    private JButton backButton; // Botón para regresar
    private JTable table;
    private DefaultTableModel model;
    private DetalleDAO detalleDAO;
    private ProductoDAO productoDAO;
    private VentaDAO ventaDAO;
    private int ventaId;

    public NewSale() {
        detalleDAO = new DetalleDAO();
        productoDAO = new ProductoDAO();
        ventaDAO = new VentaDAO();
        
        setTitle("Nueva Venta");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel clienteLabel = new JLabel("Cliente:");
        clienteLabel.setBounds(10, 10, 80, 25);
        panel.add(clienteLabel);

        clienteText = new JTextField(20);
        clienteText.setBounds(100, 10, 160, 25);
        panel.add(clienteText);

        JLabel codigoLabel = new JLabel("Código:");
        codigoLabel.setBounds(10, 40, 80, 25);
        panel.add(codigoLabel);

        codigoText = new JTextField(20);
        codigoText.setBounds(100, 40, 160, 25);
        panel.add(codigoText);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(10, 70, 80, 25);
        panel.add(cantidadLabel);

        cantidadText = new JTextField(20);
        cantidadText.setBounds(100, 70, 160, 25);
        panel.add(cantidadText);

        JLabel precioLabel = new JLabel("Precio:");
        precioLabel.setBounds(10, 100, 80, 25);
        panel.add(precioLabel);

        precioText = new JTextField(20);
        precioText.setBounds(100, 100, 160, 25);
        panel.add(precioText);

        addButton = new JButton("Agregar");
        addButton.setBounds(10, 130, 100, 25);
        panel.add(addButton);

        finishButton = new JButton("Finalizar Venta");
        finishButton.setBounds(120, 130, 150, 25);
        panel.add(finishButton);

        backButton = new JButton("Regresar");
        backButton.setBounds(280, 130, 100, 25);
        panel.add(backButton);

        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("ID");
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Cantidad");
        model.addColumn("Precio");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 170, 660, 300);
        panel.add(scrollPane);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductToSale();
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishSale();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationMenu navigationMenu = new NavigationMenu();
                navigationMenu.setVisible(true);
                dispose();
            }
        });
    }

    private void addProductToSale() {
        try {
            Producto producto = productoDAO.getProductByCodigo(codigoText.getText());
            if (producto != null) {
                int cantidad = Integer.parseInt(cantidadText.getText());
                double precio = Double.parseDouble(precioText.getText());
                double precioTotal = precio * cantidad;

                Detalle detalle = new Detalle();
                detalle.setProductoId(producto.getId());
                detalle.setCodProducto(producto.getCodigo());
                detalle.setCantidad(cantidad);
                detalle.setPrecio(precioTotal);
                detalle.setVentaId(ventaId);

                detalleDAO.addDetail(detalle);
                updateSaleTotal();
                clearFields();
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad y precio deben ser números", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSaleTotal() {
        List<Detalle> detalles = detalleDAO.getDetailsByVentaId(ventaId);
        double total = 0;
        for (Detalle detalle : detalles) {
            total += detalle.getPrecio();
        }
        ventaDAO.updateTotal(ventaId, total);
    }

    private void clearFields() {
        codigoText.setText("");
        cantidadText.setText("");
        precioText.setText("");
    }

    private void loadTableData() {
        model.setRowCount(0);
        List<Detalle> detalles = detalleDAO.getDetailsByVentaId(ventaId);
        for (Detalle detalle : detalles) {
            Producto producto = productoDAO.getProductById(detalle.getProductoId());
            model.addRow(new Object[]{detalle.getId(), producto.getCodigo(), producto.getNombre(), detalle.getCantidad(), detalle.getPrecio()});
        }
    }

    private void finishSale() {
        ventaId = ventaDAO.createNewSale(clienteText.getText(), 0.0);
        JOptionPane.showMessageDialog(this, "Venta finalizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        detalleDAO.clearDetailsByVentaId(ventaId);
        NavigationMenu navigationMenu = new NavigationMenu();
        navigationMenu.setVisible(true);
        dispose();
    }
}


