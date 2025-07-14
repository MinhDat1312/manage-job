package view.dialog.list;

import view.table.TableActionEvent;
import entity.Recruiter;
import entity.Job;
import lombok.Getter;
import view.button.Button;
import view.dialog.idialog.IDialogListener;
import view.dialog.idialog.IListJobsDialog;
import view.panel.GradientPanel;
import view.table.editor.TableCellEditorDetail;
import view.table.TableCellGradient;
import view.table.renderer.TableCellRendererDetail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListJobsDialog extends JDialog
		implements FocusListener, ActionListener, IListJobsDialog {

	private Frame parent;
	private ListJobsDialog son;
	private IDialogListener<Job> listener;
	private Recruiter recruiter;

	GradientPanel searchPanel, listPanel, btnPanel;
	JLabel searchTitleLabel;
	@Getter
	JTextField searchTitleText;
	@Getter
	JComboBox searchStatusText;
	Button btnSearch, btnReset, btnCancel;
	DefaultTableModel modelTableJob;
	JTable tableJob;
	JScrollPane scrollJob;

	public ListJobsDialog(Frame parent, boolean modal, Recruiter recruiter) {
		super(parent, modal);
		setTitle("Danh sách tin tuyển dụng");
		setResizable(false);
		setSize(950,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.son=this;
		this.parent=parent;
		this.recruiter=recruiter;

		initComponent();
		addActionListener();
		addFocusListener();
		addTableActionEvent();


	}

	public void initComponent() {
//		Tìm kiếm tin tuyển dụng
		searchPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		searchPanel.setLayout(new BorderLayout(12, 0));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(13, 10, 0, 10));

		JPanel resSearch=new JPanel();
		resSearch.setOpaque(false);
		resSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
		resSearch.setBackground(Color.WHITE);

		searchTitleLabel=new JLabel("Tiêu đề:");
		searchTitleLabel.setFont(new Font("Segoe UI",1,16));
		searchTitleLabel.setForeground(Color.WHITE);
		searchTitleText=new JTextField(20);
		searchTitleText.setFont(new Font("Segoe UI",0,16));
		searchTitleText.setForeground(new Color(0,191,165));
		searchTitleText.setOpaque(false);

		String[] trangthais= {"Trạng thái", "Khả dụng", "Không khả dụng"};
		searchStatusText=new JComboBox(trangthais);
		searchStatusText.setFont(new Font("Segoe UI",0,16));
		searchStatusText.setForeground(Color.WHITE);
		searchStatusText.setBackground(new Color(89, 145, 144));
		searchStatusText.setPreferredSize(new Dimension(156,26));
		resSearch.add(searchTitleLabel); resSearch.add(searchTitleText);
		resSearch.add(searchStatusText);

		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 0));
		resBtnSearch.setBackground(Color.WHITE);
		btnSearch=new Button("Tìm kiếm"); btnSearch.setFont(new Font("Segoe UI",0,16));
		btnSearch.setPreferredSize(new Dimension(120,25));
		btnSearch.setBackground(new Color(0,102,102));
		btnSearch.setForeground(Color.WHITE);
		btnReset=new Button("Làm lại"); btnReset.setFont(new Font("Segoe UI",0,16));
		btnReset.setPreferredSize(new Dimension(120,25));
		btnReset.setBackground(Color.RED);
		btnReset.setForeground(Color.WHITE);
		resBtnSearch.add(btnSearch); resBtnSearch.add(btnReset);

		searchPanel.add(resSearch, BorderLayout.WEST);
		searchPanel.add(resBtnSearch, BorderLayout.EAST);

//		Danh sách tin tuyển dụng
		listPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		listPanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
		listPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã tin tuyển dụng","Tiêu đề","Mức lương","Trình độ", "Trạng thái","Hành động"};
		Object[][] data = {
			    {1, "Manual Tester", "10000","Cao đẳng", "Khả dụng",null},
			    {2, "Technical Project Manager", "15000","Đại học", "Không khả dụng",null}
			};
		modelTableJob= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true
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
		tableJob.setDefaultRenderer(Object.class, new TableCellGradient());

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
		JPanel resScroll=new JPanel();
		resScroll.setOpaque(false);
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollJob);
		listPanel.add(resScroll);

