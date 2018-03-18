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
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class AlbumDetailsController extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameInput;
    private JPanel datePanel;
    private JTextField feeShareInput;
    private JTextField managerFeeShareInput;
    private JComboBox<Musician> managerBox;
    private JLabel windowLabel;
    private JDatePickerImpl datePicker;

    private List<Musician> musicians;

    private boolean createNew;
    private Album currentAblum; //of album to edit

    public AlbumDetailsController(boolean createNew, long id) {
        assert (!createNew);
        this.createNew = createNew;
        this.currentAblum = Application.self.albumService.getBy(id);
        setup();
    }

    public AlbumDetailsController(boolean createNew) {
        assert (createNew);
        this.createNew = createNew;
        setup();
    }

    private void setup(){
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

        windowLabel.setText((this.createNew) ? Strings.DIALOG_NEW_TITLE : Strings.DIALOG_EDIT_TITLE);
        //if editing -- setup fields
        if (!this.createNew) {
            nameInput.setText(currentAblum.getName());
            Date recordDate = currentAblum.getRecordDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(recordDate);
            datePicker.getModel().setDay(calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.getModel().setMonth(calendar.get(Calendar.MONTH));
            datePicker.getModel().setYear(calendar.get(Calendar.YEAR));
            datePicker.getJFormattedTextField().setText(recordDate.toString());
            feeShareInput.setText(String.valueOf(currentAblum.getFeeShare()));
            managerFeeShareInput.setText(String.valueOf(currentAblum.getManagerFeeShare()));
            managerBox.setSelectedIndex(dlm.getIndexOf(currentAblum.getManager()));
        }
    }

    private void onOK() {
        Album album = (createNew) ? new Album() : currentAblum;
        album.setName(nameInput.getText());
        if (album.getName() == null) {
            Application.showMessage(Strings.DIALOG_EMPTY_NAME_ERROR);
            return;
        }
        if ( datePicker.getModel().getValue() == null ) {
            Application.showMessage(Strings.DIALOG_EMPTY_DATE_ERROR);
            return;
        } else {
            album.setRecordDate(new Date(((java.util.Date) datePicker.getModel().getValue()).getTime()));
        }
        try {
            album.setFeeShare(Double.parseDouble(feeShareInput.getText()));
            album.setManagerFeeShare(Double.parseDouble(managerFeeShareInput.getText()));
            if (album.getFeeShare() < 0 || album.getManagerFeeShare() < 0)
                    throw new IllegalArgumentException();
        } catch (Exception ex) {
            Application.showMessage(Strings.DIALOG_NUMBER_FORMAT_ERROR);
            return;
        }
        album.setManager(musicians.get(managerBox.getSelectedIndex()));
        try {
            if (createNew) {
                Application.self.albumService.insert(album);
            } else {
                Application.self.albumService.update(album);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            /*intentionally nothing*/}
        dispose();
        Application.self.albumViewController.refresh();
    }

    private void onCancel() {
        dispose();
    }

    public static void presentDialog(boolean createNew) {
        AlbumDetailsController dialog = new AlbumDetailsController(createNew);
        doPresent(dialog);
    }

    public static void presentDialog(boolean createNew, long id) {
        AlbumDetailsController dialog = new AlbumDetailsController(createNew, id);
        doPresent(dialog);
    }

    private  static void doPresent(AlbumDetailsController dialog) {
        dialog.setupComponents();
        dialog.pack();
        dialog.setVisible(true);
    }

    public static final LocalDate createLocalDate (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
}
