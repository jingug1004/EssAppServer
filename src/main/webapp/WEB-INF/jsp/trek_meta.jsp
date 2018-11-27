<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="item" value="${requestScope.item}" />

<c:if test="${item.tc_type == 2}"> &nbsp;<b style="color:red;">(이벤트)</b></c:if>

<span id="tc_id-row-${item.tc_id}" value="${item.tc_id}"></span>
<span id="course_name-row-${item.tc_id}" value="${item.course_name}"></span>
<span id="chk_dist-row-${item.tc_id}" value="${item.chk_dist}"></span>
<span id="status-row-${item.tc_id}" value="${item.status}"></span>
<span id="groups-row-${item.tc_id}" value="${item.groups}"></span>
<span id="pids-row-${item.tc_id}" value="${item.pids}"></span>
<span id="counts-row-${item.tc_id}" value="${item.counts}"></span>
<span id="tc_type-row-${item.tc_id}" value="${item.tc_type}"></span>
<span id="cid-row-${item.tc_id}" value="${item.cid}"></span>
