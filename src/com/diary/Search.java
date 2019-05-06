/*
 *   DB Connection�� ��� ���õ� �ڿ��� �ݴ� �ߺ��� �ڵ带 �����ϱ� ���� Ŭ���� 
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.diary.db.ConnectionManager;
import com.diary.util.StringUtil;

public class Search extends JPanel {
	Main main;

	// db
	ConnectionManager connectionManager;
	Connection con;

	JPanel p_north, p_center, p_south,p_new;
	JScrollPane scroll;
	JButton bt_all, bt_search;
	JTextField t_search;

	// ���� ����ϱ� ������
	JLabel la_icon;
	String path = "C:/java_developer/JavaSE/DiaryProject/res/bt.png";
	Image img = new ImageIcon(path).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
	ImageIcon icon = new ImageIcon(img);


	
	public Search(Main main) {
		this.main = main;
		
		p_north = new JPanel();
		p_north.setPreferredSize(new Dimension(450, 50));
		//p_north.setBackground(Color.yellow);
		p_center = new JPanel();
		p_center.setPreferredSize(new Dimension(450, 90));
		//p_center.setBackground(Color.red);
		p_south = new JPanel();
		p_south.setPreferredSize(new Dimension(450, 5000));
		//p_south.setBackground(Color.green);
		scroll=new JScrollPane(p_south);
		scroll.setPreferredSize(new Dimension(450, 710));

		t_search = new JTextField();
		bt_all = new JButton("��ü��ȸ");
		bt_search = new JButton("�˻�");
		
		p_new=new JPanel();
		p_new.setPreferredSize(new Dimension(450, 80));
		p_new.setBackground(Color.pink);
		p_new.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		la_icon=new JLabel(icon);
		
		// ��ư �� �ؽ�Ʈ�ʵ� ���� / ������ ����
		Dimension d = new Dimension(100, 40);
		bt_all.setPreferredSize(d);
		bt_all.setFont(new Font("����", Font.BOLD, 15));
		t_search.setPreferredSize(new Dimension(250, 40));
		t_search.setFont(new Font("����", Font.BOLD, 30));
		bt_search.setPreferredSize(d);
		bt_search.setFont(new Font("����", Font.BOLD, 15));
		
		bt_all.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				main.getData();
			}
		});
		bt_search.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Search();
			}
		});
		p_new.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				openRegist();
			}
		});
		
		p_new.add(la_icon);
		p_north.add(bt_all);
		p_north.add(t_search);
		p_north.add(bt_search);
		p_center.add(p_new);
		setLayout(new BorderLayout(0, 0));
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(scroll, BorderLayout.SOUTH);
		
	}

	// ����ϱ� â ���� �޼���
	public void openRegist() {
		new Regist(main, this);
	}
	//�˻��ϴ� �޼���
	public void Search() {
		Connection con=main.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sb = new StringBuffer();

		sb.append("select registNo ,startDay ,place ,title ,content ,image ,category,to_char(FINISHTIME-STARTTIME) as diff, total");
		sb.append(" from regist ");
		sb.append(" where registNo like '%' || ? || '%' or startDay like '%' || ? || '%' or place like '%' || ? || '%'  ");
		sb.append(" or title like '%' || ? || '%' or content like '%' || ? || '%' or category like '%' || ? || '%' or total like '%' || ? || '%' ");  //��Ϲ�ȣ, ������, ���, ����, ���� �ȿ����� �˻���..

		System.out.println(sb.toString());
		
		String word=t_search.getText();
		
		p_south.removeAll(); //ī�� ���̱��� ���� ī�� ��� ����
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, word);
			pstmt.setString(2, word);
			pstmt.setString(3, word);
			pstmt.setString(4, word);
			pstmt.setString(5, word);
			pstmt.setString(6, word);
			pstmt.setString(7, word);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				String registNo = rs.getString("registNo"); // ��Ϲ�ȣ
				String startDay = rs.getString("startDay"); // �� ������
				String place = rs.getString("place"); // ���
				String title = rs.getString("title"); // ����
				String content = rs.getString("content"); // ����
				String image = rs.getString("image"); // ����
				String category = rs.getString("category"); // ī�װ�

				String txt=rs.getString("diff"); //���� �ð�
				String time=StringUtil.getHHMM(txt); //�úи� ����
				
				int total=rs.getInt("total"); //�ñ��� ����
				
				Card card = new Card(main, registNo, startDay, place, title, content, image, category, time, total);
				
				p_south.add(card);
				p_south.updateUI();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			main.getConnectionManager().closeDB(pstmt, rs);
		}
	}
}