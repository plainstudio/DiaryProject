/*
 *   DB Connection�� ��� ���õ� �ڿ��� �ݴ� �ߺ��� �ڵ带 �����ϱ� ���� Ŭ���� 
 */

package com.diary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.diary.db.ConnectionManager;
import com.diary.util.StringUtil;

public class Regist extends JFrame {
	Main main;
	Search search;
	
	// db
	ConnectionManager connectionManager;
	Connection con;
	

	// container ����
	JPanel p_form, p_diary;
	JPanel p_north, p_center;
	JPanel p_left, p_right;

	// ������ ����
	JLabel la_registNo; // ��Ϲ�ȣ
	JLabel la_registDate; // ��� ��¥
	JLabel la_total; // �� �հ�
	JLabel la_img; //�̹���
	JLabel la_startDate; // ���� ��¥
	JLabel la_finishDate; // ������ ��¥
	JLabel la_startTime; // ���� �ð�
	JLabel la_finishTime; // ��ģ �ð�
	JLabel la_money; // �⺻ �ñ�
	JLabel la_place; // ���

	JTextField t_startDate;
	JTextField t_finishDate;
	JTextField t_startTime;
	JTextField t_finishTime;
	JTextField t_money;
	JTextField t_place;

	JTextField t_title;
	JTextArea t_content;
	JScrollPane scroll;
	
	JButton bt_regist,bt_cancel, bt_money ;
	JFileChooser chooser;
	
	//����
	ImageIcon icon;
	Image img;
	String path;
	
	JButton bt_get,bt_remove;
	
	
	int registNo; //��Ϲ�ȣ
	int total;// �� �ݾ�
	boolean check=false; //�ѱݾ� ��ư �������� ����

