package view.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
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
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateFormatter;
import util.ExcelHelper;
import view.table.TableActionEvent;
import view.table.editor.TableCellEditorDetail;
import view.table.renderer.TableCellRendererDetail;
import entity.Employee;
import entity.constant.Gender;
import view.button.Button;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;
import view.dialog.list.ListInvoicesDialog;

public class StatisticsEmployeeFrame extends JFrame implements ActionListener, MouseListener{
	Employee employee;
	StatisticsEmployeeFrame parent;

//	Component thống kê nhân viên
	JPanel  employeePanel, centerPanelEmployee, northPanelStatistics;
	JLabel titleEmployee, startDateLabel, endDateLabel,
		numberLabel, summaryNumberLabel, valueLabel, summaryValueLabel;
	JComboBox<String> genderBox, employeeBox;
	Button btnSearch, btnReset, btnExcel, btnCancel;
	JTable tableEmployee;
	DefaultTableModel modelTableEmployee;
	JScrollPane scrollEmployee;
	UtilDateModel modelStartDate, modelEndDate;
	JDatePickerImpl startDate, endDate;
	Icon iconBtnSave;

	GradientRoundPanel totalPanel, menuPanel, searchPanel,
		listPanel, listNorthPanel, listCenterPanel;

//	private EntityManager em = Persistence.createEntityManagerFactory("default")
//			.createEntityManager();
//	private NhanVienDAO nhanvienDAO;
//	private HoaDonDAO hoadonDAO;

	public StatisticsEmployeeFrame(Employee employee) {
		this.employee = employee;
		this.parent = this;

//		Tạo component bên phải
		initComponent();

//		Thêm sự kiện
		addActionListener();
		addMousListener();
		addTableListener();

//		nhanvienDAO=new NhanVienDAO(em);
//		hoadonDAO=new HoaDonDAO(em, Invoice.class);
//
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
		employeePanel=new JPanel();
		employeePanel.setLayout(new BorderLayout(5,5));

//		Hiển thị tìm kiếm và thống kê nhân viên
		centerPanelEmployee=new JPanel();
		centerPanelEmployee.setLayout(new BorderLayout(5,5));
		centerPanelEmployee.setBackground(new Color(89, 145, 144));

//		Tìm kiếm nhân viên
		searchPanel=new GradientRoundPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));

		employeeBox=new JComboBox<String>();
		employeeBox.setFont(new Font("Segoe UI",0,16));
		employeeBox.addItem("Chọn nhân viên");
		genderBox=new JComboBox<String>();
		genderBox.setFont(new Font("Segoe UI",0,16));
		genderBox.addItem("Chọn giới tính");
		for(Gender g: Gender.class.getEnumConstants()) {
			genderBox.addItem(g.getValue());
		}

		modelStartDate=new UtilDateModel();
		modelEndDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelStartDate, p);

		startDateLabel= createLabel("Ngày lập hợp đồng:");
		startDate=new JDatePickerImpl(panelDate, new DateFormatter());
		startDate.setPreferredSize(new Dimension(150,25));

		panelDate=new JDatePanelImpl(modelEndDate, p);
		endDateLabel= createLabel("-");
		endDate=new JDatePickerImpl(panelDate, new DateFormatter());
		endDate.setPreferredSize(new Dimension(150,25));

		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setPreferredSize(new Dimension(433, 35));
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		btnSearch= createButton("Thống kê", new Color(0,102,102), Color.WHITE);
		btnReset= createButton("Hủy", Color.RED, Color.WHITE);
		resBtnSearch.add(btnSearch);
		resBtnSearch.add(btnReset);

		searchPanel.add(employeeBox);
		searchPanel.add(genderBox);
		searchPanel.add(startDateLabel); searchPanel.add(startDate);
		searchPanel.add(endDateLabel); searchPanel.add(endDate);
		searchPanel.add(resBtnSearch);

