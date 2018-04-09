package view_controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.Application;
import app.Strings;
import model.*;
import model.dto.PreparedRevenue;

public class SalesNewController {

    private JPanel panel;
    private JTextField sumField;
    private JTextField clientField;
    private JSpinner qty;
    private JRadioButton recordRadio;
    private Flow lp;
    private Sale rec;
    private JComboBox licenseSelectBox;
    private final JButton selectButton = new JButton(Strings.SELECT_ALBUM_BUTTON);
    private JTable table;
    private JLabel albumId;
    private JComboBox albumBox;
    private JComboBox newAlbumBox;
    private JComboBox operationBox;
    private JButton confirmButton;


    public SalesNewController() {
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    public void setup() {
        this.lp = null;
        this.rec = null;

        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel albumLabel = new JLabel(Strings.FIELD_ALBUM_LABEL);
        GridBagConstraints gbc_albumLabel = new GridBagConstraints();
        gbc_albumLabel.anchor = GridBagConstraints.EAST;
        gbc_albumLabel.insets = new Insets(0, 0, 5, 5);
        gbc_albumLabel.gridx = 1;
        gbc_albumLabel.gridy = 2;
        panel.add(albumLabel, gbc_albumLabel);

        albumBox = new JComboBox();
        GridBagConstraints gbc_albumBox = new GridBagConstraints();
        gbc_albumBox.gridwidth = 2;
        gbc_albumBox.insets = new Insets(0, 0, 5, 5);
        gbc_albumBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_albumBox.gridx = 2;
        gbc_albumBox.gridy = 2;


        try {
            Application.self.albumRepository.all().forEach(i -> albumBox.addItem(i));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        panel.add(albumBox, gbc_albumBox);

        /*
        JButton selectButton = new JButton("Обрати");
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    initializeSelector();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        GridBagConstraints gbc_selectButton = new GridBagConstraints();
        gbc_selectButton.insets = new Insets(0, 0, 5, 5);
        gbc_selectButton.gridx = 4;
        gbc_selectButton.gridy = 2;

        panel.add(selectButton, gbc_selectButton);
        */

        JLabel clientLabel = new JLabel(Strings.SALES_NEW_LABEL_CLIENT);
        GridBagConstraints gbc_clientLabel = new GridBagConstraints();
        gbc_clientLabel.anchor = GridBagConstraints.EAST;
        gbc_clientLabel.insets = new Insets(0, 0, 5, 5);
        gbc_clientLabel.gridx = 1;
        gbc_clientLabel.gridy = 3;
        panel.add(clientLabel, gbc_clientLabel);

        clientField = new JTextField();
        GridBagConstraints gbc_clientField = new GridBagConstraints();
        gbc_clientField.gridwidth = 2;
        gbc_clientField.insets = new Insets(0, 0, 5, 5);
        gbc_clientField.fill = GridBagConstraints.HORIZONTAL;
        gbc_clientField.gridx = 2;
        gbc_clientField.gridy = 3;
        panel.add(clientField, gbc_clientField);
        clientField.setColumns(10);



        qty = new JSpinner();
        qty.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                try {
                    calculatePrice();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        qty.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                try {
                    calculatePrice();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void mouseReleased(MouseEvent arg0) {
                try {
                    calculatePrice();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void mouseClicked(MouseEvent arg0) {
                try {
                    calculatePrice();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        JLabel mutableLabel = new JLabel(Strings.SALES_NEW_LABEL_QTY);
        GridBagConstraints gbc_mutableLabel = new GridBagConstraints();
        gbc_mutableLabel.anchor = GridBagConstraints.EAST;
        gbc_mutableLabel.insets = new Insets(0, 0, 5, 5);
        gbc_mutableLabel.gridx = 1;
        gbc_mutableLabel.gridy = 4;
        panel.add(mutableLabel, gbc_mutableLabel);
        qty.setModel(new SpinnerNumberModel(1, 1, 24, 1));
        GridBagConstraints gbc_qty = new GridBagConstraints();
        gbc_qty.gridwidth = 2;
        gbc_qty.anchor = GridBagConstraints.WEST;
        gbc_qty.insets = new Insets(0, 0, 5, 5);
        gbc_qty.gridx = 2;
        gbc_qty.gridy = 4;
        panel.add(qty, gbc_qty);

        licenseSelectBox = new JComboBox();
        GridBagConstraints gbc_licenseSelectBox = new GridBagConstraints();
        gbc_licenseSelectBox.gridwidth = 2;
        gbc_licenseSelectBox.insets = new Insets(0, 0, 5, 5);
        gbc_licenseSelectBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_licenseSelectBox.gridx = 2;
        gbc_licenseSelectBox.gridy = 5;

        try {
            Application.self.saleRepository.getOngoingLicenses().forEach(i -> licenseSelectBox.addItem(i));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        licenseSelectBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    calculatePrice();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        licenseSelectBox.setVisible(false);

        JLabel licenseSelectLabel = new JLabel(Strings.SALES_NEW_LABEL_LICENSE);
        GridBagConstraints gbc_licenseSelectLabel = new GridBagConstraints();
        gbc_licenseSelectLabel.insets = new Insets(0, 0, 5, 5);
        gbc_licenseSelectLabel.anchor = GridBagConstraints.EAST;
        gbc_licenseSelectLabel.gridx = 1;
        gbc_licenseSelectLabel.gridy = 5;
        panel.add(licenseSelectLabel, gbc_licenseSelectLabel);
        panel.add(licenseSelectBox, gbc_licenseSelectBox);

        JLabel priceLabel = new JLabel(Strings.SALES_NEW_LABEL_COST);
        GridBagConstraints gbc_priceLabel = new GridBagConstraints();
        gbc_priceLabel.anchor = GridBagConstraints.EAST;
        gbc_priceLabel.insets = new Insets(0, 0, 5, 5);
        gbc_priceLabel.gridx = 1;
        gbc_priceLabel.gridy = 6;
        panel.add(priceLabel, gbc_priceLabel);

        sumField = new JTextField();
        GridBagConstraints gbc_sumField = new GridBagConstraints();
        gbc_sumField.gridwidth = 2;
        gbc_sumField.insets = new Insets(0, 0, 5, 5);
        gbc_sumField.fill = GridBagConstraints.HORIZONTAL;
        gbc_sumField.gridx = 2;
        gbc_sumField.gridy = 6;
        panel.add(sumField, gbc_sumField);
        sumField.setColumns(10);
        sumField.setEditable(false);

        recordRadio = new JRadioButton(Strings.OPERATION_OPTION_RECORD_PURCHASE);
        recordRadio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mutableLabel.setText(Strings.SALES_NEW_LABEL_QTY);
                //priceLabel.setText("Сума :");
                sumField.setEditable(false);
                licenseSelectBox.setVisible(false);
                licenseSelectLabel.setVisible(false);
                albumLabel.setVisible(true);
                albumBox.setVisible(true);
                mutableLabel.setVisible(true);
                selectButton.setVisible(true);
                qty.setVisible(true);

                clientLabel.setVisible(true);
                clientField.setVisible(true);
                try {
                    calculatePrice();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        recordRadio.setSelected(true);
        GridBagConstraints gbc_recordRadio = new GridBagConstraints();
        gbc_recordRadio.insets = new Insets(0, 0, 5, 5);
        gbc_recordRadio.gridx = 1;
        gbc_recordRadio.gridy = 1;
        panel.add(recordRadio, gbc_recordRadio);

        JRadioButton paymentRadio = new JRadioButton(Strings.OPERATION_OPTION_MONTHLY_PAYMENT);
        paymentRadio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mutableLabel.setText(Strings.SALES_NEW_LABEL_MONTHS);
                //priceLabel.setText("За місяць :");
                sumField.setEditable(true);
                licenseSelectBox.setVisible(true);
                licenseSelectLabel.setVisible(true);
                albumLabel.setVisible(false);
                albumBox.setVisible(false);
                clientLabel.setVisible(false);
                clientField.setVisible(false);
                selectButton.setVisible(false);
                mutableLabel.setVisible(false);
                qty.setVisible(false);
                //
                try {
                    calculatePrice();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        GridBagConstraints gbc_paymentRadio = new GridBagConstraints();
        gbc_paymentRadio.insets = new Insets(0, 0, 5, 5);
        gbc_paymentRadio.gridx = 2;
        gbc_paymentRadio.gridy = 1;
        panel.add(paymentRadio, gbc_paymentRadio);

        ButtonGroup group = new ButtonGroup();
        group.add(recordRadio);
        group.add(paymentRadio);

        confirmButton = new JButton(Strings.SALES_NEW_BTN_CONFIRM);
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    initializeConfirmation();
                    refresh();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        GridBagConstraints gbc_confirmButton = new GridBagConstraints();
        gbc_confirmButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_confirmButton.insets = new Insets(0, 0, 5, 5);
        gbc_confirmButton.gridx = 2;
        gbc_confirmButton.gridy = 7;
        panel.add(confirmButton, gbc_confirmButton);

        try {
            calculatePrice();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        licenseSelectLabel.setVisible(false);
        //operationBox.setModel(new DefaultComboBoxModel(new String[] {"-", "покупка записів", "щомісячна плата"}));

        albumBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    calculatePrice();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initializeSelector() throws SQLException {
        /*
        if (dialog == null) {
            dialog = new SelectorDialog(saleSer.getAlbSer(), this, false);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        }
        dialog.setVisible(true);
        */
    }

    private void initializeConfirmation() throws SQLException {
        //If LICENSE
        Sale sale = null;
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar now = Calendar.getInstance();
        License selected = null;
        int[] payMonth = null;
        BigDecimal totalPreliminary = null;
        BigDecimal remainder = null;
        BigDecimal total = null;
        boolean finalPayment = false;
        List<PreparedRevenue> pr = null;
        String revenues = "";

        if (recordRadio.isSelected()) {
            rec = new Record(date, clientField.getText(),
                    ((Album)albumBox.getSelectedItem()).getId(),
                    (int) qty.getValue()
            );

            pr = Application.self.saleService.newRecordSale((Record)rec);
            revenues = Application.self.saleService.licenseRevenuesAsString(pr);

        }
        else {
            selected = (License)licenseSelectBox.getSelectedItem();
            payMonth = Application.self.saleService.getCurrentPayMonth(selected);
            totalPreliminary = new BigDecimal(sumField.getText());
            remainder = selected.getSum().subtract(Application.self.saleService.getSaleRevenue(selected));
            if (totalPreliminary.compareTo(remainder) < 0) {
                total = totalPreliminary;
            }
            else {
                total = remainder;
                finalPayment = true;
            }
            lp = new LicensePayment(date,
                    total,
                    selected.getId(),
                    payMonth[0], payMonth[1]
            );

            pr = Application.self.saleService.newLicensePayment((LicensePayment)lp, finalPayment);
            revenues = Application.self.saleService.licenseRevenuesAsString(pr);
        }

        //Display CONFIRMATION MESSAGE
        String  message = "";

        if (recordRadio.isSelected()) {
            message = String.format(Strings.SALES_NEW_CONFIRMATION_MESSAGE_RECORD,
                    rec.getAlbum().toString(), rec.getAlbum().getId(), rec.getAlbum().getCurrentPrice().toString(),
                    ((Record)pr.get(0).getRevenue().getSale()).getQty(), pr.get(0).getRevenue().getTotal(),
                    rec.getClient(), now.getTime().toString());
        }
        else {
            message = String.format(Strings.SALES_NEW_CONFIRMATION_MESSAGE_LICENSE,
                    lp.getSale().getId(), lp.getSale().getAlbum().toString(),
                    ((License)lp.getSale()).getExpiry(),
                    lp.getSale().getClient(), pr.get(0).getRevenue().getTotal(),
                    Strings.MONTHS.get(((LicensePayment)lp).getMonth()), (finalPayment ? "fully paid" : "partially paid"),
                    now.getTime().toString());
        }

        message += revenues;

        int option = JOptionPane.showConfirmDialog(null, message, Strings.SALES_NEW_CONFIRMATION_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Application.self.saleService.resetRes();
            if (!recordRadio.isSelected()) {
                Application.self.saleService.addLicenseRevenue(pr);
            }
        }
        else {
            if (recordRadio.isSelected()) {
                Application.self.saleService.removeSale(pr.get(0).getRevenue().getSale());
            }
            else {
                ((License)lp.getSale()).setPaid(false);
                Application.self.saleRepository.update(lp.getSale());
                Application.self.flowRepository.remove(lp);
            }
        }

    }

    private void calculatePrice() throws SQLException {
        BigDecimal albumPrice = Application.self.albumPriceRepository.getLastPriceFor((Album)albumBox.getSelectedItem());
        //BigDecimal albumPrice = saleSer.getAlbSer().getPrice(((String)albumBox.getSelectedItem()));
        BigDecimal amount = new BigDecimal((int) qty.getValue());
        if (recordRadio.isSelected()) {
            //System.out.println("HHHH");
            sumField.setEnabled(true);
            confirmButton.setEnabled(true);
            sumField.setText(albumPrice.multiply(amount) + "");
        }
        else {
            if (licenseSelectBox.getSelectedItem() != null) {
                sumField.setEnabled(true);
                confirmButton.setEnabled(true);
                sumField.setText(
                        ((License)licenseSelectBox.getSelectedItem()).getPrice()
                                //((License)saleSer.getById(saleSer.getLicenseIdByInfo(licenseSelectBox.getSelectedItem().toString()))).getPrice()
                                + "");
            }
            else {
                sumField.setText("0.00");
                sumField.setEnabled(false);
                confirmButton.setEnabled(false);
            }

        }
    }

    public JRadioButton getRecordRadio() {
        return recordRadio;
    }

    public Flow getLp() {
        return lp;
    }

    public Sale getRec() {
        return rec;
    }

    public JPanel getContentView() {
        return panel;
    }

    public void refresh() {
        licenseSelectBox.removeAllItems();
        try {
            Application.self.saleRepository.getOngoingLicenses().forEach(i -> licenseSelectBox.addItem(i));
            calculatePrice();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
