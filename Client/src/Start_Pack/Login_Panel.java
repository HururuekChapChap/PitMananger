package Start_Pack;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login_Panel extends JPanel implements ActionListener {

	private JButton button = new JButton("로그인");
	private JButton Admin = new JButton("회원가입 ");
	private JTextField ID = new JTextField();
	private JTextField PASS = new JTextField();
	private JLabel la_ID = new JLabel("아이디 ");
	private JLabel la_PASS = new JLabel("비밀번호 ");
	
	SendThread send;
	Socket socket;
	Frame frame;
	
	public Login_Panel(Socket socket, Frame frame) {
		
		send = new SendThread(socket);
		this.frame = frame;
		
		setLayout(null);
		setBackground(Color.PINK);
		setBounds(0,0,300,500);
		

		ID.addActionListener(this);
		PASS.addActionListener(this);
		button.addActionListener(this);
		Admin.addActionListener(this);

		la_ID.setFont(new Font("맑은고딕 ", Font.BOLD, 15));
		la_PASS.setFont(new Font("맑은고딕 ", Font.BOLD, 12));
		ID.setBounds(70, 150, 200, 50);
		PASS.setBounds(70, 200, 200, 50);
		la_ID.setBounds(15, 150, 50, 50);
		la_PASS.setBounds(15,200, 50, 50);
		button.setBounds(40,270,100,50);
		Admin.setBounds(170,270,100,50);
		
		
		
		add(la_ID);
		add(la_PASS);
		add(ID);
		add(PASS);
		add(button);
		add(Admin);
		
	}
	
public void actionPerformed(ActionEvent e) {
	
	
		
		if(e.getSource() == Admin) {
			
			frame.ChangeToAdmin();
			
		}
		else if(e.getSource() == button && !(ID.getText().equals("")) && !(PASS.getText().equals(""))) {
			send.sendMessage("LOGIN/!1@2#3/"+ID.getText() + "/!1@2#3/" + PASS.getText());
			ID.setText("");
			PASS.setText("");
		}
		
	}
	
}

