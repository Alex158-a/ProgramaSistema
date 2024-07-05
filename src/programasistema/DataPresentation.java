/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programasistema;

import javax.swing.*;

public class DataPresentation extends JFrame {
    private JPanel panel;

    public DataPresentation() {
        setTitle("Data Presentation");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Data will be presented here");
        label.setBounds(10, 10, 200, 25);
        panel.add(label);
    }
}


