<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="item" value="${requestScope.item}" />

<c:if test="${item.type == 9}"> &nbsp;<b style="color:red;">(최고 관리자)</b></c:if>
<c:if test="${item.type == 8}"> &nbsp;<b style="color:blue;">(관리자)</b></c:if>

<span id='uid-row-${item.uid}' value='${item.uid}'></span>
<span id='id-row-${item.uid}' value='${item.id}'></span>
<span id='type-row-${item.uid}' value='${item.type}'></span>
<span id='name-row-${item.uid}' value='${item.name}'></span>
<span id='mobile-row-${item.uid}' value='${item.mobile}'></span>
<span id='birth-row-${item.uid}' value='${item.birth}'></span>
<span id='gender-row-${item.uid}' value='${item.gender}'></span>
<span id='point-row-${item.uid}' value='${item.point}'></span>
