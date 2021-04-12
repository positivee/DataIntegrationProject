package integracja;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Endpoint;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class App {

    public int countNewRecords;
    public int countDuplicatedNewRecords;
    String[] testing = new String[16];
    List<String[]> list = new ArrayList<>();
    DatabaseHelper databaseHelper = new DatabaseHelper();
    Set<Integer> duplicateRowSet = new HashSet<>();
    Set<Integer> modifyRowSet = new HashSet<>();
    String[] headers = new String[]{"<html>Nazwa<br/>producenta",
            "Przekątna ekranu",
            "<html>Rozdzielczość<br/>ekranu",
            "<html>Rodzaj powierzchni<br/> ekranu", "<html>Czy ekran jest<br/> dotykowy?",
            "Nazwa procesora", "<html>Liczba rdzeni<br/> fizycznych", "<html>Prędkość<br/> taktowania MHz", "<html>Wielkość pamięci<br/> RAM", "Pojemność dysku",
            "Rodzaj dysku", "<html>Nazwa układu<br/> graficznego", "<html>Pamięć układu<br/> graficznego", "<html>Nazwa systemu<br/> operacyjnego",
            "<html>Rodzaj napędu<br/> fizycznego"};
    private JPanel jPanel;
    private JButton loadButton;
    private JButton saveButton;
    private JTable table;
    private JScrollPane jScrollPane;
    private JButton loadXmlButton;
    private JButton saveXmlButton;
    private JButton clearButton;
    private JButton loadDatabaseButton;
    private JButton saveDatabaseButton;
    private JLabel recordInfoJLabel;
    private String line;


    public App() throws SQLException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Integracja Systemów - Server App - Kamil Biernacki");
        frame.setContentPane(jPanel);


//        System.out.println( databaseHelper.getCountOfResolution("1600x900"));
//     //   Object[databaseHelper.prepopulateManufacturersList().length] test= databaseHelper.prepopulateManufacturersList();
//        System.out.println(databaseHelper.distinctList("resolution"));

        loadButton.addActionListener(e -> {
            TxtToList();
            ListToTable();
            showLoadedDataInfo();
            showDialog();
        });
        saveButton.addActionListener(e -> {
            saveTableToFile(table);
            showDialog();

        });
        loadXmlButton.addActionListener(e -> {
            XmlToList();
            ListToTable();
            showLoadedDataInfo();
            showDialog();
        });
        saveXmlButton.addActionListener(e -> {
            saveTableToXml(table);
            showDialog();

        });
        loadDatabaseButton.addActionListener(e -> {
            // list.clear();
            try {
                databaseHelper.saveDatabaseToList(list, duplicateRowSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ListToTable();
            showLoadedDataInfo();
            showDialog();

        });
        saveDatabaseButton.addActionListener(e -> {
            try {
                databaseHelper.saveTableToDatabase(table, duplicateRowSet, modifyRowSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        clearButton.addActionListener(e -> {
            list.clear();
            duplicateRowSet.clear();
            modifyRowSet.clear();
            recordInfoJLabel.setText("");
            ListToTable();
            showDialog();

        });
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    public static boolean isRecordDuplicated(List<String[]> list, String[] rowLine) {
        for (String[] listItem : list) {
            if (Arrays.equals(listItem, rowLine)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws SQLException {
        App app = new App();
        Endpoint.publish("http://localhost:8888/katalog", new Katalog());
    }

    public void TxtToList() {
        //  list.clear();
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Wybierz plik txt.");

            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            fileChooser.setAcceptAllFileFilterUsed(false);

            int userSelection = fileChooser.showSaveDialog(jPanel);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                BufferedReader br = new BufferedReader(new FileReader(fileToSave));

                countNewRecords = 0;
                countDuplicatedNewRecords = 0;
                while ((line = br.readLine()) != null) {

                    String[] rowLine = line.split(";", -1);
                    //  rowLine = Arrays.copyOf(rowLine,15);
                    if (isRecordDuplicated(list, rowLine)) {

                        duplicateRowSet.add(list.size());
                        countDuplicatedNewRecords++;
                    }
                    ;
                    countNewRecords++;
                    list.add(rowLine);

                }
                databaseHelper.setCountDuplicatedNewRecords(countDuplicatedNewRecords);
                databaseHelper.setCountNewRecords(countNewRecords);

            }
        } catch (IOException fileNotFoundException) {
            JOptionPane.showMessageDialog(null, "Nie istnieje plik txt");
            fileNotFoundException.printStackTrace();
        }
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
        };
        table.setModel(model);
        table.setPreferredScrollableViewportSize(new Dimension(1280, 500));
        table.setFillsViewportHeight(true);
        Checker checker = new Checker(duplicateRowSet, modifyRowSet);
        table.setDefaultRenderer(Object.class, checker);
        table.setDefaultRenderer(Integer.class, checker);


        table.getModel().addTableModelListener(e -> {
            //System.out.println("Column: " + e.getColumn() + " Row: " + e.getFirstRow());
            Object[][] test = list.toArray((new Object[][]{}));


            for (int i = 0; i <= 15; i++) {
                if (i == 15)
                    testing[15] = "";
                else
                    testing[i] = String.valueOf(table.getValueAt(e.getFirstRow(), i));
            }

            if (!(String.valueOf(table.getModel().getValueAt(e.getFirstRow(), e.getColumn())).equals(test[e.getFirstRow()][e.getColumn()]))) {
                if (!(String.valueOf(table.getModel().getValueAt(e.getFirstRow(), e.getColumn())).equals("null")))
                    modifyRowSet.add(e.getFirstRow());
            } else {
                if (isRecordSame(testing, test[e.getFirstRow()])) {
                    modifyRowSet.remove(e.getFirstRow());
                }
            }
        });
    }

    public boolean isRecordSame(String[] selectedRow, Object[] objects) {
        for (int column = 0; column < objects.length; column++) {
            if (!selectedRow[column].equals(objects[column])) {
                return false;
            }
        }
        return true;
    }

    public void XmlToList() {
        try {
            // list.clear();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Gdzie zapisać plik xml?");
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            int userSelection = fileChooser.showSaveDialog(jPanel);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                DocumentBuilder builder;
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                Document document = builder.parse(fileToSave);
                document.getDocumentElement().normalize();

                NodeList nodeList = document.getElementsByTagName("laptop");
                countDuplicatedNewRecords = 0;
                countNewRecords = nodeList.getLength();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        String[] linia = new String[16];
                        Element element = (Element) node;
                        //  test[0]= element.getAttribute("id");
                        linia[0] = element.getElementsByTagName("manufacturer").item(0).getTextContent();
                        Element screen = (Element) element.getElementsByTagName("screen").item(0);

                        linia[1] = screen.getElementsByTagName("size").item(0).getTextContent();
                        linia[2] = screen.getElementsByTagName("resolution").item(0).getTextContent();
                        if (screen.getElementsByTagName("type").item(0).getTextContent().equals("matowy")) {
                            linia[3] = "matowa";
                        } else if (screen.getElementsByTagName("type").item(0).getTextContent().equals("blyszczacy")) {
                            linia[3] = "blyszczaca";
                        } else {
                            linia[3] = screen.getElementsByTagName("type").item(0).getTextContent();
                        }
                        String EnglishToPolish = " ";
                        if (screen.getAttribute("touch").equals("yes")) EnglishToPolish = "tak";
                        else if (screen.getAttribute("touch").equals("no")) EnglishToPolish = "nie";
                        linia[4] = EnglishToPolish;

                        Element processor = (Element) element.getElementsByTagName("processor").item(0);

                        linia[5] = processor.getElementsByTagName("name").item(0).getTextContent();
                        linia[6] = processor.getElementsByTagName("physical_cores").item(0).getTextContent();
                        linia[7] = processor.getElementsByTagName("clock_speed").item(0).getTextContent();

                        linia[8] = element.getElementsByTagName("ram").item(0).getTextContent();

                        Element disc = (Element) element.getElementsByTagName("disc").item(0);

                        linia[9] = disc.getElementsByTagName("storage").item(0).getTextContent();
                        linia[10] = disc.getAttribute("type");

                        Element graphicCard = (Element) element.getElementsByTagName("graphic_card").item(0);

                        linia[11] = graphicCard.getElementsByTagName("name").item(0).getTextContent();
                        linia[12] = graphicCard.getElementsByTagName("memory").item(0).getTextContent();

                        linia[13] = element.getElementsByTagName("os").item(0).getTextContent();

                        linia[14] = element.getElementsByTagName("disc_reader").item(0).getTextContent();
                        linia[15] = "";

                        if (isRecordDuplicated(list, linia)) {
                            duplicateRowSet.add(list.size());
                            countDuplicatedNewRecords++;
                        }
                        list.add(linia);

                    }
                    databaseHelper.setCountDuplicatedNewRecords(countDuplicatedNewRecords);
                    databaseHelper.setCountNewRecords(countNewRecords);
                }
            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException saxException) {
            saxException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void saveTableToFile(JTable table) {
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Gdzie zapisać plik txt?");
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            int userSelection = fileChooser.showSaveDialog(jPanel);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                FileWriter fw = new FileWriter(fileToSave.getAbsoluteFile() + ".txt");
                BufferedWriter bw = new BufferedWriter(fw);
                String[] currentRecord = new String[15];
                List<String[]> listSaveChecker = new ArrayList<>();


                for (int row = 0; row < table.getRowCount(); row++) {
                    currentRecord = new String[15];
                    for (int y = 0; y < table.getColumnCount(); y++) {
                        currentRecord[y] = (String) table.getModel().getValueAt(row, y);
                    }
                    if ((!modifyRowSet.contains(row) && !duplicateRowSet.contains(row)) || (modifyRowSet.contains(row) && !App.isRecordDuplicated(listSaveChecker, currentRecord))) {
                        for (int column = 0; column < table.getColumnCount(); column++) {

                            if (table.getModel().getValueAt(row, column) == null) {
                                bw.write(";");
                            } else if (table.getModel().getValueAt(row, column).equals("Nie Podano")) {
                                bw.write(";");
                            } else {
                                bw.write(table.getModel().getValueAt(row, column) + ";");
                            }

                        }
                        bw.write("\n");
                        listSaveChecker.add(currentRecord);
                    }
                }
                bw.close();
                fw.close();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getModDate() {
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat simpleDateFormatTwo = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(d) + " T " + simpleDateFormatTwo.format(d);
    }

    public void saveTableToXml(JTable table) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Gdzie zapisać plik xml?");
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            int userSelection = fileChooser.showSaveDialog(jPanel);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                Element laptops = document.createElement("laptops");
                document.appendChild(laptops);

                laptops.setAttribute("moddate", getModDate());
                String[] currentRecord = new String[15];
                List<String[]> listSaveChecker = new ArrayList<>();


                for (int row = 0; row < table.getRowCount(); row++) {
                    currentRecord = new String[15];
                    for (int column = 0; column < table.getColumnCount(); column++) {
                        currentRecord[column] = String.valueOf(table.getModel().getValueAt(row, column));
                    }
                    if ((!modifyRowSet.contains(row) && !duplicateRowSet.contains(row)) || (modifyRowSet.contains(row) && !App.isRecordDuplicated(listSaveChecker, currentRecord))) {

                        Element laptop = document.createElement("laptop");
                        laptops.appendChild(laptop);

                        laptop.setAttribute("id", String.valueOf(row + 1));


                        Element manufacturer = document.createElement("manufacturer");
                        manufacturer.setTextContent(String.valueOf(table.getModel().getValueAt(row, 0).toString()));
                        laptop.appendChild(manufacturer);

                        // firstname element
                        Element screen = document.createElement("screen");
                        Element size = document.createElement("size");
                        Element resolution = document.createElement("resolution");
                        Element type = document.createElement("type");
                        size.setTextContent(String.valueOf(table.getModel().getValueAt(row, 1)));
                        resolution.setTextContent(String.valueOf(table.getModel().getValueAt(row, 2)));
                        if (String.valueOf(table.getModel().getValueAt(row, 3)).equals("matowa")) {
                            type.setTextContent("matowy");
                        } else if (String.valueOf(table.getModel().getValueAt(row, 3)).equals("blyszczaca")) {
                            type.setTextContent("blyszczacy");
                        } else {
                            type.setTextContent(String.valueOf(table.getModel().getValueAt(row, 3)));
                        }

                        screen.appendChild(size);
                        screen.appendChild(resolution);
                        screen.appendChild(type);
                        String EnglishToPolish = " ";
                        if (String.valueOf(table.getModel().getValueAt(row, 4)).equals("tak")) {
                            EnglishToPolish = "yes";
                        } else if (String.valueOf(table.getModel().getValueAt(row, 4)).equals("nie")) {
                            EnglishToPolish = "no";
                        }
                        screen.setAttribute("touch", EnglishToPolish);

                        laptop.appendChild(screen);

                        Element processor = document.createElement("processor");
                        Element name = document.createElement("name");
                        Element physical_cores = document.createElement("physical_cores");
                        Element clock_speed = document.createElement("clock_speed");

                        name.setTextContent(String.valueOf(table.getModel().getValueAt(row, 5)));
                        physical_cores.setTextContent(String.valueOf(table.getModel().getValueAt(row, 6)));
                        clock_speed.setTextContent(String.valueOf(table.getModel().getValueAt(row, 7)));

                        processor.appendChild(name);
                        processor.appendChild(physical_cores);
                        processor.appendChild(clock_speed);

                        laptop.appendChild(processor);

                        Element ram = document.createElement("ram");
                        ram.setTextContent(String.valueOf(table.getModel().getValueAt(row, 8)));

                        laptop.appendChild(ram);

                        Element disc = document.createElement("disc");
                        Element storage = document.createElement("storage");

                        storage.setTextContent(String.valueOf(table.getModel().getValueAt(row, 9)));

                        disc.appendChild(storage);
                        disc.setAttribute("type", String.valueOf(table.getModel().getValueAt(row, 10)));

                        laptop.appendChild(disc);

                        Element graphic_card = document.createElement("graphic_card");
                        Element nameGraphic = document.createElement("name");
                        Element memory = document.createElement("memory");


                        nameGraphic.setTextContent(String.valueOf(table.getModel().getValueAt(row, 11)));
                        memory.setTextContent(String.valueOf(table.getModel().getValueAt(row, 12)));

                        graphic_card.appendChild(nameGraphic);
                        graphic_card.appendChild(memory);

                        laptop.appendChild(graphic_card);


                        Element os = document.createElement("os");
                        os.setTextContent(String.valueOf(table.getModel().getValueAt(row, 13)));
                        laptop.appendChild(os);

                        Element discReader = document.createElement("disc_reader");
                        discReader.setTextContent(String.valueOf(table.getModel().getValueAt(row, 14)));
                        laptop.appendChild(discReader);


                        listSaveChecker.add(currentRecord);
                    }
                }


                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(fileToSave + ".xml");
                transformer.transform(domSource, streamResult);

            }
        } catch (ParserConfigurationException | TransformerConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (TransformerException transformerException) {
            transformerException.printStackTrace();
        }
    }

    public void showLoadedDataInfo() {
        recordInfoJLabel.setText("Znaleziono nowych rekordów " + databaseHelper.getCountNewRecords() + " w tym " + databaseHelper.getCountDuplicatedNewRecords() + " duplikatów");

    }

    public void showDialog() {
        JOptionPane.showMessageDialog(null, "Operacja zakończona");

    }

    public JButton getLoadButton() {
        return loadButton;
    }


    public JButton getSaveXmlButton() {
        return saveXmlButton;
    }


    public JButton getSaveDatabaseButton() {
        return saveDatabaseButton;
    }

    public JTable getTable() {
        return table;
    }

}