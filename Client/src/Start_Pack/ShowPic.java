package Start_Pack;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShowPic extends JFrame {

	JPanel panel;
	
	ShowPic(){
		this.setTitle("프로필 사진 선택 ");
		this.setSize(560,200);
		
		panel = new PicPanel(this);
		
		this.add(panel);
		setVisible(true);
		
	}
	
}

class PicPanel extends JPanel implements ActionListener{
	
	JButton text, text2, text3, text4, text5;
	Image img = null, img2 =null, img3 = null, img4 = null, img5 = null;
	String s = System.getProperty("user.dir");
	ShowPic frame;
	
	PicPanel(ShowPic frame){
		setLayout(null);
		setBackground(Color.PINK);
		this.frame = frame;
		
		try {
			File source = new File(s+"/1.png");
			img = ImageIO.read(source).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("이미지없음 ");
		}
		
		try {
			File source = new File(s+"/2.png");
			img2 = ImageIO.read(source).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("이미지없음 ");
		}
		
		try {
			File source = new File(s+"/3.png");
			img3 = ImageIO.read(source).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("이미지없음 ");
		}
		
		try {
			File source = new File(s+"/4.png");
			img4 = ImageIO.read(source).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("이미지없음 ");
		}
		
		try {
			File source = new File(s+"/5.png");
			img5 = ImageIO.read(source).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("이미지없음 ");
		}
		
		text = new JButton(new ImageIcon(img));
		text2 = new JButton(new ImageIcon(img2));
		text3 = new JButton(new ImageIcon(img3));
		text4 = new JButton(new ImageIcon(img4));
		text5 = new JButton(new ImageIcon(img5));
		
		
		text.addActionListener(this);
		text2.addActionListener(this);
		text3.addActionListener(this);
		text4.addActionListener(this);
		text5.addActionListener(this);
		
		//text =new JButton("test");
		text.setBounds(10, 30, 100, 100);
		text2.setBounds(120, 30, 100, 100);
		text3.setBounds(230, 30, 100, 100);
		text4.setBounds(340, 30, 100, 100);
		text5.setBounds(450, 30, 100, 100);
		
		this.add(text);
		this.add(text2);
		this.add(text3);
		this.add(text4);
		this.add(text5);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == text) {
			Admin.setPic(1);
			System.out.println(Admin.getPic());
			frame.dispose();
		}
		else if(e.getSource() == text2) {
			Admin.setPic(2);
			frame.dispose();
		}
		else if(e.getSource() == text3) {
			Admin.setPic(3);
			frame.dispose();
		}
		else if(e.getSource() == text4) {
			Admin.setPic(4);
			frame.dispose();
		}
		else if(e.getSource() == text5) {
			Admin.setPic(4);
			frame.dispose();
		}
		
	}
	
}

