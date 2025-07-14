package view.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.ExcelHelper;
import util.DateFormatter;
import entity.Employee;
import view.button.Button;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;

public class StatisticsInvoiceFrame extends JFrame implements ActionListener {
	Employee employee;
	StatisticsInvoiceFrame parent;

//	Component thống kê hợp đồng
	JPanel menuPanel, searchPanel, totalPanel, invoicePanel,centerPanelInvoice, 
		listPanel, listNorthPanel, listCenterPanel;
	JLabel titleInvoice, startDateLabel, endDateLabel, searchRecruiterLabel, 
			searchApplicantLabel, summaryValueLabel, summaryNumberLabel, valueLabel, numberLabel;
	Button btnSearch, btnReset, btnExcel;
	JTable tableInvoice;
	DefaultTableModel modelTableInvoice;
	JScrollPane scrollInvoice;
	UtilDateModel modelStartDate, modelEndDate;
	JDatePickerImpl startDate, endDate;
	JComboBox<Object> recruiterBox, applicantBox;
	Icon iconBtnSave;

//	private EntityManager em = Persistence.createEntityManagerFactory("default")
//			.createEntityManager();
//	private HoaDonDAO hoadonDAO;
//	private NhaTuyenDungDAO nhatuyendungDAO;
//	private UngVienDAO ungVienDao;

	public StatisticsInvoiceFrame(Employee employee) {
		this.employee=employee;
		this.parent = this;

//		Tạo component bên phải
		initComponent();

//		Thêm sự kiện
		addActionListener();

//		hoadonDAO =  new HoaDonDAO(em, Invoice.class);
//		nhatuyendungDAO = new NhaTuyenDungDAO(em);
//		ungVienDao = new UngVienDAO(em);

//		loadData();
//		loadDataTable();
//		loadDataTotal();
	}

	public JLabel createLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI",1,16));
		label.setForeground(Color.WHITE);
		return label;
	}

	public Button createButton(String title, Color bgColor, Color fgColor) {
		Button button = new Button(title);
		button.setFont(new Font("Segoe UI",0,16));
		button.setPreferredSize(new Dimension(120,25));
		button.setBackground(bgColor);
		button.setForeground(fgColor);
		return button;
	}

	public void initComponent() {
		invoicePanel=new JPanel();
		invoicePanel.setLayout(new BorderLayout(5,5));

//		Hiển thị Thống kê và danh sách tin tuyển dụng
		centerPanelInvoice=new JPanel();
		centerPanelInvoice.setLayout(new BorderLayout(5,5));
		centerPanelInvoice.setBackground(new Color(89, 145, 144));
//		Thống kê tin tuyển dụng
		searchPanel=new GradientRoundPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT,19,5));

		recruiterBox=new JComboBox<Object>();
		recruiterBox.setFont(new Font("Segoe UI",0,16));
		recruiterBox.setOpaque(false);
		recruiterBox.setPreferredSize(new Dimension(250,30));
		applicantBox=new JComboBox<Object>();
		applicantBox.setFont(new Font("Segoe UI",0,16));
		applicantBox.setOpaque(false);

		modelStartDate=new UtilDateModel();
		modelEndDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl panelDateBatDau=new JDatePanelImpl(modelStartDate, p);
		JDatePanelImpl panelDateKetThuc=new JDatePanelImpl(modelEndDate, p);

		startDateLabel= createLabel("Ngày bắt đầu:");
		startDate=new JDatePickerImpl(panelDateBatDau, new DateFormatter());
		startDate.setPreferredSize(new Dimension(130,25));

		endDateLabel= createLabel("Ngày kết thúc:");
		endDate=new JDatePickerImpl(panelDateKetThuc, new DateFormatter());
		endDate.setPreferredSize(new Dimension(130,25));

		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 7, 5));
		resBtnSearch.setBackground(Color.WHITE);

		btnSearch= createButton("Thống kê", new Color(0,102,102), Color.WHITE);
		btnReset= createButton("Hủy", Color.RED, Color.WHITE);
		resBtnSearch.add(btnSearch);
		resBtnSearch.add(btnReset);

		searchPanel.add(recruiterBox); searchPanel.add(applicantBox);
		searchPanel.add(startDateLabel); searchPanel.add(startDate);
		searchPanel.add(endDateLabel); searchPanel.add(endDate);
		searchPanel.add(resBtnSearch);

