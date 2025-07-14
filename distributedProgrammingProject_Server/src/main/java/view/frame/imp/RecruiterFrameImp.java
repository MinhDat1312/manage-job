package view.frame.imp;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import view.table.TableActionEvent;
import view.frame.iframe.IRecruiterFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.iframe.ilistener.IRecuiterListener;
import view.table.editor.TableCellEditorUpdateDelete;
import view.table.editor.TableCellEditorViewCreateJob;
import view.table.renderer.TableCellRendererUpdateDelete;
import entity.Recruiter;
import entity.Employee;
import view.button.Button;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;
import view.table.renderer.TableCellRendererViewCreateJob;
import view.textfield.search.SearchOption;
import view.textfield.search.SearchOptionEvent;
import view.textfield.search.TextFieldSearchOption;

public class RecruiterFrameImp extends JFrame implements ActionListener, MouseListener, IRecruiterFrame {

	private Employee employee;
	private RecruiterFrameImp parent;
	private IFrameListener<Recruiter> frameListener;
	private IRecuiterListener recuiterListener;

//	Component danh sách nhà tuyển dụng
	JPanel recruiterPanel, centerPanelRecruiter;
	JLabel titleRecruiter;
	Button btnAdd, btnSave;
	JTable tableRecruiter;
	DefaultTableModel modelTableRecruiter;
	JScrollPane scrollRecruiter;
	Icon iconBtnAdd,iconBtnSave;
	TextFieldSearchOption searchText;

	GradientRoundPanel searchPanel,
	listPanel, listNorthPanel, listCenterPanel;

	public RecruiterFrameImp(Employee employee) {

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
		recruiterPanel=new JPanel();
		recruiterPanel.setLayout(new BorderLayout(5,5));

//		Hiển thị tìm kiếm và danh sách nhà tuyển dụng
		centerPanelRecruiter=new JPanel();
		centerPanelRecruiter.setLayout(new BorderLayout(10, 10));
		centerPanelRecruiter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelRecruiter.setBackground(new Color(89, 145, 144));
//		Tìm kiếm nhà tuyển dụng
		searchText = new TextFieldSearchOption();
		searchText.addEventOptionSelected(new SearchOptionEvent() {
			@Override
			public void optionSelected(SearchOption option, int index) {
				searchText.setHint("Tìm kiếm " + option.getName() +"...");
			}
		});
		searchText.addOption(new SearchOption("tên nhà tuyển dụng",
				new ImageIcon(getClass().getResource("/image/icon/userSearch.png"))));
		searchText.addOption(new SearchOption("SĐT",
				new ImageIcon(getClass().getResource("/image/icon/tel.png"))));
		searchText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				frameListener.onSearch(getSearchCriteria());
			}
		});
//		Danh sách nhà tuyển dụng
		listPanel=new GradientRoundPanel();
		listPanel.setBackground(Color.WHITE);
		listPanel.setLayout(new BorderLayout(10, 10));


		listNorthPanel=new GradientRoundPanel();
		listNorthPanel.setLayout(new BorderLayout(10,10));
		listNorthPanel.setBackground(Color.WHITE);
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
		resBtnThem.add(searchText);
		resBtnThem.add(btnAdd); resBtnThem.add(btnSave);
		titleRecruiter=new JLabel("Danh sách nhà tuyển dụng");
		titleRecruiter.setForeground(Color.WHITE);
		titleRecruiter.setFont(new Font("Segoe UI",1,16));
		titleRecruiter.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listNorthPanel.add(titleRecruiter, BorderLayout.WEST);
		listNorthPanel.add(resBtnThem, BorderLayout.EAST);

		listCenterPanel=new GradientRoundPanel();
		listCenterPanel.setLayout(new BoxLayout(listCenterPanel, BoxLayout.PAGE_AXIS));
		listCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã nhà tuyển dụng","Tên nhà tuyển dụng","Địa chỉ","Email", "Số điện thoại", "Hành động","Tin tuyển dụng"};
		Object[][] data = {
			    {1, "MinhDat", "Nha Trang", "abc@gmail.com", "0123456789", null,null},
			    {2, "ThangDat", "Sài Gòn", "def@gmail.com", "0987654321", null,null}
			};
		modelTableRecruiter= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true, true
	            };

            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableRecruiter=new JTable(modelTableRecruiter);
		tableRecruiter.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableRecruiter.setFont(new Font("Segoe UI",0,16));
		tableRecruiter.setRowHeight(30);
		tableRecruiter.setDefaultRenderer(Object.class, new TableCellGradient());
		tableRecruiter.setCursor(new Cursor(Cursor.HAND_CURSOR));

		tableRecruiter.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter)tableRecruiter.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();

		scrollRecruiter=new JScrollPane(tableRecruiter);
		scrollRecruiter.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollRecruiter);
		listCenterPanel.add(resScroll);


		listPanel.add(listNorthPanel, BorderLayout.NORTH);
		listPanel.add(listCenterPanel, BorderLayout.CENTER);

		centerPanelRecruiter.add(listPanel, BorderLayout.CENTER);

		recruiterPanel.add(centerPanelRecruiter, BorderLayout.CENTER);
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
				frameListener.onDelete(row);
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
				recuiterListener.onViewJobs(row);
			}

			@Override
			public void onCreateTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				recuiterListener.onCreateJob(row);
			}

			@Override
			public void onViewDetail(int row) {
				// TODO Auto-generated method stub

			}
		};

		tableRecruiter.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererUpdateDelete());
		tableRecruiter.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorUpdateDelete(event));

		tableRecruiter.getColumnModel().getColumn(6).setCellRenderer(new TableCellRendererViewCreateJob());
		tableRecruiter.getColumnModel().getColumn(6).setCellEditor(new TableCellEditorViewCreateJob(event));
	}

	//	Listener
	public void addActionListener() {
		btnAdd.addActionListener(this);
		btnSave.addActionListener(this);
	}

	public void addMouseListener() {
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
		JOptionPane.showMessageDialog(rootPane, message);
	}

	@Override
	public void setFrameListener(IFrameListener<Recruiter> listener) {
		this.frameListener = listener;
	}

	@Override
	public void setRecuiterListener(IRecuiterListener listener) {
		this.recuiterListener = listener;
	}

	@Override
	public JPanel getPanel() {
		return this.recruiterPanel;
	}

	@Override
	public void showData(List<Recruiter> data, Object... objects) {
		modelTableRecruiter.setRowCount(0);
		for(Recruiter i: data) {
			Object[] obj=new Object[] {
					i.getId(), i.getName(), i.getAddress().getCity(),
					i.getContact().getEmail(), i.getContact().getPhone(),
					null, null
			};
			modelTableRecruiter.addRow(obj);
		}
	}

	@Override
	public JTable getTable() {
		return tableRecruiter;
	}

	@Override
	public Object getSearchCriteria() {
		return  new Object[]{
				searchText.getSelectedIndex(),
				searchText.getText().trim(),
		};
	}

	@Override
	public String getSearchText() {
		return searchText.getText().trim();
	}

	@Override
	public int getSelectedRow() {
		return tableRecruiter.getSelectedRow();
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
