package integracja;


import client.Katalog;
import client.KatalogService;
import client.StringArray;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClientApp {
    String[] headers = new String[]{"<html>Nazwa<br/>producenta",
            "Przekątna ekranu",
            "<html>Rozdzielczość<br/>ekranu",
            "<html>Rodzaj powierzchni<br/> ekranu", "<html>Czy ekran jest<br/> dotykowy?",
            "Nazwa procesora", "<html>Liczba rdzeni<br/> fizycznych", "<html>Prędkość<br/> taktowania MHz", "<html>Wielkość pamięci<br/> RAM", "Pojemność dysku",
            "Rodzaj dysku", "<html>Nazwa układu<br/> graficznego", "<html>Pamięć układu<br/> graficznego", "<html>Nazwa systemu<br/> operacyjnego",
            "<html>Rodzaj napędu<br/> fizycznego"};
    private JPanel jPanel;
    private JButton numberOfManufacturerButton;
    private JComboBox comboBoxScreenType;
    private JButton numberOfLaptopsResolutionButton;
    private JTable table;
    private JLabel manufacturerCount;
    private JComboBox comboBoxManufacturer;
    private JButton typeOfScreenButton;
    private JComboBox comboBoxResolution;
    private JLabel resolutionCount;
    private List<String[]> list = new ArrayList<>();
    ;
    private List<StringArray> stringArraysList = new ArrayList<>();

    public ClientApp() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Integracja Systemów - Client App - Kamil Biernacki");
        frame.setContentPane(jPanel);


        KatalogService client = new KatalogService();
        Katalog service = client.getKatalogPort();

        service.distinctList("manufacturer");
        comboBoxManufacturer.setModel(new DefaultComboBoxModel(service.distinctList("manufacturer").toArray()));
        comboBoxResolution.setModel(new DefaultComboBoxModel(service.distinctList("resolution").toArray()));
        comboBoxScreenType.setModel(new DefaultComboBoxModel(service.distinctList("type").toArray()));


        numberOfManufacturerButton.addActionListener(e -> {
            try {
                //  list.clear();
                manufacturerCount.setText(String.valueOf(service.getCountOfManufacturer(comboBoxManufacturer.getSelectedItem().toString())));
//                stringArraysList = service.getListOfelementValue("manufacturer", comboBoxManufacturer.getSelectedItem().toString());
//                stringArrayToList(stringArraysList);
//                ListToTable();
            } catch (NullPointerException ex) {
                showDialog("Bład, nic nie wybrano!");
            }

        });

        typeOfScreenButton.addActionListener(e -> {
            try {
                list.clear();
                stringArraysList = service.getListOfelementValue("type", comboBoxScreenType.getSelectedItem().toString());
                stringArrayToList(stringArraysList);
                ListToTable();
            } catch (NullPointerException ex) {
                showDialog("Bład, nic nie wybrano!");
            }
        });

        numberOfLaptopsResolutionButton.addActionListener(e -> {
            try {
                //  list.clear();
                resolutionCount.setText(String.valueOf(service.getCountOfResolution(comboBoxResolution.getSelectedItem().toString())));
//                stringArraysList = service.getListOfelementValue("resolution", comboBoxResolution.getSelectedItem().toString());
//                stringArrayToList(stringArraysList);
//                ListToTable();
            } catch (NullPointerException ex) {
                showDialog("Bład, nic nie wybrano!");
            }
        });

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void stringArrayToList(List<StringArray> stringArraysList) {
        String[] oneRecord;

        for (int i = 0; i < stringArraysList.size(); i++) {
            stringArraysList.get(i).getItem().toArray();
            oneRecord = new String[15];
            for (int y = 0; y < 15; y++) {
                oneRecord[y] = stringArraysList.get(i).getItem().get(y);
            }
            list.add(oneRecord);
        }
    }

    public void showDialog(String dialog) {
        JOptionPane.showMessageDialog(null, dialog);
    }

    public void ListToTable() {


        DefaultTableModel model = new DefaultTableModel(list.toArray(new Object[][]{}), headers) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 6, 7 -> Integer.class;
                    default -> String.class;
                };
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }

        };
        table.setModel(model);
        table.setPreferredScrollableViewportSize(new Dimension(1280, 500));
        table.setFillsViewportHeight(true);

    }

    public JButton getNumberOfManufacturerButton() {
        return numberOfManufacturerButton;
    }

    public JComboBox getComboBoxScreenType() {
        return comboBoxScreenType;
    }

    public JButton getNumberOfLaptopsResolutionButton() {
        return numberOfLaptopsResolutionButton;
    }

    public JComboBox getComboBoxManufacturer() {
        return comboBoxManufacturer;
    }

    public JComboBox getComboBoxResolution() {
        return comboBoxResolution;
    }

    public JButton getTypeOfScreenButton() {
        return typeOfScreenButton;
    }

}
