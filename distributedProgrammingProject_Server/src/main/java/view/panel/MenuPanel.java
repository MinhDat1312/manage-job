package view.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.*;

import view.button.MenuButton;
import view.frame.imp.MainFrameImp;

public class MenuPanel extends JPanel{

//	Thanh menu
//	Nhân viên
	MenuButton menuEmployee;
//	Tài khoản
	MenuButton menuAccount;
//	Ứng viên
	MenuButton menuApplicant;
// 	Hồ sơ
	MenuButton menuResume;
// 	Nhà tuyển dụng
	MenuButton menuRecruiter;
// 	Tin tuyển dụng
	MenuButton menuJob;
// 	Hóa đơn
	MenuButton menuInvoice;
//	Tìm việc làm
	MenuButton menuJobSearch;
// 	Thống kê
	MenuButton menuStatistics;
//	JMenuItem itemEmployeeSta, itemRecruiterSta, itemResumeSta, itemJobSta;
// 	Hệ thống
	MenuButton menuUser;
	MenuButton menuHome, menuLogout;
	JPanel menuPanel;

	MainFrameImp parent;

	public MenuPanel(Frame parent) {
		this.parent=(MainFrameImp) parent;

		setLayout(new GridLayout(0, 1, 0, 10));
		setForeground(Color.WHITE);

		menuEmployee=createMenu("Nhân viên", "nhanvien");
		menuAccount=createMenu("Tài khoản", "taikhoan");
		menuApplicant=createMenu("Ứng viên", "ungvien");
		menuResume=createMenu("Hồ sơ", "hoso");
		menuRecruiter=createMenu("Nhà tuyển dụng", "nhatuyendung");
		menuJob=createMenu("Tin tuyển dụng", "tintuyendung");
		menuInvoice=createMenu("Hóa đơn", "hoadon");
		menuJobSearch=createMenu("Tìm việc làm", "timviec16");
		menuStatistics=createMenu("Thống kê", "thongke");
		menuHome=createMenu("Trang chủ", "home");
		menuLogout=createMenu("Đăng xuất", "exit");

		if(this.parent.getVaiTro().equalsIgnoreCase("Admin")) {
			add(menuEmployee);
			add(menuAccount);
		}

		add(menuApplicant);
		add(menuResume);
		add(menuRecruiter);
		add(menuJob);
		add(menuInvoice);
		add(menuJobSearch);

		if(this.parent.getVaiTro().equalsIgnoreCase("Admin")) {
			add(menuStatistics);
		}

		add(new JLabel(),"push");
		add(new JLabel(),"push");

		add(menuHome);
		add(menuLogout);


	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    int w = getWidth(), h = getHeight();
	    Color color1 = Color.decode("#259195");
	    Color color2 = Color.decode("#ABC8CB");
	    GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
	    g2d.setPaint(gp);
	    g2d.fillRect(0, 0, w, h);
	}

	private MenuButton createMenu(String title, String nameImg) {
		MenuButton menu=new MenuButton(title);
		menu.setFont(new Font("Segoe UI",1,16));
		menu.setIcon(new ImageIcon(getClass().getResource("/image/icon/"+nameImg+".png")));

		return menu;
	}

}
