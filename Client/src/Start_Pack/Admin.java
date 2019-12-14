package Start_Pack;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Admin extends JPanel implements ActionListener {
	
	private JTextField ID = new JTextField();
	private JTextField PASS = new JTextField();
	private JTextField NICK = new JTextField();
	private JPanel panel = new JPanel();
	private JButton button = new JButton("가입하기 ");
	private JButton backto = new JButton("돌아가기 ");
	private JButton PicButton = new JButton("프로필 사진 선택 ");
	private JLabel la_ID = new JLabel("아이디 ");
	private JLabel la_PASS = new JLabel("비밀번호 ");
	private JLabel la_NICK = new JLabel("닉네임 ");
	
	Frame frame;
	Socket socket;
	SendThread send;
	
	private static int Choosed = 0;
	
	Admin(Socket socket,Frame frame){
		
	this.frame = frame;
	send = new SendThread(socket);
	setLayout(null);
	setBackground(Color.PINK);
	setBounds(0,0,300,500);
	
	ID.addActionListener(this);
	PASS.addActionListener(this);
	NICK.addActionListener(this);
	
	button.addActionListener(this);
	backto.addActionListener(this);
	PicButton.addActionListener(this);
	
	la_ID.setFont(new Font("맑은고딕 ", Font.BOLD, 15));
	la_PASS.setFont(new Font("맑은고딕 ", Font.BOLD, 12));
	la_NICK.setFont(new Font("맑은고딕 ", Font.BOLD, 15));
	la_ID.setBounds(15, 100, 50, 50);
	la_PASS.setBounds(15,150, 50, 50);
	la_NICK.setBounds(15,200,50,50);
	
	
	ID.setBounds(70, 100, 200, 50);
	PASS.setBounds(70,150, 200,50);
	NICK.setBounds(70,200, 200 ,50);
	
	PicButton.setBounds(70,250,200,50);
	button.setBounds(70, 320, 90, 50);
	backto.setBounds(170,320,90,50);
	
	add(la_ID);
	add(la_PASS);
	add(la_NICK);
	
	
	add(ID);
	add(PASS);
	add(NICK);
	
	add(button);
	add(PicButton);
	add(backto);
	
	}
	
	public static void setPic(int Choosed) {
		Admin.Choosed = Choosed;
	}
	
	public static int getPic() {
		return Admin.Choosed;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource() == backto) {
			frame.ChangeToLogin();
			// 송신과 화면변경은 같은 함수 안에 넣을 수가 없기 때문에, 리드쓰레드에서 변경해주던가, 아니면 이렇게 
			// 나눠서 해줘야 한다.
		}
		else if(e.getSource() == PicButton) {
			new ShowPic();
		}
		else if(e.getSource() == button){
			
			if(!(ID.getText().equals("") ) && !(PASS.getText().equals("") ) 
				&& !(NICK.getText().equals("")) && Choosed != 0 ) {
		
		send.sendMessage("ADMIN/!1@2#3/"+ID.getText()+"/!1@2#3/"+PASS.getText()+"/!1@2#3/"+NICK.getText()+"/!1@2#3/"+Choosed);
		ID.setText("");
		PASS.setText("");
		NICK.setText("");
		//frame.ChangeToLogin();
			}
			else {
				JOptionPane.showMessageDialog(null, "미입력 칸이 있거나 프로필 사진 등록을 하지 않았습니다. ");
			}
		}
		
	}

	
}