//		Danh sách tin tuyển dụng
		listPanel=new GradientRoundPanel();
		listPanel.setBackground(Color.WHITE);
		listPanel.setLayout(new BorderLayout(10, 10));

		listNorthPanel=new GradientRoundPanel();
		listNorthPanel.setLayout(new BorderLayout(10,10));
		listNorthPanel.setBackground(Color.WHITE);
		iconBtnSave=new ImageIcon(getClass().getResource("/image/icon/save.png"));
		JPanel resBtnThem=new JPanel();
		resBtnThem.setOpaque(false);
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		resBtnThem.setBackground(Color.WHITE);

		btnExcel=new Button("Xuất Excel");
		btnExcel.setIcon(iconBtnSave);
		btnExcel.setFont(new Font("Segoe UI",0,16));
		btnExcel.setPreferredSize(new Dimension(140,30));
		btnExcel.setBackground(new Color(51,51,255));
		btnExcel.setForeground(Color.WHITE);
		resBtnThem.add(btnExcel);
		titleInvoice=new JLabel("Danh sách hợp đồng");
		titleInvoice.setForeground(Color.WHITE);
		titleInvoice.setFont(new Font("Segoe UI",1,16));
		titleInvoice.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listNorthPanel.add(titleInvoice, BorderLayout.WEST);
		listNorthPanel.add(resBtnThem, BorderLayout.EAST);

		listCenterPanel=new GradientRoundPanel();
		listCenterPanel.setLayout(new BoxLayout(listCenterPanel, BoxLayout.PAGE_AXIS));
		listCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hợp đồng","Tên ứng viên","Số điện thoại","Nhà tuyển dụng", "Phí dịch vụ", "Ngày lập"};
		Object[][] data = {
			    {1, "Nguyễn Thắng Minh Đạt", "0123456789", "Facebook", "50,000 VNĐ", LocalDate.now().toString()},
			    {2, "Nguyễn Thắng Minh Đạt", "0987654321", "Amazon", "50,000 VNĐ", LocalDate.now().toString()}
			};
		modelTableInvoice= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true, true
	            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableInvoice=new JTable(modelTableInvoice);
		tableInvoice.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableInvoice.setFont(new Font("Segoe UI",0,16));
		tableInvoice.setRowHeight(30);
		tableInvoice.setDefaultRenderer(Object.class, new TableCellGradient());
		tableInvoice.setCursor(new Cursor(Cursor.HAND_CURSOR));

		tableInvoice.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter)tableInvoice.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();

		scrollInvoice=new JScrollPane(tableInvoice);
		scrollInvoice.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		scrollInvoice.setPreferredSize(new Dimension(1280, 480));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollInvoice);
		listCenterPanel.add(resScroll);

		listPanel.add(listNorthPanel, BorderLayout.NORTH);
		listPanel.add(listCenterPanel, BorderLayout.CENTER);

//		Tổng số và giá trị hợp đồng
		totalPanel=new GradientRoundPanel();
		totalPanel.setLayout(new BorderLayout());
		totalPanel.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));

		JPanel resPanelSummary = new JPanel();
		resPanelSummary.setOpaque(false);
		resPanelSummary.setLayout(new GridLayout(2, 1));

		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summaryValueLabel= createLabel("Tổng giá trị hợp đồng:");
		summaryValueLabel.setFont(new Font("Segoe UI",1,16));
		valueLabel = createLabel("");
		valueLabel.setFont(new Font("Segoe UI",1,16));
		temp.add(summaryValueLabel);
		temp.add(valueLabel);
		temp.setOpaque(false);
		resPanelSummary.add(temp);

		JPanel temp1 = new JPanel();
		temp1.setLayout(new FlowLayout(FlowLayout.LEFT));
		summaryNumberLabel= createLabel("Tổng số lượng hợp đồng:");
		summaryNumberLabel.setFont(new Font("Segoe UI",1,16));
		numberLabel = createLabel("");
		numberLabel.setFont(new Font("Segoe UI",1,16));
		temp1.add(summaryNumberLabel);
		temp1.add(numberLabel);
		temp1.setOpaque(false);
		resPanelSummary.add(temp1);

		totalPanel.add(resPanelSummary, BorderLayout.WEST);

		centerPanelInvoice.add(searchPanel, BorderLayout.NORTH);
		centerPanelInvoice.add(listPanel, BorderLayout.CENTER);
		centerPanelInvoice.add(totalPanel, BorderLayout.SOUTH);

		invoicePanel.add(centerPanelInvoice, BorderLayout.CENTER);
	}

