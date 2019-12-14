package Main_Pack;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame{
	
	 JPanel up;
	 JPanel mid;
	 JPanel low;
	 
	Socket socket;
	public int start_page;
	String name;
	String profile_IMG;
	
	public Main(Socket socket, String name, int i, String pic){
		
		this.socket = socket;
		this.name = name;
		start_page = i;
		profile_IMG = pic;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,500);
		this.setLayout(null);
		this.setTitle("메인 화면 ");
		this.getContentPane().setBackground(Color.PINK);
		
		up = new UPBAR(socket,name,pic);
	
		mid = new Board2(this,i,"front",socket);
		
		/*
		int y =50;
		for(int i =0; i<4; i++) {

		add(new Board(y));
		y += 95;
		
		}
	
		
		add(new DownBar(y));
		*/
		
		
		low = new DownBar(socket,440,this,i,name,pic);
		
		add(up);
		add(mid);
		add(low);

		this.setVisible(true);
	}
	
	public void ChangeMid(String page,int i) {
		
		this.remove(mid);
		this.remove(low);
		
		mid = new Board2(this,i,page,socket);
		low = new DownBar(socket,440,this, i,name,profile_IMG);
		
		this.add(mid);
		this.add(low);
		this.revalidate();
		this.repaint();
		
	}
	
	public  void ChangeToRoom(String pro_id, String pro_info, String date, String mcp, String price) {
		this.remove(up);
		this.remove(mid);
		this.remove(low);
		
		up = new Group_Chat(this,socket,pro_id,pro_info, date,name, mcp, price);
		this.add(up);
		this.revalidate();
		this.repaint();
	}
	
	public void BackToMain() {
		this.remove(up);
		up = new UPBAR(socket,name,profile_IMG);
		mid = new Board2(this,start_page,"front", socket);
		low = new DownBar(socket,440,this,start_page,name,profile_IMG);
		
		add(up);
		add(mid);
		add(low);
		
		this.revalidate();
		this.repaint();
		
	}
	
	public void ChangeToChat(String user, String name, String pic) {
		this.remove(up);
		this.remove(mid);
		this.remove(low);
		
		up = new Chat_Panel(this,socket,user,name, pic);
		add(up);
		
		this.revalidate();
		this.repaint();
	}
	

}
