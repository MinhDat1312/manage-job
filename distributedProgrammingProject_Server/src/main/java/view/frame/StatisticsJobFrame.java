package view.frame;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.ExcelHelper;
import util.DateFormatter;
import view.table.TableActionEvent;
import view.table.editor.TableCellEditorDetail;
import view.table.renderer.TableCellRendererDetail;
import entity.Employee;
import entity.Job;
import view.button.Button;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;
import view.dialog.list.ListResumeAppliedDialog;

public class StatisticsJobFrame extends JFrame implements ActionListener, MouseListener {
	Employee employee;
	StatisticsJobFrame parent;

//	Component thống kê tin tuyển dụng
	JPanel menuPanel, searchPanel,jobPanel, centerPanelJob, listPanel, 
		listNorthPanel, listCenterPanel, totalPanel;
	JLabel titleJob, startDateLabel, endDateLabel, searchNameLabel,
	summaryValueLabel, valueLabel, summaryValueHireLabel, valueHireLabel, summaryValueDoneLabel, valueDoneLabel;
	Button btnSearch, btnReset, btnExcel;
	JTable tableJob;
	DefaultTableModel modelTableJob;
	JScrollPane scrollJob;
	UtilDateModel modelStartDate, modelEndDate;
	JDatePickerImpl startDate, endDate;
	JComboBox<Object> recruiterBox;
	Icon iconBtnSave;

//	private EntityManager em = Persistence.createEntityManagerFactory("default")
//			.createEntityManager();
//	private HoaDonDAO hoaDonDAO;
//	private NhaTuyenDungDAO nhatuyendungDAO;
//	private TinTuyenDungDAO tintuyendungDAO;

