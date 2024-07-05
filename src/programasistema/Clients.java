package programasistema;

import Modelo.Cliente;
import Modelo.ClienteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Clients extends JFrame {
    private JPanel panel;
    private JTextField nombreText;
    private JTextField telefonoText;
    private JTextField direccionText;
    private JTextField fechaText;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton backButton;  // Botón para regresar
    private JTable table;
    private DefaultTableModel model;
    private ClienteDAO clienteDAO;
    private int selectedId = -1;

    public Clients() {
        clienteDAO = new ClienteDAO();

        setTitle("Clients");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 10, 80, 25);
        panel.add(nombreLabel);

        nombreText = new JTextField(20);
        nombreText.setBounds(100, 10, 160, 25);
        panel.add(nombreText);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(10, 40, 80, 25);
        panel.add(telefonoLabel);

        telefonoText = new JTextField(20);
        telefonoText.setBounds(100, 40, 160, 25);
        panel.add(telefonoText);

        JLabel direccionLabel = new JLabel("Dirección:");
        direccionLabel.setBounds(10, 70, 80, 25);
        panel.add(direccionLabel);

        direccionText = new JTextField(20);
        direccionText.setBounds(100, 70, 160, 25);
        panel.add(direccionText);

        JLabel fechaLabel = new JLabel("Fecha (YYYY-MM-DD):");
        fechaLabel.setBounds(10, 100, 150, 25);
        panel.add(fechaLabel);

        fechaText = new JTextField(20);
        fechaText.setBounds(160, 100, 100, 25);
        panel.add(fechaText);

        addButton = new JButton("Agregar");
        addButton.setBounds(10, 130, 80, 25);
        panel.add(addButton);

        updateButton = new JButton("Actualizar");
        updateButton.setBounds(100, 130, 100, 25);
        panel.add(updateButton);

        deleteButton = new JButton("Eliminar");
        deleteButton.setBounds(210, 130, 80, 25);
        panel.add(deleteButton);

        backButton = new JButton("Regresar");
        backButton.setBounds(300, 130, 100, 25);
        panel.add(backButton);

        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Teléfono");
        model.addColumn("Dirección");
        model.addColumn("Fecha");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 170, 560, 180);
        panel.add(scrollPane);

        loadTableData();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClient();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
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
                nombreText.setText(model.getValueAt(table.getSelectedRow(), 1).toString());
                telefonoText.setText(model.getValueAt(table.getSelectedRow(), 2).toString());
                direccionText.setText(model.getValueAt(table.getSelectedRow(), 3).toString());
                fechaText.setText(model.getValueAt(table.getSelectedRow(), 4).toString());
            }
        });
    }

    private void addClient() {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(nombreText.getText());
            cliente.setTelefono(telefonoText.getText());
            cliente.setDireccion(direccionText.getText());
            cliente.setFecha(java.sql.Date.valueOf(fechaText.getText()));

            clienteDAO.addClient(cliente);
            clearFields();
            loadTableData();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateClient() {
        if (selectedId != -1) {
            try {
                Cliente cliente = new Cliente();
                cliente.setId(selectedId);
                cliente.setNombre(nombreText.getText());
                cliente.setTelefono(telefonoText.getText());
                cliente.setDireccion(direccionText.getText());
                cliente.setFecha(java.sql.Date.valueOf(fechaText.getText()));

                clienteDAO.updateClient(cliente);
                clearFields();
                loadTableData();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteClient() {
        if (selectedId != -1) {
            clienteDAO.deleteClient(selectedId);
            clearFields();
            loadTableData();
        }
    }

    private void clearFields() {
        selectedId = -1;
        nombreText.setText("");
        telefonoText.setText("");
        direccionText.setText("");
        fechaText.setText("");
    }

    private void loadTableData() {
        model.setRowCount(0);
        List<Cliente> clientes = clienteDAO.getAllClients();
        for (Cliente cliente : clientes) {
            model.addRow(new Object[]{cliente.getId(), cliente.getNombre(), cliente.getTelefono(), cliente.getDireccion(), cliente.getFecha()});
        }
    }
}

