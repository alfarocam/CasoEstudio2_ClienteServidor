/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.casoestudio2.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author camil
 */
public class RanchoMain {

    public static void main(String[] args) {
        // Crear el marco principal
        JFrame frame = new JFrame("Gestión de Rancho - El Buen Bistec");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        // Crear el panel con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // ===== Pestaña Registro =====
        JPanel registroPanel = new JPanel();
        registroPanel.setLayout(new GridLayout(8, 2, 5, 5)); // Organización en cuadrícula

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

        registroPanel.add(new JLabel("Fecha Nacimiento (YYYY-MM-DD):"));
        registroPanel.add(new JTextField());

        registroPanel.add(new JLabel("Peso Nacimiento (kg):"));
        registroPanel.add(new JTextField());

        registroPanel.add(new JLabel("Fecha de Compra (YYYY-MM-DD):"));
        registroPanel.add(new JTextField());

        registroPanel.add(new JLabel("Color de Pelaje:"));
        registroPanel.add(new JTextField());

        tabbedPane.addTab("Registro", registroPanel);

        // ===== Pestaña Eventos =====
        JPanel eventosPanel = new JPanel();
        eventosPanel.setLayout(new GridLayout(6, 2, 5, 5));

        eventosPanel.add(new JLabel("Identificación Animal:"));
        eventosPanel.add(new JTextField());

        eventosPanel.add(new JLabel("Tipo de Evento:"));
        String[] tiposEvento = {"Vacunación", "Desparasitación", "Pesaje", "Traslado de Lote", "Cuarentena"};
        eventosPanel.add(new JComboBox<>(tiposEvento));

        eventosPanel.add(new JLabel("Fecha del Evento (YYYY-MM-DD):"));
        eventosPanel.add(new JTextField());

        eventosPanel.add(new JLabel("Estado:"));
        eventosPanel.add(new JTextField());

        eventosPanel.add(new JLabel("Detalles:"));
        eventosPanel.add(new JTextField());

        JButton btnRegistrarEvento = new JButton("Registrar Evento");
        eventosPanel.add(btnRegistrarEvento);
        eventosPanel.add(new JLabel()); // espacio

        tabbedPane.addTab("Eventos", eventosPanel);

        // ===== Pestaña Historial =====
        JPanel historialPanel = new JPanel(new BorderLayout());

        JPanel buscarPanel = new JPanel();
        buscarPanel.add(new JLabel("Identificación Animal:"));
        JTextField buscarID = new JTextField(15);
        buscarPanel.add(buscarID);
        JButton btnBuscar = new JButton("Buscar Historial");
        buscarPanel.add(btnBuscar);
        historialPanel.add(buscarPanel, BorderLayout.NORTH);

        JTextArea historialArea = new JTextArea();
        historialArea.setEditable(false);
        JScrollPane scrollHistorial = new JScrollPane(historialArea);
        historialPanel.add(scrollHistorial, BorderLayout.CENTER);

        tabbedPane.addTab("Historial", historialPanel);

        // Agregar el panel de pestañas al marco
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Hacer visible el marco
        frame.setVisible(true);
    }
}