//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(Color.BLACK);
	}

//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.GRAY);
	}

	public void addActionListener() {
		btnExcel.addActionListener(this);
		btnSearch.addActionListener(this);
		btnReset.addActionListener(this);
	}

	public int getFetchType() {
		String tenNtd = recruiterBox.getSelectedItem().toString();
		String tenUV = applicantBox.getSelectedItem().toString();
		Object ngayBD = startDate.getModel().getValue();
		Object ngayKT = endDate.getModel().getValue();

		if (!tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && !tenUV.equalsIgnoreCase("Chọn ứng viên") && (ngayBD != null && ngayKT != null)) {
			return 7;
		}

		if (!tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && (ngayBD != null && ngayKT != null)) {
			return 6;
		}

		if (!tenUV.equalsIgnoreCase("Chọn ứng viên") && (ngayBD != null && ngayKT != null)) {
			return 5;
		}

		if (!tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && !tenUV.equalsIgnoreCase("Chọn ứng viên")) {
			return 4;
		}

		if (ngayBD != null && ngayKT != null) {
			return 3;
		}

		if (!tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng")) {
			return 2;
		}

		if (!tenUV.equalsIgnoreCase("Chọn ứng viên") ) {
			return 1;
		}

		return 0;
	}

	public void fetchHopDong(String tenNtd, String tenUV) {
//		ArrayList<NhaTuyenDung> listNtd = nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1);
//		ArrayList<UngVien> listUv = ungVienDao.getUngVienBy(tenUV, 1);
//
//		if (listNtd.size() != 0 && listUv.size() == 0) {
//			NhaTuyenDung ntd = listNtd.get(0);
//			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoNhaTuyenDung(ntd.getMaNTD()));
//			return;
//		}
//		if (nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1).size() == 0 && ungVienDao.getUngVienBy(tenUV, 1).size() != 0 ) {
//			UngVien uv = listUv.get(0);
//			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVien(uv.getMaUV()));
//			return;
//		}
//		if (nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1).size() != 0 && ungVienDao.getUngVienBy(tenUV, 1).size() != 0) {
//			NhaTuyenDung ntd = listNtd.get(0);
//			UngVien uv = listUv.get(0);
//			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVienVaNhaTuyenDung(uv.getMaUV(), ntd.getMaNTD()));
//			return;
//		}
	}

	public void fetchHopDong(String tenNtd, String tenUV, LocalDate startDate, LocalDate endDate) {
//		ArrayList<NhaTuyenDung> listNtd = nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1);
//		ArrayList<UngVien> listUv = ungVienDao.getUngVienBy(tenUV, 1);
//
//		if (listNtd.size() == 0 && listUv.size() == 0) {
//			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoThoiGian(startDate, endDate));
//			return;
//		}
//		if (listNtd.size() != 0 && listUv.size() == 0) {
//			NhaTuyenDung ntd = listNtd.get(0);
//			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoNhaTuyenDung(ntd.getMaNTD(), startDate, endDate));
//			return;
//		}
//		if (listNtd.size() == 0 && listUv.size() != 0) {
//			UngVien uv = listUv.get(0);
//			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVien(uv.getMaUV(), startDate, endDate));
//			return;
//		}
//		if (listNtd.size() != 0 && listUv.size() != 0) {
//			NhaTuyenDung ntd = listNtd.get(0);
//			UngVien uv = listUv.get(0);
//			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVienVaNhaTuyenDung(uv.getMaUV(), ntd.getMaNTD(), startDate, endDate));
//			return;
//		}
}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
				var obj=e.getSource();
				if(obj.equals(btnExcel)) {
					ExcelHelper excel = new ExcelHelper();
					excel.exportData(this, tableInvoice, 0);
				}
				else if(obj.equals(btnSearch)) {
					JOptionPane.showMessageDialog(rootPane, "Search invoice");
//					String tenNtd = recruiterBox.getSelectedItem().toString();
//					String tenUV = applicantBox.getSelectedItem().toString();
//					Object ngayBD = startDate.getModel().getValue();
//					Object ngayKT = endDate.getModel().getValue();
//
//					if (tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && tenUV.equalsIgnoreCase("Chọn ứng viên") && (ngayBD == null && ngayKT == null )) {
//						return;
//					}
//
//					if ((ngayBD == null && ngayKT != null) ||
//						(ngayBD != null && ngayKT == null)) {
//						JOptionPane.showMessageDialog(this, "Phải chọn cả ngày bắt đầu và ngày kết thúc");
//						return;
//					}
//
//					// 7 fetch data theo nhà tuyển dụng, ứng viên và thời gian
//					// 6 fetch data theo nhà tuyển dụng và thời gian
//					// 5 fetch data theo ứng viên và thời gian
//					// 4 fetch data theo nhà tuyển dụng và ứng viên
//					// 3 fetch data theo thời gian
//					// 2 fetch data theo nhà tuyển dụng
//					// 1 fetch data theo ứng viên
//					switch(getFetchType()) {
//					case 7:
//					case 6:
//					case 5:
//					case 3:
//						SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//						LocalDate startDate = null;
//						LocalDate endDate = null;
//						try {
//							endDate = sdf.parse(ngayKT.toString()).toInstant()
//							 .atZone(ZoneId.systemDefault())
//							 .toLocalDate();
//							startDate = sdf.parse(ngayBD.toString()).toInstant()
//	                        .atZone(ZoneId.systemDefault())
//	                        .toLocalDate();
//						} catch (ParseException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//
//						if (startDate.isAfter(endDate)) {
//							JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc");
//							return;
//						}
//
//						fetchHopDong(tenNtd, tenUV, startDate, endDate);
//						break;
//					case 4:
//					case 2:
//					case 1:
//						fetchHopDong(tenNtd, tenUV);
//						break;
//					default:
//					}
//
//					loadDataTable();
//					loadDataTotal();
				}
				else if(obj.equals(btnReset)) {
					JOptionPane.showMessageDialog(rootPane, "Reset invoice");
//					recruiterBox.setSelectedIndex(0);
//					applicantBox.setSelectedIndex(0);
//					hopdong_dao.setListHopDong(hopdong_dao.getDSHopDong());
//					modelStartDate.setValue(null);
//					modelEndDate.setValue(null);
//
//					loadDataTable();
//					loadDataTotal();
				}
	}

	public void loadData() {
//		hopdong_dao.setListHopDong(hopdong_dao.getDSHopDong());
//		nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getDsNhaTuyenDung());
//		ungVienDao.setListUngVien(ungVienDao.getDSUngVien());
//
//		recruiterBox.addItem("Chọn nhà tuyển dụng");
//		for(NhaTuyenDung i: nhatuyendungDAO.getListNhatuyenDung()) {
//			recruiterBox.addItem(i.getTenNTD());
//		}
//
//		applicantBox.addItem("Chọn ứng viên");
//		for(UngVien i: ungVienDao.getListUngVien()) {
//			applicantBox.addItem(i.getTenUV());
//		}
	}

	public void loadDataTotal() {
//		DecimalFormat format = new DecimalFormat("#,### VNĐ");
//		double totalHopDong = 0;
//		for (HopDong hd : hopdong_dao.getListHopDong()) {
//			totalHopDong += hd.getPhiDichVu();
//		}
//		valueLabel.setText(format.format(totalHopDong));
//		numberLabel.setText(String.valueOf(hopdong_dao.getListHopDong().size()));
	}

	public void loadDataTable() {
//		modelTableInvoice.setRowCount(0);
//		for(HopDong i: hopdong_dao.getListHopDong()) {
//			UngVien uv = ungVienDao.getUngVien(i.getUngVien().getMaUV());
//			NhaTuyenDung ntd = nhatuyendungDAO.getNhaTuyenDungTheoMaTTD(i.getTinTuyenDung().getMaTTD());
//			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//			DecimalFormat formatLuong = new DecimalFormat("#,### VNĐ");
//			Object[] obj=new Object[] {
//					i.getMaHD(), uv.getTenUV(), uv.getSoDienThoai(), ntd.getTenNTD(), formatLuong.format(i.getPhiDichVu()), i.getThoiGian().format(formatters)
//			};
//			modelTableInvoice.addRow(obj);
//		}
	}

	public JPanel getPanel() {
		return this.invoicePanel;
	}
}
