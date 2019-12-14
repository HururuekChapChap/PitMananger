package Main_Pack;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class AddFriends extends JFrame implements ActionListener{

	private JTextArea information = new JTextArea();
	private JTextField friend = new JTextField();
	private JButton button = new JButton("추가 하기 ");
	
	Start_Pack.SendThread send;
	
	String user;
	
	AddFriends(Socket socket,String user){
		
		send = new Start_Pack.SendThread(socket);
		this.user = user;
		
		this.setSize(300, 220);
		this.setLayout(null);
		this.setTitle("친구 추가 ");
		
		this.getContentPane().setBackground(Color.PINK);
		
		information.append("\n 주의사항\n\n 한번 추가 버튼을 누르면 되돌릴 수 없습니다. \n\n"
				+ " 개인 정보는 직접 물어봐야합니다. \n\n"
				+ " 바른말이 가야 고운 말이 옵니다.\n");
		
		information.setEditable(false);
		information.setBorder(new LineBorder(Color.red,2));
		information.setForeground(Color.blue);
		information.setFont(new Font("Segoe Script", Font.BOLD, 13));
		information.setBounds(10,10,280,150);
		
		friend.setBounds(8,160,200,30);
		button.setBounds(205,160,90,31);
		button.addActionListener(this);
		
		add(information);
		add(friend);
		add(button);
	
		this.setVisible(true);
		
	}
	
public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == button && !(friend.getText().equals("")) ) {
			String text = friend.getText();
			
			send.sendMessage("MAKE_Friend/!1@2#3/"+user+"/!1@2#3/"+text);
			
			this.dispose();
		}
		
	}
}
