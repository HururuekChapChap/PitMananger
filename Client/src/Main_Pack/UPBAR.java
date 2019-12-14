package Main_Pack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class UPBAR extends JPanel implements ActionListener {

	private JLabel la_ID;
	private JButton Profile;
	private JButton Refresh;
	
	Image img = null;
	String s = System.getProperty("user.dir");
	Socket socket;
	
	private String ID;
	Start_Pack.SendThread send;
	
	UPBAR(Socket socket,String name, String pic){
		
		setLayout(null);
		setBackground(Color.PINK);
		setBounds(0,0,300,50);
		//setBorder(new LineBorder(Color.red,1));
		
		this.socket = socket;
		send = new Start_Pack.SendThread(socket);
		ID = name;
		
		System.out.println(s);
		
		try {
			File source = new File(s+"/"+pic +".png");
			img = ImageIO.read(source).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("이미지없음 ");
		}
		
		Profile = new JButton(new ImageIcon(img));
		Profile.setBounds(20, 5, 40, 40);
		Profile.addActionListener(this);
		
		la_ID = new JLabel(name);
		la_ID.setFont(new Font("맑은고딕 ", Font.BOLD, 15));
		la_ID.setBounds(80, 12, 100, 25);
		
		Refresh = new JButton("글쓰기 ");
		Refresh.setBounds(190,5,100, 40);
		Refresh.addActionListener(this);
		
		add(Profile);
		add(la_ID);
		add(Refresh);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == Refresh) {
			new Make_Board(socket, ID);
		}
		else if(e.getSource() == Profile) {
			send.sendMessage("Friends_INFO/!1@2#3/" + ID);
		}
		
		
	}
	
}
