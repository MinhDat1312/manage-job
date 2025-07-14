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
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import view.table.TableActionEvent;
import view.frame.iframe.*;
import view.frame.iframe.ilistener.IFrameListener;
import view.table.editor.TableCellEditorUpdateDelete;
import view.table.renderer.TableCellRendererUpdateDelete;
import entity.Account;
import entity.Employee;
import view.button.Button;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;
import view.textfield.search.SearchOption;
import view.textfield.search.SearchOptionEvent;
import view.textfield.search.TextFieldSearchOption;

public class AccountFrameImp extends JFrame
		implements ActionListener, MouseListener, IAccountFrame {

	private Employee employee;
	private AccountFrameImp parent;
	private IFrameListener<Account> frameListener;

//	Component danh sách tài khoản
	JPanel leftPanel,menuPanel,
        accountPanel, northPanelAccount, centerPanelAccount;
	JLabel userLabel, iconUserLabel, searchNameLabel, titleAccount, roleLeftLabel;
	TextFieldSearchOption searchText;
	Button btnSearch, btnReset, btnSave;
	JTable tableAccount;
	DefaultTableModel modelTableAccount;
	JScrollPane scrollAccount;
	Icon iconBtnSave;

	GradientRoundPanel timkiemPanel,
            lsitPanel, listNorthPanel, listCenterPanel;

	public AccountFrameImp(Employee employee) {
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
		accountPanel =new JPanel();
		accountPanel.setLayout(new BorderLayout(5,5));
		accountPanel.setBackground(new Color(220, 220, 220));

//		Hiển thị tìm kiếm và danh sách tài khoản
		centerPanelAccount =new JPanel();
		centerPanelAccount.setLayout(new BorderLayout(10, 10));
		centerPanelAccount.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelAccount.setBackground(new Color(89, 145, 144));

//		Danh sách tài khoản
		searchText = new TextFieldSearchOption();
		searchText.addEventOptionSelected(new SearchOptionEvent() {
			@Override
			public void optionSelected(SearchOption option, int index) {
				searchText.setHint("Tìm kiếm " + option.getName() +"...");
			}
		});
		searchText.addOption(new SearchOption("tên nhân viên",
				new ImageIcon(getClass().getResource("/image/icon/userSearch.png"))));
		searchText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				frameListener.onSearch(getSearchCriteria());
			}
		});

		lsitPanel =new GradientRoundPanel();
		lsitPanel.setBackground(Color.WHITE);
		lsitPanel.setLayout(new BorderLayout(10, 10));

		listNorthPanel =new GradientRoundPanel();
		listNorthPanel.setLayout(new BorderLayout(10,10));
		listNorthPanel.setBackground(Color.WHITE);
		iconBtnSave=new ImageIcon(getClass().getResource("/image/icon/save.png"));
		JPanel resBtn=new JPanel();
		resBtn.setOpaque(false);
		resBtn.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtn.setBackground(Color.WHITE);
		btnSave =new Button("Xuất Excel");
		btnSave.setIcon(iconBtnSave);
		btnSave.setFont(new Font("Segoe UI",0,16));
		btnSave.setPreferredSize(new Dimension(140,30));
		btnSave.setBackground(new Color(51,51,255));
		btnSave.setForeground(Color.WHITE);
		resBtn.add(searchText); resBtn.add(btnSave);
		titleAccount =new JLabel("Danh sách tài khoản nhân viên");
		titleAccount.setFont(new Font("Segoe UI",1,16));
		titleAccount.setForeground(Color.WHITE);
		titleAccount.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listNorthPanel.add(titleAccount, BorderLayout.WEST);
		listNorthPanel.add(resBtn, BorderLayout.EAST);

		listCenterPanel =new GradientRoundPanel();
		listCenterPanel.setLayout(new BoxLayout(listCenterPanel, BoxLayout.PAGE_AXIS));
		listCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã tài khoản","Tên đăng nhập", "Tên nhân viên", "Vai trò","Hành động"};
		Object[][] data = {
			    {1, "MinhDat", "Minh Đạt", "Admin", null},
			    {2, "ThangDat", "Thắng Đạt", "Nhân viên", null}
			};
		modelTableAccount = new DefaultTableModel(data, colName){
			 boolean[] canEdit = new boolean [] {
		                false, false, false, false, true
		            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableAccount =new JTable(modelTableAccount);
		tableAccount.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableAccount.setFont(new Font("Segoe UI",0,16));
		tableAccount.setRowHeight(30);
		tableAccount.setDefaultRenderer(Object.class, new TableCellGradient());
		tableAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));

		tableAccount.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter) tableAccount.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();

		scrollAccount =new JScrollPane(tableAccount);
		scrollAccount.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollAccount);
		listCenterPanel.add(resScroll);

		lsitPanel.add(listNorthPanel, BorderLayout.NORTH);
		lsitPanel.add(listCenterPanel, BorderLayout.CENTER);

		centerPanelAccount.add(lsitPanel, BorderLayout.CENTER);

		accountPanel.add(centerPanelAccount, BorderLayout.CENTER);
	}

	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				if(frameListener != null){
					frameListener.onUpdate(row);
				}
			}

			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				if(frameListener != null){
					frameListener.onDelete(row);
				}
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

			}
		};

		tableAccount.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererUpdateDelete());
		tableAccount.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorUpdateDelete(event));
	}

	//	Listener
	public void addActionListener() {
		btnSave.addActionListener(this);
	}

	public void addMouseListener() {
		tableAccount.addMouseListener(this);
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
	public void setFrameListener(IFrameListener<Account> listener) {
		this.frameListener = listener;
	}

	@Override
	public JPanel getPanel() {
		return this.accountPanel;
	}

	@Override
	public void showData(List<Account> data, Object... objects) {
		modelTableAccount.setRowCount(0);
		for(Account i: data) {
			Employee emp=i.getEmployee();
			Object[] obj=new Object[] {
					i.getId(), i.getEmail(), emp.getName(),
					i.getRole().getValue(), null
			};
			modelTableAccount.addRow(obj);
		}
	}

	@Override
	public JTable getTable() {
		return tableAccount;
	}

	@Override
	public Object getSearchCriteria() {
		return new Object[]{searchText.getSelectedIndex(), searchText.getText().trim()};
	}

	@Override
	public String getSearchText() {
		return searchText.getText().trim();
	}

	@Override
	public int getSelectedRow() {
		return tableAccount.getSelectedRow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnSave)) {
			if(frameListener != null){
				frameListener.onExport();
			}
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
