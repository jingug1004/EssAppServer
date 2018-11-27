<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>${gname} 전자지도 관리자 시스템</title>
  <!-- Bootstrap Core CSS -->
  <link href="../../resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- MetisMenu CSS -->
  <link href="../../resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
  <!-- Custom CSS -->
  <link href="../../resources/dist/css/sb-admin-2.css" rel="stylesheet">
  <!-- Font CSS -->
  <link href="../../resources/dist/css/font.css" rel="stylesheet">
  <!-- Morris Charts CSS -->
  <link href="../../resources/vendor/morrisjs/morris.css" rel="stylesheet">
  <!-- Custom Fonts -->
  <link href="../../resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


  <link href="../../resources/vendor/bootstrap-toggle/css/bootstrap-toggle.min.css" rel="stylesheet">
  <link href="../../resources/vendor/flatpickr/dist/flatpickr.min.css" rel="stylesheet">

  <script src="../../resources/vendor/jquery/jquery.min.js"></script>

	<script src="../../resources/noblappXHRClient.js"> </script>

  <!-- Bootstrap Core JavaScript -->
  <script src="../../resources/vendor/bootstrap/js/bootstrap.min.js"></script>
  <script src="../../resources/vendor/bootstrap/js/bootstrap-filestyle.min.js"></script>
  <!-- Metis Menu Plugin JavaScript -->
  <script src="../../resources/vendor/metisMenu/metisMenu.min.js"></script>
  <!-- Morris Charts JavaScript -->
  <script src="../../resources/vendor/raphael/raphael.min.js"></script>
  <script src="../../resources/vendor/morrisjs/morris.min.js"></script>
  <!-- <script src="../../resources/data/morris-data.js"></script> -->
  <!-- Config value -->
  <!-- <script src="../../resources/js/config.js"></script> -->

  <script src="../../resources/vendor/bootstrap-toggle/js/bootstrap-toggle.min.js"></script>

  <script src="../../resources/vendor/flatpickr/dist/flatpickr.min.js"></script>

  <!-- Custom Theme JavaScript -->
  <script src="../../resources/dist/js/sb-admin-2.js"></script>


  <style type="text/css">
    .table-row {
      cursor:pointer;
    }

    .category {
        padding-right:0px;
    }


	.clabel {
		font-size: 14px;
		padding-top: 6px;
		padding-bottom: 6px;
	}

    .detail {
        font-size: 12px;
        padding-top: 10px;
        padding-bottom: 10px;
    }
    
    .detailer {
            font-size: 10px;
            padding-top: 10px;
            padding-bottom: 10px;
	}

	.nav-second-level .active {
		background-color: #f2f2f2 !important;
	}
  </style>
