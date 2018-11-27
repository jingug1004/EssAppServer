<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="item" value="${requestScope.item}" />

<span id="cid-row-${item.cid}" value="${item.cid}"></span>
<span id="mid-row-${item.mid}" value="${item.mid}"></span>
<span id="icon_url-row-${item.cid}" value="${item.icon_url}"></span>
<span id="pcid-row-${item.cid}" value="${item.pcid}"></span>
<span id="ccu-row-${item.cid}" value="${item.ccu}"></span>
<span id="tc_id-row-${item.cid}" value="${item.tc_id}"></span>
<span id="sort-row-${item.cid}" value="${item.sort}"></span>
<span id="link-row-${item.cid}" value="${item.link}"></span>
<span id="help_file-row-${item.cid}" value="${item.help_file}"></span>
<span id="status-row-${item.cid}" value="${item.status}"></span>

<c:forEach var="lang" items="${requestScope.languages}" >
	<c:choose>
		<c:when test="${lang == 'en'}">
			<span id="title_en-row-${item.cid}" value='${item.title_en}'></span>
			<span id="ccf_en-row-${item.cid}" value='${item.ccf_en}'></span>
		</c:when>
		<c:when test="${lang == 'cn'}">
			<span id="title_cn-row-${item.cid}" value='${item.title_cn}'></span>
			<span id="ccf_cn-row-${item.cid}" value='${item.ccf_cn}'></span>
		</c:when>
		<c:when test="${lang == 'jp'}">
			<span id="title_jp-row-${item.cid}" value='${item.title_jp}'></span>
			<span id="ccf_jp-row-${item.cid}" value='${item.ccf_jp}'></span>
		</c:when>
		<c:otherwise>
			<span id="title_ko-row-${item.cid}" value='${item.title}'></span>
			<span id="ccf_ko-row-${item.cid}" value='${item.ccf}'></span>
		</c:otherwise>
	</c:choose>
</c:forEach>
