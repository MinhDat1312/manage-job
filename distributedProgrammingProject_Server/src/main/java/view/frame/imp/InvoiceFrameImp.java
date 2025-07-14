package view.frame.imp;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entity.*;
import lombok.Getter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateFormatter;
import view.table.TableActionEvent;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.iframe.IInvoiceFrame;
import view.table.editor.TableCellEditorDetail;
import view.table.renderer.TableCellRendererDetail;
import view.button.Button;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;

public class InvoiceFrameImp extends JFrame
		implements ActionListener, MouseListener, FocusListener, IInvoiceFrame {

	private Employee employee;
	private IFrameListener<Invoice> frameListener;
	private InvoiceFrameImp parent;

//	Component hợp đồng
	JPanel leftPanel,menuPanel,
		searchJobPanel, centerPanelSearchJob, northPanelSearchJob;
	JLabel titleJob, titleResume, titleInvoice, recruiterLabel, applicantLabel,
		startDateLabel, endDateLabel;
	JTable tableRecruiter, tableApplicant, tableInvoice;
	DefaultTableModel modelTableRecruiter, modelTableApplicant, modelTableInvoice;
	JScrollPane scrollJob, scrollResume, scrollInvoice;
	JComboBox recruiterBox, applicantBox;
	@Getter
	UtilDateModel modelStartDate, modelEndDate;
	JDatePickerImpl startDateText, endDateText;
	Button btnReset, btnSearch;

	GradientRoundPanel listJobsPanel, listJobsNorthPanel, listJobsCenterPanel,
				listInvoicesPanel, listInvoicesNorthPanel, listInvoicesCenterPanel,
				listResumesPanel, listResumesNorthPanel, listResumesCenterPanel;


	public InvoiceFrameImp(Employee employee) {
		this.employee=employee;
		this.parent=this;

//		Tạo component bên phải
		initComponent();
//		Thêm update và delete vào table
		addTableHopDongActionEvent();
//		Thêm sự kiện
		addActionListener();
		addMouseListener();
	}

	public void initComponent() {
		searchJobPanel=new JPanel();
		searchJobPanel.setLayout(new BorderLayout(5,5));
		searchJobPanel.setBackground(new Color(89, 145, 144));

		northPanelSearchJob=new JPanel();
		northPanelSearchJob.setLayout(new FlowLayout(FlowLayout.RIGHT,17,0));
		northPanelSearchJob.setBackground(new Color(89, 145, 144));
		btnReset=new Button("Hủy"); btnReset.setFont(new Font("Segoe UI",0,16));
		btnReset.setPreferredSize(new Dimension(120,25));
		btnReset.setBackground(Color.RED);
		btnReset.setForeground(Color.WHITE);
		northPanelSearchJob.add(btnReset);

//		Hiển thị danh sách tin tuyển dụng, danh sách hồ sơ ứng viên và hợp đồng
		centerPanelSearchJob=new JPanel();
		centerPanelSearchJob.setLayout(new BorderLayout(10, 10));
		centerPanelSearchJob.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelSearchJob.setBackground(new Color(89, 145, 144));

		JPanel panelCenter=new JPanel();
		panelCenter.setLayout(new BorderLayout());
		panelCenter.setBackground(new Color(89, 145, 144));
//		Danh sách nhà tuyển dụng
		listJobsPanel=new GradientRoundPanel();
		listJobsPanel.setBackground(Color.WHITE);
		listJobsPanel.setLayout(new BorderLayout(10, 10));
		listJobsPanel.setPreferredSize(new Dimension(660,700));

		listJobsNorthPanel=new GradientRoundPanel();
		listJobsNorthPanel.setLayout(new BorderLayout(10,10));
		listJobsNorthPanel.setBackground(Color.WHITE);
		titleJob=new JLabel("Danh sách nhà tuyển dụng");
		titleJob.setFont(new Font("Segoe UI",1,16));
		titleJob.setForeground(Color.WHITE);
		titleJob.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listJobsNorthPanel.add(titleJob, BorderLayout.WEST);

		listJobsCenterPanel=new GradientRoundPanel();
		listJobsCenterPanel.setLayout(new BoxLayout(listJobsCenterPanel, BoxLayout.PAGE_AXIS));
		listJobsCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã","Tên nhà tuyển dụng","Email"};
		Object[][] data = {
			    {"1","Technical Project Manager","Đại học"},
			    {"2","Manual Tester","Cao đẳng", }
			};
		modelTableRecruiter= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false,false, false
	            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableRecruiter=createTable(modelTableRecruiter);
		tableRecruiter.setCursor(new Cursor(Cursor.HAND_CURSOR));
		scrollJob=new JScrollPane(tableRecruiter);
		scrollJob.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScrollTinTuyenDung=new GradientRoundPanel();
		resScrollTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollTinTuyenDung.setLayout(new BoxLayout(resScrollTinTuyenDung, BoxLayout.PAGE_AXIS));
		resScrollTinTuyenDung.setBackground(Color.WHITE);
		resScrollTinTuyenDung.add(scrollJob);
		listJobsCenterPanel.add(resScrollTinTuyenDung);

		listJobsPanel.add(listJobsNorthPanel, BorderLayout.NORTH);
		listJobsPanel.add(listJobsCenterPanel, BorderLayout.CENTER);

//		Danh sách hợp đồng
		listInvoicesPanel=new GradientRoundPanel();
		listInvoicesPanel.setBackground(Color.WHITE);
		listInvoicesPanel.setLayout(new BorderLayout(10, 10));
		listInvoicesPanel.setPreferredSize(new Dimension(getWidth(),400));

		listInvoicesNorthPanel=new GradientRoundPanel();
		listInvoicesNorthPanel.setLayout(new BorderLayout(10,10));
		listInvoicesNorthPanel.setBackground(Color.WHITE);

		JPanel resHD=new JPanel();
		resHD.setOpaque(false);
		resHD.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));

		startDateLabel=new JLabel("Bắt đầu từ");
		startDateLabel.setFont(new Font("Segoe UI",1,16));
		startDateLabel.setForeground(Color.WHITE);
		modelStartDate = new UtilDateModel();
		modelStartDate.setSelected(true);
		modelStartDate.setValue(null);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		JDatePanelImpl panelBatDau=new JDatePanelImpl(modelStartDate, p);
		startDateText = new JDatePickerImpl(panelBatDau,new DateFormatter());
		startDateText.setPreferredSize(new Dimension(200, 25));

		endDateLabel=new JLabel("Kết thúc vào");
		endDateLabel.setFont(new Font("Segoe UI",1,16));
		endDateLabel.setForeground(Color.WHITE);
		modelEndDate = new UtilDateModel();
		modelEndDate.setSelected(true);
		modelEndDate.setValue(null);
		Properties q=new Properties();
		q.put("text.day", "Day"); q.put("text.month", "Month"); q.put("text.year","Year");
		JDatePanelImpl panelKetThuc=new JDatePanelImpl(modelEndDate, q);
		endDateText = new JDatePickerImpl(panelKetThuc,new DateFormatter());
		endDateText.setPreferredSize(new Dimension(200, 25));

		btnSearch=new Button("Tìm kiếm"); btnSearch.setFont(new Font("Segoe UI",0,16));
		btnSearch.setPreferredSize(new Dimension(120,25));
		btnSearch.setBackground(new Color(0,102,102));
		btnSearch.setForeground(Color.WHITE);

		resHD.add(startDateLabel); resHD.add(startDateText);
		resHD.add(endDateLabel); resHD.add(endDateText);
		resHD.add(btnSearch);

		titleInvoice=new JLabel("Danh sách hóa đơn");
		titleInvoice.setFont(new Font("Segoe UI",1,16));
		titleInvoice.setForeground(Color.WHITE);
		titleInvoice.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listInvoicesNorthPanel.add(titleInvoice, BorderLayout.WEST);
		listInvoicesNorthPanel.add(resHD, BorderLayout.EAST);
