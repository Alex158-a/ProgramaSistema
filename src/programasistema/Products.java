package programasistema;

import Modelo.Producto;
import Modelo.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Products extends JFrame {
    private JPanel panel;
    private JTextField codigoText;
    private JTextField nombreText;
    private JTextField stockText;
    private JTextField precioText;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton reportButton;
    private JButton backButton;  // Botón para regresar
    private JTable table;
    private DefaultTableModel model;
    private ProductoDAO productoDAO;
    private int selectedId = -1;

    public Products() {
        productoDAO = new ProductoDAO();

        setTitle("Products");
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

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 40, 80, 25);
        panel.add(nombreLabel);

        nombreText = new JTextField(20);
        nombreText.setBounds(100, 40, 160, 25);
        panel.add(nombreText);

        JLabel stockLabel = new JLabel("Stock:");
        stockLabel.setBounds(10, 70, 80, 25);
        panel.add(stockLabel);

        stockText = new JTextField(20);
        stockText.setBounds(100, 70, 160, 25);
        panel.add(stockText);

        JLabel precioLabel = new JLabel("Precio:");
        precioLabel.setBounds(10, 100, 80, 25);
        panel.add(precioLabel);

        precioText = new JTextField(20);
        precioText.setBounds(100, 100, 160, 25);
        panel.add(precioText);

        addButton = new JButton("Agregar");
        addButton.setBounds(10, 130, 80, 25);
        panel.add(addButton);

        updateButton = new JButton("Actualizar");
        updateButton.setBounds(100, 130, 100, 25);
        panel.add(updateButton);

        deleteButton = new JButton("Eliminar");
        deleteButton.setBounds(210, 130, 80, 25);
        panel.add(deleteButton);

        reportButton = new JButton("Generar Reporte");
        reportButton.setBounds(300, 130, 150, 25);
        panel.add(reportButton);

        backButton = new JButton("Regresar");
        backButton.setBounds(460, 130, 100, 25);
        panel.add(backButton);

        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("ID");
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Stock");
        model.addColumn("Precio");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 170, 660, 280);
        panel.add(scrollPane);

        loadTableData();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
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
                nombreText.setText(model.getValueAt(table.getSelectedRow(), 2).toString());
                stockText.setText(model.getValueAt(table.getSelectedRow(), 3).toString());
                precioText.setText(model.getValueAt(table.getSelectedRow(), 4).toString());
            }
        });
    }

    private void addProduct() {
        try {
            Producto producto = new Producto();
            producto.setCodigo(codigoText.getText());
            producto.setNombre(nombreText.getText());
            producto.setStock(Integer.parseInt(stockText.getText()));
            producto.setPrecio(Double.parseDouble(precioText.getText()));

            productoDAO.addProduct(producto);
            clearFields();
            loadTableData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stock y Precio deben ser números.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        if (selectedId != -1) {
            try {
                Producto producto = new Producto();
                producto.setId(selectedId);
                producto.setCodigo(codigoText.getText());
                producto.setNombre(nombreText.getText());
                producto.setStock(Integer.parseInt(stockText.getText()));
                producto.setPrecio(Double.parseDouble(precioText.getText()));

                productoDAO.updateProduct(producto);
                clearFields();
                loadTableData();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stock y Precio deben ser números.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProduct() {
        if (selectedId != -1) {
            productoDAO.deleteProduct(selectedId);
            clearFields();
            loadTableData();
        }
    }

    private void clearFields() {
        selectedId = -1;
        codigoText.setText("");
        nombreText.setText("");
        stockText.setText("");
        precioText.setText("");
    }

    private void loadTableData() {
        model.setRowCount(0);
        List<Producto> productos = productoDAO.getAllProducts();
        for (Producto producto : productos) {
            model.addRow(new Object[]{producto.getId(), producto.getCodigo(), producto.getNombre(), producto.getStock(), producto.getPrecio()});
        }
    }

    private void generateReport() {
        List<Producto> productos = productoDAO.getAllProducts();
        StringBuilder report = new StringBuilder("Reporte de Productos:\n\n");
        report.append("ID\tCódigo\tNombre\tStock\tPrecio\n");
        for (Producto producto : productos) {
            report.append(producto.getId()).append("\t")
                  .append(producto.getCodigo()).append("\t")
                  .append(producto.getNombre()).append("\t")
                  .append(producto.getStock()).append("\t")
                  .append(producto.getPrecio()).append("\n");
        }
        JTextArea textArea = new JTextArea(report.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "Reporte de Productos", JOptionPane.INFORMATION_MESSAGE);
    }
}
