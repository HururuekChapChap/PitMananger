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

public class CallMoney extends JFrame implements ActionListener{

	private JTextArea information = new JTextArea();
	private JTextField money = new JTextField();
	private JButton button = new JButton("호가 하기 ");
	
	Start_Pack.SendThread send;
	String pro_id = null;
	String user = null;
	
	CallMoney(Socket socket, String pro_id, String user){
		
		this.setSize(300, 220);
		this.setLayout(null);
		this.setTitle("메인 화면 ");
		
		this.getContentPane().setBackground(Color.PINK);
		
		send = new Start_Pack.SendThread(socket);
		this.pro_id = pro_id;
		this.user = user;
		
		information.append("\n 주의사항\n\n 한번 호가 버튼을 누르 되돌릴 수 없습니다. \n\n"
				+ " 호가 단위는 1000원 단위 입니다. \n\n"
				+ " 지나친 경매는 건강에 해로울 수 있습니다.\n");
		
		information.setEditable(false);
		information.setBorder(new LineBorder(Color.red,2));
		information.setForeground(Color.blue);
		information.setFont(new Font("Segoe Script", Font.BOLD, 13));
		information.setBounds(10,10,280,150);
		
		money.setBounds(8,160,200,30);
		button.setBounds(205,160,90,31);
		button.addActionListener(this);
		
		add(information);
		add(money);
		add(button);
	
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == button && !(money.getText().equals("")) ) {
			String text = money.getText();
			int num = Integer.parseInt(text);
			String[] text_price = Main_Pack.Group_Chat.Price.getText().split(" | ");
			
			for(int i = 0; i< text_price.length; i++) {
				System.out.println(text_price[i]);
			}
			
			
			if(num %1000 == 0 && Integer.parseInt(text_price[2]) < num ) {
				send.sendMessage("CALLMONEY/!1@2#3/"+pro_id +"/!1@2#3/"+user+"/!1@2#3/"+money.getText());
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "호가가 천원 단위가 아닙니다. ");
			}
			
		}
		
	}
	
}
