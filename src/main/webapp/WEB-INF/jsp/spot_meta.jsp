<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="item" value="${requestScope.item}" />

<span id='pid-row-${item.pid}' value='${item.pid}'></span>
<span id='lat-row-${item.pid}' value='${item.lat}'></span>
<span id='lng-row-${item.pid}' value='${item.lng}'></span>
<span id='map_type-row-${item.pid}' value='${item.map_type}'></span>
<span id='map_res_url-row-${item.pid}' value='${item.map_res_url}'></span>
<span id='mid-row-${item.pid}' value='${item.mid}'></span>
<span id='text_direction-row-${item.pid}' value='${item.text_direction}'></span>
<span id='display_zoom-row-${item.pid}' value='${item.display_zoom}'></span>
<span id='display_zoom_max-row-${item.pid}' value='${item.display_zoom_max}'></span>
<span id='name_display-row-${item.pid}' value='${item.name_display}'></span>
<span id='text_rotate-row-${item.pid}' value='${item.text_rotate}'></span>
<span id='font_size-row-${item.pid}' value='${item.font_size}'></span>
<span id='type-row-${item.pid}' value='${item.type}'></span>
<span id='w-row-${item.pid}' value='${item.w}'></span>
<span id='h-row-${item.pid}' value='${item.h}'></span>
<span id='status-row-${item.pid}' value='${item.status}'></span>

<span id='name_en-row-${item.pid}' value='${item.name_en}'></span>
<span id='name_cn-row-${item.pid}' value='${item.name_cn}'></span>
<span id='name_jp-row-${item.pid}' value='${item.name_jp}'></span>
<span id='name_ko-row-${item.pid}' value='${item.name}'></span>


