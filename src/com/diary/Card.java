/*
 *   DB Connection을 얻고 관련된 자원을 닫는 중복된 코드를 방지하기 위한 클래스 
 */

package com.diary;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.diary.db.ConnectionManager;
import com.diary.util.StringUtil;

public class Card extends JPanel {
	Main main;

	// 생성정보
	String registNo; //등록번호
	String startDay; //시작일
	String place; //장소
	String title; //제목
	String content; //내용
	String path; // 사진 경로
	String category;  //분류
	String time; //일한 시간
	int total;//총 금액

	// db
	ConnectionManager connectionManager;
	Connection con;

	// container
	JPanel p_north, p_center, p_south, p_info;
	JLabel la_category, la_registNo, la_title, la_date, la_time, la_place, la_total, la_img,la_del;

	// 사진
	ImageIcon icon;
	Image img;

	public Card(Main main, String registNo, String startDay, String place, String title, String content,
			String image, String category, String time, int total) {		
		this.main = main;
		this.registNo = registNo;
		this.startDay = startDay;
		this.place = place;
		this.title = title;
		this.content = content;
		this.path = image;
		this.category = category;
		this.time=time;
		this.total=total;

		// container
		p_north = new JPanel();
		p_north.setBackground(Color.yellow);
		p_north.setPreferredSize(new Dimension(400, 40));
		p_center = new JPanel();
		p_center.setBackground(Color.yellow);
		p_center.setPreferredSize(new Dimension(400, 250));
		p_south = new JPanel();
		p_south.setBackground(Color.yellow);
		p_south.setPreferredSize(new Dimension(400, 50));
		p_info = new JPanel();
		p_info.setBackground(Color.yellow);
		p_info.setPreferredSize(new Dimension(200, 250));

		// component
		la_category = new JLabel(category);
		la_registNo = new JLabel("등록번호: " + registNo);
		la_title = new JLabel("제목 : " + title);
		la_date = new JLabel("날짜 : " + StringUtil.getDate(startDay));
		la_time = new JLabel("총 시간 : "+time);
		la_place = new JLabel("장 소 : " + place);
		la_total = new JLabel("총 금액 : "+total);////////////////
		la_del=new JLabel("X");
		
		// 사진
		icon = new ImageIcon(path);
		img = icon.getImage().getScaledInstance(150, 160, Image.SCALE_REPLICATE);
		ImageIcon thumb = new ImageIcon(img);
		la_img = new JLabel(thumb);
		la_img.setPreferredSize(new Dimension(150, 160));

		// component 사이즈 및 서식
		la_category.setPreferredSize(new Dimension(230, 30));
		la_category.setFont(new Font("MD개성체", Font.BOLD, 15));
		la_registNo.setPreferredSize(new Dimension(160, 30));
		la_registNo.setFont(new Font("MD개성체", Font.BOLD, 15));
		la_title.setPreferredSize(new Dimension(250, 30));
		la_title.setFont(new Font("MD개성체", Font.BOLD, 15));
		la_date.setPreferredSize(new Dimension(250, 30));
		la_date.setFont(new Font("MD개성체", Font.BOLD, 15));
		la_time.setPreferredSize(new Dimension(250, 30));
		la_time.setFont(new Font("MD개성체", Font.BOLD, 15));
		la_place.setPreferredSize(new Dimension(250, 30));
		la_place.setFont(new Font("MD개성체", Font.BOLD, 15));
		la_total.setPreferredSize(new Dimension(350, 30));
		la_total.setFont(new Font("MD개성체", Font.BOLD, 30));
		la_del.setPreferredSize(new Dimension(30, 30));
		la_del.setFont(new Font("MD개성체", Font.BOLD, 30));

		p_north.add(la_category);
		p_north.add(la_registNo);
		p_info.add(la_title);
		p_info.add(la_date);
		p_info.add(la_time);
		p_info.add(la_place);
		p_south.add(la_total);
		p_south.add(la_del);
		p_center.setLayout(new BorderLayout(0, 0));
		p_center.add(la_img, BorderLayout.WEST);
		p_center.add(p_info);
		setLayout(new BorderLayout(0, 0));
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(p_south, BorderLayout.SOUTH);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				main.getNote().setContent(registNo,startDay,title,content,path);
			}
		});
		la_del.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int result=JOptionPane.showConfirmDialog(null, "카드를 삭제하시겠습니까?");
				if(result==JOptionPane.OK_OPTION) {
					deleteCard();
				}
			}
		});

		setPreferredSize(new Dimension(450, 250));
		setBackground(Color.WHITE);
	}
	//화면에서 카드 삭제
	public void deleteCard() {
		main.search.p_south.remove(this);
		main.search.p_south.updateUI();
		
		deleteData();
	}
	//실제 DB에서 데이터 삭제
	public void deleteData() {
		Connection con = main.getCon();
		PreparedStatement pstmt = null;
		
		StringBuffer sb = new StringBuffer();

		sb.append("delete from regist where registNo=?");
		System.out.println(sb.toString());
		System.out.println(registNo);
	
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, registNo);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			main.getConnectionManager().closeDB(pstmt);
		}
	}
}