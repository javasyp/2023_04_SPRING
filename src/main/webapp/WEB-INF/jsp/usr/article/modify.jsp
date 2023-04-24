<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE MODIFY" />
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form action="doModify" method="POST">
				<input type="hidden" name="id" value="${article.id }"/>
				<table border="1">
					<colgroup>
						<col width="200" />
					</colgroup>
		
					<tbody>
						<tr>
							<td>번호</td>
							<td>${article.id }</td>
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
							<td>제목</td>
							<td><input type="text" class="w-full" autocomplete="off" name="title" value="${article.title }"/></td>
						</tr>
						<tr>
							<td>내용</td>
							<td><textarea type="text" class="w-full" autocomplete="off" name="body">${article.body }</textarea>
						</tr>
						<tr>
							<td colspan="2">
							<button type="submit">수정</button>
							</td>
						</tr>
					</tbody>
		
				</table>
			</form>
		</div>
		<div class="btns">
			<button class="btn-text-link" type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.actorCanModify }">
				<a class="btn-text-link" href="modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.actorCanDelete }">
				<a class="btn-text-link" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="doDelete?id=${article.id }">삭제</a>
			</c:if>
			
		</div>
	</div>
</section>
	
<%@ include file="../common/foot.jspf"%>