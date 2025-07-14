package view.dialog.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.swing.*;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Table;
import entity.ApplicationInvoice;
import entity.PostedInvoice;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

//import com.spire.doc.Document;
//import com.spire.doc.FileFormat;
//import com.spire.doc.Table;

import util.DateFormatter;
import entity.Invoice;
import view.button.Button;
import view.panel.GradientPanel;

public class DetailPostedInvoiceDialog extends JDialog implements ActionListener{

    private PostedInvoice invoice;

    GradientPanel inforInvoicePanel, btnPanel;
    JLabel idLabel, nameJobLabel, createdDateLabel, recruiterLabel, feeLabel,
            numberOfAppLabel, addressLabel, phoneLabel, emailLabel, employeeLabel;
    JTextField idText, nameJobText, recruiterText,
            numberOfAppText, feeText, addressText, phoneText, emailText, employeeText;
    UtilDateModel modelCreatedDate;
    JDatePickerImpl createdDateText;
    Button btnSave, btnReset;
    GridBagConstraints gbc;

    public DetailPostedInvoiceDialog(Frame parent, boolean modal, Invoice invoice) {
        super(parent, modal);
        setTitle("Xem chi tiết hóa đơn nhà tuyển dụng");
        setResizable(false);
        setSize(900,450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        this.invoice = (PostedInvoice) invoice;

        initComponent();
        addActionListener();
        loadDataInvoice();
    }

    public DetailPostedInvoiceDialog(Dialog parent, boolean modal, Invoice invoice) {
        super(parent, modal);
        setTitle("Xem chi tiết hóa đơn nhà tuyển dụng");
        setResizable(false);
        setSize(850,450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        this.invoice= (PostedInvoice) invoice;
//		hoadonDAO=new HoaDonDAO(em, Invoice.class);
//		ungvienDAO=new UngVienDAO(em);
//		nhanvienDAO=new NhanVienDAO(em);
//		tintuyendungDAO=new TinTuyenDungDAO(em);
//		nhatuyendungDAO=new NhaTuyenDungDAO(em);

        initComponent();
        addActionListener();
//		loadData();
//		loadDataHoaDon();
    }

    public void initComponent() {
        inforInvoicePanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
        inforInvoicePanel.setLayout(new GridBagLayout());
        inforInvoicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc= new GridBagConstraints();

//		Thông tin hợp đồng
        gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 5);
        gbc.anchor=GridBagConstraints.WEST;
        idLabel=new JLabel("Mã hóa đơn"); idLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(idLabel, gbc);
        gbc.gridx=0; gbc.gridy=1;
        idText=new JTextField(13); idText.setFont(new Font("Segoe UI",0,16));
        idText.setEditable(false);
        inforInvoicePanel.add(idText, gbc);

        gbc.gridx=1; gbc.gridy=0;
        feeLabel=new JLabel("Phí dịch vụ"); feeLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(feeLabel, gbc);
        gbc.gridx=1; gbc.gridy=1;
        feeText=new JTextField(12); feeText.setFont(new Font("Segoe UI",0,16));
        feeText.setEditable(false);
        inforInvoicePanel.add(feeText, gbc);

        gbc.gridx=2; gbc.gridy=0; gbc.gridwidth=1;
        createdDateLabel=new JLabel("Ngày lập"); createdDateLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(createdDateLabel, gbc);
        gbc.gridx=2; gbc.gridy=1; gbc.gridwidth=2;
        modelCreatedDate=new UtilDateModel();
        modelCreatedDate.setSelected(true);
        Properties p=new Properties();
        p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
        JDatePanelImpl panelNgayLap=new JDatePanelImpl(modelCreatedDate, p);
        createdDateText=new JDatePickerImpl(panelNgayLap, new DateFormatter());
        createdDateText.setPreferredSize(new Dimension(375, 24));
        inforInvoicePanel.add(createdDateText, gbc);

        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=1;
        recruiterLabel=new JLabel("Nhà tuyển dụng"); recruiterLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(recruiterLabel,gbc);
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
        recruiterText=new JTextField(27); recruiterText.setFont(new Font("Segoe UI",0,16));
        recruiterText.setEditable(false);
        inforInvoicePanel.add(recruiterText,gbc);

        gbc.gridx=2; gbc.gridy=2; gbc.gridwidth=1;
        nameJobLabel=new JLabel("Tin tuyển dụng"); nameJobLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(nameJobLabel, gbc);
        gbc.gridx=2; gbc.gridy=3; gbc.gridwidth=2;
        nameJobText=new JTextField(26); nameJobText.setFont(new Font("Segoe UI",0,16));
        nameJobText.setEditable(false);
        inforInvoicePanel.add(nameJobText, gbc);

        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=1;
        addressLabel=new JLabel("Địa chỉ"); addressLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(addressLabel,gbc);
        gbc.gridx=0; gbc.gridy=5;
        addressText=new JTextField(13); addressText.setFont(new Font("Segoe UI",0,16));
        addressText.setEditable(false);
        inforInvoicePanel.add(addressText,gbc);

        gbc.gridx=1; gbc.gridy=4;
        numberOfAppLabel=new JLabel("Số lượng tuyển"); numberOfAppLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(numberOfAppLabel, gbc);
        gbc.gridx=1; gbc.gridy=5;
        numberOfAppText=new JTextField(12); numberOfAppText.setFont(new Font("Segoe UI",0,16));
        numberOfAppText.setEditable(false);
        inforInvoicePanel.add(numberOfAppText, gbc);

        gbc.gridx=2; gbc.gridy=4;
        phoneLabel=new JLabel("Số điện thoại"); phoneLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(phoneLabel, gbc);
        gbc.gridx=2; gbc.gridy=5;
        phoneText=new JTextField(12); phoneText.setFont(new Font("Segoe UI",0,16));
        phoneText.setEditable(false);
        inforInvoicePanel.add(phoneText, gbc);

        gbc.gridx=3; gbc.gridy=4;
        emailLabel=new JLabel("Email"); emailLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(emailLabel, gbc);
        gbc.gridx=3; gbc.gridy=5;
        emailText=new JTextField(12); emailText.setFont(new Font("Segoe UI",0,16));
        emailText.setEditable(false);
        inforInvoicePanel.add(emailText, gbc);

        gbc.gridx=0; gbc.gridy=6;
        employeeLabel=new JLabel("Nhân viên phụ trách"); employeeLabel.setFont(new Font("Segoe UI",0,16));
        inforInvoicePanel.add(employeeLabel, gbc);
        gbc.gridx=0; gbc.gridy=7; gbc.gridwidth=2;
        employeeText=new JTextField(27); employeeText.setFont(new Font("Segoe UI",0,16));
        employeeText.setEditable(false);
        inforInvoicePanel.add(employeeText, gbc);

        add(inforInvoicePanel, BorderLayout.CENTER);

//		Button
        btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 45));

