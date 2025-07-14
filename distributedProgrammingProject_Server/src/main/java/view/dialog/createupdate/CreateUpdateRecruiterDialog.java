package view.dialog.createupdate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

import javax.swing.*;

import entity.embeddable.Address;
import entity.embeddable.Contact;
import exception.checkPhone;
import exception.checkUserEmail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import lombok.Getter;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.RecruiterDAO;
import entity.Recruiter;
import util.FilterImp;
import view.button.Button;
import view.panel.GradientPanel;

public class CreateUpdateRecruiterDialog extends JDialog implements ActionListener{

	private Frame parent;
	@Getter
	private Recruiter recruiter;
	@Getter
	private boolean confirmed;
	private String logo;

	JPanel logoPanel;
	GradientPanel inforRecruiterPanel, btnPanel;
	JLabel idLabel, nameLabel, phoneLabel, dateLabel, genderLabel, addressLabel,emailLabel;
	JTextField idText, nameText, phoneText, emailText;
	JTextField[] addressFields;
	JComboBox genderBox;
	UtilDateModel modelDate;
	JDatePickerImpl dateText;
	Button btnAdd, btnReset, btnLogo;
	JFileChooser fileChooser;


	public CreateUpdateRecruiterDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Thêm mới nhà tuyển dụng");
		setResizable(false);
		setSize(700,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.parent=parent;
		this.recruiter=new Recruiter();
		this.confirmed=false;
		this.logo = "";

		initComponent();
		addActionListener();
	}

	public CreateUpdateRecruiterDialog(Frame parent, boolean modal, Recruiter recruiter) {
		this(parent, modal);
		this.recruiter=recruiter;
		setTitle("Cập nhật nhà tuyển dụng");
		btnAdd.setText("Cập nhật");

		loadDataRecuiter();
	}

	public void initComponent() {
		inforRecruiterPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		inforRecruiterPanel.setBackground(Color.WHITE);
		inforRecruiterPanel.setLayout(new GridBagLayout());
		inforRecruiterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc= new GridBagConstraints();

//		Thông tin nhà tuyển dụng
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã nhà tuyển dụng"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforRecruiterPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforRecruiterPanel.add(idText, gbc);

		gbc.gridx=0; gbc.gridy=2;
		nameLabel=new JLabel("Tên nhà tuyển dụng"); nameLabel.setFont(new Font("Segoe UI",0,16));
		inforRecruiterPanel.add(nameLabel, gbc);
		gbc.gridx=0; gbc.gridy=3;
		nameText=new JTextField(20); nameText.setFont(new Font("Segoe UI",0,16));
		inforRecruiterPanel.add(nameText, gbc);

		gbc.gridx=1; gbc.gridy=0; gbc.gridheight=4;
		logoPanel=new JPanel();
		logoPanel.setLayout(new BorderLayout());
		logoPanel.setOpaque(false);
		logoPanel.setBorder(BorderFactory.createLineBorder(new Color(0,102,102)));
		logoPanel.setPreferredSize(new Dimension(125, 125));
		logoPanel.setBackground(Color.WHITE);
		inforRecruiterPanel.add(logoPanel, gbc);

		gbc.gridx=2; gbc.gridy=3; gbc.gridheight=1;
		btnLogo=new Button("Chọn logo");
		btnLogo.setFont(new Font("Segoe UI",0,16));
		btnLogo.setBackground(new Color(0,102,102));
		btnLogo.setForeground(Color.WHITE);
		btnLogo.setPreferredSize(new Dimension(110,25));
		inforRecruiterPanel.add(btnLogo, gbc);

		gbc.gridx=0; gbc.gridy=4;
		phoneLabel=new JLabel("Số điện thoại"); phoneLabel.setFont(new Font("Segoe UI",0,16));
		inforRecruiterPanel.add(phoneLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		phoneText=new JTextField(20); phoneText.setFont(new Font("Segoe UI",0,16));
		inforRecruiterPanel.add(phoneText, gbc);

		gbc.gridx=1; gbc.gridy=4; gbc.gridwidth=2;
		emailLabel=new JLabel("Email"); emailLabel.setFont(new Font("Segoe UI",0,16));
		inforRecruiterPanel.add(emailLabel,gbc);
		gbc.gridx=1; gbc.gridy=5;
		emailText=new JTextField(20); emailText.setFont(new Font("Segoe UI",0,16));
		inforRecruiterPanel.add(emailText,gbc);

		String[] addressLabels = {"Thành phố", "Quốc gia", "Quận", "Số nhà", "Đường", "Phường"};
		addressFields = new JTextField[addressLabels.length];
		for (int i = 0; i < addressLabels.length; i += 2) {
			int row = i / 2;
			for (int j = 0; j < 2; j++) {
				int index = i + j;
				if (index >= addressLabels.length) break;

				int col = j;

				gbc.gridwidth =  j == 1 ? 2 : 1;
				gbc.gridx = col;
				gbc.gridy = 6 + row * 2;
				gbc.anchor = GridBagConstraints.WEST;
				JLabel addressLabel = new JLabel(addressLabels[index]);
				addressLabel.setFont(new Font("Segoe UI", 0, 16));
				inforRecruiterPanel.add(addressLabel, gbc);

				gbc.gridx = col;
				gbc.gridy = 6 + row * 2 + 1;
				addressFields[index] = new JTextField(20);
				addressFields[index].setFont(new Font("Segoe UI", 0, 16));
				inforRecruiterPanel.add(addressFields[index], gbc);
			}
		}
		add(inforRecruiterPanel, BorderLayout.CENTER);

//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 38));

		btnAdd=new Button("Thêm mới"); btnAdd.setFont(new Font("Segoe UI",0,16));
		btnAdd.setPreferredSize(new Dimension(120,25));
		btnAdd.setBackground(new Color(0,102,102));
		btnAdd.setForeground(Color.WHITE);

		btnReset=new Button("Hủy"); btnReset.setFont(new Font("Segoe UI",0,16));
		btnReset.setPreferredSize(new Dimension(120,25));
		btnReset.setBackground(Color.WHITE);
		btnReset.setForeground(Color.BLACK);

		btnPanel.add(btnAdd); btnPanel.add(btnReset);

		add(btnPanel, BorderLayout.SOUTH);
	}

	public void addActionListener() {
		btnAdd.addActionListener(this);
		btnReset.addActionListener(this);
		btnLogo.addActionListener(this);
	}

	public void loadDataRecuiter() {
		idText.setText(String.valueOf(recruiter.getId()));
		nameText.setText(recruiter.getName());
		addressFields[0].setText(recruiter.getAddress().getCity());
		addressFields[1].setText(recruiter.getAddress().getCountry());
		addressFields[2].setText(recruiter.getAddress().getDistrict());
		addressFields[3].setText(recruiter.getAddress().getNumber());
		addressFields[4].setText(recruiter.getAddress().getStreet());
		addressFields[5].setText(recruiter.getAddress().getWard());
		phoneText.setText(recruiter.getContact().getPhone());
		emailText.setText(recruiter.getContact().getEmail());
		logo = recruiter.getLogo();

		if (getClass().getResource("/image/recruiterLogo/" + logo) != null) {
			if (logoPanel.getComponents() != null) {
				logoPanel.removeAll();
				logoPanel.revalidate();
				logoPanel.repaint();
			}

			ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/recruiterLogo/" + logo));
			Image image = imageIcon.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH);
			JLabel logo = new JLabel();
			logo.setIcon(new ImageIcon(image));
			logoPanel.add(logo);
			logoPanel.revalidate();
			logoPanel.repaint();
		} else {
			logoPanel.removeAll();
			logoPanel.revalidate();
			logoPanel.repaint();
		}
	}

