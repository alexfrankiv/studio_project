package view_controller;

import app.Application;
import app.Strings;
import model.Album;
import model.Musician;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import utilities.DateLabelFormatter;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;
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

    private List<Musician> musicians;

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

        musicians = Application.self.musicianService.all();
        DefaultComboBoxModel<Musician> dlm = new DefaultComboBoxModel<>();
        for (Musician m : musicians) {
            dlm.addElement(m);
        }
        managerBox.setModel(dlm);
    }

    private void onOK() {
        // TODO: check
        Album newAlbum = new Album();
        newAlbum.setName(nameInput.getText());
        if (newAlbum.getName() == null) {
            Application.showMessage(Strings.DIALOG_EMPTY_NAME_ERROR);
            return;
        }
        if ( datePicker.getModel().getValue() == null ) {
            Application.showMessage(Strings.DIALOG_EMPTY_DATE_ERROR);
            return;
        } else {
            newAlbum.setRecordDate(new Date(((java.util.Date) datePicker.getModel().getValue()).getTime()));
        }
        try {
            newAlbum.setFeeShare(Double.parseDouble(feeShareInput.getText()));
            newAlbum.setManagerFeeShare(Double.parseDouble(managerFeeShareInput.getText()));
        } catch (NumberFormatException ex) {
            Application.showMessage(Strings.DIALOG_NUMBER_FORMAT_ERROR);
            return;
        }
        newAlbum.setManager(musicians.get(managerBox.getSelectedIndex()));
        try {
            Application.self.albumService.insert(newAlbum);
        } catch (IllegalStateException ex) {/*intentionally nothing*/}
        dispose();
        Application.self.albumViewController.refresh();
    }

    private void onCancel() {
        dispose();
    }

    public static void presentDialog() {
        NewAlbumViewController dialog = new NewAlbumViewController();
        dialog.setupComponents();
        dialog.pack();
        dialog.setVisible(true);
    }
}
