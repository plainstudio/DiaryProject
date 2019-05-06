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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.diary.db.ConnectionManager;
import com.diary.util.StringUtil;

public class Note extends JPanel {
	Main main;

	JPanel p_north, p_center, p_south;
	JLabel la_date, la_registNo, la_title, la_content, la_img;

	// 오른쪽 그림
	String path ;
	ImageIcon icon;
	Image img;

	public Note(Main main) {
		System.out.println("note생성");
		this.main = main;

		// container
		p_north = new JPanel();
		p_north.setBackground(Color.PINK);
		p_north.setPreferredSize(new Dimension(450, 50));
		p_center = new JPanel();
		p_center.setBackground(Color.pink);
		p_center.setPreferredSize(new Dimension(450, 60));
		p_south = new JPanel();
		p_south.setBackground(Color.pink);
		p_south.setPreferredSize(new Dimension(450, 560));

		//사진
		la_img = new JLabel();
		la_img.setPreferredSize(new Dimension(400, 230));
		
		la_date = new JLabel();
		la_date.setPreferredSize(new Dimension(230, 30));
		la_date.setFont(new Font("MD개성체", Font.BOLD, 15));
		la_registNo = new JLabel();
		la_registNo.setPreferredSize(new Dimension(150, 30));
		la_registNo.setFont(new Font("MD개성체", Font.BOLD, 15));
		la_title = new JLabel();
		la_title.setPreferredSize(new Dimension(450, 50));
		la_title.setFont(new Font("MD개성체", Font.BOLD, 30));
		la_content = new JLabel();
		la_content.setPreferredSize(new Dimension(450, 30));
		la_content.setFont(new Font("MD개성체", Font.BOLD, 30));

		p_north.add(la_date);
		p_north.add(la_registNo);
		p_center.add(la_img);
		p_south.add(la_title);
		p_south.add(la_content);

		setLayout(new BorderLayout(0, 0));
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(p_south, BorderLayout.SOUTH);

		setPreferredSize(new Dimension(500, 850));
		//setBackground(Color.PINK);

	}

	public void setContent(String registNo,String startDay,String title,String content, String path) {
		String afterStartDay=StringUtil.getDate(startDay);
		la_date.setText("날짜: "+afterStartDay);
		la_registNo.setText("등록번호: "+registNo);
		la_title.setText("제목: " +title);
		la_content.setText("내용: "+content);
		
		// 사진
		icon = new ImageIcon(path);
		img = icon.getImage().getScaledInstance(400, 230, Image.SCALE_REPLICATE);
		ImageIcon thumb = new ImageIcon(img);
		la_img.setIcon(thumb); 
		la_img.updateUI();
	}
}