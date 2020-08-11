package service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.BoardDao;
import dao.UserDao;

public class BoardService {

	private static BoardService instance;
	private BoardService(){}
	public static BoardService getInstance(){
		if(instance == null){
			instance = new BoardService();
		}
		return instance;
	}
	
	private BoardDao boardDao = BoardDao.getInstance();
	
	private int boardno;
	
	
	public int boardList(){
		List<Map<String, Object>> boardList = boardDao.selectBoardList();
		System.out.println("=========================================");
		System.out.println("번호\t제목\t작성자\t작성일");
		System.out.println("-----------------------------------------");
		for(Map<String, Object> board : boardList){
			System.out.println(board.get("BOARD_NO") + "\t"
					+ board.get("TITLE") + "\t"
					+ board.get("USER_NAME") + "\t"
					+ sdf1.format(board.get("REG_DATE")));
		}
		System.out.println("=========================================");
		System.out.println("1.조회\t2.등록\t0.로그아웃");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			System.out.println("게시글 번호 입력>");
			boardno = ScanUtil.nextInt();
			return View.BOARD_VIEW;
		case 2:
			return View.BOARD_INSERT_FORM;
		case 0: 
			Controller.loginUser = null;
			return View.HOME;
		}
		return View.BOARD_LIST;
	}
	
	SimpleDateFormat sdf1 = new SimpleDateFormat("yy/MM/dd");
	
	public int boardView() {
		Map<String, Object> boardView = boardDao.selectBoardView(boardno);
		System.out.println("=========================================");
		System.out.println("번호 : " + boardView.get("BOARD_NO"));
		System.out.println("제목 : " + boardView.get("TITLE"));
		System.out.println("작성자 : " + boardView.get("USER_NAME"));
		System.out.println("작성일자 : " + sdf1.format(boardView.get("REG_DATE")));
		System.out.println("----------------------------------------");
		System.out.println("내용 : " + boardView.get("CONTENT"));
		System.out.println("=========================================");
		System.out.println("1.수정\t2.삭제\t0.목록");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			System.out.println("1.제목\t2.내용\t0.취소");
			System.out.println("수정할 대상 입력>");
			input = ScanUtil.nextInt();
			String column;
			switch (input) {
			case 1:
				column = "TITLE";
				return updateTitle(boardno);
			case 2:
				column = "CONTENT";
				return updateContent(boardno);
			case 0:
				return View.BOARD_VIEW;
			}
		case 2:
			System.out.println("삭제하시겠습니까?");
			System.out.println("1.확인\t2.취소");
			input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				return deleteBoard(boardno);
			case 2:
				return View.BOARD_VIEW;
			}
		case 0:
			return View.BOARD_LIST;
		}
		return View.BOARD_VIEW;
	}
	
	private int updateContent(int boardno) {
		System.out.println("수정할 내용 입력>");
		String content = ScanUtil.nextLine();
		int result = boardDao.updateContent(boardno, content);
		if(0 < result){
			System.out.println("내용 수정 성공");
		} else {
			System.out.println("내용 수정 실패");
		}
		return View.BOARD_VIEW;
	}
	private int updateTitle(int boardno) {
		System.out.println("수정할 제목 입력>");
		String title = ScanUtil.nextLine();
		int result = boardDao.updateTitle(boardno, title);
		if(0 < result){
			System.out.println("제목 수정 성공");
		} else {
			System.out.println("제목 수정 실패");
		}
		return View.BOARD_VIEW;
	}
	private int deleteBoard(int boardno) {
		int result = boardDao.deleteBoard(boardno);
		if(0 < result){
			System.out.println("삭제 성공");
		} else {
			System.out.println("삭제 실패");
		}
		return View.BOARD_LIST;
	}
	public int boardInsertForm() {
		System.out.println("============ 글쓰기 ============");
		System.out.print("제목>");
		String title = ScanUtil.nextLine();
		System.out.print("내용>");
		String content = ScanUtil.nextLine();
		
		int result = boardDao.insertBoard(title, content);
		if(0 < result){
			System.out.println("새 글 등록 성공");
		} else {
			System.out.println("새 글 등록 실패");
		}
		return View.BOARD_LIST;
	}

}


















