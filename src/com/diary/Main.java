/*
 *   DB Connection을 얻고 관련된 자원을 닫는 중복된 코드를 방지하기 위한 클래스 
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

	// 메뉴바 관련
	String[] menuTitle = { "작성", "조회" };
	JMenu[] menu = new JMenu[menuTitle.length];
	JMenuBar bar;

	// 디자인 관련
	JPanel p_container, p_west, p_center, p_east;

	public Main() {
		// db접속
		connectionManager = new ConnectionManager();
		con = connectionManager.connect();

		// 메뉴바 생성 및 부착
		bar = new JMenuBar();
		for (int i = 0; i < menuTitle.length; i++) {
			// 메뉴이름
			menu[i] = new JMenu(menuTitle[i]);
			menu[i].setPreferredSize(new Dimension(200, 100));
			menu[i].setFont(new Font("돋움체", Font.BOLD, 50));
			bar.add(menu[i]);
		}
		setJMenuBar(bar);

		// 패널 생성 및 디자인
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

		// 부착
		p_container.setLayout(new BorderLayout(0, 0));
		p_container.add(p_west, BorderLayout.WEST);
		p_container.add(p_center);
		p_container.add(p_east, BorderLayout.EAST);
		getContentPane().add(p_container);

		// 윈도우 리스너 구현하기!!
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

	// getter!! -> 남이 내꺼를 가져가게 해줌
	public Connection getCon() {
		return con;
	}

	// getter!! -> 남이 내꺼를 가져가게 해줌
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public Search getSearch() {
		return search;
	}

	public Note getNote() {
		return note;
	}

	// 현재까지 등록된 card보여주는 메서드
	public void getData() {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sb = new StringBuffer();

		sb.append(
				"select registNo ,startDay ,place ,title ,content ,image ,category, to_char(FINISHTIME-STARTTIME) as diff, total ");
		sb.append(" from regist");

		search.p_south.removeAll(); // 카드 붙이기전 기존 카드 모두 삭제

		try {
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("여기까지 오류 없음--1");
				
				String registNo = rs.getString("registNo"); // 등록번호
				String startDay = rs.getString("startDay"); // 일 시작일
				String place = rs.getString("place"); // 장소
				String title = rs.getString("title"); // 제목
				String content = rs.getString("content"); // 내용
				String image = rs.getString("image"); // 사진
				String category = rs.getString("category"); // 카테고리

				String txt = rs.getString("diff"); // 일한 시간
				String time = StringUtil.getHHMM(txt);
				
				int total=rs.getInt("total"); //총금액을 구함
				
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