package programasistema;

import Modelo.Empresa;
import Modelo.EmpresaDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JFrame {
    private JPanel panel;
    private JTextField idText;
    private JTextField rucText;
    private JTextField nombreText;
    private JTextField telefonoText;
    private JButton updateButton;
    private JButton backButton;
    private EmpresaDAO empresaDAO;

    public Settings() {
        empresaDAO = new EmpresaDAO();

        setTitle("Settings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Company Settings");
        label.setBounds(10, 10, 200, 25);
        panel.add(label);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(10, 40, 80, 25);
        panel.add(idLabel);

        idText = new JTextField(20);
        idText.setBounds(100, 40, 160, 25);
        idText.setEnabled(false); // Disable editing of ID initially
        panel.add(idText);

        JLabel rucLabel = new JLabel("RUC:");
        rucLabel.setBounds(10, 70, 80, 25);
        panel.add(rucLabel);

        rucText = new JTextField(20);
        rucText.setBounds(100, 70, 160, 25);
        panel.add(rucText);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 100, 80, 25);
        panel.add(nombreLabel);

        nombreText = new JTextField(20);
        nombreText.setBounds(100, 100, 160, 25);
        panel.add(nombreText);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(10, 130, 80, 25);
        panel.add(telefonoLabel);

        telefonoText = new JTextField(20);
        telefonoText.setBounds(100, 130, 160, 25);
        panel.add(telefonoText);

        updateButton = new JButton("Actualizar");
        updateButton.setBounds(10, 160, 120, 25);
        panel.add(updateButton);

        backButton = new JButton("Regresar");
        backButton.setBounds(140, 160, 120, 25);
        panel.add(backButton);

        loadCompanyData();

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCompanyData();
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

    private void loadCompanyData() {
        Empresa empresa = empresaDAO.getCompanyData();
        if (empresa != null) {
            idText.setText(String.valueOf(empresa.getId()));
            rucText.setText(empresa.getRuc());
            nombreText.setText(empresa.getNombre());
            telefonoText.setText(empresa.getTelefono());
        } else {
            idText.setEnabled(true); // Enable editing if ID is not loaded
        }
    }

    private void updateCompanyData() {
        String idStr = idText.getText();
        if (idStr == null || idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empresa empresa = new Empresa();
        empresa.setId(Integer.parseInt(idStr));
        empresa.setRuc(rucText.getText());
        empresa.setNombre(nombreText.getText());
        empresa.setTelefono(telefonoText.getText());

        empresaDAO.updateCompanyData(empresa);
        JOptionPane.showMessageDialog(this, "Datos de la empresa actualizados correctamente");
    }
}
