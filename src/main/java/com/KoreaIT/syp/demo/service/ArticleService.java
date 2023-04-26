package com.KoreaIT.syp.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.syp.demo.repository.ArticleRepository;
import com.KoreaIT.syp.demo.util.Ut;
import com.KoreaIT.syp.demo.vo.Article;
import com.KoreaIT.syp.demo.vo.ResultData;
import com.KoreaIT.syp.demo.vo.Rq;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private Rq rq;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	// 서비스 메서드
	// 작성
	public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {
		// 서비스에서 ResultData로 처리
		articleRepository.writeArticle(memberId, boardId, title, body);
		
		int id = articleRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다.", id), "id", id);
	}
	
	// 삭제
	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}
	
	// 수정
	public ResultData modifyArticle(int id, String title, String body) {		// void -> ResultData
		articleRepository.modifyArticle(id, title, body);
		
		Article article = getArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 글을 수정 했습니다.", id), "article", article);
	}
	
	// 목록
	public List<Article> articles() {
		return articleRepository.getArticles();
	}
	
	// 목록 (출력용)
	public List<Article> getForPrintArticles(int boardId, int itemsInAPage, int page, String searchType, String searchKeyword) {
		/*
		 * SELECT * FROM article WHERE boardId = 1 ORDER BY id DESC LIMIT 0, 10
		 */
		int limitFrom = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		
		return articleRepository.getForPrintArticles(boardId, limitFrom, limitTake, searchType, searchKeyword);
	}
	
	// 상세보기
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}
	
	// 상세보기 (출력용), 권한 체크
	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getForPrintArticle(id);

		controlForPrintData(actorId, article);

		return article;
	}
	
	// 수정 및 삭제 버튼 노출 여부
	private void controlForPrintData(int actorId, Article article) {
		if (article == null) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setActorCanDelete(actorCanDeleteRd.isSuccess());
		
		ResultData actorCanModifyRd = actorCanModify(actorId, article);
		article.setActorCanModify(actorCanModifyRd.isSuccess());
	}
	
	// 수정 권한 체크
	public ResultData actorCanModify(int loginedMemberId, Article article) {
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("해당 글에 대한 권한이 없습니다."));
		}
		return ResultData.from("S-1", "수정 가능");
	}
	
	// 삭제 권한 체크
	private ResultData actorCanDelete(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}

		return ResultData.from("S-1", "삭제 가능");
	}
	
	// 게시물 개수
	public int getArticlesCount(int boardId, String searchType, String searchKeyword) {
		return articleRepository.getArticlesCount(boardId, searchType, searchKeyword);
	}
	
	// 게시물 조회수 증가시키기
	public ResultData increaseHitCount(int id) {
		int affectedRow = articleRepository.increaseHitCount(id);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다.", "affectedRow", affectedRow);
		}

		return ResultData.from("S-1", "조회수 증가", "affectedRowRd", affectedRow);
	}
	
	// 조회수 값 가져오기
	public int getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}
	
}