	public Regist(Main main,Search search) {
		this.main = main;
		this.search=search;
		
		// container
		p_form = new JPanel();
		p_form.setPreferredSize(new Dimension(480, 350));
		p_diary = new JPanel();
		p_diary.setPreferredSize(new Dimension(480, 350));
		p_north = new JPanel();
		p_north.setPreferredSize(new Dimension(480, 80));
		p_north.setBackground(Color.pink);
		p_center = new JPanel();
		p_center.setLayout(new GridLayout(1, 2));
		p_center.setPreferredSize(new Dimension(480, 270));
		p_left=new JPanel();
		//p_left.setBackground(Color.white);
		p_right=new JPanel();
		//p_right.setBackground(Color.pink);
		
		//���� ����
		la_img = new JLabel("����");
		la_img.setHorizontalAlignment(SwingConstants.CENTER);
		la_img.setPreferredSize(new Dimension(220, 200));
		
		// component
		registNo=(int)System.currentTimeMillis(); //��Ϲ�ȣ
		
		java.util.Date beforeDate = new java.util.Date(); //��� ��¥
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy.MM.dd ", Locale.KOREA );
		String date = format.format ( beforeDate );
		

		la_registNo = new JLabel("��� ��ȣ : "+registNo);
		la_registDate = new JLabel("��� ��¥ : "+date);
		la_total = new JLabel("�� �ݾ� : "); /////////////la_total ������Ʈ ui ���ֱ�////////////////////

		la_startDate = new JLabel("���� ��¥ : ");
		la_startDate.setBounds(10,10,100,40);
		la_finishDate = new JLabel("��ģ ��¥ : ");
		la_finishDate.setBounds(10,50,100,40);
		la_startTime = new JLabel("���� �ð� : ");
		la_startTime.setBounds(10,90,100,40);
		la_finishTime = new JLabel("��ģ �ð� : ");
		la_finishTime.setBounds(10,130,100,40);
		la_money = new JLabel("�⺻ �ñ�  : ");
		la_money.setBounds(10,170,100,40);
		la_place = new JLabel("�� �� : ");
		la_place.setBounds(10,210,100,40);

		t_money = new JTextField(20);
		t_money.setBounds(80,175,120,30);
		t_place = new JTextField(20);
		t_place.setBounds(80,215,120,30);

		// ��¥
		t_startDate=new JTextField();
		t_startDate.setBounds(80,15,120,30);
		t_finishDate=new JTextField();
		t_finishDate.setBounds(80,55,120,30);
		t_startTime=new JTextField();
		t_startTime.setBounds(80,95,120,30);
		t_finishTime=new JTextField();
		t_finishTime.setBounds(80,135,120,30);

		// �ϴ� ����
		t_title = new JTextField();
		t_title.setPreferredSize(new Dimension(450,30));
		t_content = new JTextArea();
		scroll = new JScrollPane(t_content);
		scroll.setPreferredSize(new Dimension(450,220));
		bt_regist=new JButton("���");
		bt_regist.setPreferredSize(new Dimension(100,30));
		bt_cancel=new JButton("���");
		bt_cancel.setPreferredSize(new Dimension(100,30));
		bt_money=new JButton("�ݾ׺���");
		bt_money.setPreferredSize(new Dimension(100,30));

		//component ������ �� ����
		la_registNo.setPreferredSize(new Dimension(170,30));
		la_registNo.setFont(new Font("MD����ü", Font.BOLD, 15));
		la_registDate.setPreferredSize(new Dimension(170,30));
		la_registDate.setFont(new Font("MD����ü", Font.BOLD, 15));
		la_total.setPreferredSize(new Dimension(150,30));
		la_total.setFont(new Font("MD����ü", Font.BOLD, 15));
		bt_get=new JButton("���� �ֱ�");
		bt_get.setBounds(0,200,100,40);
		bt_remove=new JButton("���� ����");
		bt_remove.setBounds(130,200,100,40);
		
		// ����
		p_north.add(la_registNo);
		p_north.add(la_registDate);
		p_north.add(la_total);
		p_north.add(bt_money);
		
		p_left.add(la_img);
		p_left.add(bt_get);
		p_left.add(bt_remove);
		
		p_right.setLayout(null);
		p_right.add(la_startDate);
		p_right.add(la_finishDate);
		p_right.add(la_startTime);
		p_right.add(la_finishTime);
		p_right.add(la_money);
		p_right.add(la_place);
		p_right.add(t_startDate);
		p_right.add(t_finishDate);
		p_right.add(t_startTime);
		p_right.add(t_finishTime);
		p_right.add(t_money);
		p_right.add(t_place);
		
		p_center.add(p_left);
		p_center.add(p_right);
		p_form.add(p_north);
		p_form.add(p_center);
		p_diary.add(t_title);
		p_diary.add(scroll);
		p_diary.add(bt_regist);
		p_diary.add(bt_cancel);
		
		setLayout(new BorderLayout(0, 0));
		add(p_form, BorderLayout.NORTH);
		add(p_diary, BorderLayout.CENTER);
		
		bt_money.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				check=true;
				getTotal();
			}
		});
		bt_get.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
					getPhoto();
			}
		});
		bt_remove.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int result=JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?");
				if(result==JOptionPane.OK_OPTION) {
					deletePhoto();
				}
			}
		});
		bt_regist.addActionListener((e)->{
			if(check) {
				sendDB();//�ۼ��� ���� db�� ����
				main.getData();//����� ���� ���̱� >�ѱݾ�>����>ī�� �Ѱܼ� ���̱�....
				closeRegist(); //â�ݱ�
			}else {
				JOptionPane.showMessageDialog(null, "�� �ݾ� ��ư�� �����ּ���!!");
			}
		});
		bt_cancel.addActionListener((e)->{
			closeRegist();
		});
		
		setTitle("���� �ۼ�");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	//������ ����ϴ� �޼���
	public void getPhoto() {
		chooser=new JFileChooser("C:/java_developer/JavaSE/DiaryProject/res");
		int result=chooser.showOpenDialog(null);
		if(result==JFileChooser.APPROVE_OPTION) {
			File file=chooser.getSelectedFile();
			path=file.getAbsolutePath();
			
			icon = new ImageIcon(path);
			img = icon.getImage().getScaledInstance(220, 160, Image.SCALE_REPLICATE);
			ImageIcon thumb = new ImageIcon(img);
			la_img.setIcon(thumb); 
			la_img.updateUI();
		}
	}
	//���� �����ϴ� �޼���
	public void deletePhoto() {
		path="";
		icon = new ImageIcon(path);
		img = icon.getImage().getScaledInstance(220, 160, Image.SCALE_REPLICATE);
		ImageIcon thumb = new ImageIcon(img);
		la_img.setIcon(thumb); 
		la_img.updateUI();
	}
	//�� �ݾ��� ���ϴ� �޼���
	public void getTotal() {
		
		String money=t_money.getText();
		String startTime=t_startTime.getText();
		String finishTime=t_finishTime.getText();
		
		//1. �ñ��� ������ ���> ���ѽð� ������ ��� > ���� x
		boolean isNull=money.equals("") || startTime.equals("") || finishTime.equals("");

		if(isNull) {	//�ؽ�Ʈ �ڽ��� ��ä���ٸ�
			JOptionPane.showMessageDialog(null, "���۽ð�, ��ģ�ð�, �ñ��� �־��ּ���!!!");
		}else {
			int mmoney=(Integer.parseInt(money));//���ڷ� ��ȯ�� �ñ�	
			int hourMoney=(mmoney/60); //�д� �ݾ�
			
			//�� ���� �ð� ���ϱ�
			String time=getTime();
			int totalMin=StringUtil.getMM(time); //������ �ٲٱ�
			
			total=totalMin*hourMoney; //���� ��*�ñ�
			
			System.out.println("�Ѿ� ��ư ��������:" + total);
			
			la_total.setText("�� �ݾ�: "+(Integer.toString(total))+" ��"); // �� �հ�� �����ֱ�
			la_total.updateUI();
			
		}
	}
	//�� �ð� ���ϴ� �޼���
	public String getTime() {
		String diffmin=StringUtil.getTime(t_finishTime.getText(), t_startTime.getText());
		return diffmin;
	}
	public void closeRegist() {
		System.out.println("closeRegist()ȣ��!!");
		setVisible(false);
		t_money.setText("");
		t_place.setText("");
		t_title.setText("");
		t_content.setText("");
	}
	public void sendDB() {
		Connection con = main.getCon();
		PreparedStatement pstmt = null;
		
		StringBuffer sb = new StringBuffer();

		String startTime =t_startTime.getText();
		String finishTime =t_finishTime.getText();
		
		sb.append("insert into regist(regist_id ,registNo ,registDate,startDay,finishDay,STARTTIME,FINISHTIME,money,place,title,content,image,category,total)");
		sb.append(" values(seq_regist.nextval,?,sysdate,?,?,to_timestamp('01/01/01 "+startTime+":00', 'yy/mm/dd HH24:MI:SS') ");
		sb.append(",to_timestamp('01/01/01 "+finishTime+":00', 'yy/mm/dd HH24:MI:SS'),?,?,?,?,?,?,?)");
		
		
		String startDay =t_startDate.getText();
		String finishDay =t_finishDate.getText();	
		int money=(Integer.parseInt(t_money.getText()));
		String place =t_place.getText();
		String title =t_title.getText();
		String content =t_content.getText();
		String image =path;
		String category ="@@@@>****";
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, registNo ); // registNo
			pstmt.setString(2, startDay);// startDay
			pstmt.setString(3,finishDay );// finishDay
			pstmt.setInt(4, money);// money
			pstmt.setString(5,place );// place
			pstmt.setString(6,title );// title
			pstmt.setString(7,content );// content
			pstmt.setString(8,image );// image
			pstmt.setString(9,category );// category
			pstmt.setInt(10,total );// total

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			main.getConnectionManager().closeDB(pstmt);
		}
	}
}