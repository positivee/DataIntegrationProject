package integracja;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static integracja.Main.app;
import static integracja.Main.clientApp;

public class TestingApp {
    Robot robot = new Robot();
    List<Boolean> listCheckboxValues = new ArrayList<>();
    List<String> listTextFieldValues = new ArrayList<>();
    HashMap<Integer, String> valuesToChange = new HashMap<>();
    Frame[] frames = Frame.getFrames();
    private JPanel jPanel;
    private JTextField rowToModify;
    private JTextField manufacturerTextField;
    private JCheckBox manufacturerCheckBox;
    private JButton executeButton;
    private JCheckBox sizeCheckBox;
    private JTextField sizeTextField;
    private JCheckBox resolutionCheckBox;
    private JTextField resolutionTextField;
    private JCheckBox typeCheckBox;
    private JTextField typeTextField;
    private JCheckBox processorCheckBox;
    private JTextField processorTextField;
    private JCheckBox physialCoresCheckBox;
    private JTextField physialCoresTextField;
    private JCheckBox clockSpeedCheckBox;
    private JTextField clockSpeedTextField;
    private JCheckBox ramCheckBox;
    private JTextField ramTextField;
    private JCheckBox storageCheckBox;
    private JTextField storageTextField;
    private JCheckBox discTypeCheckBox;
    private JTextField disckTypeTextField;
    private JCheckBox graphicCardCheckBox;
    private JTextField graphicCardTextField;
    private JCheckBox graphicCardMemoryCheckBox;
    private JTextField graphicCardMemoryTextField;
    private JCheckBox osCheckBox;
    private JTextField osTextField;
    private JCheckBox diskReaderCheckBox;
    private JTextField diskReaderTextField;
    private JCheckBox additionalXMLButton;
    private JCheckBox touchCheckBox;
    private JTextField touchTextField;

    public TestingApp() throws AWTException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Integracja Systemów - Automatic Testing App - Kamil Biernacki");
        frame.setContentPane(jPanel);

