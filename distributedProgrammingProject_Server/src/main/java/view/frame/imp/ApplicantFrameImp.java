package view.frame.imp;

import view.table.TableActionEvent;
import entity.Applicant;
import entity.Employee;
import lombok.Getter;
import view.button.Button;
import view.frame.iframe.IApplicantFrame;
import view.frame.iframe.ilistener.IApplicantListener;
import view.frame.iframe.ilistener.IFrameListener;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;
import view.table.editor.TableCellEditorUpdateDelete;
import view.table.editor.TableCellEditorViewCreateResume;
import view.table.renderer.TableCellRendererUpdateDelete;
import view.table.renderer.TableCellRendererViewCreateResume;
import view.textfield.search.SearchOption;
import view.textfield.search.SearchOptionEvent;
import view.textfield.search.TextFieldSearchOption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantFrameImp extends JFrame implements ActionListener, IApplicantFrame {

	@Getter
	private Employee employee;
	private ApplicantFrameImp parent;
	private IFrameListener<Applicant> frameListener;
	private IApplicantListener applicantListener;

	// Component declarations
	private JPanel applicantPanel, centerPanelApplicant;
	private JLabel titleApplicant;
	private TextFieldSearchOption searchText;
	private Button btnAdd, btnSave;
	private JTable tableApplicant;
	private DefaultTableModel modelTableApplicant;
	private JScrollPane scrollApplicant;
	private Icon iconBtnAdd, iconBtnSave;

	private GradientRoundPanel searchPanel,
			listPanel, listNorthPanel, listCenterPanel;

	public ApplicantFrameImp(Employee employee) {
		this.employee = employee;
		this.parent = this;

		// Create components
		initComponent();
		addTableActionEvent();
		addActionListener();
	}

	public void initComponent() {
		applicantPanel=new JPanel();
		applicantPanel.setLayout(new BorderLayout(5,5));
		applicantPanel.setBackground(new Color(220, 220, 220));

//		Hiển thị tìm kiếm và danh sách ứng viên
		centerPanelApplicant=new JPanel();
		centerPanelApplicant.setLayout(new BorderLayout(10, 10));
		centerPanelApplicant.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelApplicant.setBackground(new Color(89, 145, 144));

//		Danh sách ứng viên
		searchText = new TextFieldSearchOption();
		searchText.addEventOptionSelected(new SearchOptionEvent() {
			@Override
			public void optionSelected(SearchOption option, int index) {
				searchText.setHint("Tìm kiếm " + option.getName() +"...");
			}
		});
		searchText.addOption(new SearchOption("họ tên",
				new ImageIcon(getClass().getResource("/image/icon/userSearch.png"))));
		searchText.addOption(new SearchOption("SĐT",
				new ImageIcon(getClass().getResource("/image/icon/tel.png"))));
		searchText.addOption(new SearchOption("email",
				new ImageIcon(getClass().getResource("/image/icon/email.png"))));
		searchText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				frameListener.onSearch(getSearchCriteria());
			}
		});

		listPanel=new GradientRoundPanel();
		listPanel.setLayout(new BorderLayout(10, 10));

		listNorthPanel=new GradientRoundPanel();
		listNorthPanel.setLayout(new BorderLayout(10,10));
		iconBtnAdd=new ImageIcon(getClass().getResource("/image/icon/add.png"));
		iconBtnSave=new ImageIcon(getClass().getResource("/image/icon/save.png"));
		JPanel resBtnThem=new JPanel();
		resBtnThem.setOpaque(false);
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtnThem.setBackground(Color.WHITE);
		btnAdd=new Button("Thêm mới");
		btnAdd.setIcon(iconBtnAdd);
		btnAdd.setFont(new Font("Segoe UI",0,16));
		btnAdd.setPreferredSize(new Dimension(140,30));
		btnAdd.setBackground(new Color(0,102,102));
		btnAdd.setForeground(Color.WHITE);
		btnSave=new Button("Xuất Excel");
		btnSave.setIcon(iconBtnSave);
		btnSave.setFont(new Font("Segoe UI",0,16));
		btnSave.setPreferredSize(new Dimension(140,30));
		btnSave.setBackground(new Color(51,51,255));
		btnSave.setForeground(Color.WHITE);
		resBtnThem.add(searchText); resBtnThem.add(btnAdd); resBtnThem.add(btnSave);
		titleApplicant=new JLabel("Danh sách ứng viên");
		titleApplicant.setForeground(Color.WHITE);
		titleApplicant.setFont(new Font("Segoe UI",1,16));
		titleApplicant.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listNorthPanel.add(titleApplicant, BorderLayout.WEST);
		listNorthPanel.add(resBtnThem, BorderLayout.EAST);

		listCenterPanel=new GradientRoundPanel();
		listCenterPanel.setLayout(new BoxLayout(listCenterPanel, BoxLayout.PAGE_AXIS));
		listCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã ứng viên","Tên ứng viên","Số điện thoại","Email","Hành động","Hồ sơ"};
		Object[][] data = {
			    {1, "MinhDat", "01234567", "abc@gmail.com",null,null},
			    {2, "ThangDat", "07654321", "def@gmail.com",null,null}
			};
		modelTableApplicant= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, true, true
	            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableApplicant=new JTable(modelTableApplicant);
		tableApplicant.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableApplicant.setFont(new Font("Segoe UI",0,16));
		tableApplicant.setRowHeight(30);
		tableApplicant.setDefaultRenderer(Object.class, new TableCellGradient());
		tableApplicant.setCursor(new Cursor(Cursor.HAND_CURSOR));

		tableApplicant.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter)tableApplicant.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();

		scrollApplicant=new JScrollPane(tableApplicant);
		scrollApplicant.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollApplicant);
		listCenterPanel.add(resScroll);

		listPanel.add(listNorthPanel, BorderLayout.NORTH);
		listPanel.add(listCenterPanel, BorderLayout.CENTER);

		centerPanelApplicant.add(listPanel, BorderLayout.CENTER);

		applicantPanel.add(centerPanelApplicant, BorderLayout.CENTER);
	}

	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				frameListener.onUpdate(row);
			}

			@Override
			public void onDelete(int row) {
				frameListener.onDelete(row);
			}

			@Override
			public void onViewHoSo(int row) {
				// TODO Auto-generated method stub
				applicantListener.onViewResume(row);
			}

			@Override
			public void onCreateHoSo(int row) {
				applicantListener.onCreateResume(row);
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
			}
		};

		tableApplicant.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererUpdateDelete());
		tableApplicant.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorUpdateDelete(event));

		tableApplicant.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererViewCreateResume());
		tableApplicant.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorViewCreateResume(event));
	}

	//	Listener
	public void addActionListener() {
		btnAdd.addActionListener(this);
		btnSave.addActionListener(this);
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
		JOptionPane.showMessageDialog(rootPane, message);
	}

	@Override
	public void setFrameListener(IFrameListener<Applicant> listener) {
		this.frameListener = listener;
	}

	@Override
	public JPanel getPanel() {
		return this.applicantPanel;
	}

	@Override
	public void showData(List<Applicant> data, Object... objects) {
		modelTableApplicant.setRowCount(0);
		for(Applicant i: data) {
			Object[] obj=new Object[] {
					i.getId(), i.getName(), i.getContact().getPhone(), i.getContact().getEmail(),
					null, null
			};
			modelTableApplicant.addRow(obj);
		}
	}

	@Override
	public JTable getTable() {
		return tableApplicant;
	}

	@Override
	public Object getSearchCriteria() {
		return  new Object[]{searchText.getSelectedIndex(), searchText.getText().trim()};
	}

	@Override
	public String getSearchText() {
		return searchText.getText().trim();
	}

	@Override
	public int getSelectedRow() {
		return tableApplicant.getSelectedRow();
	}


	@Override
	public void setApplicantListener(IApplicantListener listener) {
		this.applicantListener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();

		if(obj.equals(btnAdd)) {
			frameListener.onAdd();
		}
		else if(obj.equals(btnSave)) {
			frameListener.onExport();
		}
	}
}
