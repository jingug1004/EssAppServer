<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<span id='mid-row-${requestScope.item.mid}' value='${requestScope.item.mid}'></span>
<span id='name-row-${requestScope.item.mid}' value='${requestScope.item.name}'></span>
<span id='zoom-row-${requestScope.item.mid}' value='${requestScope.item.zoom}'></span>
<span id='map_res_url-row-${requestScope.item.mid}' value='${requestScope.item.map_res_url}'></span>
<span id='w-row-${requestScope.item.mid}' value='${requestScope.item.w}'></span>
<span id='h-row-${requestScope.item.mid}' value='${requestScope.item.h}'></span>
<span id='lat-row-${requestScope.item.mid}' value='${requestScope.item.lat}'></span>
<span id='lng-row-${requestScope.item.mid}' value='${requestScope.item.lng}'></span>
<span id='header-row-${requestScope.item.mid}' value='${requestScope.item.header}'></span>
<span id='tile_info-row-${requestScope.item.mid}' value='${requestScope.item.tile_info}'></span>
<span id='status-row-${requestScope.item.mid}' value='${requestScope.item.status}'></span>

<c:forEach var='lang' items='${requestScope.languages}' >
	<c:choose>
		<c:when test="${lang == 'en'}">
			<span id='name_en-row-${requestScope.item.mid}' value='${requestScope.item.name_en}'></span>
		</c:when>
		<c:when test="${lang == 'cn'}">
			<span id='name_cn-row-${requestScope.item.mid}' value='${requestScope.item.name_cn}'></span>
		</c:when>
		<c:when test="${lang == 'jp'}">
			<span id='name_jp-row-${requestScope.item.mid}' value='${requestScope.item.name_jp}'></span>
		</c:when>
		<c:otherwise>
			<span id='name_ko-row-${requestScope.item.mid}' value='${requestScope.item.name}'></span>
		</c:otherwise>
	</c:choose>
</c:forEach>
