package view_controller;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import app.Application;
import app.Strings;

public class SalesViewController {

	private JPanel contentView;
	private final JButton selectButton = new JButton(Strings.SELECT_ALBUM_BUTTON);
	private JTable table;
	private JLabel albumId;
	private JComboBox albumBox;
	private JComboBox newAlbumBox;
	private JComboBox operationBox;
	//private SelectorDialog dialog;
	private JPanel newPanel;

	public SalesViewController() {
		contentView = new JPanel();
		contentView.setBorder(new EmptyBorder(5, 5, 5, 5));
		initialize();
	}

	private void initialize( ) {
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 103, 103, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{23, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentView.setLayout(gbl_contentPane);

		albumId = new JLabel("albumId");
		GridBagConstraints gbc_albumId = new GridBagConstraints();
		gbc_albumId.insets = new Insets(0, 0, 5, 5);
		gbc_albumId.gridx = 1;
		gbc_albumId.gridy = 0;
		albumId.setVisible(false);
		contentView.add(albumId, gbc_albumId);

		JLabel albumLabel = new JLabel(Strings.FIELD_ALBUM_LABEL);
		albumLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_albumLabel = new GridBagConstraints();
		gbc_albumLabel.insets = new Insets(0, 0, 5, 5);
		gbc_albumLabel.anchor = GridBagConstraints.EAST;
		gbc_albumLabel.gridx = 1;
		gbc_albumLabel.gridy = 1;
		contentView.add(albumLabel, gbc_albumLabel);

		albumBox = new JComboBox();
		GridBagConstraints gbc_albumBox = new GridBagConstraints();
		gbc_albumBox.insets = new Insets(0, 0, 5, 5);
		gbc_albumBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_albumBox.gridx = 2;
		gbc_albumBox.gridy = 1;

		albumBox.addItem("-");
		try {
			for(String i : Application.self.albumService.getNames())
				albumBox.addItem(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		albumBox.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					refreshTable();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		contentView.add(albumBox, gbc_albumBox);

		GridBagConstraints gbc_selectButton = new GridBagConstraints();
		gbc_selectButton.insets = new Insets(0, 0, 5, 5);
		gbc_selectButton.gridx = 3;
		gbc_selectButton.gridy = 1;

		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					initializeSelector();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		contentView.add(selectButton, gbc_selectButton);

		JLabel operationLabel = new JLabel(Strings.FIELD_OPEARION_LABEL);
		operationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_operationLabel = new GridBagConstraints();
		gbc_operationLabel.anchor = GridBagConstraints.EAST;
		gbc_operationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_operationLabel.gridx = 5;
		gbc_operationLabel.gridy = 1;
		contentView.add(operationLabel, gbc_operationLabel);

		operationBox = new JComboBox();
		operationBox.setModel(new DefaultComboBoxModel(new String[] {
				Strings.OPERATION_OPTION_NONE,
				Strings.OPERATION_OPTION_RECORD_PURCHASE,
				Strings.OPERATION_OPTION_MONTHLY_PAYMENT}));
		GridBagConstraints gbc_operationBox = new GridBagConstraints();
		gbc_operationBox.insets = new Insets(0, 0, 5, 5);
		gbc_operationBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_operationBox.gridx = 6;
		gbc_operationBox.gridy = 1;

		operationBox.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					refreshTable();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		contentView.add(operationBox, gbc_operationBox);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		contentView.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		try {
			table.setModel(new MainTableModel());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public JPanel getContentView() {
		return contentView;
	}

	private void initializeSelector() throws SQLException {
		/*
		if (dialog == null) {
			dialog = new SelectorDialog(saleSer.getAlbSer(), this, false);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		dialog.setVisible(true);*/
	}

	private void refreshTable( ) throws SQLException {
		String name = null;
		String type = null;
		if (!((String)albumBox.getSelectedItem()).equals(Strings.OPERATION_OPTION_NONE))
			name = (String)albumBox.getSelectedItem();
		if (!((String)operationBox.getSelectedItem()).equals(Strings.OPERATION_OPTION_NONE))
			type = (String)operationBox.getSelectedItem();
		//System.out.println(name);System.out.println(type);
		table.setModel(new MainTableModel(type,name));
	}

}