        executeButton.addActionListener(e -> {
            listCheckboxValues = new ArrayList<>();
            listTextFieldValues = new ArrayList<>();
            valuesToChange = new HashMap<>();
            clientApp.getComboBoxManufacturer().setSelectedIndex(0);
            clientApp.getComboBoxScreenType().setSelectedIndex(0);
            clientApp.getComboBoxResolution().setSelectedIndex(0);
            getValuesToChangeMap();

            if (isInteger(rowToModify.getText())) {

                if ((valuesToChange.containsKey(6) && isInteger(valuesToChange.get(6))) && (valuesToChange.containsKey(7) && isInteger(valuesToChange.get(7)))) {
                    new Thread(() -> executeTests()).start();
                } else if (((valuesToChange.containsKey(6) && isInteger(valuesToChange.get(6))) && !(valuesToChange.containsKey(7))) || (!(valuesToChange.containsKey(6)) && (valuesToChange.containsKey(7) && isInteger(valuesToChange.get(7))))) {
                    new Thread(() -> executeTests()).start();
                } else if (!valuesToChange.containsKey(6) && !valuesToChange.containsKey(7)) {
                    new Thread(() -> executeTests()).start();
                } else {
                    clientApp.showDialog("Wrong data!");
                }
            } else {
                clientApp.showDialog("There is record selected for editing!");
            }

        });

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


    }

    public static boolean isInteger(String strnig) {
        try {
            Integer.parseInt(strnig);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws AWTException {

        TestingApp testingApp = new TestingApp();
    }

    public void executeTests() {
        try {
            robot = new Robot();
            robot.setAutoDelay(50);
            robot.setAutoWaitForIdle(true);
            JTable testingTable = app.getTable();

            frames[0].setVisible(true);
            Point loadTXTButtonPoint = getCenterPoint(app.getLoadButton());
            animateMouseMove(loadTXTButtonPoint.x, loadTXTButtonPoint.y);
            pressMouseTimes(1);

            putRobotKatalogText();
            robot.keyPress(KeyEvent.VK_PERIOD);
            robot.keyRelease(KeyEvent.VK_PERIOD);
            robot.keyPress(KeyEvent.VK_T);
            robot.keyRelease(KeyEvent.VK_T);
            robot.keyPress(KeyEvent.VK_X);
            robot.keyRelease(KeyEvent.VK_X);
            robot.keyPress(KeyEvent.VK_T);
            robot.keyRelease(KeyEvent.VK_T);
            pressEnterTimes(2);

            for (int column : valuesToChange.keySet()) {
                animateMouseMove(testingTable.getLocationOnScreen().x + (int) testingTable.getCellRect(Integer.parseInt(rowToModify.getText()), column, true).getCenterX(), testingTable.getLocationOnScreen().y + (int) testingTable.getCellRect(Integer.parseInt(rowToModify.getText()), column, true).getCenterY());
                pressMouseTimes(2);
                copyAndPasteFromClipbaord(valuesToChange.get(column));
                pressEnterTimes(1);
            }
            if (additionalXMLButton.isSelected()) {
                Point saveXmlButtonPoint = getCenterPoint(app.getSaveXmlButton());
                animateMouseMove(saveXmlButtonPoint.x, saveXmlButtonPoint.y);
                pressMouseTimes(1);
                ;
                putRobotKatalogText();
                pressEnterTimes(2);
            }
            Point saveDataBaseButtonPoint = getCenterPoint(app.getSaveDatabaseButton());
            animateMouseMove(saveDataBaseButtonPoint.x, saveDataBaseButtonPoint.y);
            pressMouseTimes(1);


            frames[1].setVisible(true);
            test(clientApp.getComboBoxManufacturer(), clientApp.getNumberOfManufacturerButton(), 0);
            test(clientApp.getComboBoxScreenType(), clientApp.getTypeOfScreenButton(), 3);
            test(clientApp.getComboBoxResolution(), clientApp.getNumberOfLaptopsResolutionButton(), 2);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private Point getCenterPoint(JButton button) {
        return new Point(button.getLocationOnScreen().x + button.getWidth() / 2, button.getLocationOnScreen().y + button.getHeight() / 2);
    }

    private Point getCenterPointComboBox(JComboBox jComboBox) {
        return new Point(jComboBox.getLocationOnScreen().x + jComboBox.getWidth() / 2, jComboBox.getLocationOnScreen().y + jComboBox.getHeight() / 2);
    }

    public void animateMouseMove(int endX, int endY) {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        double x = (endX - mousePosition.x) / ((double) 15);
        double y = (endY - mousePosition.y) / ((double) 15);
        for (int step = 1; step <= 15; step++) {
            robot.mouseMove((int) (mousePosition.x + x * step), (int) (mousePosition.y + y * step));
        }
    }

    public void test(JComboBox jComboBox, JButton button, int postion) {
        Boolean value = true;
        Point ComboBoxCenterPoint = getCenterPointComboBox(jComboBox);

        if (valuesToChange.get(postion) != null) {

            animateMouseMove(ComboBoxCenterPoint.x, ComboBoxCenterPoint.y);
            pressMouseTimes(1);
            for (int i = 0; i < jComboBox.getItemCount(); i++) {
                if (valuesToChange.get(postion).equals(jComboBox.getItemAt(i))) {
                    pressDownTimes(i);
                    value = false;
                }
            }
            if (value) {
                pressDownTimes(jComboBox.getItemCount());
            }
        } else if (valuesToChange.get(postion) == null && app.getTable().getValueAt(Integer.parseInt(rowToModify.getText()), postion) != null) {
            animateMouseMove(ComboBoxCenterPoint.x, ComboBoxCenterPoint.y);
            pressMouseTimes(1);
            for (int i = 0; i < jComboBox.getItemCount(); i++) {
                if (app.getTable().getValueAt(Integer.parseInt(rowToModify.getText()), postion).toString().equals(jComboBox.getItemAt(i))) {
                    pressDownTimes(i);
                    value = false;
                }
            }
            if (value) {
                pressDownTimes(jComboBox.getItemCount());
            }

        } else if (valuesToChange.get(postion) == null) {
            animateMouseMove(ComboBoxCenterPoint.x, ComboBoxCenterPoint.y);
            pressMouseTimes(1);
            pressDownTimes(jComboBox.getItemCount());
        }
        Point buttonPoint = getCenterPoint(button);
        animateMouseMove(buttonPoint.x, buttonPoint.y);
        pressMouseTimes(1);
    }

    public void pressDownTimes(int times) {
        for (int i = 0; i < times; i++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
    }

    public void pressEnterTimes(int times) {
        for (int i = 0; i < times; i++) {
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
    }

    public void pressMouseTimes(int times) {
        for (int i = 0; i < times; i++) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
    }

    public void copyAndPasteFromClipbaord(String text) {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = defaultToolkit.getSystemClipboard();
        clipboard.setContents(new StringSelection(text), null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_DELETE);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void getValuesToChangeMap() {

        listCheckboxValues.add(manufacturerCheckBox.isSelected());
        listCheckboxValues.add(sizeCheckBox.isSelected());
        listCheckboxValues.add(resolutionCheckBox.isSelected());
        listCheckboxValues.add(typeCheckBox.isSelected());
        listCheckboxValues.add(touchCheckBox.isSelected());
        listCheckboxValues.add(processorCheckBox.isSelected());
        listCheckboxValues.add(physialCoresCheckBox.isSelected());
        listCheckboxValues.add(clockSpeedCheckBox.isSelected());
        listCheckboxValues.add(ramCheckBox.isSelected());
        listCheckboxValues.add(storageCheckBox.isSelected());
        listCheckboxValues.add(discTypeCheckBox.isSelected());
        listCheckboxValues.add(graphicCardCheckBox.isSelected());
        listCheckboxValues.add(graphicCardMemoryCheckBox.isSelected());
        listCheckboxValues.add(osCheckBox.isSelected());
        listCheckboxValues.add(diskReaderCheckBox.isSelected());
        listCheckboxValues.add(osCheckBox.isSelected());

        listTextFieldValues.add(manufacturerTextField.getText());
        listTextFieldValues.add(sizeTextField.getText());
        listTextFieldValues.add(resolutionTextField.getText());
        listTextFieldValues.add(typeTextField.getText());
        listTextFieldValues.add(touchTextField.getText());
        listTextFieldValues.add(processorTextField.getText());
        listTextFieldValues.add(physialCoresTextField.getText());
        listTextFieldValues.add(clockSpeedTextField.getText());
        listTextFieldValues.add(ramTextField.getText());
        listTextFieldValues.add(storageTextField.getText());
        listTextFieldValues.add(disckTypeTextField.getText());
        listTextFieldValues.add(graphicCardTextField.getText());
        listTextFieldValues.add(graphicCardMemoryTextField.getText());
        listTextFieldValues.add(osTextField.getText());
        listTextFieldValues.add(diskReaderTextField.getText());
        listTextFieldValues.add(osTextField.getText());

        for (int i = 0; i < listCheckboxValues.size(); i++) {
            if (listCheckboxValues.get(i)) {
                valuesToChange.put(i, listTextFieldValues.get(i));
            }
        }
    }

    public void putRobotKatalogText() {
        robot.keyPress(KeyEvent.VK_K);
        robot.keyRelease(KeyEvent.VK_K);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_L);
        robot.keyPress(KeyEvent.VK_O);
        robot.keyRelease(KeyEvent.VK_O);
        robot.keyPress(KeyEvent.VK_G);
        robot.keyRelease(KeyEvent.VK_G);

    }


}
