<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<span id="pid-row-${requestScope.item.pid}" value="${requestScope.item.pid}"></span>
<span id="title-row-${requestScope.item.pid}" value="${requestScope.item.title}"></span>
<span id="icon_url-row-${requestScope.item.pid}" value="${requestScope.item.icon_url}"></span>
