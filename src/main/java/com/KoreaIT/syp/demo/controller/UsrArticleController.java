package com.KoreaIT.syp.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.syp.demo.service.ArticleService;
import com.KoreaIT.syp.demo.service.BoardService;
import com.KoreaIT.syp.demo.service.ReactionPointService;
import com.KoreaIT.syp.demo.util.Ut;
import com.KoreaIT.syp.demo.vo.Article;
import com.KoreaIT.syp.demo.vo.Board;
import com.KoreaIT.syp.demo.vo.ResultData;
import com.KoreaIT.syp.demo.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	
	// 액션 메서드
	// 수정
	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다!", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		
		Article article = articleService.getArticle(id);
		
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}

		articleService.modifyArticle(id, title, body);

		return rq.jsReplace(Ut.f("%d번 글을 수정했습니다.", id), Ut.f("../article/detail?id=%d", id));
	}
	
	// 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		
		Article article = articleService.getArticle(id);
		
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id));
		}
		
		// 권한 체크
		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 글에 대한 권한이 없습니다.", id));
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 글을 삭제했습니다.", id), "../article/list");
	}
	
	// 작성
	@RequestMapping("/usr/article/write")
	public String showWrite(String title, String body) {
		
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(int boardId, String title, String body, String replaceUri) {
		
		if (Ut.empty(title)) {
			return rq.jsHistoryBack("F-1", "제목을 입력해 주세요.");
		}
		
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-2", "내용을 입력해 주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);
		
		int id = (int) writeArticleRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}
		
		return rq.jsReplace(Ut.f("%d번 글이 생성되었습니다.", id), replaceUri);
	}
	
	// 목록
	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue="1") int boardId, 
			@RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="title,body") String searchType,
			@RequestParam(defaultValue="") String searchKeyword) {
		
		// 게시판 도입
		Board board = boardService.getBoardById(boardId);
		
		if (board == null) {
			return rq.jsHistoryBackOnView("해당 게시판은 존재하지 않습니다.");
		}
		
		int articlesCount = articleService.getArticlesCount(boardId, searchType, searchKeyword);
		
		// 페이징
		
		int itemsInAPage = 10;		// 한 페이지에 나오는 글 개수
		// 글 20개 -> 2
		// 글 24개 -> 3

		int pagesCount = (int) Math.ceil(articlesCount / (double) itemsInAPage);

		List<Article> articles = articleService.getForPrintArticles(boardId, itemsInAPage, page, searchType, searchKeyword);
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("board", board);
		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);
		
		return "usr/article/list";
	}
	
	// 상세보기
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		// 추천 여부 확인
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), "article", id);
		
		model.addAttribute("article", article);
		model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());
		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		
		// 추천 불가 시
		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {
			// 로그인 된 회원 번호의 추천 point 가져옴
			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();
			
			if (sumReactionPointByMemberId > 0) {
				// 로그인 한 회원이 "좋아요"를 이미 누른 상태라면 "좋아요 취소" 보여주기
				model.addAttribute("actorCanCancelGoodReaction", true);
			} else {
				// 로그인 한 회원이 "싫어요"를 이미 누른 상태라면 "싫어요 취소" 보여주기
				model.addAttribute("actorCanCancelBadReaction", true);
			}
		}
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData doIncreaseHitCountRd(int id) {
		
		// 조회수 증가 후 보여주기
		ResultData increaseHitCountRd = articleService.increaseHitCount(id);
		
		// 파라미터로 조회 시 게시물 없을 때
		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}
		
		ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));

		rd.setData2("id", id);

		return rd;
	}
}