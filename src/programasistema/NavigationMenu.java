package programasistema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationMenu extends JFrame {
    private JPanel panel;
    private JButton dataButton;
    private JButton cartButton;
    private JButton newSaleButton;
    private JButton salesButton;
    private JButton productsButton;
    private JButton suppliersButton;
    private JButton settingsButton;

    public NavigationMenu() {
        setTitle("Navigation Menu");
        setSize(300, 300); // Aumenta el tamaño de la ventana para acomodar más botones
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        dataButton = new JButton("Data Presentation");
        dataButton.setBounds(10, 10, 150, 25);
        panel.add(dataButton);

        cartButton = new JButton("Shopping Cart");
        cartButton.setBounds(10, 40, 150, 25);
        panel.add(cartButton);

        newSaleButton = new JButton("New Sale");
        newSaleButton.setBounds(10, 70, 150, 25);
        panel.add(newSaleButton);

        salesButton = new JButton("Sales");
        salesButton.setBounds(10, 100, 150, 25);
        panel.add(salesButton);

        productsButton = new JButton("Products");
        productsButton.setBounds(10, 130, 150, 25);
        panel.add(productsButton);

        suppliersButton = new JButton("Suppliers");
        suppliersButton.setBounds(10, 160, 150, 25);
        panel.add(suppliersButton);

        settingsButton = new JButton("Settings");
        settingsButton.setBounds(10, 190, 150, 25);
        panel.add(settingsButton);

        dataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataPresentation dataPresentation = new DataPresentation();
                dataPresentation.setVisible(true);
                dispose();
            }
        });

        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.setVisible(true);
                dispose();
            }
        });

        newSaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewSale newSale = new NewSale();
                newSale.setVisible(true);
                dispose();
            }
        });

        salesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sales sales = new Sales();
                sales.setVisible(true);
                dispose();
            }
        });

        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Products products = new Products();
                products.setVisible(true);
                dispose();
            }
        });

        suppliersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Suppliers suppliers = new Suppliers();
                suppliers.setVisible(true);
                dispose();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings settings = new Settings();
                settings.setVisible(true);
                dispose();
            }
        });
    }
}

