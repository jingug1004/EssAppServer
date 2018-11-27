<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="item" value="${requestScope.item}" />

<span style="display:none;" id='eid-row-${item.eid}' >${item.eid}</span>
<span style="display:none;" id='image-row-${item.eid}' >${item.image}</span>
<span style="display:none;" id='pid-row-${item.eid}' >${item.pid}</span>

<c:forEach var='lang' items='${requestScope.languages}' >
    <c:choose>
        <c:when test="${lang == 'en'}">
            <span style="display:none;" id='name_en-row-${requestScope.item.eid}' >${requestScope.item.name_en}</span>
            <span style="display:none;" id='text_en-row-${requestScope.item.eid}' >${requestScope.item.text_en}</span>
            <span style="display:none;" id='cf_en-row-${requestScope.item.eid}' >${requestScope.item.cf_en}</span>
        </c:when>
        <c:when test="${lang == 'cn'}">
            <span style="display:none;" id='name_cn-row-${requestScope.item.eid}' >${requestScope.item.name_cn}</span>
            <span style="display:none;" id='text_cn-row-${requestScope.item.eid}' >${requestScope.item.text_cn}</span>
            <span style="display:none;" id='cf_cn-row-${requestScope.item.eid}' >${requestScope.item.cf_cn}</span>
        </c:when>
        <c:when test="${lang == 'jp'}">
            <span style="display:none;" id='name_jp-row-${requestScope.item.eid}' >${requestScope.item.name_jp}</span>
            <span style="display:none;" id='text_jp-row-${requestScope.item.eid}' >${requestScope.item.text_jp}</span>
            <span style="display:none;" id='cf_jp-row-${requestScope.item.eid}' >${requestScope.item.cf_jp}</span>
        </c:when>
        <c:otherwise>
            <span style="display:none;" id='name_ko-row-${requestScope.item.eid}' >${requestScope.item.name}</span>
            <span style="display:none;" id='text_ko-row-${requestScope.item.eid}' >${requestScope.item.text}</span>
            <span style="display:none;" id='cf_ko-row-${requestScope.item.eid}' >${requestScope.item.cf}</span>
        </c:otherwise>
    </c:choose>
</c:forEach>