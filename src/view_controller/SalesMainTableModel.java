package view_controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import app.Application;
import app.Strings;

public class SalesMainTableModel extends AbstractTableModel {

	private ArrayList data;

	SalesMainTableModel() throws SQLException {
		ArrayList arr = (ArrayList) Application.self.saleService.getData();
		arr.trimToSize();
		data = arr;
	}

	SalesMainTableModel(String type, String name) throws SQLException {//, Date from, Date till) throws SQLException, ParseException {
		ArrayList arr = (ArrayList) Application.self.saleService.getData();
		arr.trimToSize();
		if (type != null)
			arr = (ArrayList) Application.self.saleService.filterByType(type, arr);
		if (name != null)
			arr = (ArrayList) Application.self.saleService.filterByArtist(name, arr);
		/*
		if (from != null)
			arr = (ArrayList) Application.self.saleService.filterByStart(from, arr);
		if (till != null)
			arr = (ArrayList) Application.self.saleService.filterByEnd(till, arr);
		*/
		data = arr;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return ((ArrayList) data.get(row)).get(col);
	}

	public String getColumnName(int col) {
		String[] names = Strings.SALES_MAIN_TABLE_LABELS;
		return (String) names[col];
	}

	public Class getColumnClass(int col){
		if (col == 6) return BigDecimal.class;
		return String.class;
	}


}
