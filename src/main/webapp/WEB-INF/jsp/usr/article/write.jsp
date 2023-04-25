<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form action="doWrite" method="POST">
				<table border="1">
					<colgroup>
						<col width="200" />
					</colgroup>
		
					<tbody>
						<tr>
							<td>작성자</td>
							<td>${rq.loginedMember.nickname }</td>
						</tr>
						<tr>
							<td>게시판</td>
							<td>
								<select class="select w-full max-w-xs" name="boardId">
									<option value="1">공지사항</option>
								    <option value="2">자유</option>
								    <option value="3">QNA</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>제목</td>
							<td><input class="input input-bordered w-full max-w-xs" type="text" autocomplete="off" placeholder="제목을 입력해 주세요." name="title"/></td>
						</tr>
						<tr>
							<td>내용</td>
							<td><textarea class="input input-bordered w-full max-w-xs" autocomplete="off" placeholder="내용을 입력해 주세요." name="body"></textarea>
						</tr>
						<tr>
							<td colspan="2">
							<button type="submit">작성</button>
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