//		Các nút chức năng
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,20,0));
		btnPanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 10, 0));
		btnCancel=new Button("Hủy"); btnCancel.setFont(new Font("Segoe UI",0,16));
		btnCancel.setPreferredSize(new Dimension(100,30));
		btnCancel.setBackground(Color.RED);
		btnCancel.setForeground(Color.WHITE);
		btnPanel.add(btnCancel);

		add(searchPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}

	public void addActionListener() {
		btnSearch.addActionListener(this);
		btnReset.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	public void addFocusListener() {
		searchTitleText.addFocusListener(this);
		addPlaceHolder(searchTitleText);
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
				listener.onView(row);
//				new DetailJobDialog(son, rootPaneCheckingEnabled, new Job(), new Recruiter()).setVisible(true);
			}
		};

		tableJob.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererDetail());
		tableJob.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorDetail(event));
	}

	public void timkiem() {
//		modelTableJob.setRowCount(0);
//		String tieude=searchTitleText.getText();
//		boolean trangthai=searchStatusText.getSelectedItem().toString().equalsIgnoreCase("Khả dụng")?true:false;
//
//		if(tieude.equals("Nhập dữ liệu") || tieude.equals("")) {
//			for(TinTuyenDung i: tintuyendungDAO.getTinTuyenDungTheoNTD(ntd.getMaNTD()+"/"+String.valueOf(trangthai?1:0), 2)) {
//				Object[] obj=new Object[] {
//						i.getMaTTD(), i.getTieuDe(), i.getLuong(),
//						i.getTrinhDo().getValue(), i.isTrangThai()?"Khả dụng":"Không khả dụng",
//						null
//				};
//				modelTableJob.addRow(obj);
//			}
//		}
//		else {
//			for(TinTuyenDung i: tintuyendungDAO.
//					getTinTuyenDungTheoNTD(ntd.getMaNTD()+"/"+String.valueOf(trangthai?1:0)+"/"+tieude, 3)) {
//				Object[] obj=new Object[] {
//						i.getMaTTD(), i.getTieuDe(), i.getLuong(),
//						i.getTrinhDo().getValue(), i.isTrangThai()?"Khả dụng":"Không khả dụng",
//						null
//				};
//				modelTableJob.addRow(obj);
//			}
//		}
	}

//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(new Color(0,191,165));
		text.setText("Nhập dữ liệu");
	}

//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(new Color(0,191,165));
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(searchTitleText)) {
			if(searchTitleText.getText().equals("Nhập dữ liệu")) {
				searchTitleText.setText(null);
				searchTitleText.requestFocus();

				removePlaceHolder(searchTitleText);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(searchTitleText)) {
			if(searchTitleText.getText().isEmpty()) {
				addPlaceHolder(searchTitleText);
				searchTitleText.setText("Nhập dữ liệu");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnSearch)) {
			listener.onSearch(getSearchCriteria());
		}
		else if(obj.equals(btnReset)) {
			listener.onReset();
		}
		else if(obj.equals(btnCancel)) {
			close();
		}
	}

	@Override
	public void visible() {
		setVisible(true);
	}

	@Override
	public void close() {
		this.dispose();
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void setDialogListener(IDialogListener<Job> listener) {
		this.listener = listener;
	}

	@Override
	public JPanel getPanel() {
		return (JPanel) getContentPane();
	}

	@Override
	public void showData(List<Job> data, Object... objects) {
		modelTableJob.setRowCount(0);
		DecimalFormat df = new DecimalFormat("#,###");
		for(Job i: data) {
			Object[] obj=new Object[] {
					i.getId(), i.getTitle(), df.format(i.getSalary())+" VNĐ",
					i.getLevel().getValue(), i.isVisible()?"Khả dụng":"Không khả dụng",
					null
			};
			modelTableJob.addRow(obj);
		}
	}

	@Override
	public JTable getTable() {
		return tableJob;
	}

	@Override
	public Object getSearchCriteria() {
		return new Object[]{
				getSearchText(),
				searchStatusText.getSelectedItem().toString(),
		};
	}

	@Override
	public String getSearchText() {
		return searchTitleText.getText().trim();
	}

	@Override
	public int getSelectedRow() {
		return tableJob.getSelectedRow();
	}
}
