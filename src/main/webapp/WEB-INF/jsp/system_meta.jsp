<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<span id='id-row-${requestScope.item.id}' value='${requestScope.item.id}'></span>
<span id='name-row-${requestScope.item.id}' value='${requestScope.item.name}'></span>
<span id='the_value-row-${requestScope.item.id}' value='${requestScope.item.the_value}'></span>
<span id='description-row-${requestScope.item.id}' value='${requestScope.item.description}'></span>
