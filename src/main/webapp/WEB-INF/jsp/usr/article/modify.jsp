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
							<td>제목</td>
							<td><input class="input input-bordered w-full max-w-xs" type="text" autocomplete="off" name="title" value="${article.title }"/></td>
						</tr>
						<tr>
							<td>내용</td>
							<td><textarea class="input input-bordered w-full max-w-xs" autocomplete="off" name="body">${article.body }</textarea>
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
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
		</div>
	</div>
</section>
	
<%@ include file="../common/foot.jspf"%>