/*
 *   DB Connection�� ��� ���õ� �ڿ��� �ݴ� �ߺ��� �ڵ带 �����ϱ� ���� Ŭ���� 
 */

package com.diary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.diary.db.ConnectionManager;
import com.diary.util.StringUtil;

public class Main extends JFrame {
	private ConnectionManager connectionManager;
	private Connection con;

	Directory directory;
	Search search;
	Note note;

	// �޴��� ����
	String[] menuTitle = { "�ۼ�", "��ȸ" };
	JMenu[] menu = new JMenu[menuTitle.length];
	JMenuBar bar;

	// ������ ����
	JPanel p_container, p_west, p_center, p_east;

	public Main() {
		// db����
		connectionManager = new ConnectionManager();
		con = connectionManager.connect();

		// �޴��� ���� �� ����
		bar = new JMenuBar();
		for (int i = 0; i < menuTitle.length; i++) {
			// �޴��̸�
			menu[i] = new JMenu(menuTitle[i]);
			menu[i].setPreferredSize(new Dimension(200, 100));
			menu[i].setFont(new Font("����ü", Font.BOLD, 50));
			bar.add(menu[i]);
		}
		setJMenuBar(bar);

		// �г� ���� �� ������
		p_container = new JPanel();
		//p_container.setBackground(Color.yellow);

		p_west = new Directory(this);
		p_west.setPreferredSize(new Dimension(400, 850));
		p_west.setBackground(Color.pink);
		p_center = new JPanel();
		p_center.setPreferredSize(new Dimension(500, 850));
		p_east = new JPanel();
		p_east.setPreferredSize(new Dimension(500, 850));

		note = new Note(this);
		search = new Search(this);

		p_east.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		p_center.add(search);
		p_east.add(note);

		// ����
		p_container.setLayout(new BorderLayout(0, 0));
		p_container.add(p_west, BorderLayout.WEST);
		p_container.add(p_center);
		p_container.add(p_east, BorderLayout.EAST);
		getContentPane().add(p_container);

		// ������ ������ �����ϱ�!!
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				connectionManager.closeDB(con);
				System.exit(0);
			}
		});

		setTitle("MyDiary");
		setSize(1400, 1000);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// getter!! -> ���� ������ �������� ����
	public Connection getCon() {
		return con;
	}

	// getter!! -> ���� ������ �������� ����
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public Search getSearch() {
		return search;
	}

	public Note getNote() {
		return note;
	}

	// ������� ��ϵ� card�����ִ� �޼���
	public void getData() {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sb = new StringBuffer();

		sb.append(
				"select registNo ,startDay ,place ,title ,content ,image ,category, to_char(FINISHTIME-STARTTIME) as diff, total ");
		sb.append(" from regist");

		search.p_south.removeAll(); // ī�� ���̱��� ���� ī�� ��� ����

		try {
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("������� ���� ����--1");
				
				String registNo = rs.getString("registNo"); // ��Ϲ�ȣ
				String startDay = rs.getString("startDay"); // �� ������
				String place = rs.getString("place"); // ���
				String title = rs.getString("title"); // ����
				String content = rs.getString("content"); // ����
				String image = rs.getString("image"); // ����
				String category = rs.getString("category"); // ī�װ�

				String txt = rs.getString("diff"); // ���� �ð�
				String time = StringUtil.getHHMM(txt);
				
				int total=rs.getInt("total"); //�ѱݾ��� ����
				
				Card card = new Card(this, registNo, startDay, place, title, content, image, category, time, total);

				search.p_south.add(card);
				search.p_south.updateUI();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeDB(pstmt, rs);
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}