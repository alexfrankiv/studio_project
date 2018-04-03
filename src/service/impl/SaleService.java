package service.impl;

import app.Application;
import app.Strings;
import model.*;
import model.dto.PreparedRevenue;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SaleService {
	
	private List<ArrayList<String>> res;
	
	public Sale getById(int id) throws SQLException {
		return Application.self.saleRepository.getById(id);
	}
	
	public boolean updateSale(Sale sale) throws SQLException {
		return Application.self.saleRepository.update(sale);
	}
	
	public boolean removeSale(Sale sale) throws SQLException {
		return Application.self.saleRepository.remove(sale);
	}
	
	public boolean removeSale(int id) throws SQLException {
		return Application.self.saleRepository.remove(id);
	}
	
	public void newRecordSale(Record rec) throws SQLException {
		int recId = Application.self.saleRepository.add(rec);
		RecordCost rc = new RecordCost( rec, recId, Application.self.albumPriceRepository.getLastPriceFor(rec.getAlbum()));
		Application.self.flowRepository.add(rc);
		List<PreparedRevenue> pr = Application.self.flowRepository.getAlbumRevenues(rc, (int) rec.getAlbumId());
		for (PreparedRevenue i : pr)
			Application.self.flowRepository.addRevenue(i);
	}
	
	public void newLicenseSale(License l) throws SQLException {
		Application.self.saleRepository.add(l);
	}
	
	public void newLicensePayment(LicensePayment lp) throws SQLException {
		Application.self.flowRepository.add(lp);
		List<PreparedRevenue> pr = Application.self.flowRepository.getAlbumRevenues(lp, (int) lp.getSale().getAlbumId());
		for (PreparedRevenue i : pr)
			Application.self.flowRepository.addRevenue(i);
	}
	
	public String getLicenseInfo(License l) throws SQLException {
		String client = l.getClient();
		String album = l.getAlbum().getRecordDate().toString();
		String date = l.getDate().toString();
		return l.getId() + ": " + album + " - " + client + " - " + date + " +" + l.getPeriod() + "��.";
		
	}
	
	public int getLicenseIdByInfo(String info) {
		return Integer.parseInt((info.split(":"))[0]);
	}
	
	public List<Sale> getLicenses() throws SQLException {
		return Application.self.saleRepository.getLicenses();
	}
	
	public List<String> getLicenseInfos() throws SQLException {
		List res = new ArrayList<String>();
		for (Sale l : getLicenses())
			res.add(getLicenseInfo((License)l));
		return res;
	}
	
	public List getData() throws SQLException {
		if (res == null) {
			res = new ArrayList<ArrayList<String>>();
			List<Flow> flows = Application.self.flowRepository.getAll();
			for(Flow f : flows) {
				ArrayList<String> row = new ArrayList<String>();
				if (f.getType() != Flow.FlowType.MUSICIAN_REVENUE) {
					row.add(f.getDate().toString()); //����
					row.add(f.getSale().getAlbum().getName()); //������
					row.add(f.getSale().getAlbum().getManager().getFullName()); //����������
					if (f.getType() == Flow.FlowType.LICENSE_PAYMENT) {
						row.add(Strings.OPERATION_OPTION_MONTHLY_PAYMENT);
						row.add( Strings.MONTHS.get(((LicensePayment)f).getMonth())); //�����
					}
					else {
						row.add(Strings.OPERATION_OPTION_RECORD_PURCHASE);
						row.add( ((Record)f.getSale()).getQty() + "" ); //���
					}
					
					row.add(f.getSale().getClient()); //��������
					row.add(f.getTotal().doubleValue() + " UAH"); //����
					res.add(row);
				}
				row.trimToSize();
				
			}
		}
		return res;
	}
	
	public List getLicenseData() throws SQLException {
		List table = new ArrayList<>();
		List<Sale> licenses = Application.self.saleRepository.getLicenses();
		for(Sale li : licenses) {
			ArrayList<String> row = new ArrayList<>();
			row.add(li.getId() + ""); //id
			row.add(li.getDate().toString()); //����
			row.add(li.getAlbum().getName()); //������
			row.add(li.getAlbum().getManager().getFullName()); //����������
			row.add(((License)li).getPeriod() + " mo."); //������
			if (((License)li).isPaid()) {
				row.add(((License) li).getSum() + " UAH");
			}
			else {
				row.add(getSaleRevenue(li) + " / " + ((License) li).getSum()); //���������
			}
			row.add(li.getClient()); //��������
			row.trimToSize();
			table.add(row);
		}
		return table;
	}
	
	public BigDecimal getSaleRevenue(Sale sale) throws SQLException {
		return Application.self.flowRepository.getSumBySaleId(sale.getId());
	}

	public List filterByArtist(String name, List<ArrayList<String>> in) {
		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> row : in)
			if (row.get(1).contains(name))
				list.add(row);
		return list;
	}
	
	public List filterByType(String type, List<ArrayList<String>> in) {
		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> row : in)
			if (row.get(3).contains(type))
				list.add(row);
		return list;
	}
	
	public List filterByStart(Date date, List<ArrayList<String>> in) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> row : in)
			if (format.parse(row.get(0)).after(date))
				list.add(row);
		return list;
	}
	
	public List filterByEnd(Date date, List<ArrayList<String>> in) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> row : in)
			if (format.parse(row.get(0)).before(date))
				list.add(row);
		return list;
	}

	public void resetRes() {
		this.res = null;
	}

}
