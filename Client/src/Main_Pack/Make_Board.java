package Main_Pack;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


class Make_Board_Panel extends JPanel implements ActionListener{
	
	private JLabel Product_name = new JLabel("상품 이름 : ");
	private JTextField Product_name_Text = new JTextField();
	
	private JLabel Product_deadline = new JLabel("마감 시간 : ");
	private JTextField Product_deadline_Text;
	
	private JLabel Find_File = new JLabel("이미지 첨부 : ");
	private JTextField File_name = new JTextField();
	private JButton File_Button = new JButton("파일 선택 ");
	
	private JLabel Text_label = new JLabel("한줄 상품 상세 글 ");
	private JTextField Text = new JTextField();
	
	private JButton Send = new JButton("보내기 ");
	
	Make_Board frame;
	String directory;
	String Name_Of_file;
	String id;
	
	Socket socket;
	Start_Pack.SendThread send;
	SendData sendData;
	
	Date current = new Date();
	String time;
	SimpleDateFormat f= new SimpleDateFormat("HH:mm:ss");// 시간 형식 변
	
	Make_Board_Panel(Make_Board frame, Socket socket, String id){
		
		this.frame = frame;
		this.id = id;
		this.socket = socket;
		send = new Start_Pack.SendThread(socket);
		
		setLayout(null);
		setBackground(Color.PINK);
		setBorder(new LineBorder(Color.WHITE,2));
		setBounds(10,10,380,350);
		
		Product_name.setFont(new Font("맑은 고딕 ", Font.BOLD, 15));
		Product_name.setBounds(20,15,80,50);
		
		Product_name_Text.setBounds(90,20,200,45);
		
		Find_File.setFont(new Font("맑은 고딕 ",Font.BOLD, 15));
		Find_File.setBounds(20,75,100,50);
		File_name.setBounds(105, 75, 188,45);
		File_Button.setBounds(295,75, 70,45);
		File_Button.addActionListener(this);
		
		
		time =f.format(current.getTime() + (60*60*1000));
		Product_deadline_Text = new JTextField(time); // 한시간 더하
		Product_deadline.setFont(new Font("맑은 고딕 ", Font.BOLD, 15));
		Product_deadline.setBounds(20,130,150,50);
		Product_deadline_Text.setBounds(95,130,200,45);
		
		Text_label.setFont(new Font("맑은 고딕 ", Font.BOLD, 15));
		Text_label.setBounds(120, 175, 200, 50);
		Text.setBounds(20,225,320,50);
		
		Send.setBounds(130,290,90,40);
		Send.addActionListener(this);
		
		add(Product_name);
		add(Product_name_Text);
		
		add(Find_File);
		add(File_name);
		add(File_Button);
		
		add(Product_deadline);
		add(Product_deadline_Text);
		
		add(Text_label);
		add(Text);
		
		add(Send);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == File_Button) {
			
			FileDialog file = new FileDialog(frame,"",FileDialog.LOAD);
			file.setVisible(true);
			if(file.getFile() == null ) {
				System.out.println("NO");
				File_name.setText("");
				directory = null;
				Name_Of_file = null;
			}
			else {
			directory = file.getDirectory();
			Name_Of_file = file.getFile();
			File_name.setText(directory + Name_Of_file);
			//
			try {
				sendData = new SendData(directory + Name_Of_file, Name_Of_file ,socket);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//
			System.out.println(directory);
			}
			
		}
		else if(e.getSource() == Send && !(Product_name_Text.getText().equals("")) && Name_Of_file != null && !(Text.getText().equals("") && !(Product_deadline_Text.getText().equals("")))) {
			long over = 0, less = 0 ;
			
			try {
				over = f.parse(Product_deadline_Text.getText()).getTime() - f.parse(time).getTime();
				less = f.parse(f.format(current.getTime())).getTime() - f.parse(Product_deadline_Text.getText()).getTime();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println(over + " " + less);
			
			if(over > 0 || less > -180000 ) {
				JOptionPane.showMessageDialog(null, "한시간 보다 길게 할 수 없고 최소 3분 보다 길어야합니다. ");
			}
			else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd:hh");
			
			System.out.println("Make_Board/!1@2#3/"+ id +"/!1@2#3/" + Product_name_Text.getText() + "/!1@2#3/" +sdf.format(current)+"_"+Name_Of_file + "/!1@2#3/"+ Text.getText() + "/!1@2#3/" + Product_deadline_Text.getText());
			send.sendMessage("Make_Board/!1@2#3/"+ id +"/!1@2#3/" + Product_name_Text.getText() + "/!1@2#3/" +sdf.format(current)+"_"+Name_Of_file + "/!1@2#3/"+ Text.getText() + "/!1@2#3/" + Product_deadline_Text.getText() );
			//
			
			sendData.SendDataFunc();
			
			frame.dispose();
			}
		}
	}
	
}

public class Make_Board extends JFrame {
	
	
	public Make_Board(Socket socket, String id) {
		
		setLayout(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.PINK);
		setTitle("경매글 작성 ");
		this.setSize(400,400);
		
		add(new Make_Board_Panel(this,socket,id));
		
		this.setVisible(true);
		
	}
	
	
	
}