//		Danh sách ứng viên
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
		titleEmployee=new JLabel("Danh sách nhân viên");
		titleEmployee.setForeground(Color.WHITE);
		titleEmployee.setFont(new Font("Segoe UI",1,16));
		titleEmployee.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listNorthPanel.add(titleEmployee, BorderLayout.WEST);
		listNorthPanel.add(resBtnThem, BorderLayout.EAST);

		listCenterPanel=new GradientRoundPanel();
		listCenterPanel.setLayout(new BoxLayout(listCenterPanel, BoxLayout.PAGE_AXIS));
		listCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã nhân viên","Tên nhân viên","Số điện thoại","Giới tính", "Ngày sinh",
				"Số hợp đồng", "Tổng giá trị hợp đồng","Xem hợp đồng"};
		Object[][] data = {
			    {1, "Nguyễn Thắng Minh Đạt", "0123456789", "Nam",
			    	"13-12-2003","10","1000000",null},
			};
		modelTableEmployee= new DefaultTableModel(data, colName){
		boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

        @Override
        public boolean isCellEditable(int row, int column) {
           return canEdit[column];
        }};
		tableEmployee=new JTable(modelTableEmployee);
		tableEmployee.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableEmployee.setFont(new Font("Segoe UI",0,16));
		tableEmployee.setRowHeight(30);
		tableEmployee.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableEmployee.getColumnModel().getColumn(3).setPreferredWidth(150);
		tableEmployee.setDefaultRenderer(Object.class, new TableCellGradient());
		tableEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));

		tableEmployee.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter)tableEmployee.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();

		scrollEmployee=new JScrollPane(tableEmployee);
		scrollEmployee.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		scrollEmployee.setPreferredSize(new Dimension(1280, 480));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollEmployee);
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
		summaryNumberLabel= createLabel("Tổng số lượng nhân viên:");
		summaryNumberLabel.setFont(new Font("Segoe UI",1,16));
		numberLabel = createLabel("");
		numberLabel.setFont(new Font("Segoe UI",1,16));
		temp1.add(summaryNumberLabel);
		temp1.add(numberLabel);
		temp1.setOpaque(false);
		resPanelSummary.add(temp1);

		totalPanel.add(resPanelSummary, BorderLayout.WEST);

		centerPanelEmployee.add(searchPanel, BorderLayout.NORTH);
		centerPanelEmployee.add(listPanel, BorderLayout.CENTER);
		centerPanelEmployee.add(totalPanel, BorderLayout.SOUTH);

		employeePanel.add(centerPanelEmployee, BorderLayout.CENTER);
	}