	public void addUpdate(){
		String name=nameText.getText();
		String phone=phoneText.getText();
		String email=emailText.getText();
		String city = addressFields[0].getText().trim();
		String country = addressFields[1].getText().trim();
		String district = addressFields[2].getText().trim();
		String number = addressFields[3].getText().trim();
		String street = addressFields[4].getText().trim();
		String ward = addressFields[5].getText().trim();

		if (name.isEmpty() || phone.isEmpty() || city.isEmpty() || country.isEmpty()
				|| district.isEmpty() || number.isEmpty()
				|| street.isEmpty() || ward.isEmpty() || email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
		}
//		else if(logo.isEmpty()) {
//			JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn logo nhà tuyển dụng");
//		}
		else {
			var check=new FilterImp();
            try {
                if(check.checkPhone(phone) && check.checkUserEmail(email)){
                    recruiter.setName(name);
					recruiter.setContact(new Contact(phone, email));
					recruiter.setAddress(new Address(number, street, ward, district, city, country));
					recruiter.setLogo(logo == null ? "" : logo);

					confirmed = true;
					dispose();
                }
            } catch (checkPhone | checkUserEmail e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
        }
	}

	public void reset(){
		nameText.setText("");
		phoneText.setText("");
		emailText.setText("");

		addressFields[0].setText("");
		addressFields[1].setText("");
		addressFields[2].setText("");
		addressFields[3].setText("");
		addressFields[4].setText("");
		addressFields[5].setText("");

		logoPanel.removeAll();
		logoPanel.revalidate();
		logoPanel.repaint();

		logo="";

		confirmed = false;
	}

	public void openFile() {
		URL resourceUrl = getClass().getClassLoader().getResource("image/recruiterLogo");
		if(resourceUrl != null){
            try {
                File directory = new File(resourceUrl.toURI());
				fileChooser = new JFileChooser(directory);
				int actionResult=fileChooser.showOpenDialog(this);
				if(actionResult==fileChooser.APPROVE_OPTION) {
					String path=fileChooser.getSelectedFile().getAbsolutePath();
					var res=path.split("\\\\");
					String extension = res[res.length-1].split("\\.")[1];
					logo=res[res.length-1].split("\\.")[0]+"."+extension;
					Pattern pattern=Pattern.compile("(png|jpg|gif)$",Pattern.CASE_INSENSITIVE);
					if(pattern.matcher(extension).matches()) {
						if(logoPanel.getComponents()!=null) {
							logoPanel.removeAll();
							logoPanel.revalidate();
							logoPanel.repaint();
						}

						ImageIcon imageIcon=new ImageIcon(path);
						Image image=imageIcon.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH);
						JLabel logo=new JLabel(); logo.setIcon(new ImageIcon(image));
						logoPanel.add(logo, BorderLayout.CENTER);
						logoPanel.revalidate();
						logoPanel.repaint();
					}
					else {
						JOptionPane.showMessageDialog(rootPane, "Không phải file ảnh");
					}
				}
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnAdd)) {
			addUpdate();
		}
		else if(obj.equals(btnReset)) {
			reset();
		}
		else if(obj.equals(btnLogo)) {
			openFile();
		}
	}
}
