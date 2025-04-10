/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ranchomain;

/**
 *
 * @author camil
 */
public class RanchoMain {

    public static void main(String[] args) {
        // Crear el marco principal
        JFrame frame = new JFrame("Ejemplo de Tabbed Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Crear el panel con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Crear el panel para la pestaña "Registro"
        JPanel registroPanel = new JPanel();
        registroPanel.setLayout(new GridLayout(8, 2, 5, 5)); // Organización en una cuadrícula

        // Agregar los campos y etiquetas para "Registro"
        registroPanel.add(new JLabel("Identificación:"));
        registroPanel.add(new JTextField());

        registroPanel.add(new JLabel("Raza:"));
        registroPanel.add(new JTextField());

        registroPanel.add(new JLabel("Sexo:"));
        JPanel sexoPanel = new JPanel();
        JRadioButton macho = new JRadioButton("Macho");
        JRadioButton hembra = new JRadioButton("Hembra");
        ButtonGroup sexoGroup = new ButtonGroup();
        sexoGroup.add(macho);
        sexoGroup.add(hembra);
        sexoPanel.add(macho);
        sexoPanel.add(hembra);
        registroPanel.add(sexoPanel);

        registroPanel.add(new JLabel("Nombre:"));
        registroPanel.add(new JTextField());

        registroPanel.add(new JLabel("Fecha Nacimiento:"));
        registroPanel.add(new JTextField());

        registroPanel.add(new JLabel("Peso Nacimiento:"));
        registroPanel.add(new JTextField());

        registroPanel.add(new JLabel("Fecha de Compra:"));
        registroPanel.add(new JTextField());

        registroPanel.add(new JLabel("Color de Pelaje:"));
        registroPanel.add(new JTextField());

        // Agregar el panel de "Registro" como una pestaña
        tabbedPane.addTab("Registro", registroPanel);

        // Crear otras pestañas (por ejemplo, "Eventos" y "Historial")
        JPanel eventosPanel = new JPanel();
        eventosPanel.add(new JLabel("Contenido de la pestaña 'Eventos'."));
        tabbedPane.addTab("Eventos", eventosPanel);

        JPanel historialPanel = new JPanel();
        historialPanel.add(new JLabel("Contenido de la pestaña 'Historial'."));
        tabbedPane.addTab("Historial", historialPanel);

        // Agregar el panel con pestañas al marco
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Hacer visible el marco
        frame.setVisible(true);
    }

}
