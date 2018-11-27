<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 카테고리용 모달 편집창 -->
<c:set var="lang_names" value="${['한국어', '영어', '중국어', '일본어']}" />

<div class="modal fade imapModal" id="editModal" tabindex="-1" role="dialog"
	aria-labelledby="editModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeDialog()">&times;</button> -->
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="editModalTitle">타이틀</h4>
			</div>
			
			<!-- 언어 선택 버튼 -->
				<ul class="nav nav-tabs">
					<c:forEach var="l" items="${languages}" varStatus="loop">
						<li class="lang_tabs" id="lang_tab_${l}"><a style="cursor: pointer;" onclick="selectLang('${l}')">${lang_names[loop.index]}</a></li>
					</c:forEach>
				</ul>

			<form role="form" enctype="multipart/form-data"
				action="/process/update_imap/" method="post" id="edit_form">
				<!-- <input type="hidden" id="input_action" name="action" value=""> -->

				<input type="hidden" id="input_mid" name="mid" value="">
				
				<div class="modal-body">
					
										
					<c:forEach var="l" items="${languages}" varStatus="loop">
					<div class="row lang_display lang_dis_${l}">
						<div class="col-md-2 category">
								<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">이름</button>
						</div>
						<div class="col-md-1 detailer" style="padding-right:0px;">(${lang_names[loop.index]})</div>
						<div class="col-md-9">
							<input type="text" class="form-control" value="" name="name_${l}"
								id="input_name_${l}">
						</div>
					</div>
					</c:forEach>
					<div style="height: 10px;"></div>
					
					<div class="row" id="zoom_area">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;">zoom</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="zoom" id="input_zoom">
						</div>
					</div>
					<div style="height: 10px;"></div>
					
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="addUpload('image');">이미지</button>
						</div>
						<div class="col-md-8" id="image_main_field">
							<input type="file" class="filestyle" data-btnClass="btn-primary" accept="text/html" name="map_res_url"
									id="input_map_res_url" />
						</div>
						<div class="col-md-2">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="deleteUpload('image', 0);">삭제</button>
						</div>
					</div>
					<div id="image_more_area">
					</div>
					<div style="height: 10px;"></div>

					<div class="row" id="image_size_area">
						<div class="col-md-2 clabel" style="padding-right:0px;">width</div>
						<div class="col-md-4">
							<input type="number" class="form-control" value="" name="width" id="input_w">
						</div>
						<div class="col-md-2 clabel" style="padding-right:0px;">height</div>
						<div class="col-md-4">
							<input type="number" class="form-control" value="" name="height" id="input_h">
						</div>
					</div>
					<div style="height: 10px;"></div>

					<div class="row" id="gps_area">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" id="picker_button" style="cursor: default;">위치</button>
						</div>
						<div class="col-md-1 clabel" style="padding-right:0px;">위도</div>
						<div class="col-md-4">
							<input type="number" class="form-control" value="" name="lat" id="input_lat">
						</div>
						<div class="col-md-1 clabel" style="padding-right:0px;">경도</div>
						<div class="col-md-4">
							<input type="number" class="form-control" value="" name="lng" id="input_lng">
						</div>
					</div>
					<div style="height: 10px;"></div>
					
					<div class="row" id="gps_area_sw">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" id="picker_button" style="cursor: default;">헤더 SW</button>
						</div>
						<div class="col-md-1 clabel" style="padding-right:0px;">위도</div>
						<div class="col-md-4">
							<input type="number" class="form-control" value="" name="lat" id="input_sw_lat">
						</div>
						<div class="col-md-1 clabel" style="padding-right:0px;">경도</div>
						<div class="col-md-4">
							<input type="number" class="form-control" value="" name="lng" id="input_sw_lng">
						</div>
						
					</div>
					<div style="height: 10px;"></div>
					
					<div class="row" id="gps_area_ne">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" id="picker_button" style="cursor: default;">헤더 NE</button>
						</div>
						<div class="col-md-1 clabel" style="padding-right:0px;">위도</div>
						<div class="col-md-4">
							<input type="number" class="form-control" value="" name="lat" id="input_ne_lat">
						</div>
						<div class="col-md-1 clabel" style="padding-right:0px;">경도</div>
						<div class="col-md-4">
							<input type="number" class="form-control" value="" name="lng" id="input_ne_lng">
						</div>
						
					</div>
					<div style="height: 10px;"></div>
					<div class="row" id="tile_info_area_title">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" id="picker_button" style="cursor: default;">타일</button>
						</div>
					</div>
					<div class="row" id="tile_info_area">
						<div class="col-md-2">
							<button type="button" class="btn btn-default btn-block" id="tileAdd" style="cursor: default;margin-top: 5px;" >추가</button>
						</div>
						<div class="col-md-2 clabel" style="padding-right:0px;">
							<input type="number" class="form-control" value="" name="level" id="input_level" placeholder="zoom"> 
						</div>
						<div class="col-md-2 clabel" style="padding-right:0px;">
							<input type="number" class="form-control" value="" name="x" id="input_x" placeholder="x"> 
						</div>
						<div class="col-md-2 clabel" style="padding-right:0px;">
							<input type="number" class="form-control" value="" name="y" id="input_y" placeholder="y"> 
						</div>
						<div class="col-md-2 clabel" style="padding-right:0px;">
							<input type="number" class="form-control" value="" name="xRange" id="input_xRange" placeholder="xRange"> 
						</div>
						<div class="col-md-2 clabel" style="padding-right:0px;">
							<input type="number" class="form-control" value="" name="yRange" id="input_yRange" placeholder="yRange"> 
						</div>
						
						
					</div>
					<div style="height: 10px;"></div>
					<input type="hidden" name="header" />
					<input type="hidden" name="tile_info" />
					<input type="hidden" name="file_delete" />
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
	var langs = ['ko', 'en', 'cn', 'jp'];
	var lang_names = ['한국어', '영어', '중국어', '일본어'];
	var max_assets = 5;
	var thisMid = -1;
	
	$("#tileAdd").on("click", function() {
		$("#tile_info_area").after('<div class="row" id="addTile">'+$("#tile_info_area").html()+'</div>');		
		$("#addTile #tileAdd").text("삭제").attr("id", "tileDel");
	});
	
	$(".imapModal").on("click", "#tileDel", function(){
		$(this).parents(".row").remove();
	});
	
	function post(path, params, method) {
	    method = method || "post"; // Set method to post by default if not specified.

	    // The rest of this code assumes you are not using a library.
	    // It can be made less wordy if you use one.
	    var form = document.createElement("form");
	    form.setAttribute("method", method);
	    form.setAttribute("action", path);

	    for(var key in params) {
	        if(params.hasOwnProperty(key)) {
	            var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
	            hiddenField.setAttribute("name", key);
	            hiddenField.setAttribute("value", params[key]);

	            form.appendChild(hiddenField);
	        }
	    }

	    document.body.appendChild(form);
	    form.submit();
	}
	

	function addUpload(type) {
		type = type.trim();
		var name = type + '_' + 'more_';
		var mia = $('#' + name + 'area');

		var miidx = (mia.children().length) + 1;
		if (miidx == max_assets) {
			alert('최대 ' + max_assets + '개 까지만 업로드 할 수 있습니다.');
			return;
		}

		var key = Date.now() + miidx;

		var elements = '';
		
		//elements += '<div class="row" id="'+name+'area_' + miidx + '">';
		elements += '<div class="row" id="'+name+'area_' + key + '">';
		elements += '  <div class="col-md-2 category">&nbsp;</div>';
		// elements += '  <div id="uploaded_image_area" class="col-md-1"><img style="height:34px;"/></div>';
		elements += '  <div id="upload_'+type+'_field_'+miidx +'" class="col-md-8 input_holder">';
		elements += '    <input type="file" class="filestyle" data-btnClass="btn-primary" name="'+name + miidx + '" id="input_'+name + key + '" />';
		// elements += '    <div class="bootstrap-filestyle input-group"><input type="text" class="form-control " placeholder="" disabled=""> <span class="group-span-filestyle input-group-btn" tabindex="0"><label for="input_more_image_' + miidx + '" style="margin-bottom: 0;" class="btn btn-primary "><span class="buttonText">파일선택</span></label></span></div>';
		elements += '  </div>';
		elements += '  <div class="col-md-2">';
		elements += '    <button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="deleteUpload(\'' + type + '\', ' + key + ');">삭제</button>';
		elements += '  </div>';
		elements += '</div>';
		
		mia.append(elements);
		
		// bootstrap 적용하기....
		var $this = $('#' + name + 'area_' + key + ' .filestyle');
		$this.filestyle({
			'input' : $this.attr('data-input') !== 'false',
			'htmlIcon' : $this.attr('data-icon'),
			'buttonBefore' : $this.attr('data-buttonBefore') === 'true',
			'disabled' : $this.attr('data-disabled') === 'true',
			'size' : $this.attr('data-size'),
			'text' : $this.attr('data-text'),
			'btnClass' : $this.attr('data-btnClass'),
			'badge' : $this.attr('data-badge') === 'true',
			'badgeName' : $this.attr('data-badgeName'),
			'placeholder': $this.attr('data-placeholder')
		});
		
		return key;
	}
	function deleteUpload(type, idx) {
		//var mia = $('#more_image_area');
		//var miidx = (mia.children().length) + 1;
		if (idx === 0) {
			// 애는 좀 다르게....
			//$('#main_' + type + '_field .filestyle').clear();
			//$('#main_' + type + '_field .filestyle').fileinput('clear');
			$('#' + type + '_main_field .filestyle').val('');
			$('#' + type + '_main_field .form-control').attr("placeholder", '');
		} else
			// $('#more_' + type + '_area_' + idx).remove();
			$('#' + type + '_more_area_' + idx).remove();

		$("[name=file_delete]").val("delete");
	}
	
	function clearMoreUploads(type) {
		var mia = $('#' + type + '_more_area');
		mia.empty();

		deleteUpload(type, 0);
	}
	
	function newForm() {
		$('#editModalTitle').html('이미지 지도 추가');
		$('#edit_form').attr('action', 'imap/add');
		
		thisMid = -1;
		$('#input_mid').val(0);

		$('#picker_button').attr("disabled", "disabled");

		$('#input_lat, #input_lng, #input_map_res_url, #input_name_ko, #input_zoom, #input_w, #input_h').val("");
		$("#input_map_res_url").next().find(".form-control").attr("placeholder", "");
		$("#addTile #tileDel").click();
		
		$("#input_sw_lat, #input_sw_lng, #input_ne_lat, #input_ne_lng").val("");
		
		var area = $("#tile_info_area");
		area.find("#input_level, #input_x, #input_y, #input_xRange, #input_yRange").val("");
		
		selectLang('ko');
        $("[name=file_delete]").val("");
	}

	function initEditForm(id) {
		newForm();
		console.log("id:", id);
		thisMid = id;

		$('#editModalTitle').html('이미지 지도 변경');

		$('#edit_form').attr('action', 'imap/update');
		$('#input_mid').val(id);

		$('#picker_button').attr("disabled", "disabled");

		$("#input_name_ko").val($('#name-row-' + id).attr('value'));
		$("#input_zoom").val($('#zoom-row-' + id).attr('value'));

        $("#input_w").val($('#w-row-' + id).attr('value'));
        $("#input_h").val($('#h-row-' + id).attr('value'));
		
		$('#input_map_res_url').val('');
		$("#input_map_res_url").next().find(".form-control").attr("placeholder", $('#map_res_url-row-' + id).attr('value'));
		
		
		$('#input_lat').val($('#lat-row-' + id).attr('value'));
		$('#input_lng').val($('#lng-row-' + id).attr('value'));
		
		var header = JSON.parse($('#header-row-' + id).attr('value') || "{}");
		var tile_info = JSON.parse($('#tile_info-row-' + id).attr('value') || "{}");

        langs.forEach(function(lang) {
            $('#input_name_' + lang).val($('#name_' + lang + '-row-' + id).attr('value'));
        });
		
		if(header.sw && header.sw.lat) {
			$("#input_sw_lat").val(header.sw.lat);
			$("#input_sw_lng").val(header.sw.lng);
			$("#input_ne_lat").val(header.ne.lat);
			$("#input_ne_lng").val(header.ne.lng);
		}
		
		// DOM 생성과 같이 데이터를 설정하면 생성시점이 밀려서 그런지 제대로 셋팅이 안됨.
		// 생성을 먼저하고 데이터를 셋팅하면 잘 됨.
		Object.entries(tile_info).forEach((data, idx) => {			
			if(idx > 0) {
				$("#tileAdd").click();
			}
		});
		
		Object.entries(tile_info).forEach((data, idx) => {			
			var area = $("#tile_info_area, #addTile").eq(idx);
			area.find("#input_level").val(data[0]);
			area.find("#input_x").val(data[1].x);
			area.find("#input_y").val(data[1].y);
			area.find("#input_xRange").val(data[1].xRange);
			area.find("#input_yRange").val(data[1].yRange);			
		});
		
		selectLang('ko');
        $("[name=file_delete]").val("");
	}
	
	var curLang = '';
	function selectLang(lang) {
		console.log("selectLang", lang);
		if (lang == null)
			lang = curLang;
		else
			curLang = lang;
		
		$(".lang_tabs").removeClass("active");
		$("#lang_tab_" + lang).addClass("active");

		$(".lang_display").hide();
		$(".lang_dis_" + lang).show();
	}
	
	function setHeader() {		
		if($("#input_sw_lat").val() !== "" && $("#input_ne_lat").val() !== "") {
			var header = {
				sw:{lat:$("#input_sw_lat").val(), lng:$("#input_sw_lng").val()},
				ne:{lat:$("#input_ne_lat").val(), lng:$("#input_ne_lng").val()}
			};
			
			$("[name=header]").val(JSON.stringify(header));	
		}
	}
	
	function setTileInfo(tile_info, that) {
		tile_info[Number(that.find("[name=level]").val())] = {
			x: Number(that.find("[name=x]").val()),
			y: Number(that.find("[name=y]").val()),
			xRange: Number(that.find("[name=xRange]").val()),
			yRange: Number(that.find("[name=yRange]").val())
		};	
	}
	
	function setTileInfoWrap() {
		var tile_info = {};
		$("#tile_info_area, #addTile").each(function() {
			setTileInfo(tile_info, $(this));
		});		
		
		$("[name=tile_info]").val(JSON.stringify(tile_info));
	}
	
	function clearHeader() {
		$("[name=header]").val("");
		$("[name=tile_info]").val("");
	}
	function uploadData() {	
		setHeader();
		setTileInfoWrap();
		$("#edit_form")[0].submit();
	}
</script>