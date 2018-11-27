<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 카테고리용 모달 편집창 -->

<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
	aria-labelledby="editModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeDialog()">&times;</button> -->
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="editModalTitle"></h4>
			</div>

			<!-- 언어 선택 버튼 
			<ul class="nav nav-tabs">
				<c:forEach var="l" items="${languages}" varStatus="loop">
					<li class="lang_tabs" id="lang_tab_${l}"><a style="cursor: pointer;" onclick="selectLang('${l}')">${lang_names[loop.index]}</a></li>
				</c:forEach>
			</ul>
			-->

			<form role="form" enctype="multipart/form-data"
				action="/process/update_category/" method="post" id="edit_form">
				<!-- <input type="hidden" id="input_action" name="action" value=""> -->
				<input type="hidden" id="input_tc_id" name="tc_id" value="">
				<input type="hidden" id="input_groups" name="groups" value="">
				<input type="hidden" id="input_to_delete" name="to_delete" value="">
				<input type="hidden" id="input_chk_dist" name="chk_dist" value="">
				<input type="hidden" id="input_pids" name="pids" value="">

				<div class="modal-body">
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">이름</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="course_name"
								id="input_course_name">
						</div>
					</div>
					<div style="height: 10px;"></div>
					
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">타입</button>
						</div>
						<div class="col-md-10">
							<select class="form-control" name="tc_type" id="input_tc_type" onchange="courseTypeChanged($(this).val());">
                              <option value="1">일반 코스</option>
                              <option value="2">이벤트 코스</option>
							</select>
						</div>
					</div>
					<div style="height: 10px;"></div>
					
					<div id="category_link_area">
					<div class="row" >
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block detailer"
								style="cursor: default;">연결 카테고리</button>
						</div>
						<div class="col-md-10">
						  <select class="form-control" name="cid" id="input_category" onchange="categoryChanged($(this).val());">
							<c:forEach var="cat" items="${categories}" >
                              <option value="${cat.cid}">${cat.title}</option>
							</c:forEach>
                          </select>
						</div>
					</div>
					<div style="height: 10px;"></div> 
					</div>

					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">색상</button>
						</div>
						<div class="col-md-8">
							<input type="text" class="form-control" value="#ff0000" name="color" id="input_color">
						</div>
						<div class="col-md-2">
							<input type="color" class="form-control" value="#ff0000" id="input_color_palette" onchange="changeColorValueText($(this).val());">
						</div>
					</div>
					<div style="height: 10px;"></div>
					
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">확인 반경</button>
						</div>
						<div class="col-md-3">
							<input type="text" class="form-control" value="" align="right" id="input_chk_dist_field">
						</div>
						<div class="col-md-1 clabel" style="padding-left:0px;">
							미터
						</div>
					</div>
					<div style="height: 10px;"></div>
					<div style="height: 10px;"></div>
					
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="col-md-12 btn btn-default" id="add_group_button"
								style="cursor: default;" onclick="addSectorGroup(-1, 0);">섹터 그룹 추가</button>
						</div>
					</div>
					<div id="sector_area"></div>
					<div style="height: 10px;"></div>

				</div>
				<div class="modal-footer">
					<!-- <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeDialog()">취소</button> -->
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" onclick="uploadData()">저장</button>
				</div>
			</form>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<script>
	function changeColorValueText(val) {
		$('#input_color').val(val);
	}


    
	// TODO 처음 추가할 때 바뀌는게 있을꺼구...
	// TODO 뽑아낼 데이터. tcg_id, 이름.
	function addSectorGroup(g_idx, count, name) {
		var sa = $('#sector_area');

		// +1은 사용 체크 버튼
		// TODO key!!!!!!
		// var index = sa.children().length;
		var key = Date.now() + g_idx;
		var elements = '';
	
		elements += '<div class="sector_group" id="sector_group_' + key + '" style="padding-top:5px;">';
		elements += '<div class="row">';
		elements += '  <div class="col-md-2 category"> <button type="button" class="btn btn-default btn-block detailer" style="cursor: default;" onclick="deleteSectorGroup('+key+', '+g_idx+');" >섹터그룹 삭제</button></div>';
		if ($('#input_tc_type').val() == 1)
			elements += sectorGroupForNormal(key, g_idx, count, name);
		else
			elements += sectorGroupForEvent(key, g_idx, count, name);

		elements += '</div>';
		elements += '</div>';

		sa.append(elements);
		
		// addSubSector(key);
		return key;
	}
	
	function sectorGroupForNormal(key, g_idx, count, name) {
		var elements = '';
		elements += '  <div class="col-md-1 clabel" style="padding-right:0px;">이름</div>';
		var tc_id = $('#input_tc_id').val();
		if (g_idx >= 0) {
			elements += '  <div class="col-md-6"><input type="text" class="form-control group_name" value=""></div>';
			elements += '  <div class="col-md-3" style="padding-left:0px;">';
			elements += '    <form role="form" enctype="multipart/form-data" action="trek/sectors" method="post" id="add_sector_form">';
			elements += '      <input type="hidden" name="tc_id" value="' + tc_id + '">';
			elements += '      <input type="hidden" name="g_idx" value="' + g_idx + '">';
			elements += '      <input type="hidden" name="g_name" value="' + course_name + ' [' + name + ']">';
			elements += '      <button type="submit" class="btn btn-default btn-block" style="cursor: default;" >섹터 추가 (' + count + ')</button>';
			elements += '    </form>';
			elements += '  </div>';
		} else {
			elements += '  <div class="col-md-9"><input type="text" class="form-control group_name" value=""></div>';
		}
		return elements;
	}
	
	function sectorGroupForEvent(key, g_idx, count, name) {
		var elements = '';
		elements += '<div class="col-md-10">';
		elements += '  <select class="form-control pids">';
		if (relatedPlaces != null) {
			relatedPlaces.forEach(function(place, index) {
				elements += '    <option value="' + place.pid + '">' + place.name + '</option>';
			});
		}
		elements += '  </select>';
		elements += '</div>';
		return elements;
	}
	
	var toBeDeleted = [];
	function deleteSectorGroup(key, prevIndex) {
		$('#sector_group_' + key).remove();
		
		if (prevIndex >= 0) {
			toBeDeleted.push(prevIndex);
		}
	}

	var default_color = "#ff0000";
	var course_name = '';
	var pids = [];
	function newForm() {
		$('#editModalTitle').html('트레킹 코스 추가');
		$('#edit_form').attr('action', 'trek/add');
		
		$('#input_tc_id').val(0);
		$('#input_course_name').val('');
		course_name = '';

		var def_dist = ${def_dist};
		$('#input_chk_dist_field').val(def_dist * 1000);
		$('#input_chk_dist').val(def_dist);

		$('#input_color').val(default_color);
		$('#input_color_palette').val(default_color);

		$('#input_groups').val('');
		$('#input_to_delete').val('');
		toBeDeleted = [];

		$('#input_tc_type').val(1);
		$('#input_pids').val('');
		pids = [];

		$('.sector_group').remove();
	}

	function initEditForm(id) {
		console.log("id:", id);

		$('#editModalTitle').html('트레킹 코스 변경');
		$('#edit_form').attr('action', 'trek/update?p=${p}&u=${u}');
		
		$('#input_tc_id').val(id);
		course_name = $('#course_name-row-' + id).attr('value');
		$('#input_course_name').val(course_name);
		
		var chk_dist = parseFloat($('#chk_dist-row-' + id).attr('value'));
		if (chk_dist <= 0.0)
			chk_dist = ${def_dist};
		$('#input_chk_dist').val(chk_dist);
		$('#input_chk_dist_field').val(chk_dist * 1000);

		$('#input_to_delete').val('');
		toBeDeleted = [];

		var tc_type = $('#tc_type-row-' + id).attr('value');
		if (tc_type == 2) {
			$('#input_category').val($('#cid-row-' + id).attr('value'));
		}
		$('#input_tc_type').val(tc_type);
		courseTypeChanged(tc_type);
		
		var pids_str = $('#pids-row-' + id).attr('value');
		pids = pids_str.split(',');
		// $('#input_pids').val(pids_str);
		$('#input_pids').val('');
		
		$('.sector_group').remove();
		var counts = $('#counts-row-' + id).attr('value').split(',');
		var groups = $('#groups-row-' + id).attr('value');
		$('#input_groups').val(groups);
		var group_array = groups.split(',');
		group_array.forEach(function(group, g_idx) {
			var key = addSectorGroup(g_idx, counts[g_idx] ? counts[g_idx] : 0, group);
			$('#sector_group_' + key + ' .group_name').val(group);
		});
	}
	
	function courseTypeChanged(val) {
		var isNormalCourse = (val == 1);
		if (isNormalCourse) {
			// TODO 일반
			$('#category_link_area').hide();
			$('#add_group_button').text('섹터 그룹 추가');
		} else {
			$('#category_link_area').show();
			categoryChanged($('#input_category').val());
			$('#add_group_button').text('연결 장소 추가');
		}
	}
	
	var relatedPlaces = null;
	function categoryChanged(cid) {
		// TODO 바뀌면 장소 목록을 가져와야 한다...
		var module = '/map/places/' + cid + '?count=0';
		noblapp.request(module, null, function(data) {
				console.log(data);
				if (data['success']) {
					relatedPlaces = JSON.parse(data['response']).list;
					
					var elements = '';
					relatedPlaces.forEach(function(place, index) {
						elements += '    <option value="' + place.pid + '">' + place.name + '</option>';
					});

					var $pids = $('.pids');
					$pids.each(function(index) {
						$(this).empty();
						$(this).append(elements);
						if (index < pids.length)
							$(this).val(pids[index]);
					});
				}
				//	document.location.replace('../trek');
			});
		// /map/places/cid?count=0
	}
	
	function adjustForm() {
		// 이름 확인.
		if ($('#input_course_name').val() == "")
			return "이름이 비었어요.";
		
		if (toBeDeleted.length > 0) {
			$('#input_to_delete').val(JSON.stringify(toBeDeleted));
		}

		var chk_dist = parseFloat($('#input_chk_dist_field').val()) / 1000;
		$('#input_chk_dist').val(chk_dist);
		
		if ($('#input_tc_type').val() == 1) {
			// TODO 섹터들이 없으면???
			var group_names = $('#sector_area .group_name');
			if (group_names.length <= 0)
				return '그룹이 없어요.';

			var names = [];
			group_names.each(function(index) {
				names.push($(this).val());
			});
			$('#input_groups').val(JSON.stringify(names));
		} else {
			var $pids = $('.pids');
			var pids = [];
			var names = [];
			$pids.each(function(index) {
				pids.push(parseInt($(this).val()));
				names.push($(this).children("option:selected").text());
			});
			$('#input_pids').val(JSON.stringify(pids));
			$('#input_groups').val(JSON.stringify(names));
		}

		return null;
	}

    function uploadData() {
		var err = adjustForm();
		if (err != null) {
			alert(err);
			return;
		}

		$("#edit_form")[0].submit();
    }
</script>