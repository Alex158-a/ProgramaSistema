package programasistema;

import Modelo.Producto;
import Modelo.ProductoDAO;
import Modelo.Detalle;
import Modelo.DetalleDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShoppingCart extends JFrame {
    private JPanel panel;
    private JTextField codigoText;
    private JTextField cantidadText;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton checkoutButton;
    private JButton backButton; // Botón para regresar
    private JTable table;
    private DefaultTableModel model;
    private DetalleDAO detalleDAO;
    private ProductoDAO productoDAO;
    private int selectedId = -1;

    public ShoppingCart() {
        detalleDAO = new DetalleDAO();
        productoDAO = new ProductoDAO();

        setTitle("Shopping Cart");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel codigoLabel = new JLabel("Código:");
        codigoLabel.setBounds(10, 10, 80, 25);
        panel.add(codigoLabel);

        codigoText = new JTextField(20);
        codigoText.setBounds(100, 10, 160, 25);
        panel.add(codigoText);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(10, 40, 80, 25);
        panel.add(cantidadLabel);

        cantidadText = new JTextField(20);
        cantidadText.setBounds(100, 40, 160, 25);
        panel.add(cantidadText);

        addButton = new JButton("Agregar");
        addButton.setBounds(10, 70, 80, 25);
        panel.add(addButton);

        updateButton = new JButton("Actualizar");
        updateButton.setBounds(100, 70, 100, 25);
        panel.add(updateButton);

        deleteButton = new JButton("Eliminar");
        deleteButton.setBounds(210, 70, 80, 25);
        panel.add(deleteButton);

        checkoutButton = new JButton("Comprar");
        checkoutButton.setBounds(300, 70, 80, 25);
        panel.add(checkoutButton);

        backButton = new JButton("Regresar");
        backButton.setBounds(390, 70, 100, 25);
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
        scrollPane.setBounds(10, 110, 660, 340);
        panel.add(scrollPane);

        loadTableData();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductToCart();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductInCart();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProductFromCart();
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
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

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                selectedId = (int) model.getValueAt(table.getSelectedRow(), 0);
                codigoText.setText(model.getValueAt(table.getSelectedRow(), 1).toString());
                cantidadText.setText(model.getValueAt(table.getSelectedRow(), 3).toString());
            }
        });
    }

    private void addProductToCart() {
        try {
            Producto producto = productoDAO.getProductByCodigo(codigoText.getText());
            if (producto != null) {
                int cantidad = Integer.parseInt(cantidadText.getText());
                double precioTotal = producto.getPrecio() * cantidad;

                Detalle detalle = new Detalle();
                detalle.setProductoId(producto.getId());
                detalle.setCodProducto(producto.getCodigo());
                detalle.setCantidad(cantidad);
                detalle.setPrecio(precioTotal);

                detalleDAO.addDetail(detalle);
                clearFields();
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProductInCart() {
        if (selectedId != -1) {
            try {
                Detalle detalle = detalleDAO.getDetailById(selectedId);
                if (detalle != null) {
                    int cantidad = Integer.parseInt(cantidadText.getText());
                    double precioTotal = detalle.getPrecio() * cantidad / detalle.getCantidad(); // Recalcula el precio total

                    detalle.setCantidad(cantidad);
                    detalle.setPrecio(precioTotal);
                    detalleDAO.updateDetail(detalle);
                    clearFields();
                    loadTableData();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Cantidad debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProductFromCart() {
        if (selectedId != -1) {
            detalleDAO.deleteDetail(selectedId);
            clearFields();
            loadTableData();
        }
    }

    private void clearFields() {
        selectedId = -1;
        codigoText.setText("");
        cantidadText.setText("");
    }

    private void loadTableData() {
        model.setRowCount(0);
        List<Detalle> detalles = detalleDAO.getAllDetails();
        for (Detalle detalle : detalles) {
            Producto producto = productoDAO.getProductById(detalle.getProductoId());
            model.addRow(new Object[]{detalle.getId(), producto.getCodigo(), producto.getNombre(), detalle.getCantidad(), detalle.getPrecio()});
        }
    }

    private void checkout() {
        // Aquí puedes implementar la lógica para realizar la compra
        JOptionPane.showMessageDialog(this, "Compra realizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        detalleDAO.clearDetails();
        loadTableData();
    }
}




