package Main_Pack;

import java.awt.Color;
import java.awt.Image;
import java.awt.Panel;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Chat_Panel extends JPanel implements ActionListener{
	
	private JButton Send_Text = new JButton("보내기 ");
	private JButton Exit = new JButton("나가기 ");
	public static JTextArea Chat_Panel_TextArea = new JTextArea();
	private JTextField textField = new JTextField();
	
    private JLabel Name;
    private JLabel Pic;
    
    Image img = null;
	String s = System.getProperty("user.dir");
    
    public static String user ="";
    public static String name ="";
    
    Main frame;
    Start_Pack.SendThread send;
    
	Chat_Panel(Main frame, Socket socket, String user, String name, String pic){
		
		setLayout(null);
		setBackground(Color.PINK);
		setBorder(new LineBorder(Color.white,3));
		setBounds(10,10,280,460);
		
		this.user = user;
		this.name = name;
		this.frame = frame;
		send = new Start_Pack.SendThread(socket);
		
		if(frame.start_page > 0) {
			send.sendMessage("getINFO/!1@2#3/" + "1");
			new ReceiveData(1);
		}
		
		try {
			File source = new File(s+"/"+pic +".png");
			img = ImageIO.read(source).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("이미지없음 ");
		}
		
		Pic = new JLabel(new ImageIcon(img));
		Pic.setBounds(10, 10, 50, 50);
		
		Name = new JLabel(name);
		Name.setBounds(75, 10, 100, 50);
		
		Exit.setBounds(200, 10, 70, 50);
		Exit.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane(Chat_Panel_TextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		Chat_Panel_TextArea.append("방에 입장하셨습니다. \n");
		Chat_Panel_TextArea.setEditable(false);
		scrollPane.setBounds(10,70,260,350);
		
		textField.setBounds(8, 422, 210, 33);
		Send_Text.setBounds(215,422,60,33);
		Send_Text.addActionListener(this);
		
		add(Pic);
		add(Name);
		add(Exit);
		add(scrollPane);
		add(textField);
		add(Send_Text);
	}
	
public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == Exit) {
			Chat_Panel.name = "";
			Chat_Panel_TextArea.selectAll();
			Chat_Panel_TextArea.replaceSelection("");
			frame.BackToMain();
		}
		else if(e.getSource() == Send_Text && !textField.getText().equals("")) {
			String temp_Text = textField.getText();
			Chat_Panel_TextArea.append(temp_Text +"\n");
			textField.setText("");
			send.sendMessage("Single_Chat/!1@2#3/" + name +"/!1@2#3/" + user +"/!1@2#3/" +temp_Text );
		}
		
	}
}
