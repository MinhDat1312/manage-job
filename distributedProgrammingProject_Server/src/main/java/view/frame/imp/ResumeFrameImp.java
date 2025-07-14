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

import entity.*;
import view.table.TableActionEvent;
import lombok.Getter;
import view.frame.iframe.IResumeFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.table.editor.TableCellEditorViewUpdateDelete;
import view.button.Button;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;
import view.table.renderer.TableCellRendererViewUpdateDelete;
import view.textfield.search.SearchOption;
import view.textfield.search.SearchOptionEvent;
import view.textfield.search.TextFieldSearchOption;

public class ResumeFrameImp extends JFrame implements
		ActionListener, MouseListener, IResumeFrame {

	@Getter
	private Employee empployee;
	private ResumeFrameImp parent;
	private IFrameListener<Resume> frameListener;
	private boolean flag=false;

//	Component danh sách hồ sơ
	JPanel resumePanel, centerPanelResume;
	JLabel titleResume;
	TextFieldSearchOption searchText;
	Button btnSave;
	JTable tableResume;
	DefaultTableModel modelTableResume;
	JScrollPane scrollResume;
	Icon iconBtnSave;
	GradientRoundPanel searchPanel,
	listPanel, listNorthPanel, listCenterPanel;

	public ResumeFrameImp(Employee empployee) {
		this.empployee=empployee;
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
		resumePanel=new JPanel();
		resumePanel.setLayout(new BorderLayout(5,5));

//		Hiển thị tìm kiếm và danh sách hồ sơ
		centerPanelResume=new JPanel();
		centerPanelResume.setLayout(new BorderLayout(10, 10));
		centerPanelResume.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelResume.setBackground(new Color(89, 145, 144));

//		Danh sách hồ sơ
		searchText = new TextFieldSearchOption();
		searchText.addEventOptionSelected(new SearchOptionEvent() {
			@Override
			public void optionSelected(SearchOption option, int index) {
				searchText.setHint("Tìm kiếm " + option.getName() +"...");
			}
		});
		searchText.addOption(new SearchOption("họ tên ứng viên",
				new ImageIcon(getClass().getResource("/image/icon/userSearch.png"))));
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
		iconBtnSave=new ImageIcon(getClass().getResource("/image/icon/save.png"));
		JPanel resBtn=new JPanel();
		resBtn.setOpaque(false);
		resBtn.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtn.setBackground(Color.WHITE);
		btnSave=new Button("Xuất Excel");
		btnSave.setIcon(iconBtnSave);
		btnSave.setFont(new Font("Segoe UI",0,16));
		btnSave.setPreferredSize(new Dimension(140,30));
		btnSave.setBackground(new Color(51,51,255));
		btnSave.setForeground(Color.WHITE);
		resBtn.add(searchText);
		resBtn.add(btnSave);
		titleResume=new JLabel("Danh sách hồ sơ");
		titleResume.setFont(new Font("Segoe UI",1,16));
		titleResume.setForeground(Color.WHITE);
		titleResume.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		listNorthPanel.add(titleResume, BorderLayout.WEST);
		listNorthPanel.add(resBtn, BorderLayout.EAST);

		listCenterPanel=new GradientRoundPanel();
		listCenterPanel.setLayout(new BoxLayout(listCenterPanel, BoxLayout.PAGE_AXIS));
		listCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hồ sơ","Tên ứng viên","Trình độ","Ngành nghề","Hành động"};
		Object[][] data = {
			    {1, "Chờ", "Minh Đạt", "Amazon",null},
			    {2, "Chờ", "Thắng Đạt", "Facebook",null}
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
		tableResume=new JTable(modelTableResume);
		tableResume.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableResume.setFont(new Font("Segoe UI",0,16));
		tableResume.setRowHeight(30);
		tableResume.setDefaultRenderer(Object.class, new TableCellGradient());
		tableResume.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollResume);
		listCenterPanel.add(resScroll);

		listPanel.add(listNorthPanel, BorderLayout.NORTH);
		listPanel.add(listCenterPanel, BorderLayout.CENTER);

		centerPanelResume.add(listPanel, BorderLayout.CENTER);

		resumePanel.add(centerPanelResume, BorderLayout.CENTER);
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

		tableResume.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererViewUpdateDelete());
		tableResume.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorViewUpdateDelete(event));
	}

	//	Listener
	public void addActionListener() {
		btnSave.addActionListener(this);
	}

	public void addMouseListener() {
		tableResume.addMouseListener(this);
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
	public void setFrameListener(IFrameListener<Resume> listener) {
		this.frameListener=listener;
	}

	@Override
	public JPanel getPanel() {
		return this.resumePanel;
	}

	@Override
	public void showData(List<Resume> data, Object... objects) {
		modelTableResume.setRowCount(0);
		for(Resume r: data) {
			Applicant applicant =r.getApplicant();
			String[] professionsList = r.getProfessions().stream().map(Profession::getName).toArray(String[]::new);
			Object[] obj=new Object[] {
					r.getId(), applicant.getName(),
					r.getLevel().getValue(),
					String.join(", ", professionsList),
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
		return new Object[]{searchText.getSelectedIndex(), searchText.getText().trim()};
	}

	@Override
	public String getSearchText() {
		return searchText.getText().trim();
	}

	@Override
	public int getSelectedRow() {
		return tableResume.getSelectedRow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnSave)) {
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
