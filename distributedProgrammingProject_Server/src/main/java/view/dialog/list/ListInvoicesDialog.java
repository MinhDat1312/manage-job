package view.dialog.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;

import view.table.TableActionEvent;
import view.table.editor.TableCellEditorDetail;
import view.table.renderer.TableCellRendererDetail;
import entity.Employee;
import view.button.Button;
import view.panel.GradientPanel;
import view.table.TableCellGradient;

public class ListInvoicesDialog extends JDialog implements ActionListener{

	GradientPanel searchPanel, listPanel, btnPanel;
	JLabel searchStatusLabel, searchRecruiterLabel;
	JComboBox searchStatusBox, searchRecuiterBox;
	Button btnSearch, btnReset, btnCancel;
	DefaultTableModel modelTableInvoice;
	JTable tableInvoice;
	JScrollPane scrollInvoice;

	ListInvoicesDialog son;

//	private EntityManager em = Persistence.createEntityManagerFactory("default")
//			.createEntityManager();
//	private NhanVienDAO nhanvienDAO;
//	private HoaDonDAO hoadonDAO;
//	private NhaTuyenDungDAO nhatuyendungDAO;
//	private TinTuyenDungDAO tintuyendungDAO;
//	private UngVienDAO ungvienDAO;
	private Employee employee;
	private LocalDate startDate, endDate;

	public ListInvoicesDialog(Frame parent, boolean modal, Employee employee,
                              LocalDate startDate, LocalDate endDate) {
		super(parent, modal);
		setTitle("Danh sách hợp đồng");
		setResizable(false);
		setSize(1000,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.son=this;
		this.employee=employee;
		this.startDate=startDate;
		this.endDate=endDate;
//		nhanvienDAO=new NhanVienDAO(em);
//		hoadonDAO =new HoaDonDAO(em, Invoice.class);
//		nhatuyendungDAO=new NhaTuyenDungDAO(em);
//		tintuyendungDAO=new TinTuyenDungDAO(em);
//		ungvienDAO=new UngVienDAO(em);

		initComponent();
		addActionListener();

		addTableActionEvent();

//		loadDataHoSo();
	}

	public void initComponent() {
//		Danh sách hợp đồng
		listPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		listPanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
		listPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hợp đồng","Tên nhà tuyển dụng","Tên ứng viên","Phí","Ngày lập","Xem chi tiết"};
		Object[][] data = {
			    {1, "FaceBook","Minh Đạt", "500","13-12-2024",null}
			};
		modelTableInvoice= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true
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
		JPanel resScroll=new JPanel();
		resScroll.setOpaque(false);
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.add(scrollInvoice);
		listPanel.add(resScroll);

//		Các nút chức năng
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		btnCancel=new Button("Hủy"); btnCancel.setFont(new Font("Segoe UI",0,16));
		btnCancel.setPreferredSize(new Dimension(100,30));
		btnCancel.setBackground(Color.RED);
		btnCancel.setForeground(Color.WHITE);
		btnPanel.add(btnCancel);

		add(listPanel, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);

	}

	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onViewHoSo(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCreateHoSo(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCreateTaiKhoan(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onViewTinTuyenDung(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCreateTinTuyenDung(int row) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onViewDetail(int row) {
				// TODO Auto-generated method stub
//				Invoice hd= hoadonDAO.findHoaDon(
//						Integer.parseInt(tableInvoice.getValueAt(row, 0).toString()));
//				new DetailInvoiceDialog(son, rootPaneCheckingEnabled, hd).setVisible(true);
//				new DetailApplicationInvoiceDialog(son, rootPaneCheckingEnabled, new Invoice() {
//					@Override
//					public double getFee() {
//						return 0;
//					}
//				}).setVisible(true);
			}
		};

		tableInvoice.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererDetail());
		tableInvoice.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorDetail(event));
	}

	public void addActionListener() {
		btnCancel.addActionListener(this);
	}

	public void loadDataHoSo() {
//		DecimalFormat df = new DecimalFormat("#,###");
//		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		modelTableInvoice.setRowCount(0);
//
//		for(HopDong i: hoadonDAO.getHopDongTheoNhanVien(nv.getMaNV(),ngaybatdau,ngayketthuc)) {
//			Object[] obj=new Object[] {
//					i.getMaHD(),
//					nhatuyendungDAO.getNhaTuyenDung(tintuyendungDAO.getTinTuyenDung(i.getTinTuyenDung().getMaTTD())
//							.getNhaTuyenDung().getMaNTD()).getTenNTD(),
//					ungvienDAO.getUngVien(i.getUngVien().getMaUV()).getTenUV(),
//					df.format(i.getPhiDichVu())+" VNĐ",
//					format.format(i.getThoiGian()), null
//			};
//			modelTableInvoice.addRow(obj);
//		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();

		if(obj.equals(btnCancel)) {
			this.dispose();
		}
	}


}
