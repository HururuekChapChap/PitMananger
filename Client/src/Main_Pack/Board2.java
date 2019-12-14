package Main_Pack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Start_Pack.SendThread;



public class Board2 extends JPanel implements ActionListener{
	
	JLabel product_pic;
	JButton To;
	JLabel product_end;
	JLabel product_name;
	JLabel empty;
	
	Image img = null;
	Date d1;
	Date d2;
	String s = System.getProperty("user.dir"); 
	
	public static int total = 0;
	public static String product_Id = null;
	public static String product_Info = null;
	public static String product_Name = null;
	public static String date = null;
	public static int take = 0;
	public static String MCP = null;
	public static String price = null;
	
	private static String temp_info = null;
	private static String temp_id = null;
	private static String temp_date = null;
	public static String temp_MCP = null;
	public static String temp_price = null;

	
	Start_Pack.SendThread send;
	static Main main;
	Board2(Main main,int i,String page ,Socket socket){
		
		String num = Integer.toString(i);
		String num2 = Integer.toString(i+1);
		String num3 = Integer.toString(i-1);
		send = new Start_Pack.SendThread(socket);
		this.main = main;
		
		
		setLayout(null);
		setBackground(Color.PINK);
		setBorder(new LineBorder(Color.white,2));
		setBounds(10,60,280,370);
		
		
		if(i >0) {
			
			temp_id = product_Id;
			temp_info = product_Info;
			temp_date = date;
			temp_MCP = MCP;
			temp_price = price;
		// 시간 출력 방식을 정하는 SimpleDateFormat 과 시간을 정해주는 parse 함
				SimpleDateFormat f= new SimpleDateFormat("HH:mm:ss");
		
				try {
					d1 = f.parse(date);
					 //d1 = f.parse("01:05:10");
					 //d2 = f.parse("01:07:10");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// 시간 계산하는 방법 
				//long minut = d2.getTime() - d1.getTime();
				
				//시: 60*60*1000 분:60*1000 초:1000 
				//System.out.println(minut/(60*1000));
				
				System.out.println(f.format(d1));
				
				try {
					File source = new File(s+"/img/" + num + ".png");
					img = ImageIO.read(source).getScaledInstance(180, 180, Image.SCALE_SMOOTH);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("이미지없음 ");
				}
				
				product_pic = new JLabel(new ImageIcon(img));
				product_pic.setBounds(50, 25, 180, 180);
				
				System.out.println(date + " " + total + " " + product_Name);
				
				product_name = new JLabel(product_Name);
				product_name.setFont(new Font("맑은고딕 ", Font.BOLD, 20));
				product_name.setBounds(50,260,200,35);
				
				product_end = new JLabel("마감시간: " + f.format(d1));
				product_end.setFont(new Font("맑은고딕 ", Font.BOLD, 20));
				product_end.setBounds(50, 220, 200, 35);
				
				To = new JButton("입장하기 ");
				To.addActionListener(this);
				To.setBounds(75,305,120,40);
				
				add(product_pic);
				add(product_name);
				add(product_end);
				add(To);
		}
		else {
			
			empty = new JLabel("방이 없습니다. ");
			empty.setFont(new Font("맑은고딕 ", Font.BOLD, 20));
			empty.setBounds(80,150,200,35);
			
			add(empty);
			
		}
		
				if((i+1 <= total && page.equals("front")) || (i-1 == 0 && total != 1)) {
					send.sendMessage("getINFO/!1@2#3/" + num2);
					new ReceiveData(i+1);
					}
				else if( (i-1 > 0 && page.equals("Back")) || (i+1 > total && i != 0 && total != 1) ) {
					send.sendMessage("getINFO/!1@2#3/" + num3);
					new ReceiveData(i-1);
				}
				
	}
	
	public static void getInfo(int total, String ProductName, String date,String id, String info, String MCP, String price) {
		
		Board2.total = total;
		Board2.product_Name = ProductName;
		Board2.date = date;
		Board2.product_Id = id;
		Board2.product_Info = info;
		Board2.MCP = MCP;
		Board2.price = price;
		
	}
	
	public static void CloseMain() {
		main.dispose();
	}
	
	public static void PathToMain(String id, String text, String date) {
		main.ChangeToRoom(id,text,date, "비어있음" , "0");
	}
	
	public static void PathToChat(String user, String name, String pic) {
		main.ChangeToChat(user, name, pic);
	}
	
	public static void PathTo_ChangeToRoom() {
		main.ChangeToRoom(temp_id, temp_info, temp_date, temp_MCP, temp_price);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == To) {
			//main.ChangeToRoom(Board2.product_Id, Board2.product_Info, Board2.date);
			//main.ChangeToRoom(temp_id, temp_info, temp_date);
			send.sendMessage("ISENTER/!1@2#3/"+temp_id);
		}
		
	}


}
