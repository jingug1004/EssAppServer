<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//<jsp:param name="target" value="${target}" />
//<jsp:param name="total" value="${total}" />
//<jsp:param name="current" value="${current}" />
//<jsp:param name="per_page" value="${per_page}" />

	String target = request.getParameter("target");
	int total = Integer.parseInt(request.getParameter("total"));
	int current = Integer.parseInt(request.getParameter("current"));
	int per_page = Integer.parseInt(request.getParameter("per_page"));
	
    //category, total, current, per_page, idx_range
    int totPages = (total % per_page == 0) ? (int)(total / per_page) : ((int)(total / per_page) + 1);
    int currNaviViewIndex = ((current%per_page == 0)?  (int)(current/per_page) : ((int)(current/per_page) + 1));
    int currNaviViewIndexStartPage = (currNaviViewIndex-1)*per_page + 1;                                                               //현재 네비게이션 인덱스 시작페이지 번호
    int currNaviViewIndexEndPage = ((currNaviViewIndexStartPage + (per_page-1) > totPages)? totPages : (currNaviViewIndexStartPage + (per_page-1)));

    // pageInfo["totPages"] = totPages;
    int prev = ((currNaviViewIndexStartPage > 1)? currNaviViewIndexStartPage-1 : 0);
    int begin = currNaviViewIndexStartPage;
    int end = currNaviViewIndexEndPage;
    int next = ((currNaviViewIndexEndPage < totPages)? currNaviViewIndexEndPage+1 : 0);

%>

<%!
    final public String getPageLink(String target, int current, int unit) 
    {
    	//return "/process/"+target+ "/?p=" +current+ "&u=" + unit;
    	return target+ "?p=" +current+ "&u=" + unit;
    }
%>

<div class="jb-center" align="center">
    <ul class="pagination">
        <li class= <%= ((prev > 0)? "" : "disabled") %>><a href="<%= ((prev > 0) ? getPageLink(target, prev, per_page) : "#") %>"><span class="glyphicon glyphicon-chevron-left"></span></a></li>

		<% for (int i = begin; i <= end; i++) { %>
            <li class="<%= ((i == current)? "active" : "") %>"><a href="<%= getPageLink(target, i, per_page) %>"><%=i%></a></li>
		<% } %>

        <li class= <%= ((next > 0)? "" : "disabled")%>><a href="<%= ((next > 0)? getPageLink(target, next, per_page) : "#")%>"><span class="glyphicon glyphicon-chevron-right"></span></a></li>
    </ul>
</div>