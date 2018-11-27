<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="categories" value="${requestScope.categories}" />

<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">
            <li> <a href="category" id="category_tab"><i class="fa fa-cube fa-fw"></i>&nbsp;카테고리 관리</a> </li>
            <li> <a href="pictogram" id="pictogram_tab"><i class="fa fa-map-signs fa-fw"></i>&nbsp;픽토그램 관리</a> </li>
            <li> <a href="place" id="place_tab"><i class="fa fa-map fa-fw"></i>&nbsp;장소 관리</a>
            	<ul id="place_sub" class="nav nav-second-level">
					<li> <a href="place">모든 카테고리</a> </li>
					<c:forEach var="cat" items="${categories}" >
						<c:if test="${cat.pcid == 0 && '1' eq cat.status && cat.title ne '도움말' && cat.title ne '링크'}">
							<li> <a id="cat_${cat.cid}" href="place?c=${cat.cid}">${cat.title}</a> </li>
							<c:forEach var="subcat" items="${categories}" >
								<c:if test="${subcat.pcid == cat.cid && '1' eq subcat.status }">
									<li><a id="cat_${subcat.cid}" href="place?c=${subcat.cid}">&nbsp;&nbsp;&nbsp;&nbsp;${subcat.title}</a> </li>
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach>
                </ul>
            </li>
            <li> <a href="imap" id="imap_tab"><i class="fa fa-flag fa-fw"></i>&nbsp;이미지 지도 관리</a> </li>
            <li> <a href="spot" id="spot_tab"><i class="fa fa-map-signs fa-fw"></i>&nbsp;지도 장소 관리</a>
            <li> <a href="user" id="user_tab"><i class="fa fa-users fa-fw"></i>&nbsp;유저 관리</a> </li>
            <li> <a href="event" id="event_tab"><i class="fa fa-flag fa-fw"></i>&nbsp;행사 관리</a> </li>
            <li> <a href="trek" id="trek_tab"><i class="fa fa-flag fa-fw"></i>&nbsp;트레킹 관리</a> </li>
            <li> <a href="hits" id="hits_tab"><i class="fa fa-flag fa-fw"></i>&nbsp;누적 조회 수</a> </li>
            <li> <a href="system" id="system_tab"><i class="fa fa-cog fa-fw"></i>&nbsp;시스템</a> </li>
        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div>

<script>
	var target = '${target}';
	$('#' + target + '_tab').addClass('active');
	
	if (target == 'place') {
		$('#place_sub').attr('aria-expanded', true);
		$('#place_sub').addClass('collapse in');
		$('#cat_' + ${cid}).addClass('active');
	}
	
</script>