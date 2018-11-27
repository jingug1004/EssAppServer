<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<span id='pid-row-${requestScope.item.pid}' value='${requestScope.item.pid}'></span>
<span id='cid-row-${requestScope.item.pid}' value='${requestScope.item.cid}'></span>
<span id='images-row-${requestScope.item.pid}' value='${requestScope.item.images}'></span>
<span id='videos-row-${requestScope.item.pid}' value='${requestScope.item.videos}'></span>
<span id='others-row-${requestScope.item.pid}' value='${requestScope.item.others}'></span>
<span id='phone-row-${requestScope.item.pid}' value='${requestScope.item.phone}'></span>
<span id='lat-row-${requestScope.item.pid}' value='${requestScope.item.lat}'></span>
<span id='lng-row-${requestScope.item.pid}' value='${requestScope.item.lng}'></span>
<span id='map_type-row-${requestScope.item.pid}' value='${requestScope.item.map_type}'></span>
<span id='map_res_url-row-${requestScope.item.pid}' value='${requestScope.item.map_res_url}'></span>
<span id='display_zoom-row-${requestScope.item.pid}' value='${requestScope.item.display_zoom}'></span>
<span id='status-row-${requestScope.item.pid}' value='${requestScope.item.status}'></span>

<c:forEach var='lang' items='${requestScope.languages}' >
	<c:choose>
		<c:when test="${lang == 'en'}">
			<span id='name_en-row-${requestScope.item.pid}' value='${requestScope.item.name_en}'></span>
			<span id='text_en-row-${requestScope.item.pid}' value='${requestScope.item.text_en}'></span>
			<span id='address_en-row-${requestScope.item.pid}' value='${requestScope.item.address_en}'></span>
			<span id='ccv_en-row-${requestScope.item.pid}' value='${requestScope.item.ccv_en}'></span>
			<span id='cf_en-row-${requestScope.item.pid}' value='${requestScope.item.cf_en}'></span>
			<span id='cv_en-row-${requestScope.item.pid}' value='${requestScope.item.cv_en}'></span>
		</c:when>
		<c:when test="${lang == 'cn'}">
			<span id='name_cn-row-${requestScope.item.pid}' value='${requestScope.item.name_cn}'></span>
			<span id='text_cn-row-${requestScope.item.pid}' value='${requestScope.item.text_cn}'></span>
			<span id='address_cn-row-${requestScope.item.pid}' value='${requestScope.item.address_cn}'></span>
			<span id='ccv_cn-row-${requestScope.item.pid}' value='${requestScope.item.ccv_cn}'></span>
			<span id='cf_cn-row-${requestScope.item.pid}' value='${requestScope.item.cf_cn}'></span>
			<span id='cv_cn-row-${requestScope.item.pid}' value='${requestScope.item.cv_cn}'></span>
		</c:when>
		<c:when test="${lang == 'jp'}">
			<span id='name_jp-row-${requestScope.item.pid}' value='${requestScope.item.name_jp}'></span>
			<span id='text_jp-row-${requestScope.item.pid}' value='${requestScope.item.text_jp}'></span>
			<span id='address_jp-row-${requestScope.item.pid}' value='${requestScope.item.address_jp}'></span>
			<span id='ccv_jp-row-${requestScope.item.pid}' value='${requestScope.item.ccv_jp}'></span>
			<span id='cf_jp-row-${requestScope.item.pid}' value='${requestScope.item.cf_jp}'></span>
			<span id='cv_jp-row-${requestScope.item.pid}' value='${requestScope.item.cv_jp}'></span>
		</c:when>
		<c:otherwise>
			<span id='name_ko-row-${requestScope.item.pid}' value='${requestScope.item.name}'></span>
			<span id='text_ko-row-${requestScope.item.pid}' value='${requestScope.item.text}'></span>
			<span id='address_ko-row-${requestScope.item.pid}' value='${requestScope.item.address}'></span>
			<span id='ccv_ko-row-${requestScope.item.pid}' value='${requestScope.item.ccv}'></span>
			<span id='cf_ko-row-${requestScope.item.pid}' value='${requestScope.item.cf}'></span>
			<span id='cv_ko-row-${requestScope.item.pid}' value='${requestScope.item.cv}'></span>
		</c:otherwise>
	</c:choose>
</c:forEach>

