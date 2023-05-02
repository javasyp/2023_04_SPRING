package com.KoreaIT.syp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.syp.demo.service.ReactionPointService;
import com.KoreaIT.syp.demo.vo.ResultData;
import com.KoreaIT.syp.demo.vo.Rq;

@Controller
public class UsrReactionPointController {
	
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	
	// 좋아요
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode,
				relId);
		
		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBack("F-1", "이미 좋아요를 눌렀습니다.");
		}

		ResultData rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "좋아요 실패");
		}

		return rq.jsReplace("이 글을 좋아합니다.", replaceUri);
	}
	
	// 싫어요
	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {
		
		boolean actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode,
				relId).isSuccess();
		
		if (actorCanMakeReaction == false) {
			return rq.jsHistoryBack("F-1", "이미 싫어요를 눌렀습니다.");
		}

		ResultData rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "싫어요 실패");
		}

		return rq.jsReplace("이 글을 싫어합니다.", replaceUri);
	}
	
	// 좋아요 취소
	@RequestMapping("/usr/reactionPoint/doCancelGoodReaction")
	@ResponseBody
	public String doCancelGoodReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode,
				relId);
		
		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBackOnView(actorCanMakeReactionRd.getMsg());
		}

		ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "좋아요 취소 실패");
		}

		return rq.jsReplace("좋아요를 취소합니다.", replaceUri);
	}
	
	// 싫어요 취소
	@RequestMapping("/usr/reactionPoint/doCancelBadReaction")
	@ResponseBody
	public String doCancelBadReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode,
				relId);
		
		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBackOnView(actorCanMakeReactionRd.getMsg());
		}

		ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "싫어요 취소 실패");
		}

		return rq.jsReplace("싫어요를 취소합니다.", replaceUri);
	}

}