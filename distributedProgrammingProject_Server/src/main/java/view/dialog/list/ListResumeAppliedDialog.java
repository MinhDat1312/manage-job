package view.dialog.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import view.dialog.detail.DetailResumeDialog;
import view.table.editor.TableCellEditorDetail;
import view.table.renderer.TableCellRendererDetail;
import entity.Resume;
import entity.Job;
import entity.Applicant;
import view.button.Button;
import view.panel.GradientPanel;
import view.table.TableCellGradient;

public class ListResumeAppliedDialog extends JDialog implements ActionListener{

	GradientPanel searchPanel, listPanel, btnPanel;
	JLabel searchStatusLabel, searchRecruiterLabel;
	JComboBox searchStatusBox, searchRecruiterBox;
	Button btnSearch, btnReset, btnCancel;
	DefaultTableModel modelTableResume;
	JTable tableResume;
	JScrollPane scrollResume;

	ListResumeAppliedDialog son;

	private Job job;

	public ListResumeAppliedDialog(Frame parent, boolean modal, Job job) {
		super(parent, modal);
		setTitle("Danh sách hồ sơ");
		setResizable(false);
		setSize(1000,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.son=this;
		this.job=job;
//		hosoDAO=new HoSoDAO(em);
//		ungvienDAO=new UngVienDAO(em);
//		tintuyendungDAO=new TinTuyenDungDAO(em);

		initComponent();
		addActionListener();

		addTableActionEvent();

//		loadDataHoSo();
	}

	public void initComponent() {
//		Danh sách hồ sơ
		listPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		listPanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
		listPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hồ sơ","Trạng thái","Tên ứng viên","Hành động"};
		Object[][] data = {
			    {1, "Chờ","Minh Đạt", null}
			};
		modelTableResume= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, true
	            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableResume=new JTable(modelTableResume);
		tableResume.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableResume.setFont(new Font("Segoe UI",0,16));
		tableResume.setRowHeight(30);
		tableResume.setDefaultRenderer(Object.class, new TableCellGradient());

		tableResume.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter)tableResume.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();

		scrollResume=new JScrollPane(tableResume);
		scrollResume.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		JPanel resScroll=new JPanel();
		resScroll.setOpaque(false);
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.add(scrollResume);
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
//				Resume hs=hosoDAO.findHoSo(
//						Integer.parseInt(tableResume.getValueAt(row, 0).toString()));
//				Applicant uv=ungvienDAO.findUngVien(hs.getApplicant().getId());
//				new DetailResumeDialog(son, rootPaneCheckingEnabled, hs, uv).setVisible(true);
				new DetailResumeDialog(son, rootPaneCheckingEnabled,
						new Resume(), new Applicant()).setVisible(true);
			}
		};

		tableResume.getColumnModel().getColumn(3).setCellRenderer(new TableCellRendererDetail());
		tableResume.getColumnModel().getColumn(3).setCellEditor(new TableCellEditorDetail(event));
	}

	public void addActionListener() {
		btnCancel.addActionListener(this);
	}

	public void loadDataHoSo() {
//		modelTableResume.setRowCount(0);
//		for(HoSo i: hosoDAO.getHoSoTheoTinTD(ttd.getMaTTD())) {
//			Object[] obj=new Object[] {
//					i.getMaHS(), i.getTrangThai().getValue(),
//					ungvienDAO.getUngVien(i.getUngVien().getMaUV()).getTenUV(),
//					null
//			};
//			modelTableResume.addRow(obj);
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
