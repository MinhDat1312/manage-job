package view.frame.imp;

import view.table.TableActionEvent;
import entity.*;
import lombok.Getter;
import view.button.Button;
import view.frame.iframe.IJobSearchFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.label.ComboBoxRenderer;
import view.panel.GradientRoundPanel;
import view.table.TableCellGradient;
import view.table.editor.TableCellEditorDetail;
import view.table.renderer.TableCellRendererDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobSearchFrameImp extends JFrame implements ActionListener, MouseListener, IJobSearchFrame {

    private static final Font COMPONENT_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    @Getter
    private Employee employee;
    private JobSearchFrameImp parent;
    private IFrameListener<Job> frameListener;
    private boolean isLoadingComboBox = false;

    // Component declarations
    JPanel leftPanel,menuPanel,
            jobSearchPanel, centerPanelJobSearch, northPanelJobSearch;
    JLabel titleJob, titleResume, recruiterLabel, applicantLabel;
    JTable tableJob, tableResume;
    DefaultTableModel modelTableJob, modelTableResume;
    JScrollPane scrollJob, scrollResume;
    @Getter
    JComboBox recruiterBox, applicantBox;
    Button btnReset;
    GradientRoundPanel listJobsPanel, listJobsNorthPanel, listJobsCenterPanel,
            listResumesPanel, listResumesNorthPanel, listResumesCenterPanel;
    GradientRoundPanel searchPanel, filtersPanel,
            listPanel, listNorthPanel, listCenterPanel;

    public JobSearchFrameImp(Employee employee) {
        this.employee = employee;
        this.parent = this;

        // Initialize components
        initComponent();
        addActionListener();
        addMouseListener();
        addTableActionEvents();
    }

    public void initComponent() {
        jobSearchPanel=new JPanel();
        jobSearchPanel.setLayout(new BorderLayout());
        jobSearchPanel.setBackground(new Color(89, 145, 144));

        northPanelJobSearch=new JPanel();
        northPanelJobSearch.setLayout(new FlowLayout(FlowLayout.RIGHT,10,0));
        northPanelJobSearch.setBackground(new Color(89, 145, 144));
        btnReset=new Button("Hủy"); btnReset.setFont(new Font("Segoe UI",0,16));
        btnReset.setPreferredSize(new Dimension(120,25));
        btnReset.setBackground(Color.RED);
        btnReset.setForeground(Color.WHITE);
        northPanelJobSearch.add(btnReset);

//		Hiển thị danh sách tin tuyển dụng, danh sách hồ sơ ứng viên
        centerPanelJobSearch=new JPanel();
        centerPanelJobSearch.setLayout(new BoxLayout(centerPanelJobSearch,BoxLayout.Y_AXIS));
        centerPanelJobSearch.add(Box.createHorizontalStrut(10));
        centerPanelJobSearch.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanelJobSearch.setBackground(new Color(89, 145, 144));

//		Danh sách tin tuyển dụng
        listJobsPanel=new GradientRoundPanel();
        listJobsPanel.setLayout(new BorderLayout());
        listJobsPanel.setPreferredSize(new Dimension(getWidth(),500));

        listJobsNorthPanel=new GradientRoundPanel();
        listJobsNorthPanel.setLayout(new BorderLayout(10,10));
        listJobsNorthPanel.setBackground(Color.WHITE);
        JPanel resNTD=new JPanel();
        resNTD.setOpaque(false);
        resNTD.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
        recruiterBox=new JComboBox();
        recruiterBox.setFont(new Font("Segoe UI",0,16));
        recruiterBox.setForeground(Color.WHITE);
        recruiterBox.setBackground(new Color(89, 145, 144));
        recruiterBox.setPreferredSize(new Dimension(200,25));
        recruiterBox.setRenderer(new ComboBoxRenderer("Chọn nhà tuyển dụng"));
        resNTD.add(recruiterBox);
        titleJob=new JLabel("Danh sách tin tuyển dụng");
        titleJob.setFont(new Font("Segoe UI",1,16));
        titleJob.setForeground(Color.WHITE);
        titleJob.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
        listJobsNorthPanel.add(titleJob, BorderLayout.WEST);
        listJobsNorthPanel.add(resNTD, BorderLayout.EAST);

        listJobsCenterPanel=new GradientRoundPanel();
        listJobsCenterPanel.setLayout(new BoxLayout(listJobsCenterPanel, BoxLayout.PAGE_AXIS));
        listJobsCenterPanel.setBackground(Color.WHITE);
        String[] colName= {"Mã", "Nhà tuyển dụng", "Tiêu đề","Trình độ","Lương", "Hành động"};
        Object[][] data = {
                {"1", "Viettel", "Technical Project Manager","Đại học","1000",null},
                {"2", "Viettel", "Manual Tester","Cao đẳng", "500",null}
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
        tableJob=createTable(modelTableJob);
        tableJob.setCursor(new Cursor(Cursor.HAND_CURSOR));
        scrollJob=new JScrollPane(tableJob);
        scrollJob.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
        GradientRoundPanel resScrollTinTuyenDung=new GradientRoundPanel();
        resScrollTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
        resScrollTinTuyenDung.setLayout(new BoxLayout(resScrollTinTuyenDung, BoxLayout.PAGE_AXIS));
        resScrollTinTuyenDung.setBackground(Color.WHITE);
        resScrollTinTuyenDung.add(scrollJob);
        listJobsCenterPanel.add(resScrollTinTuyenDung);

        listJobsPanel.add(listJobsNorthPanel, BorderLayout.NORTH);
        listJobsPanel.add(listJobsCenterPanel, BorderLayout.CENTER);

//		Danh sách hồ sơ ứng viên
        listResumesPanel=new GradientRoundPanel();
        listResumesPanel.setLayout(new BorderLayout());
        listResumesPanel.setPreferredSize(new Dimension(getWidth(),400));

        listResumesNorthPanel=new GradientRoundPanel();
        listResumesNorthPanel.setLayout(new BorderLayout(10,10));
        listResumesNorthPanel.setBackground(Color.WHITE);
        JPanel resUngVien=new JPanel();
        resUngVien.setOpaque(false);
        resUngVien.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
        resUngVien.setBackground(Color.WHITE);

        applicantBox=new JComboBox();
        applicantBox.setForeground(Color.WHITE);
        applicantBox.setBackground(new Color(89, 145, 144));
        applicantBox.setFont(new Font("Segoe UI",0,16));
        applicantBox.setPreferredSize(new Dimension(200,25));
        applicantBox.setRenderer(new ComboBoxRenderer("Chọn ứng viên"));
        resUngVien.add(applicantBox);

        titleResume = new JLabel("Danh sách hồ sơ ứng viên");
        titleResume.setFont(new Font("Segoe UI",1,16));
        titleResume.setForeground(Color.WHITE);
        titleResume.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
        listResumesNorthPanel.add(titleResume, BorderLayout.WEST);
        listResumesNorthPanel.add(resUngVien, BorderLayout.EAST);

        listResumesCenterPanel=new GradientRoundPanel();
        listResumesCenterPanel.setLayout(new BoxLayout(listResumesCenterPanel, BoxLayout.PAGE_AXIS));
        listResumesCenterPanel.setBackground(Color.WHITE);
        String[] col= {"Mã", "Tên ứng viên","Trình độ", "Ngành nghề", "Hành động"};
        Object[][] datas = {
                {"1","Chưa nộp","Minh Đạt", "Đại học", null},
                {"2","Chưa nộp","Thắng Đạt", "Cao đẳng", null}
        };
        modelTableResume= new DefaultTableModel(datas, col){
            boolean[] canEdit = new boolean [] {
                    false,false, false, false, true
            };

            @Override
            public boolean isCellEditable(int row, int column) {
                return canEdit[column];
            }
        };
        tableResume=createTable(modelTableResume);
        tableResume.setCursor(new Cursor(Cursor.HAND_CURSOR));
        scrollResume=new JScrollPane(tableResume);
        scrollResume.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
        GradientRoundPanel resScrollHoSo=new GradientRoundPanel();
        resScrollHoSo.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
        resScrollHoSo.setLayout(new BoxLayout(resScrollHoSo, BoxLayout.PAGE_AXIS));
        resScrollHoSo.add(scrollResume);
        listResumesCenterPanel.add(resScrollHoSo);

        listResumesPanel.add(listResumesNorthPanel, BorderLayout.NORTH);
        listResumesPanel.add(listResumesCenterPanel, BorderLayout.CENTER);

        centerPanelJobSearch.add(listResumesPanel);
        centerPanelJobSearch.add(listJobsPanel);

        jobSearchPanel.add(northPanelJobSearch, BorderLayout.NORTH);
        jobSearchPanel.add(centerPanelJobSearch, BorderLayout.CENTER);
    }

    public JTable createTable(DefaultTableModel model) {
        JTable table=new JTable(model);
        table.getTableHeader().setFont(new Font("Segoe UI",1,14));
        table.setFont(COMPONENT_FONT);
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

    public void addActionListener() {
        btnReset.addActionListener(this);
        recruiterBox.addActionListener(this);
        applicantBox.addActionListener(this);
    }

    public void addMouseListener() {
        tableResume.addMouseListener(this);
        tableJob.addMouseListener(this);
    }

    public void addTableActionEvents() {
        TableActionEvent resumeEvent=new TableActionEvent() {
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
                if (frameListener != null) {
                    frameListener.onView(
                        Integer.parseInt(tableResume.getValueAt(row, 0).toString())
                    );
                }
            }

        };

        tableResume.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererDetail());
        tableResume.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorDetail(resumeEvent));

        TableActionEvent jobEvent=new TableActionEvent() {
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
                if (frameListener != null) {
                    frameListener.onUpdate(row);
                }
            }

        };

        tableJob.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererDetail());
        tableJob.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorDetail(jobEvent));
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
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void setFrameListener(IFrameListener<Job> listener) {
        this.frameListener = listener;
    }

    @Override
    public JPanel getPanel() {
        return this.jobSearchPanel;
    }

    @Override
    public void showData(List<Job> data, Object... objects) {
        modelTableJob.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        if (data != null) {
            for (Job job : data) {
                modelTableJob.addRow(new Object[]{
                        job.getId(),
                        job.getRecruiter().getName(),
                        job.getTitle(),
                        job.getLevel().getValue(),
                        df.format(job.getSalary())+" VNĐ",
                        null
                });
            }
        }
    }

    @Override
    public void showDataResume(List<Resume> resumes) {
        modelTableResume.setRowCount(0);
        if (resumes != null) {
            for (Resume resume : resumes) {
                String professions = String.join(", ",
                        resume.getProfessions().stream().map(Profession::getName).collect(Collectors.toList()));
                modelTableResume.addRow(new Object[]{
                        resume.getId(),
                        resume.getApplicant().getName(),
                        resume.getLevel().getValue(),
                        professions
                });
            }
        }
    }

    @Override
    public void setRecruiterOptions(List<Recruiter> recruiters) {
        isLoadingComboBox = true;
        recruiterBox.removeAllItems();
        if (recruiters != null) {
            recruiters.forEach(recruiter -> {recruiterBox.addItem(recruiter.getName());});
        }
        recruiterBox.setSelectedItem(null);
        isLoadingComboBox = false;
    }

    @Override
    public void setApplicantOptions(List<Applicant> applicants) {
        isLoadingComboBox = true;
        applicantBox.removeAllItems();
        if (applicants != null) {
            applicants.forEach(applicant -> {applicantBox.addItem(applicant.getName());});
        }
        applicantBox.setSelectedItem(null);
        isLoadingComboBox = false;
    }

    @Override
    public JTable getTable() {
        return tableJob;
    }

    @Override
    public Object getSearchCriteria() {
        int indexResume = tableResume.getSelectedRow();
        int indexJob = tableJob.getSelectedRow();
        if(indexResume != -1){
            return new Object[]{tableResume.getValueAt(indexResume, 0).toString(), "Resume"};
        } else if(indexJob != -1){
            return new Object[]{tableJob.getValueAt(indexJob, 0).toString(), "Job"};
        } else {
            return new Object[]{"-1", "None"};
        }
    }

    @Override
    public String getSearchText() {
        return "";
    }

    @Override
    public int getSelectedRow() {
        return tableJob.getSelectedRow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        var obj=e.getSource();
        if(obj.equals(btnReset)) {
            frameListener.onReset();
        }
        else if(obj.equals(applicantBox) || obj.equals(recruiterBox)) {
            int index = obj.equals(applicantBox) ? 0 : 1;
            if(!isLoadingComboBox) {
                if(index == 0) {
                    frameListener.onExport(applicantBox.getSelectedItem().toString()
                            , index);
                } else {
                    frameListener.onExport(recruiterBox.getSelectedItem().toString()
                            , index);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource().equals(tableResume)) {
            tableJob.clearSelection();
            frameListener.onSearch(getSearchCriteria());
        } else if(e.getSource().equals(tableJob)){
            tableResume.clearSelection();
            frameListener.onSearch(getSearchCriteria());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
