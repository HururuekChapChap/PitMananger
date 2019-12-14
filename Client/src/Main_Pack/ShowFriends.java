package Main_Pack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ShowFriends extends JFrame implements ActionListener {

	private JButton logout = new JButton("로그아웃 ");
	private JButton friend= new JButton("친구찾기 ");

	int i =3, y = 60, p = 0;
	Socket socket;
	String user;
	Start_Pack.SendThread send;
	public ShowFriends(Socket socket,String user,int total, String word){
		
		
		this.socket = socket;
		this.user = user;
		send = new Start_Pack.SendThread(socket);
		
		String[] words = word.split("/");
		String[] pics = null;
		if(total >3) {
		pics = words[2].split("@");
		}
		
		this.setSize(300, 500);
		this.setLayout(null);
		this.setTitle("친구상태 ");
		this.getContentPane().setBackground(Color.PINK);
		
		logout.setBounds(10,5,100,50);
		logout.addActionListener(this);
		
		friend.setBounds(190,5,100,50);
		friend.addActionListener(this);
		
		while(i< total) {
		add(new Friends_Panel(this,socket,user,pics[p],words[i], y));
		i++;
		p++;
		y += 65;
		}
		add(logout);
		add(friend);
		
		
		this.setVisible(true);	
	}
	
public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == logout) {
			send.sendMessage("EXIT/!1@2#3/"+user);
			
		}
		else if( e.getSource() == friend) {
			new AddFriends(socket,user);
			this.dispose();
		}
	}
	

}
