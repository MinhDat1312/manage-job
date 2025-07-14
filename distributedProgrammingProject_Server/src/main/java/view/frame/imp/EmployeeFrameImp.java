package view.frame.imp;

import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entity.constant.Role;
import view.table.TableActionEvent;
import view.frame.iframe.IEmployeeFrame;
import view.frame.iframe.ilistener.IEmployeeListener;
import view.frame.iframe.ilistener.IFrameListener;
import view.table.editor.TableCellEditorCreateAccount;
import view.table.editor.TableCellEditorUpdateDelete;
import view.table.renderer.TableCellRendererCreateAccount;
import view.table.renderer.TableCellRendererUpdateDelete;
import entity.Employee;
import view.button.Button;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;
import view.textfield.search.SearchOption;
import view.textfield.search.SearchOptionEvent;
import view.textfield.search.TextFieldSearchOption;

public class EmployeeFrameImp extends JFrame
		implements ActionListener, MouseListener, IEmployeeFrame {

	private IFrameListener<Employee> frameListener;
	private IEmployeeListener employeeListener;
	private Employee employee;
	private EmployeeFrameImp parent;

//	Component danh sách nhân viên
	JPanel employeePanel, centerPanelEmployee;
	JLabel titleEmployee;
	Button btnAdd,btnSave;
	JTable tableEmployee;
	DefaultTableModel modelTableEmployee;
	JScrollPane scrollEmployee;
	Icon iconBtnAdd, iconBtnSave;
	TextFieldSearchOption searchText;
	GradientRoundPanel listPanel, listNorthPanel, listCenterPanel;

	public EmployeeFrameImp(Employee employee) {
		this.employee=employee;
		this.parent=this;

//		Tạo component bên phải
		initComponent();
//		Thêm update và delete vào table
		addTableActionEvent();
//		Thêm sự kiện
		addActionListener();
		addMouseListener();
	}

	public void initComponent() {
		employeePanel=new JPanel();
		employeePanel.setLayout(new BorderLayout(5,5));

//		Hiển thị tìm kiếm và danh sách nhân viên
		centerPanelEmployee=new JPanel();
		centerPanelEmployee.setLayout(new BorderLayout(10, 10));
		centerPanelEmployee.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelEmployee.setBackground(new Color(89, 145, 144));

//		Danh sách nhân viên
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
		resBtnThem.add(searchText);
		resBtnThem.add(btnAdd); resBtnThem.add(btnSave);
		titleEmployee=new JLabel("Danh sách nhân viên");
		titleEmployee.setFont(new Font("Segoe UI",1,16));
		titleEmployee.setForeground(Color.WHITE);
		titleEmployee.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listNorthPanel.add(titleEmployee, BorderLayout.WEST);
		listNorthPanel.add(resBtnThem, BorderLayout.EAST);

		listCenterPanel=new GradientRoundPanel();
		listCenterPanel.setLayout(new BoxLayout(listCenterPanel, BoxLayout.PAGE_AXIS));
		String[] colName= {"Mã nhân viên","Tên nhân viên","Số điện thoại","Ngày vào làm","Vai trò","Hành động","Tài khoản"};
		Object[][] data = {
			    {16, "MinhDat", "01234567", "13/12/2003", "Admin", null,null},
			    {2, "ThangDat", "07654321", "13/12/2003", "Nhân viên",null,null}
			};
		modelTableEmployee= new DefaultTableModel(data, colName){
			 boolean[] canEdit = new boolean [] {
		                false, false, false, false, false, true, true
		            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableEmployee=new JTable(modelTableEmployee);
		tableEmployee.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableEmployee.setFont(new Font("Segoe UI",0,16));
		tableEmployee.setRowHeight(30);
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
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollEmployee);
		listCenterPanel.add(resScroll);

		listPanel.add(listNorthPanel, BorderLayout.NORTH);
		listPanel.add(listCenterPanel, BorderLayout.CENTER);

		centerPanelEmployee.add(listPanel, BorderLayout.CENTER);

		employeePanel.add(centerPanelEmployee, BorderLayout.CENTER);
	}

	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				frameListener.onUpdate(row);
			}

			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				frameListener.onDelete(row);
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
				employeeListener.createAccount(row);
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

		tableEmployee.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererUpdateDelete());
		tableEmployee.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorUpdateDelete(event));

		tableEmployee.getColumnModel().getColumn(6).setCellRenderer(new TableCellRendererCreateAccount());
		tableEmployee.getColumnModel().getColumn(6).setCellEditor(new TableCellEditorCreateAccount(event));
	}

	//	Listener
	public void addActionListener() {
		btnAdd.addActionListener(this);
		btnSave.addActionListener(this);
	}

	public void addMouseListener() {
		tableEmployee.addMouseListener(this);
	}

	@Override
	public void visible() {
		setVisible(true);
	}

	@Override
	public void close() {
		dispose();
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(rootPane, message);
	}

	@Override
	public void setFrameListener(IFrameListener<Employee> listener) {
		this.frameListener=listener;
	}

	@Override
	public JPanel getPanel() {
		return this.employeePanel;
	}

	@Override
	public void showData(List<Employee> data, Object... objects) {
		modelTableEmployee.setRowCount(0);
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for(Employee i: data) {
			Role role = Arrays.stream(Role.class.getEnumConstants())
					.filter(r->i.getAccount() != null
							&& i.getAccount().getRole().toString().toLowerCase()
							.equalsIgnoreCase(r.toString().toLowerCase()))
					.findFirst().orElse(null);
			Object[] obj=new Object[] {
					i.getId(), i.getName(), i.getContact().getPhone(),
					format.format(i.getStartDate()), role != null ? role.getValue() : "Chưa có", null, null
			};
			modelTableEmployee.addRow(obj);
		}
	}

	@Override
	public JTable getTable() {
		return tableEmployee;
	}

	@Override
	public Object getSearchCriteria() {
		return  new Object[]{searchText.getSelectedIndex(), searchText.getText().trim()};
	}

	@Override
	public int getSelectedRow() {
		return tableEmployee.getSelectedRow();
	}

	@Override
	public String getSearchText() {
		return searchText.getText().trim();
	}

	@Override
	public void setEmployeeListener(IEmployeeListener listener) {
		this.employeeListener = listener;
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
