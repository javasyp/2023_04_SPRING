package com.KoreaIT.syp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.syp.demo.repository.ReactionPointRepository;

@Service
public class ReactionPointService {
	
	@Autowired
	private ReactionPointRepository reactionPointRepository;

	public ReactionPointService(ReactionPointRepository reactionPointRepository) {
		this.reactionPointRepository = reactionPointRepository;
	}
	
	// 서비스 메서드
	// 추천 여부 확인
	public boolean actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		
		if (actorId == 0) {
			return false;
		}
		
		// 0이면 추천한 적 없음.
		return reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId) == 0;
	}

}
