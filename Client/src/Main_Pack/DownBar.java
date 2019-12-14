package Main_Pack;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class DownBar extends JPanel implements ActionListener {

	JButton	BackPage = new JButton("이저 페이지 ");
	JButton NextPage = new JButton("다음 페이지 ");
	JButton Refresh = new JButton("새로고침 ");
	
	Main frame;
	int current;
	String user;
	String pic;
	Start_Pack.SendThread send;
	
	DownBar(Socket socket,int y,Main frame, int i, String user, String pic){
		
		this.frame = frame;
		this.user = user;
		this.pic = pic;
		current = i;
		send = new Start_Pack.SendThread(socket);
		
		setLayout(null);
		setBackground(Color.PINK);
		setBounds(0,y,300,40);
		
		BackPage.setBounds(38, 12, 70, 20);
		Refresh.setBounds(110,12,50,20);
		NextPage.setBounds(160, 12, 100, 20);
		
		BackPage.addActionListener(this);
		NextPage.addActionListener(this);
		Refresh.addActionListener(this);
		
		
		add(BackPage);
		add(Refresh);
		add(NextPage);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() ==  NextPage) {
			
			if(Board2.total == current) {
				JOptionPane.showMessageDialog(null, "마지막 방 입니다 ");
			}
			else {
				
			try {
				
				frame.ChangeMid("front",current+1);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			}
		}
		else if(e.getSource() == BackPage) {
			
			if(current == 1 || current == 0) {
				JOptionPane.showMessageDialog(null, "첫번 방 입니다 ");
			}
			else {
				try {
					
					frame.ChangeMid("Back",current-1);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		else if(e.getSource() == Refresh) {
			send.sendMessage("Refresh/!1@2#3/"+user+"/!1@2#3/"+pic);
		}
		
	}
	
}
