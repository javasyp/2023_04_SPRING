package com.KoreaIT.syp.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.syp.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int memberId, int boardId, String title, String body);

	public List<Article> getArticles();
	
	@Select("""
			<script>
			SELECT A.*,
			IFNULL(SUM(RP.point),0) AS extra_sumReactionPoint,
			IFNULL(SUM(IF(RP.point &gt; 0,RP.point,0)),0) AS extra_goodReactionPoint,
			IFNULL(SUM(IF(RP.point &lt; 0,RP.point,0)),0) AS extra_badReactionPoint,
			M.nickname AS extra_writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			LEFT JOIN reactionPoint AS RP 
			ON A.id = RP.relId AND RP.relTypeCode = 'article'
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchType == 'title'">
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchType == 'body'">
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			GROUP BY A.id
			ORDER BY A.id DESC
			<if test="limitFrom >= 0">
				LIMIT #{limitFrom}, #{limitTake}
			</if>
			</script>
			""")
	public List<Article> getForPrintArticles(int boardId, int limitFrom, int limitTake, String searchType, String searchKeyword);

	public Article getArticle(int id);
	
	@Select("""
			<script>
			SELECT A.*, M.nickname AS extra_writer,
			IFNULL(SUM(RP.point),0) AS extra_sumReactionPoint,
			IFNULL(SUM(IF(RP.point &gt; 0,RP.point,0)),0) AS extra_goodReactionPoint,
			IFNULL(SUM(IF(RP.point &lt; 0,RP.point,0)),0) AS extra_badReactionPoint
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			LEFT JOIN reactionPoint AS RP 
			ON A.id = RP.relId AND RP.relTypeCode = 'article'
			WHERE A.id = #{id}
			GROUP BY A.id
			</script>
			""")
	public Article getForPrintArticle(int id);

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();
	
	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS A
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchType == 'title'">
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchType == 'body'">
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			</script>
			""")
	public int getArticlesCount(int boardId, String searchType, String searchKeyword);
	
	// 조회수 증가
	@Update("""
			<script>
				UPDATE article
				SET hitCount = hitCount + 1
				WHERE id = #{id}
			</script>
			""")

	public int increaseHitCount(int id);
	
	// 조회수 값 가져오기 (ajax)
	@Select("""
			<script>
				SELECT hitCount
				FROM article
				WHERE id = #{id}
			</script>
			""")
	public int getArticleHitCount(int id);
	
}
