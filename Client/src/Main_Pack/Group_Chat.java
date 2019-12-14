package Main_Pack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Group_Chat extends JPanel implements ActionListener{
	
	private JTextArea productText = new JTextArea();
	private JTextField textField = new JTextField();
	public static JTextArea textArea = new JTextArea();
	public static JTextArea Price = new JTextArea();
	
	private JLabel Name;
	private JLabel Time;
	
	private JButton Search = new JButton("시세 검색 ");
	private JButton To = new JButton("나가기 ");
	private JButton Call = new JButton("호가 ");
	private JButton Send_Text = new JButton("보내기 ");
	
	Main frame;
	Start_Pack.SendThread send;
	Socket socket;
	String user_id;
	String pro_id;
	String date;
	
	Group_Chat(Main frame, Socket socket, String id, String text, String date, String user, String mcp, String price){
		
		setLayout(null);
		setBackground(Color.PINK);
		setBorder(new LineBorder(Color.white,3));
		setBounds(10,10,280,460);
		this.socket = socket;
		this.frame = frame;
		
		send = new Start_Pack.SendThread(socket);
		user_id = user;
		pro_id = id;
		this.date = date;
		
		send.sendMessage("GROUP_IN/!1@2#3/" + id + "/!1@2#3/" + user);
		
		send.sendMessage("getINFO/!1@2#3/" + "1");
		new ReceiveData(1);
		
		productText.append(text);
		productText.setEditable(false);
		productText.setBounds(10, 10, 260, 30);
		
		Name = new JLabel("작성자 :"+ id);
		Name.setBounds(10, 35, 100, 50);
		
		Time = new JLabel("마감시간 :"+date);
		Time.setBounds(150,35,150,50);
		
		Price.setText("");
		Price.append(mcp + " | " + price );
		Price.setForeground(Color.RED);
		Price.setBorder(new LineBorder(Color.YELLOW,1));
		Price.setBackground(Color.orange);
		Price.setEditable(false);
		Price.setBounds(10,75,130,20);
		
		Search.setBounds(160,75,100,20);
		
		JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		textArea.setText("");
		textArea.append("방에 입장하셨습니다. \n");
		textArea.setEditable(false);
		
		
		
		scrollPane.setBounds(10,100,260,270);
		To.setBounds(10,374,80,40);
		To.addActionListener(this);
		Call.setBounds(100,374,80,40);
		Call.addActionListener(this);
		Send_Text.setBounds(190, 374, 80, 40);
		Send_Text.addActionListener(this);
	    textField.setBounds(0,410,280,50);
	    
	    
		
	    add(productText);
	    add(Time);
	    add(Name);
	    add(Price);
	    add(Search);
	    add(scrollPane);
	    add(To);
	    add(Call);
	    add(Send_Text);
	    add(textField);
	   
	    
	}
	
public void actionPerformed(ActionEvent e) {
		
		Date current;
		SimpleDateFormat f= new SimpleDateFormat("HH:mm:ss");
		
		if(e.getSource() == To) {
			
			current = new Date();
			boolean check = false;
			
			try {
				long time =  f.parse( f.format(current) ).getTime();
				long END_Time = f.parse(date).getTime();
				
				if(time - END_Time > 0 ) {
					check = true;
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(user_id.equals(pro_id) && check == false) {
				JOptionPane.showMessageDialog(null, "방장은 나갈 수 없습니다. ");
			}
			else {
				send.sendMessage("GROUP_OUT/!1@2#3/"+pro_id+"/!1@2#3/"+user_id);
			if(frame.start_page == 0) {
				frame.start_page += 1;
			frame.BackToMain();
			}
			else {
				frame.BackToMain();
			}
			}
		}
		else if(e.getSource() == Send_Text && !textField.getText().equals("") ) {
			send.sendMessage("GROUP_CHAT/!1@2#3/"+pro_id+"/!1@2#3/"+user_id + "/!1@2#3/" + textField.getText());
			textField.setText("");
		}
		else if(e.getSource() == Call) {
			
			current = new Date();
			boolean Call_check = false;
			
			try {
				long time =  f.parse( f.format(current) ).getTime();
				long END_Time = f.parse(date).getTime();
				
				if(time - END_Time > 0 ) {
					Call_check = true;
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(!user_id.equals(pro_id) && Call_check == false) {
				new CallMoney(socket,pro_id,user_id);
			}
			else {
				JOptionPane.showMessageDialog(null, "방장이거나 마감시간이 끝날 경우 호가 하실 수 없습니다. ");
			}
		}
		else if(e.getSource() == Search) {
			
		}
	}
	

}