        btnSave=new Button("In hóa đơn"); btnSave.setFont(new Font("Segoe UI",0,16));
        btnSave.setPreferredSize(new Dimension(120,25));
        btnSave.setBackground(new Color(0,102,102));
        btnSave.setForeground(Color.WHITE);

        btnReset=new Button("Hủy"); btnReset.setFont(new Font("Segoe UI",0,16));
        btnReset.setPreferredSize(new Dimension(120,25));
        btnReset.setBackground(Color.WHITE);
        btnReset.setForeground(Color.BLACK);

        btnPanel.add(btnSave); btnPanel.add(btnReset);

        add(btnPanel, BorderLayout.SOUTH);
    }

    public void addActionListener() {
        btnReset.addActionListener(this);
        btnSave.addActionListener(this);
    }

    public void loadDataInvoice() {
        DecimalFormat df = new DecimalFormat("#,###");

        idText.setText(String.valueOf(invoice.getId()));
        feeText.setText(df.format(invoice.getFee()));
        modelCreatedDate.setValue(Date.from(invoice.getCreatedDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        recruiterText.setText(invoice.getRecruiter().getName());
        addressText.setText(invoice.getRecruiter().getAddress().getCity());
        numberOfAppText.setText(String.valueOf((int)invoice.getApplicationCount() ));
        nameJobText.setText(invoice.getJob().getTitle());
        phoneText.setText(invoice.getRecruiter().getContact().getPhone());
        emailText.setText(invoice.getRecruiter().getContact().getEmail());
        employeeText.setText(invoice.getEmployee().getName());
    }

    private String luu() {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DecimalFormat df = new DecimalFormat("#,###");
        Document doc = new Document();

        doc.loadFromFile("form/formPosted.docx");

        doc.replace("#MaHD", String.valueOf(invoice.getId()), true, true);
        doc.replace("#Date", invoice.getCreatedDate().format(dft), true, true);
        doc.replace("#nhanvien", invoice.getEmployee().getName(), true, true);

        doc.replace("#tenNTD", invoice.getJob().getRecruiter().getName(), true, true);
        doc.replace("#emailNTD", invoice.getJob().getRecruiter().getContact().getEmail(), true, true);
        doc.replace("#diachiNTD", invoice.getJob().getRecruiter().getAddress().getCity(), true, true);
        doc.replace("#sodienthoaiNTD", invoice.getJob().getRecruiter().getContact().getPhone(), true, true);

        doc.replace("#title", invoice.getJob().getTitle(), true, true);
        doc.replace("#level", invoice.getJob().getLevel().getValue(), true, true);
        doc.replace("#type", invoice.getJob().getWorkingType().getValue(), true, true);
        doc.replace("#salary", df.format(invoice.getJob().getSalary()) + " VNĐ", true, true);

        doc.replace("#fee", df.format(invoice.getApplicationFee())+" VNĐ", true, true);
        doc.replace("#number", String.valueOf(invoice.getApplicationCount()), true, true);
        doc.replace("#total", df.format(invoice.getFee())+" VNĐ", true, true);

        doc.isUpdateFields(true);

        int number=new Random().nextInt();

        String filePath="form"+File.separator+"Invoice_"+number+".pdf";
        doc.saveToFile(filePath, FileFormat.PDF);

        return filePath;
    }

    public void openFile(String filePath) {
        try {
            String filePathNew=filePath.replace("\\", "\\\\");
            File pdfFile = new File(filePathNew);

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();

                if (pdfFile.exists()) {
                    desktop.open(pdfFile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        var obj=e.getSource();
        if(obj.equals(btnReset)) {
            this.dispose();
        }
        else if(obj.equals(btnSave)) {
			openFile(luu());
			this.dispose();
        }
    }
}