<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>
<hr />

<!-- 조회수 증가 ajax 통신으로 보여주기 -->
<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCountRd?id=2" frameborder="0"></iframe> -->
<script>
	const params = {}
	params.id = parseInt('${param.id}');
	params.memberId = parseInt('${param.loginedMemberId}');
</script>

<script>
	// AJAX
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView';
		
		if (localStorage.getItem(localStorageKey)) {
			return;
		}
		
		localStorage.setItem(localStorageKey, true);
		
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
         
	$(function() {
		setTimeout(ArticleDetail__increaseHitCount, 2000);
	})
</script>

<script>
	function ArticleDetail__increaseLikeCount() {
		
		$.get('../reaction/doIncreaseLikeCountRd', {
			relId : params.id,
			memberId : params.memberId,
			ajaxMode : 'Y'
		}, function(data) {
			$('.likeCount').html(data.data1);
		}, 'json');
	}
	
	$(function() {
		setTimeout(ArticleDetail__increaseLikeCount, 2000);
	})
		
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<table border="1">
				<colgroup>
					<col width="200" />
				</colgroup>
	
				<tbody>
					<tr>
						<td>번호</td>
						<td><div class="badge">${article.id }</div></td>
					</tr>
					<tr>
						<td>작성날짜</td>
						<td>${article.regDate }</td>
					</tr>
					<tr>
						<td>수정날짜</td>
						<td>${article.updateDate }</td>
					</tr>
					<tr>
						<td>작성자</td>
						<td>${article.extra_writer }</td>
					</tr>
					<tr>
						<td>조회수</td>
						<td>
							<span class="article-detail__hit-count">${article.hitCount }</span>
						</td>
					</tr>
					<tr>
						<td>제목</td>
						<td>${article.title }</td>
					</tr>
					<tr>
						<td>내용</td>
						<td>${article.body }</td>
					</tr>
					<tr>
						<td>추천</td>
						<td>
							<span>좋아요 : ${article.extra_goodReactionPoint }</span>
							<c:if test="${actorCanMakeReaction }">
								<span>
									<span>&nbsp;</span>
									<button>👍</button>
								</span>
								<span>
									<span>&nbsp;</span>
									<button>👎</button>
								</span>
							</c:if>
						</td>
					</tr>
				</tbody>
	
			</table>
		</div>
		<div class="btns">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.actorCanModify }">
				<a class="btn-text-link btn btn-active btn-ghost" href="modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.actorCanDelete }">
				<a class="btn-text-link btn btn-active btn-ghost" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="doDelete?id=${article.id }">삭제</a>
			</c:if>
			
		</div>
	</div>
</section>
	
<%@ include file="../common/foot.jspf"%>