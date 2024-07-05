package programasistema;

import Modelo.Proveedor;
import Modelo.ProveedorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Suppliers extends JFrame {
    private JPanel panel;
    private JTextField rucText;
    private JTextField nombreText;
    private JTextField telefonoText;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton backButton;  // Botón para regresar
    private JTable table;
    private DefaultTableModel model;
    private ProveedorDAO proveedorDAO;
    private int selectedId = -1;

    public Suppliers() {
        proveedorDAO = new ProveedorDAO();

        setTitle("Suppliers");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel rucLabel = new JLabel("RUC:");
        rucLabel.setBounds(10, 10, 80, 25);
        panel.add(rucLabel);

        rucText = new JTextField(20);
        rucText.setBounds(100, 10, 160, 25);
        panel.add(rucText);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 40, 80, 25);
        panel.add(nombreLabel);

        nombreText = new JTextField(20);
        nombreText.setBounds(100, 40, 160, 25);
        panel.add(nombreText);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(10, 70, 80, 25);
        panel.add(telefonoLabel);

        telefonoText = new JTextField(20);
        telefonoText.setBounds(100, 70, 160, 25);
        panel.add(telefonoText);

        addButton = new JButton("Agregar");
        addButton.setBounds(10, 100, 80, 25);
        panel.add(addButton);

        updateButton = new JButton("Actualizar");
        updateButton.setBounds(100, 100, 100, 25);
        panel.add(updateButton);

        deleteButton = new JButton("Eliminar");
        deleteButton.setBounds(210, 100, 80, 25);
        panel.add(deleteButton);

        backButton = new JButton("Regresar");
        backButton.setBounds(300, 100, 100, 25);
        panel.add(backButton);

        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("ID");
        model.addColumn("RUC");
        model.addColumn("Nombre");
        model.addColumn("Teléfono");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 140, 560, 200);
        panel.add(scrollPane);

        loadTableData();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplier();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSupplier();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSupplier();
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
                rucText.setText(model.getValueAt(table.getSelectedRow(), 1).toString());
                nombreText.setText(model.getValueAt(table.getSelectedRow(), 2).toString());
                telefonoText.setText(model.getValueAt(table.getSelectedRow(), 3).toString());
            }
        });
    }

    private void addSupplier() {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc(rucText.getText());
        proveedor.setNombre(nombreText.getText());
        proveedor.setTelefono(telefonoText.getText());

        proveedorDAO.addSupplier(proveedor);
        clearFields();
        loadTableData();
    }

    private void updateSupplier() {
        if (selectedId != -1) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(selectedId);
            proveedor.setRuc(rucText.getText());
            proveedor.setNombre(nombreText.getText());
            proveedor.setTelefono(telefonoText.getText());

            proveedorDAO.updateSupplier(proveedor);
            clearFields();
            loadTableData();
        }
    }

    private void deleteSupplier() {
        if (selectedId != -1) {
            proveedorDAO.deleteSupplier(selectedId);
            clearFields();
            loadTableData();
        }
    }

    private void clearFields() {
        selectedId = -1;
        rucText.setText("");
        nombreText.setText("");
        telefonoText.setText("");
    }

    private void loadTableData() {
        model.setRowCount(0);
        List<Proveedor> proveedores = proveedorDAO.getAllSuppliers();
        for (Proveedor proveedor : proveedores) {
            model.addRow(new Object[]{proveedor.getId(), proveedor.getRuc(), proveedor.getNombre(), proveedor.getTelefono()});
        }
    }
}