	public StatisticsJobFrame(Employee employee) {
		this.employee=employee;
		this.parent = this;

//		hoaDonDAO=new HoaDonDAO(em, Invoice.class);
//		nhatuyendungDAO=new NhaTuyenDungDAO(em);
//		tintuyendungDAO=new TinTuyenDungDAO(em);

//		Tạo component bên phải
		initComponent();

//		Thêm sự kiện
		addTableListener();
		addActionListener();
		addMouseListener();

//		loadData();
//		loadDataTable();
//		loadComboBox();
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
		jobPanel=new JPanel();
		jobPanel.setLayout(new BorderLayout(5,5));

//		Hiển thị Thống kê và danh sách tin tuyển dụng
		centerPanelJob=new JPanel();
		centerPanelJob.setLayout(new BorderLayout(5, 5));
		centerPanelJob.setBackground(new Color(89, 145, 144));

//		Thống kê tin tuyển dụng
		searchPanel=new GradientRoundPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));

		recruiterBox=new JComboBox<Object>();
		recruiterBox.setFont(new Font("Segoe UI",0,16));
		recruiterBox.setOpaque(false);
		recruiterBox.setPreferredSize(new Dimension(270,30));
		recruiterBox.addItem("Chọn nhà tuyển dụng");

		JPanel res=new JPanel();
		res.setOpaque(false);
		modelStartDate=new UtilDateModel();
		modelEndDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelStartDate, p);
		startDateLabel= createLabel("Ngày đăng tin:");
		startDate=new JDatePickerImpl(panelDate, new DateFormatter());
		startDate.setPreferredSize(new Dimension(130,25));
		panelDate=new JDatePanelImpl(modelEndDate, p);
		endDateLabel= createLabel("-");
		endDate=new JDatePickerImpl(panelDate, new DateFormatter());
		endDate.setPreferredSize(new Dimension(130,25));

		res.add(startDateLabel); res.add(startDate);
		res.add(endDateLabel); res.add(endDate);

		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		resBtnSearch.setPreferredSize(new Dimension(610, 30));
		resBtnSearch.setBackground(Color.WHITE);

		btnSearch= createButton("Thống kê", new Color(0,102,102), Color.WHITE);
		btnReset= createButton("Hủy", Color.RED, Color.WHITE);
		resBtnSearch.add(btnSearch);
		resBtnSearch.add(btnReset);

		searchPanel.add(recruiterBox, BorderLayout.WEST);
		searchPanel.add(res, BorderLayout.CENTER);
		searchPanel.add(resBtnSearch, BorderLayout.EAST);

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
		titleJob=new JLabel("Danh sách tin tuyển dụng");
		titleJob.setForeground(Color.WHITE);
		titleJob.setFont(new Font("Segoe UI",1,16));
		titleJob.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listNorthPanel.add(titleJob, BorderLayout.WEST);
		listNorthPanel.add(resBtnThem, BorderLayout.EAST);

		listCenterPanel=new GradientRoundPanel();
		listCenterPanel.setLayout(new BoxLayout(listCenterPanel, BoxLayout.PAGE_AXIS));
		listCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã", "Tiêu đề", "Nhà tuyển dụng","Hình thức làm việc",
				"Trình độ","Ngày đăng tin","Số lượng tuyển","Số lượng đã tuyển","Xem hồ sơ"};
		Object[][] data = {
			    {1, "Manual Tester", "Amazon", "Full-time", "Đại học",
			    	"13-12-2024",10,5,null},
		};
		modelTableJob= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false,
	                false, false, false, false, true
	            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableJob=new JTable(modelTableJob);
		tableJob.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableJob.setFont(new Font("Segoe UI",0,16));
		tableJob.setRowHeight(30);
		tableJob.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableJob.getColumnModel().getColumn(2).setPreferredWidth(150);
		tableJob.getColumnModel().getColumn(3).setPreferredWidth(150);
		tableJob.setDefaultRenderer(Object.class, new TableCellGradient());
		tableJob.setCursor(new Cursor(Cursor.HAND_CURSOR));

		tableJob.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter)tableJob.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();

		scrollJob=new JScrollPane(tableJob);
		scrollJob.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		scrollJob.setPreferredSize(new Dimension(1280, 480));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollJob);
		listCenterPanel.add(resScroll);

		// tổng số lượng
		totalPanel=new GradientRoundPanel();
		totalPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		totalPanel.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));

		JPanel resPanelSummary = new JPanel();
		resPanelSummary.setOpaque(false);
		resPanelSummary.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));

		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summaryValueLabel= createLabel("Tổng số lượng tin tuyển dụng:");
		summaryValueLabel.setFont(new Font("Segoe UI",1,16));
		valueLabel = createLabel("");
		valueLabel.setFont(new Font("Segoe UI",1,16));
		temp.add(summaryValueLabel);
		temp.add(valueLabel);
		temp.setOpaque(false);
		resPanelSummary.add(temp);

		JPanel temp1 = new JPanel();
		temp1.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summaryValueHireLabel= createLabel("Tổng số lượng cần tuyển:");
		summaryValueHireLabel.setFont(new Font("Segoe UI",1,16));
		valueHireLabel = createLabel("");
		valueHireLabel.setFont(new Font("Segoe UI",1,16));
		temp1.add(summaryValueHireLabel);
		temp1.add(valueHireLabel);
		temp1.setOpaque(false);
		resPanelSummary.add(temp1);

		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summaryValueDoneLabel= createLabel("Tổng số lượng đã tuyển:");
		summaryValueDoneLabel.setFont(new Font("Segoe UI",1,16));
		valueDoneLabel = createLabel("");
		valueDoneLabel.setFont(new Font("Segoe UI",1,16));
		temp2.add(summaryValueDoneLabel);
		temp2.add(valueDoneLabel);
		temp2.setOpaque(false);
		resPanelSummary.add(temp2);

		totalPanel.add(resPanelSummary, BorderLayout.WEST);

		listPanel.add(listNorthPanel, BorderLayout.NORTH);
		listPanel.add(listCenterPanel, BorderLayout.CENTER);

		centerPanelJob.add(searchPanel, BorderLayout.NORTH);
		centerPanelJob.add(listPanel, BorderLayout.CENTER);
		centerPanelJob.add(totalPanel, BorderLayout.SOUTH);

		jobPanel.add(centerPanelJob, BorderLayout.CENTER);
	}

	public void loadData() {
//		tintuyendung_DAO.setListTinTuyenDung(tintuyendung_DAO.getDsTinTuyenDung());
//		nhatuyendung_DAO.setListNhatuyenDung(nhatuyendung_DAO.getDsNhaTuyenDung());
//		hopDong_DAO.setListHopDong(hopDong_DAO.getDSHopDong());
	}

	public void loadDataTable() {
//		modelTableJob.setRowCount(0);
//		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		int totalHire=0;
//		int totalDone=0;
//
//		for(TinTuyenDung i: tintuyendung_DAO.getListTinTuyenDung()) {
//			totalHire+=i.getSoLuong();
//			totalDone+=hopDong_DAO.getSoLuongHopDongTheoTTD(i.getMaTTD());
//
//			Object[] row=new Object[] {
//				i.getMaTTD(), i.getTieuDe(), nhatuyendung_DAO.getNhaTuyenDung(i.getNhaTuyenDung().getMaNTD()).getTenNTD(),
//				i.getHinhThuc().getValue(), i.getTrinhDo().getValue(), format.format(i.getNgayDangTin()),
//				i.getSoLuong()+hopDong_DAO.getSoLuongHopDongTheoTTD(i.getMaTTD()),
//				hopDong_DAO.getSoLuongHopDongTheoTTD(i.getMaTTD()), null
//			};
//			modelTableJob.addRow(row);
//		}
//
//		valueLabel.setText(String.valueOf(tintuyendung_DAO.getListTinTuyenDung().size()));
//		valueHireLabel.setText(String.valueOf(totalHire));
//		valueDoneLabel.setText(String.valueOf(totalDone));
	}

	public void loadComboBox() {
//		nhatuyendung_DAO.setListNhatuyenDung(nhatuyendung_DAO.getDsNhaTuyenDung());
//		for (NhaTuyenDung ntd: nhatuyendung_DAO.getListNhatuyenDung()) {
//			recruiterBox.addItem(ntd.getTenNTD());
//		}
	}

	public void addActionListener() {
		btnExcel.addActionListener(this);
		btnSearch.addActionListener(this);
		btnReset.addActionListener(this);
	}

	public void addMouseListener() {
		tableJob.addMouseListener(this);
	}

	public void addTableListener() {
		TableActionEvent event=new TableActionEvent() {

			@Override
			public void onViewTinTuyenDung(int row) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onViewHoSo(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onViewDetail(int row) {
				// TODO Auto-generated method stub
//				String maTTD=tableJob.getValueAt(row, 0).toString();
//				Job ttd=tintuyendungDAO.findTinTuyenDung(Integer.parseInt(maTTD));
//
//				new ListResumeAppliedDialog(parent, rootPaneCheckingEnabled, ttd).setVisible(true);
				new ListResumeAppliedDialog(parent, rootPaneCheckingEnabled, new Job()).setVisible(true);;
			}

			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCreateTinTuyenDung(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCreateTaiKhoan(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCreateHoSo(int row) {
				// TODO Auto-generated method stub

			}
		};

		tableJob.getColumnModel().getColumn(8).setCellRenderer(new TableCellRendererDetail());
		tableJob.getColumnModel().getColumn(8).setCellEditor(new TableCellEditorDetail(event));
	}

	public int getFetchType() {
		String ntd = recruiterBox.getSelectedItem().toString();
		Object ngayBD = startDate.getModel().getValue();
		Object ngayKT = endDate.getModel().getValue();

		if (!ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && ngayBD != null && ngayKT != null) {
			return 3;
		}

		if (!ntd.equalsIgnoreCase("Chọn nhà tuyển dụng")) {
			return 2;
		}

		if (ngayBD != null && ngayKT != null) {
			return 1;
		}

		return 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnExcel)) {
			ExcelHelper excel = new ExcelHelper();
			excel.exportData(this, tableJob, 1);
		}
		else if(obj.equals(btnSearch)) {
			JOptionPane.showMessageDialog(rootPane, "Search job");
//			String tenNtd = recruiterBox.getSelectedItem().toString();
//			Object ngayBD = startDate.getModel().getValue();
//			Object ngayKT = endDate.getModel().getValue();
//
//			if (tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && ngayBD == null && ngayKT == null) return;
//
//			if ((ngayBD == null && ngayKT != null) ||
//				(ngayBD != null && ngayKT == null)) {
//				JOptionPane.showMessageDialog(this, "Phải chọn cả ngày bắt đầu và ngày kết thúc");
//				return;
//			}
//
//			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//			LocalDate startDate = null;
//			LocalDate endDate = null;
//
//			if (ngayBD != null && ngayKT != null) {
//				try {
//					endDate = sdf.parse(ngayKT.toString()).toInstant()
//					 .atZone(ZoneId.systemDefault())
//					 .toLocalDate();
//					startDate = sdf.parse(ngayBD.toString()).toInstant()
//	                .atZone(ZoneId.systemDefault())
//	                .toLocalDate();
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				if (startDate.isAfter(endDate)) {
//					JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc");
//					return;
//				}
//			}

			// 3 thống kê theo nhà tuyển dụng và thời gian
			// 2 thống kê theo nhà tuyển dụng
			// 1 thống kê theo thời gian
//			modelTableJob.setRowCount(0);
//			switch(getFetchType()) {
//			case 3:
//				tintuyendung_DAO.setListTinTuyenDung(tintuyendung_DAO.thongKeTheoNTD(tenNtd, startDate, endDate));
//				loadDataTable();
//				break;
//			case 2:
//				tintuyendung_DAO.setListTinTuyenDung(tintuyendung_DAO.thongKeTheoNTD(tenNtd));
//				loadDataTable();
//				break;
//			case 1:
//				tintuyendung_DAO.setListTinTuyenDung(tintuyendung_DAO.thongKeTheoNTD(startDate, endDate));
//				loadDataTable();
//				break;
//			default:
//				break;
//			}

		}
		else if(obj.equals(btnReset)) {
			JOptionPane.showMessageDialog(rootPane, "Reset job");
//			recruiterBox.setSelectedIndex(0);
//			modelStartDate.setValue(null);
//			modelEndDate.setValue(null);
//			loadData();
//			loadDataTable();
		}
	}

	public JPanel getPanel() {
		return this.jobPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
