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
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import view.table.TableActionEvent;
import entity.*;
import lombok.Getter;
import view.dialog.idialog.IDialogListener;
import view.dialog.idialog.IListResumeDialog;
import view.table.editor.TableCellEditorDetail;
import view.table.renderer.TableCellRendererDetail;
import entity.constant.Status;
import view.button.Button;
import view.panel.GradientPanel;
import view.table.TableCellGradient;

public class ListResumesDialog extends JDialog implements ActionListener, IListResumeDialog {

	private Applicant applicant;
	private Job job;
	private Employee employee;
	private IDialogListener<JobResume> dialogListener;

	GradientPanel searchPanel, listPanel, btnPanel;
	JLabel searchStatusLabel, searchRecruiterLabel;
	@Getter
	JComboBox searchStatusText, searchRecruiterText;
	Button btnSearch, btnReset, btnCancel, btnApply;
	DefaultTableModel modelTableResume;
	JTable tableResume;
	JScrollPane scrollResume;

	public ListResumesDialog(Frame parent, boolean modal, Applicant applicant) {
		super(parent, modal);
		setTitle("Danh sách tin tuyển dụng ứng tuyển");
		setResizable(false);
		setSize(1000,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.applicant=applicant;

		initComponent();
		addActionListener();
		addTableActionEvent();
	}

	public ListResumesDialog(Frame parent, boolean modal, Job job, Employee employee) {
		super(parent, modal);
		setTitle("Danh sách hồ sơ");
		setResizable(false);
		setSize(1000,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.job=job;
		this.employee=employee;

		initComponent();
		addActionListener();
	}

	public void initComponent() {
//		Tìm kiếm hồ sơ
		searchPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 0));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		searchStatusLabel=new JLabel("Trạng thái:"); searchStatusLabel.setFont(new Font("Segoe UI",0,16));
		searchStatusText=new JComboBox();
		Status[] trangthais= Status.class.getEnumConstants();
		for(Status t: trangthais) {
			searchStatusText.addItem(t.getValue());
		}
		searchStatusText.setFont(new Font("Segoe UI",0,16));
		searchStatusText.setPreferredSize(new Dimension(156,26));

		searchRecruiterLabel=new JLabel("Nhà tuyển dụng:"); searchRecruiterLabel.setFont(new Font("Segoe UI",0,16));
		searchRecruiterText=new JComboBox();
		searchRecruiterText.setFont(new Font("Segoe UI",0,16));
		searchRecruiterText.setPreferredSize(new Dimension(300,26));

		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
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

		searchPanel.add(searchStatusLabel); searchPanel.add(searchStatusText);
		searchPanel.add(searchRecruiterLabel); searchPanel.add(searchRecruiterText);
		searchPanel.add(resBtnSearch);

//		Danh sách hồ sơ
		listPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		listPanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
		listPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hồ sơ","Trạng thái","Nhà tuyển dụng","Tin tuyển dụng","Hành động"};
		Object[][] data = {
			    {1, "Chờ","Amazon", "abc",null},
			    {2, "Chờ","Facebook", "xyz",null}
			};
		modelTableResume= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, true
	            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		if(job!=null){
			colName = new String[]{"Mã hồ sơ", "Tên ứng viên", "Trình độ", "Ngành nghề"};
			data = new Object[][]{
                    {1, "Chờ", "Amazon", "abc"},
                    {2, "Chờ", "Facebook", "xyz"}
            };
			modelTableResume= new DefaultTableModel(data, colName){
				boolean[] canEdit = new boolean [] {
						false, false, false, false
				};

				@Override
				public boolean isCellEditable(int row, int column) {
					return canEdit[column];
				}
			};
		}
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
		btnApply=new Button("Ứng tuyển"); btnApply.setFont(new Font("Segoe UI",0,16));
		btnApply.setPreferredSize(new Dimension(140,30));
		btnApply.setBackground(new Color(0,102,102));
		btnApply.setForeground(Color.WHITE);
		if(job!=null){
			btnPanel.add(btnApply);
		}
		btnPanel.add(btnCancel);

		if(job==null){
			add(searchPanel, BorderLayout.NORTH);
		}
		add(listPanel, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);

	}

	public void addActionListener() {
		btnSearch.addActionListener(this);
		btnReset.addActionListener(this);
		btnCancel.addActionListener(this);
		btnApply.addActionListener(this);
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
				dialogListener.onView(row);
			}
		};

		if(job==null){
			tableResume.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererDetail());
			tableResume.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorDetail(event));
		}

	}

	public void loadDataRecruiter(List<Recruiter> data) {
		for(Recruiter recruiter: data) {
			searchRecruiterText.addItem(recruiter.getName());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnSearch)) {
			dialogListener.onSearch(getSearchCriteria());
		}
		else if(obj.equals(btnReset)) {
			dialogListener.onReset();
		}
		else if(obj.equals(btnCancel)) {
			this.dispose();
		} else if(obj.equals(btnApply)) {
			dialogListener.onView(tableResume.getSelectedRow());
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
	public void setDialogListener(IDialogListener<JobResume> listener) {
		this.dialogListener=listener;
	}

	@Override
	public JPanel getPanel() {
		return (JPanel) getContentPane();
	}

	@Override
	public void showData(List<JobResume> data, Object... objects) {
		modelTableResume.setRowCount(0);
		for(JobResume j: data) {
			Object[] obj=new Object[] {
					j.getResume().getId(), j.getStatus().getValue(),
					j.getJob().getRecruiter().getName(),
					j.getJob().getTitle(),
					null
			};
			modelTableResume.addRow(obj);
		}
	}

	@Override
	public JTable getTable() {
		return tableResume;
	}

	@Override
	public Object getSearchCriteria() {
		return new Object[]{searchStatusText.getSelectedItem().toString(),
				searchRecruiterText.getSelectedItem().toString()};
	}

	@Override
	public String getSearchText() {
		return "";
	}

	@Override
	public int getSelectedRow() {
		return tableResume.getSelectedRow();
	}

	@Override
	public void showDataResume(List<Resume> data) {
		modelTableResume.setRowCount(0);
		for(Resume r: data) {
			Object[] obj=new Object[] {
					r.getId(),
					r.getApplicant().getName(),
					r.getLevel().getValue(),
					String.join(", ",
						r.getProfessions().stream().map(Profession::getName).collect(Collectors.toList())
					)
			};
			modelTableResume.addRow(obj);
		}
	}
}
