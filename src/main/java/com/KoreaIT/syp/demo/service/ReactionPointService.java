package com.KoreaIT.syp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.syp.demo.repository.ReactionPointRepository;
import com.KoreaIT.syp.demo.vo.ResultData;

@Service
public class ReactionPointService {
	
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;

	public ReactionPointService(ReactionPointRepository reactionPointRepository) {
		this.reactionPointRepository = reactionPointRepository;
	}
	
	// 서비스 메서드
	// 추천 여부 확인
	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해 주세요.");
		}
		
		// 0이면 추천한 적 없음.
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
				relId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "추천 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		
		return ResultData.from("S-1", "추천 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}
	
	// 좋아요
	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		
		int affectedRow = reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-2", "좋아요 실패");
		}

		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요 처리 완료");

	}
	
	// 싫어요
	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		
		int affectedRow = reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-2", "싫어요 실패");
		}
		
		reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리 완료");
	}
	
	// 좋아요 취소
	public ResultData deleteGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.deleteGoodReactionPoint(actorId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요 취소 처리 완료");
	}
	
	// 싫어요 취소
	public ResultData deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
			
		reactionPointRepository.deleteBadReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReationPoint(relId);
			break;
		}
		
		return ResultData.from("S-1", "싫어요 취소 처리 완료");
	}
}