//
		listInvoicesCenterPanel=new GradientRoundPanel();
		listInvoicesCenterPanel.setLayout(new BoxLayout(listInvoicesCenterPanel, BoxLayout.PAGE_AXIS));
		String[] column= {"Mã","Tên nhà tuyển dụng","Tên ứng viên", "Phí", "Ngày lập", "Hành động"};
		Object[][] dt = {
			    {"1","Technical Project Manager","Minh Đạt", "10000", "13/12/2022", null},
			    {"2","Manual Tester", "Thắng Đạt", "2000", "13/01/2024", null}
			};
		modelTableInvoice= new DefaultTableModel(dt, column){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true
	            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableInvoice=createTable(modelTableInvoice);
		tableInvoice.setCursor(new Cursor(Cursor.HAND_CURSOR));
		scrollInvoice=new JScrollPane(tableInvoice);
		scrollInvoice.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScrollHopDong=new GradientRoundPanel();
		resScrollHopDong.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollHopDong.setLayout(new BoxLayout(resScrollHopDong, BoxLayout.PAGE_AXIS));
		resScrollHopDong.setBackground(Color.WHITE);
		resScrollHopDong.add(scrollInvoice);
		listInvoicesCenterPanel.add(resScrollHopDong);

		listInvoicesPanel.add(listInvoicesNorthPanel, BorderLayout.NORTH);
		listInvoicesPanel.add(listInvoicesCenterPanel, BorderLayout.CENTER);

//		Danh sách ứng viên
		listResumesPanel=new GradientRoundPanel();
		listResumesPanel.setLayout(new BorderLayout(10, 10));
		listResumesPanel.setPreferredSize(new Dimension(660, 700));

		listResumesNorthPanel=new GradientRoundPanel();
		listResumesNorthPanel.setLayout(new BorderLayout(10,10));
		listResumesNorthPanel.setBackground(Color.WHITE);

		titleResume = createLabel("Danh sách ứng viên", true);
		titleResume.setFont(new Font("Segoe UI",1,16));
		titleResume.setForeground(Color.WHITE);
		titleResume.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listResumesNorthPanel.add(titleResume, BorderLayout.WEST);

		listResumesCenterPanel=new GradientRoundPanel();
		listResumesCenterPanel.setLayout(new BoxLayout(listResumesCenterPanel, BoxLayout.PAGE_AXIS));
		listResumesCenterPanel.setBackground(Color.WHITE);
		String[] col= {"Mã","Tên ứng viên","Số điện thoại"};
		Object[][] datas = {
			    {"1","Chưa nộp","Minh Đạt"},
			    {"2","Chưa nộp","Thắng Đạt"}
			};
		modelTableApplicant= new DefaultTableModel(datas, col){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, true
	            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableApplicant=createTable(modelTableApplicant);
		tableApplicant.setCursor(new Cursor(Cursor.HAND_CURSOR));
		scrollResume=new JScrollPane(tableApplicant);
		scrollResume.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScrollHoSo=new GradientRoundPanel();
		resScrollHoSo.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollHoSo.setLayout(new BoxLayout(resScrollHoSo, BoxLayout.PAGE_AXIS));
		resScrollHoSo.add(scrollResume);
		listResumesCenterPanel.add(resScrollHoSo);

		listResumesPanel.add(listResumesNorthPanel, BorderLayout.NORTH);
		listResumesPanel.add(listResumesCenterPanel, BorderLayout.CENTER);


		panelCenter.add(listResumesPanel, BorderLayout.WEST);
		panelCenter.add(listJobsPanel, BorderLayout.EAST);

		centerPanelSearchJob.add(northPanelSearchJob, BorderLayout.NORTH);
		centerPanelSearchJob.add(panelCenter, BorderLayout.CENTER);
		centerPanelSearchJob.add(listInvoicesPanel, BorderLayout.SOUTH);

		searchJobPanel.add(centerPanelSearchJob, BorderLayout.CENTER);
	}

	public JLabel createLabel(String title, boolean isBordered) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI",1,16));
		if (isBordered) label.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		return label;
	}

	public JTable createTable(DefaultTableModel model) {
		JTable table=new JTable(model);
		table.getTableHeader().setFont(new Font("Segoe UI",1,14));
		table.setFont(new Font("Segoe UI",0,16));
		table.setRowHeight(30);
		table.setDefaultRenderer(Object.class, new TableCellGradient());
		table.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> lists = new ArrayList<>();
		lists.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorters = ((DefaultRowSorter)table.getRowSorter());
        sorters.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorters.setSortsOnUpdates(true);
        sorters.setSortKeys(lists);
        sorters.sort();

		return table;
	}

	public void addTableHopDongActionEvent() {
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
				frameListener.onView(row);
			}

		};

		tableInvoice.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererDetail());
		tableInvoice.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorDetail(event));
	}

	//	Listener
	public void addActionListener() {
		btnReset.addActionListener(this);
		btnSearch.addActionListener(this);
	}

	public void addMouseListener() {
		tableApplicant.addMouseListener(this);
		tableRecruiter.addMouseListener(this);
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
		JOptionPane.showMessageDialog(this, message);
	}

	@Override
	public void setFrameListener(IFrameListener<Invoice> listener) {
		this.frameListener=listener;
	}

	@Override
	public JPanel getPanel() {
		return this.searchJobPanel;
	}

	@Override
	public void showData(List<Invoice> data, Object... objects) {
		DecimalFormat df = new DecimalFormat("#,###");
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		modelTableInvoice.setRowCount(0);
		for(Invoice i: data) {
			Object[] obj=new Object[] {
					i.getId(),
					i instanceof PostedInvoice ? ((PostedInvoice)i).getRecruiter().getName()
							: ((ApplicationInvoice)i).getJob().getRecruiter().getName(),
					i instanceof ApplicationInvoice ? ((ApplicationInvoice)i).getApplicant().getName() : null,
					df.format(i.getFee())+" VNĐ",
					format.format(i.getCreatedDate()), null
			};
			modelTableInvoice.addRow(obj);
		}
	}

	@Override
	public JTable getTable() {
		return tableInvoice;
	}

	@Override
	public int getSelectedRow() {
		return tableInvoice.getSelectedRow();
	}

	@Override
	public Object getSearchCriteria() {
		return new Object[]{modelStartDate.getValue(), modelEndDate.getValue(),
			tableApplicant.getSelectedRow(), tableRecruiter.getSelectedRow()
		};
	}

	@Override
	public String getSearchText() {
		return "";
	}

	@Override
	public void showDataApplicant(List<Applicant> data) {
		modelTableApplicant.setRowCount(0);
		for(Applicant i: data) {
			Object[] obj=new Object[] {
					i.getId(),
					i.getName(),
					i.getContact().getPhone()
			};
			modelTableApplicant.addRow(obj);
		}
	}

	@Override
	public void showDataRecruiter(List<Recruiter> data) {
		modelTableRecruiter.setRowCount(0);
		for(Recruiter i: data) {
			Object[] obj=new Object[] {
					i.getId(), i.getName(),
					i.getContact().getEmail()
			};
			modelTableRecruiter.addRow(obj);
		}
	}

	@Override
	public int getRowTableApplicant() {
		return tableApplicant.getSelectedRow();
	}

	@Override
	public int getRowTableRecruiter() {
		return tableRecruiter.getSelectedRow();
	}

	@Override
	public JTable getTableApplicant() {
		return tableApplicant;
	}

	@Override
	public JTable getTableRecruiter() {
		return tableRecruiter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnReset)) {
			if(frameListener!=null) {
				frameListener.onReset();
			}
		}
		else if(obj.equals(btnSearch)) {
			if(frameListener!=null) {
				frameListener.onSearch(getSearchCriteria());
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(tableApplicant)) {
			tableRecruiter.clearSelection();
			int index=tableApplicant.getSelectedRow();
			if(index!=-1 && frameListener!=null) {
				frameListener.onSearch(getSearchCriteria());
			}
		}
		else if(e.getSource().equals(tableRecruiter)) {
			tableApplicant.clearSelection();
			int idx=tableRecruiter.getSelectedRow();
			if(idx!=-1 && frameListener!=null) {
				frameListener.onSearch(getSearchCriteria());
			}
		}
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

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	}

}
