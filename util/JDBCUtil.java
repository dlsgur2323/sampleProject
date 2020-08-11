package util;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JDBCUtil {
	
	//싱글톤 패턴 : 인스턴스의 생성을 제한하여 하나의 인스턴스만 사용하는 디자인 패턴
	/* 	 /) /)
		( >ㅅ <)
		(  >@<)
	*/
	private JDBCUtil() {
		
	}
	
	//인스턴스를 보관할 변수
	private static JDBCUtil instance;
	
	//인스턴스를 빌려주는 메소드
	public static JDBCUtil getInstance(){
		if(instance == null){
			instance = new JDBCUtil();
		}
		return instance;
	}
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "dlsgur";
	String password = "java";
		
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/*
//	 * Map<String, Object> selectOne(String sql) //인자가 하나인 메소드는 where절이 없는 쿼리
	 * Map<String, Object> selectOne(String sql, List<Object> param) ?에 값을 넘기기 위해서 list param을 받는다.
	 * List<Map<String, Object>> selectList(String sql)
	 * List<Map<String, Object>> selectList(String sql, List<Object> param)
	 * int update(String sql)
	 * int update(String sql, List<Object> param)
	 */
	
	public Map<String, Object> selectOne(String sql){ //인자가 하나인 메소드는 where절이 없는 쿼리
		 HashMap<String, Object> map = new HashMap<>();
		 try {
				con = DriverManager.getConnection(url, user, password);
				
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				
				while(rs.next()){
					for(int i = 1; i <= columnCount; i++){
						map.put(md.getColumnName(i), rs.getObject(i));
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs != null) try { rs.close();} catch (Exception e) {}
				if(ps != null) try { ps.close();} catch (Exception e) {}
				if(con != null) try { con.close();} catch (Exception e) {}
			}
		 return map;
	 }
	 
	public Map<String, Object> selectOne(String sql, List<Object> param){  //?에 값을 넘기기 위해서 list param을 받는다.
		 HashMap<String, Object> map = new HashMap<>();
		 try {
				con = DriverManager.getConnection(url, user, password);
				
				ps = con.prepareStatement(sql);
				for(int i = 0; i < param.size(); i++){
					ps.setObject(i+1, param.get(i));
				}
				rs = ps.executeQuery();
				
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				
				while(rs.next()){
					for(int i = 1; i <= columnCount; i++){
						map.put(md.getColumnName(i), rs.getObject(i));
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs != null) try { rs.close();} catch (Exception e) {}
				if(ps != null) try { ps.close();} catch (Exception e) {}
				if(con != null) try { con.close();} catch (Exception e) {}
			}
		 return map;
	 }
	 
	public List<Map<String, Object>> selectList(String sql){
		 ArrayList<Map<String, Object>> list = new ArrayList<>();
		 try {
				con = DriverManager.getConnection(url, user, password);
				
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				
				while(rs.next()){
					list.add(new HashMap<String, Object>());
					for(int i = 1; i <= columnCount; i++){
						list.get(list.size()-1).put(md.getColumnName(i), rs.getObject(i));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs != null) try { rs.close();} catch (Exception e) {}
				if(ps != null) try { ps.close();} catch (Exception e) {}
				if(con != null) try { con.close();} catch (Exception e) {}
			}
		 return list;
	 }
	 
	public List<Map<String, Object>> selectList(String sql, List<Object> param){
		 ArrayList<Map<String, Object>> list = new ArrayList<>();
		 try {
				con = DriverManager.getConnection(url, user, password);
				
				ps = con.prepareStatement(sql);
				for(int i = 0; i < param.size(); i++){
					ps.setObject(i+1, param.get(i));
				}
				rs = ps.executeQuery();
				
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				
				while(rs.next()){
					list.add(new HashMap<String, Object>());
					for(int i = 1; i <= columnCount; i++){
						list.get(list.size()-1).put(md.getColumnName(i), rs.getObject(i));
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs != null) try { rs.close();} catch (Exception e) {}
				if(ps != null) try { ps.close();} catch (Exception e) {}
				if(con != null) try { con.close();} catch (Exception e) {}
			}
		 return list;
	 }
	 
	public int update(String sql){
		 int result = 0;
		 try {
				con = DriverManager.getConnection(url, user, password);
				
				ps = con.prepareStatement(sql);
				result = ps.executeUpdate();				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs != null) try { rs.close();} catch (Exception e) {}
				if(ps != null) try { ps.close();} catch (Exception e) {}
				if(con != null) try { con.close();} catch (Exception e) {}
			}
		 return result;
	 }
	 
	public int update(String sql, List<Object> param){
		 int result = 0;
		 try {
				con = DriverManager.getConnection(url, user, password);
				
				ps = con.prepareStatement(sql);
				for(int i = 0; i < param.size(); i++){
					ps.setObject(i+1, param.get(i));
				}
				result = ps.executeUpdate();				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs != null) try { rs.close();} catch (Exception e) {}
				if(ps != null) try { ps.close();} catch (Exception e) {}
				if(con != null) try { con.close();} catch (Exception e) {}
			}
		 return result;
	 }
	 
		
	
}



