//	Load dữ liệu lên bảng
	public void loadDataTable() {
//		DecimalFormat df = new DecimalFormat("#,###");
//		double totalValue = 0;
//		int countTotal = 0;
//		modelTableEmployee.setRowCount(0);
//		for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien()) {
//			modelTableEmployee.addRow(new Object[] {
//					i[0], i[1], i[2], i[3], i[4],
//					i[5], df.format(i[6])+" VNĐ",null
//			});
//
//			countTotal++;
//			totalValue += Double.valueOf(i[6].toString());
//		}
//
//		valueLabel.setText(df.format(totalValue)+" VNĐ");
//		numberLabel.setText(String.valueOf(countTotal));
	}

	public void loadComboBox() {
//		nhanvienDAO.setListNhanVien(nhanvienDAO.getDSNhanVien());
//		for(NhanVien nv : nhanvienDAO.getListNhanVien()) {
//			employeeBox.addItem(nv.getTenNV());
//		}
	}

	public void addActionListener() {
		btnExcel.addActionListener(this);
		btnSearch.addActionListener(this);
		btnReset.addActionListener(this);
	}

	public void addMousListener() {
		tableEmployee.addMouseListener(this);
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
//				Employee nv=nhanvienDAO.findNhanVien(
//						Integer.parseInt(tableEmployee.getValueAt(row, 0).toString()));
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//
//				new ListInvoicesDialog(parent, rootPaneCheckingEnabled, nv,
//					modelStartDate.getValue()!=null?LocalDate.parse(format.format(modelStartDate.getValue())):null,
//					modelEndDate.getValue()!=null?LocalDate.parse(format.format(modelEndDate.getValue())):null).setVisible(true);;
				new ListInvoicesDialog(parent, rootPaneCheckingEnabled, new Employee(),
						modelStartDate.getValue()!=null?LocalDate.parse(format.format(modelStartDate.getValue())):null,
						modelEndDate.getValue()!=null?LocalDate.parse(format.format(modelEndDate.getValue())):null).setVisible(true);;
			
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
		tableEmployee.getColumnModel().getColumn(7).setCellRenderer(new TableCellRendererDetail());
		tableEmployee.getColumnModel().getColumn(7).setCellEditor(new TableCellEditorDetail(event));
	}

	public int getFetchType() {
		Object ngayBD = startDate.getModel().getValue();
		Object ngayKT = endDate.getModel().getValue();
		String gioiTinh = genderBox.getSelectedItem().toString();
		String nhanVien = employeeBox.getSelectedItem().toString();

		if (!nhanVien.equalsIgnoreCase("Chọn nhân viên") && !gioiTinh.equalsIgnoreCase("Chọn giới tính") && (ngayBD != null && ngayKT != null)) {
			return 7;
		}

		if (!nhanVien.equalsIgnoreCase("Chọn nhân viên") && !gioiTinh.equalsIgnoreCase("Chọn giới tính")) {
			return 6;
		}

		if (!nhanVien.equalsIgnoreCase("Chọn nhân viên") && (ngayBD != null && ngayKT != null)) {
			return 5;
		}

		if (!gioiTinh.equalsIgnoreCase("Chọn giới tính") && (ngayBD != null && ngayKT != null)) {
			return 4;
		}

		if (ngayBD != null && ngayKT != null) {
			return 3;
		}

		if (!gioiTinh.equalsIgnoreCase("Chọn giới tính")) {
			return 2;
		}

		if (!gioiTinh.equalsIgnoreCase("Chọn nhân viên")) {
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
			excel.exportData(this, tableEmployee, 0);
		}
		else if(obj.equals(btnSearch)) {
			JOptionPane.showMessageDialog(rootPane, "Search employee");
//			DecimalFormat df = new DecimalFormat("#,###");
//			Object ngayBD = startDate.getModel().getValue();
//			Object ngayKT = endDate.getModel().getValue();
//			String gioiTinh = genderBox.getSelectedItem().toString();
//			String nhanVien = employeeBox.getSelectedItem().toString();
//
//			if (gioiTinh.equalsIgnoreCase("Chọn giới tính") && nhanVien.equalsIgnoreCase("Chọn nhân viên") && (ngayBD == null && ngayKT == null )) {
//				return;
//			}
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
//
//			double totalValue = 0;
//			int countTotal = 0;
//			modelTableEmployee.setRowCount(0);

			// 7 thống kê theo nhân viên, giới tính và thời gian
			// 6 thống kê theo nhân viên và giới tính
			// 5 thống kê theo nhân viên và thời gian
			// 4 thống kê theo giới tính và thời gian
			// 3 thống kê theo thời gian
			// 2 thống kê theo giới tính
			// 1 thống kê theo nhân viên
//			switch(getFetchType()) {
//			case 7:
//				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(nhanVien, gioiTinh, startDate, endDate)) {
//					modelTableEmployee.addRow(new Object[] {
//							i[0], i[1], i[2], i[3], i[4],
//							i[5], df.format(i[6])+" VNĐ"
//					});
//					countTotal++;
//					totalValue += Double.valueOf(i[6].toString());
//				}
//				break;
//			case 6:
//				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(nhanVien, gioiTinh)) {
//					modelTableEmployee.addRow(new Object[] {
//							i[0], i[1], i[2], i[3], i[4],
//							i[5], df.format(i[6])+" VNĐ"
//					});
//					countTotal++;
//					totalValue += Double.valueOf(i[6].toString());
//				}
//				break;
//			case 5:
//				for(Object[] i: hopdongDAO.thongKeHopDongTheoTenNhanVien(nhanVien, startDate, endDate)) {
//					modelTableEmployee.addRow(new Object[] {
//							i[0], i[1], i[2], i[3], i[4],
//							i[5], df.format(i[6])+" VNĐ"
//					});
//					countTotal++;
//					totalValue += Double.valueOf(i[6].toString());
//				}
//				break;
//			case 4:
//				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(gioiTinh, startDate, endDate)) {
//					modelTableEmployee.addRow(new Object[] {
//							i[0], i[1], i[2], i[3], i[4],
//							i[5], df.format(i[6])+" VNĐ"
//					});
//					countTotal++;
//					totalValue += Double.valueOf(i[6].toString());
//				}
//				break;
//			case 3:
//				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(startDate, endDate)) {
//					modelTableEmployee.addRow(new Object[] {
//							i[0], i[1], i[2], i[3], i[4],
//							i[5], df.format(i[6])+" VNĐ"
//					});
//					countTotal++;
//					totalValue += Double.valueOf(i[6].toString());
//				}
//				break;
//			case 2:
//				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(gioiTinh)) {
//					modelTableEmployee.addRow(new Object[] {
//							i[0], i[1], i[2], i[3], i[4],
//							i[5], df.format(i[6])+" VNĐ"
//					});
//					countTotal++;
//					totalValue += Double.valueOf(i[6].toString());
//				}
//				break;
//			case 1:
//				for(Object[] i: hopdongDAO.thongKeHopDongTheoTenNhanVien(nhanVien)) {
//					modelTableEmployee.addRow(new Object[] {
//							i[0], i[1], i[2], i[3], i[4],
//							i[5], df.format(i[6])+" VNĐ"
//					});
//					countTotal++;
//					totalValue += Double.valueOf(i[6].toString());
//				}
//				break;
//			default:
//				return;
//			}
//			valueLabel.setText(df.format(totalValue)+" VNĐ");
//			numberLabel.setText(String.valueOf(countTotal));

		}
		else if(obj.equals(btnReset)) {
			JOptionPane.showMessageDialog(rootPane, "Reset employee");
//			nhanvienDAO.setListNhanVien((nhanvienDAO.getDSNhanVien()));
//			modelStartDate.setValue(null);
//			modelEndDate.setValue(null);
//			genderBox.setSelectedIndex(0);
//			employeeBox.setSelectedIndex(0);
//			loadDataTable();
		}
	}

	public JPanel getPanel() {
		return this.employeePanel;
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
