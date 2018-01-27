package view_controller;

import app.Application;
import model.Musician;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.Properties;

public class NewAlbumViewController extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameInput;
    private JPanel datePanel;
    private JTextField feeShareInput;
    private JTextField managerFeeShareInput;
    private JComboBox<Musician> managerBox;
    private JDatePickerImpl datePicker;

    public NewAlbumViewController() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void setupComponents() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        this.datePanel.add(datePicker);

        DefaultComboBoxModel<Musician> dlm = new DefaultComboBoxModel<>();
        for (Musician m : Application.self.musicianService.all()) {
            dlm.addElement(m);
        }
        managerBox.setModel(dlm);
    }

    private void onOK() {
        // TODO: check & insert
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void presentDialog() {
        NewAlbumViewController dialog = new NewAlbumViewController();
        dialog.setupComponents();
        dialog.pack();
        dialog.setVisible(true);
    }
}
