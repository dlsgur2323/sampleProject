package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class BoardDao {

	private static BoardDao instance;
	private BoardDao(){}
	public static BoardDao getInstance(){
		if(instance == null){
			instance = new BoardDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectBoardList(){
		String sql = "SELECT A.BOARD_NO, A.TITLE, A.CONTENT, B.USER_NAME, A.REG_DATE"
				+ " FROM TB_JDBC_BOARD A"
				+ " LEFT OUTER JOIN TB_JDBC_USER B"
				+ " ON A.USER_ID = B.USER_ID"
				+ " ORDER BY A.BOARD_NO DESC";
		return jdbc.selectList(sql);
	}
	public Map<String, Object> selectBoardView(int boardno) {
		String sql = "SELECT A.BOARD_NO, A.TITLE, A.CONTENT, B.USER_NAME, A.REG_DATE"
				+ " FROM TB_JDBC_BOARD A"
				+ " LEFT OUTER JOIN TB_JDBC_USER B"
				+ " ON A.USER_ID = B.USER_ID"
				+ " WHERE A.BOARD_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(boardno);
		
		return jdbc.selectOne(sql, param);
	}
	
	
	public int insertBoard(String title, String content) {		
		String sql = "INSERT INTO TB_JDBC_BOARD (BOARD_NO, TITLE, CONTENT, USER_ID, REG_DATE)"
				+ " VALUES ((SELECT NVL(MAX(BOARD_NO), 0)+1 FROM TB_JDBC_BOARD), ?, ?, ?, SYSDATE)";
		List<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(Controller.loginUser.get("USER_ID"));
		
		return jdbc.update(sql, param);
	}
	
	public int deleteBoard(int boardno) {
		String sql = "DELETE TB_JDBC_BOARD WHERE BOARD_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(boardno);
		
		return jdbc.update(sql, param);
	}
	public int updateTitle(int boardno, String title) {
		String sql = "UPDATE TB_JDBC_BOARD SET TITLE = ?"
				+ "WHERE BOARD_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(title);
		param.add(boardno);
		
		return jdbc.update(sql, param);
	}
	public int updateContent(int boardno, String content) {
		String sql = "UPDATE TB_JDBC_BOARD SET CONTENT = ?"
				+ "WHERE BOARD_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(content);
		param.add(boardno);
		
		return jdbc.update(sql, param);
	}
	
}












