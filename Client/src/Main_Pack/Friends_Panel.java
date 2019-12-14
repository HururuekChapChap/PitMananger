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

public class Friends_Panel extends JPanel implements ActionListener{

	JButton state;
	Image img = null;
	String s = System.getProperty("user.dir");
	
	JLabel Friend_ID;
	private JButton Chat= new JButton("채팅하기 ");
	
	Start_Pack.SendThread send;
	String friend;
	String user;
	String Picture = null;
	
	ShowFriends frame;
	
	public Friends_Panel(ShowFriends frame,Socket socket,String user ,String pic,String ID, int y){
		
		send = new Start_Pack.SendThread(socket);
		friend = ID;
		this.user = user;
		this.frame = frame;
		Picture = pic;
		
		setLayout(null);
		setBackground(Color.PINK);
		setBorder(new LineBorder(Color.white,3));
		setBounds(10,y,280,60);
		
		try {
			File source = new File(s+"/"+pic+".png");
			img = ImageIO.read(source).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("이미지없음 ");
		}
		
		state = new JButton(new ImageIcon(img));
		state.setBounds(10, 5, 50, 50);
		state.addActionListener(this);
		
		Friend_ID = new JLabel(ID);
		Friend_ID.setFont(new Font("맑은고딕 ", Font.BOLD, 15));
		Friend_ID.setBounds(70,10,150,35);
		
		Chat.setBounds(195,5,80,50);
		Chat.addActionListener(this);
		
		add(state);
		add(Friend_ID);
		add(Chat);
	}
	
public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == state) {
			send.sendMessage("STATE_INFO/!1@2#3/"+friend);
		}
		else if(e.getSource() == Chat) {
			Board2.PathToChat(user, friend, Picture);
			frame.dispose();
		}
		
	}
	
}
