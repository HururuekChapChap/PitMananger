package Start_Pack;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main_Pack.Main;

public class Frame extends JFrame{
	
	JPanel panel = new JPanel();
	SendThread send;
	Socket socket;
	Login_Panel login;
	Admin admin;
	
	
	Frame(Socket socket){
		
		this.socket = socket;
		
		login = new Login_Panel(socket, this);
		admin = new Admin(socket, this);
		// 프레임의 객체를 이어 받아서, 프레임에서 변경 해주도록 해준다.
		// 즉 프레임이 핵심 GUI 부분의 핵심 객체 부분인 것이다.
			
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,500);
		this.setTitle("Login");
		this.setLayout(null);
		
		panel = login;
		
		this.add(panel);
		this.setVisible(true);
		
	}
	
	public void ChangeToAdmin() {
		this.remove(panel);
		panel = admin;
		this.add(panel);
		this.revalidate();
		this.repaint();
	}
	
	public void ChangeToLogin() {
		this.remove(panel);
		panel = login;
		this.add(panel);
		this.revalidate();
		this.repaint();
	}
	
	public void ChangeToMain(String name, int num, String pic) throws ParseException {
		new Main_Pack.Main(socket,name,num, pic);
		this.dispose();
	}

	
	
		
	
}