<title>${gname} 전자지도 관리자 시스템</title>
</head>
<body>

  <div id="wrapper">
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0px;">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
        <a class="navbar-brand" href="./">${gname} 전자지도 관리자 시스템</a>
      </div>

	<jsp:include page="navi.jsp" />
	<jsp:include page="sidebar.jsp" >
		<jsp:param name="target" value="${target}" />
	</jsp:include>

    </nav>

    <div id="page-wrapper">
      <div class="row">
        <div class="col-lg-12">
          <h2 class="page-header">
            ${list_title}
          </h2>
        </div>
        <!-- /.col-lg-12 -->
      </div>

	  <c:set var="roots" value="${roots}" scope="request" />
	  <c:set var="languages" value="${languages}" scope="request" />
	  <c:set var="categories" value="${categories}" scope="request" />
	  <c:set var="p" value="${current}" scope="request" />
	  <c:set var="u" value="${per_page}" scope="request" />

      <div class="row">
        <!-- /.col-lg-6 -->
        <div class="col-lg-12">
          <div class="panel panel-default">
            <div class="panel-body">
              <div class="table-responsive">
                <table class="table table-hover">

                  <thead>
                  <tr>
                    <th>&nbsp;</th>
                    <th></th>
                    <c:if test="${target == 'hits'}">
                        <th width="100px"></th>
                        <th width="100px"></th>
                    </c:if>
                    <th width="70px"></th>
                    <th width="70px"></th>
                  </tr>
                  </thead>

                  <tbody>

				  <c:set var="item_index" value="${(current - 1) * per_page}" />
				  <c:forEach var="list_item" items="${list}" >
					<tr class="table-row" id="table-row-${list_item.id}">
					    <c:set var="item_index" value="${item_index + 1}" />
						<td id="mn_id-row-${list_item.id}" class="col-md-2" value="${list_item.id}">${item_index} (${list_item.id})</td>
						<td class="table-row-main" id="title-row-${list_item.id}" value="${list_item.name}">${list_item.name}
							<!-- target에 따라 meta.jsp를 첨부 -->
							<c:set var="item" value="${list_item.object}" scope="request" />
							<c:set var="pictograms" value="${pictograms}" scope="request" />
							<c:import url="${target}_meta.jsp" />
						</td>
                        <c:if test="${target == 'hits'}">
                            <td> ${list_item.cnt} </td>
                            <td> ${list_item.tableType} </td>
                        </c:if>
                        <td>
						  <c:choose>
							  <c:when test="${target == 'pictogram'}">
							    <img style="height:34px;" src="../../assets/pictogram/${list_item.id}/${list_item.object.icon_url}" />
							  </c:when>
							  <c:when test="${target == 'system' || target == 'hits'}">
							  	<!-- 없어... -->
							  </c:when>
							  <c:otherwise>
								<input class="toggle-trigger" id="toggle-trigger_${list_item.id}" type="checkbox" ${list_item.checked} data-toggle="toggle" onchange="toggleStatus(${list_item.id})">
							  </c:otherwise>
						  </c:choose>
						</td>
						<td>
						<c:if test="${target != 'system' && target != 'hits'}">
						  <button type="button" class="btn btn-default" data-toggle="modal" onclick="popupDelDialog(${list_item.id}, '${list_item.name}');">삭제</button>
						</c:if>
						</td>
					  </tr>
				  </c:forEach>
				  
                  </tbody>
                </table>
              </div>
              <!-- /.table-responsive -->

              <c:if test="${target != 'system'}">
			  <!-- pagenation -->
			  <jsp:include page="pagenation.jsp" >
				<jsp:param name="target" value="${target}" />
				<jsp:param name="total" value="${total}" />
				<jsp:param name="current" value="${current}" />
				<jsp:param name="per_page" value="${per_page}" />
			  </jsp:include>

              </c:if>
			  
            </div>
            <!-- /.panel-body -->
          </div>
          <!-- /.panel -->
        </div>
        <!-- /.col-lg-6 -->
      </div>
      <!-- /.row -->


	<c:choose>
        <c:when test="${target == 'system'}">
        <div class="col-lg-4">
            <button type="button" class="btn btn-primary btn-lg btn-block" onclick="popupMascotDialog()">로딩화면 로고 변경</button>
        </div>
        <div class="col-lg-4">
            <button type="button" class="btn btn-primary btn-lg btn-block" onclick="popupFontDialog()">사이트 폰트 변경</button>
        </div>
        <div class="col-lg-4">
            <button type="button" class="btn btn-primary btn-lg btn-block" onclick="applyServiceConfig()">시스템 변수 적용</button>
        </div>
	    </c:when>
        <c:when test="${target == 'hits'}">
        </c:when>
        <c:otherwise>
        <div class="col-lg-12">
            <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="modal" onclick="popupNewDialog()">추가</button>
        </div>
        </c:otherwise>
	</c:choose>

						  

	<!-- Delete Modal -->
      <div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="delModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
              <h4 class="modal-title" id="delModalLabel">${list_title} 삭제</h4>
            </div>
            <!-- <form role="form" action="${target}/delete" method="post"> --!>
            <!-- <form role="form"> -->
              <input type="hidden" id="del_id" name="id">
              <div class="modal-body">
                <span id="delMessage"></span>
              </div>
              <div class="modal-footer">
                <!-- <button class="btn btn-default" data-dismiss="modal" onclick="closeDialog()">취소</button> -->
                <button class="btn btn-default" data-dismiss="modal">취소</button>
                <button type="submit" class="btn btn-primary" onclick="deleteItem()">삭제</button>
              </div>
            <!-- </form> -->
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->
	
	<!-- 장소냐 카테고리냐에 따라 modal.jsp를 첨부 -->
        <c:if test="${target != 'hits'}">
            <c:import url="${target}_modal.jsp" />
        </c:if>
        <c:if test="${target == 'system'}">
            <c:import url="${target}_mascot_modal.jsp" />
            <c:import url="${target}_font_modal.jsp" />
        </c:if>


  <!-- /#page-wrapper -->
  </div>


  <!-- <script src="../../resources/socket.io.js"></script> -->

  
    
  <script>
	console.log("setting noblapp");
	noblapp.setup("../..");
    var target = '${target}';

	$(document).ready(function() {

        $(".table-row-main").click(function() {     //edit
            if(target === "system" && $(this).find("[id^=name-row]").attr("value") === "setFont") {
                return false;
            }
			var rowInfo = this.id.split('-');
			var id = rowInfo[2];
			initEditForm(id);
			$('#editModal').modal('show');
        });
    });

	function popupNewDialog() {
		newForm();
		$('#editModal').modal('show');
	}

    function popupFontDialog() {
        newFormFont();
        $('#fontModal').modal('show');
    }

    function popupMascotDialog() {
        newFormMascot();
        $('#mascotModal').modal('show');
    }

  function toggleStatus(item_id) {
	  var status;

      if($('#toggle-trigger_'+item_id).prop('checked')) {           //go On
        status = 1;
      } else {                                                      //go Off
        status = 0;
      }

	  $('.toggle-trigger').bootstrapToggle('disable');

      var module = "/cms/process/${target}/status";
      var param = {
    		  id: item_id,
    		  status: status
      };
    	  noblapp.request(module, param, function(data) {
  			console.log(data);
  			$('.toggle-trigger').bootstrapToggle('enable');
  			if (!data['success']) {
  				// return the vaule..
  				console.log("should return check value");
		        // setTimeout(function () {
					  if (status == 1)
						  $('#toggle-trigger_'+item_id).data("bs.toggle").off(true);
					  else
						  $('#toggle-trigger_'+item_id).data("bs.toggle").off(false);
				// }, 500);
  			}
  		});
	  
	  }
  
  function popupDelDialog(id, title) {
      var msg = title + " ${list_title}를 삭제 하시겠습니까?";

      $('#del_id').val(id);
      $('#delMessage').html(msg);

	  $('#delModal').modal('show');
  }

  function closeDelDialog() {
	  $('#delModal').modal('hide');
  }

  function deleteItem() {
	  var item_id = $('#del_id').val();
	  var module = "/cms/process/${target}/delete";
      var param = {
    		  id: item_id
      };

	  noblapp.request(module, param, function(data) {
		if (!data['success']) {
			if (data['ecode'] == 80001) {
				closeDelDialog();
				alert("해당 카테고리를 가진 장소가 있습니다.");
			} else if (data['ecode'] == 80002) {
				closeDelDialog();
				alert("해당 카테고리에 하위 카테고리가 있습니다.");
			}
		} else {
			// redirect
			document.location.replace('${target}?p=${current}&u=${per_page}');
		}
	  });
  }
  

  function closeDialog() {
  }

  function applyServiceConfig() {
		var module = '/cms/reloadConfig';
	    noblapp.request(module, null, function(data) {
	    	if (data['success'])
				document.location.replace('${target}?p=${current}&u=${per_page}');
	    	else
				console.log('fail?', data);
		});
  }

  </script>
  
</body>
</html>