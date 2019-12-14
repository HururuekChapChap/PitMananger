package Main_Pack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Board extends JPanel{
	
	JLabel product_pic;
	JButton product_name;
	JLabel product_end;
	
	Image img = null;
	Date d1;
	Date d2;
	String s = System.getProperty("user.dir");
	
	Board(int y){
		setLayout(null);
		setBackground(Color.PINK);
		setBorder(new LineBorder(Color.WHITE,1));
		setBounds(5,y,290,95);
		
		// 시간 출력 방식을 정하는 SimpleDateFormat 과 시간을 정해주는 parse 함
		SimpleDateFormat f= new SimpleDateFormat("HH:mm:ss");
		try {
			 d1 = f.parse("01:05:10");
			 d2 = f.parse("01:07:10");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// 시간 계산하는 방법 
		long minut = d2.getTime() - d1.getTime();
		
		//시: 60*60*1000 분:60*1000 초:1000 
		System.out.println(minut/(60*1000));
		
		System.out.println(f.format(d1));
		
		try {
			File source = new File(s+"/1.png");
			img = ImageIO.read(source).getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("이미지없음 ");
		}
		
		product_pic = new JLabel(new ImageIcon(img));
		product_pic.setBounds(15, 13, 70, 70);
		
		product_name = new JButton("경매물품_이름 ");
		product_name.setFont(new Font("맑은고딕 ", Font.BOLD, 15));
		product_name.setBounds(100,13,150,35);
		
		product_end = new JLabel("마감시간: " + f.format(d1));
		product_end.setBounds(105, 50, 150, 35);
		
		add(product_pic);
		add(product_name);
		add(product_end);
		
	}
	
}
