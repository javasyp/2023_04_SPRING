package com.KoreaIT.syp.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {
	// 게시판 타입, 게시글 번호, 로그인한 멤버 번호 조회
	@Select("""
			<script>
				SELECT IFNULL(SUM(RP.point),0)
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = #{relTypeCode}
				AND RP.relId = #{relId}
				AND RP.memberId = #{actorId}
			</script>
			""")
	int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId);
	
	// 좋아요 클릭 시 포인트 +1 입력
	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				memberId = #{actorId},
				`point` = 1
			</script>
			""")
	public int addGoodReactionPoint(int actorId, String relTypeCode, int relId);
	
	// 싫어요 클릭 시 포인트 -1 입력
	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				memberId = #{actorId},
				`point` = -1
			</script>
			""")
	public int addBadReactionPoint(int actorId, String relTypeCode, int relId);
	
	// 좋아요 취소
	@Delete("""
			<script>
				DELETE FROM reactionPoint
				WHERE relTypeCode = #{relTypeCode}
				AND relId = #{relId}
				AND memberId = #{actorId}
			</script>
			""")
	public void deleteGoodReactionPoint(int actorId, String relTypeCode, int relId);
	
	// 싫어요 취소
	@Delete("""
			<script>
				DELETE FROM reactionPoint
				WHERE relTypeCode = #{relTypeCode}
				AND relId = #{relId}
				AND memberId = #{actorId}
			</script>
			""")
	public void deleteBadReactionPoint(int actorId, String relTypeCode, int relId);
	
}
