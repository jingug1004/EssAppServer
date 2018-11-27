<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 카테고리용 모달 편집창 -->
<c:set var="lang_names" value="${['한국어', '영어', '중국어', '일본어']}" />

<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
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
				action="/process/update_category/" method="post" id="edit_form">
				<!-- <input type="hidden" id="input_action" name="action" value=""> -->

				<input type="hidden" id="input_pid" name="pid" value="">

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

					<div class="row" id="gsp_area">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" id="picker_button" style="cursor: default;" onclick="openPicker()">위치</button>
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

					<div class="row" id="map_area">
						<div class="col-md-4 category">
							<button type="button" class="btn btn-default btn-block"
									style="cursor: default;">이미지 지도</button>
						</div>
						<div id="map_imap_field">
							<div class="col-md-8">
								<select class="form-control" name="mid" id="input_mid">
									<c:forEach var="imap" items="${imaps}" >
										<option value="${imap.mid}">${imap.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div style="height: 10px;"></div>

					<div class="row">
						<div class="col-md-3">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;">마커타입</button>
						</div>
						<div class="col-md-9">
							<select class="form-control" name="type" id="input_type" onchange="selectType()">
								<option value="">기본</option>
								<option value="text">텍스트마커</option>
							</select>
						</div>
					</div>
					<div style="height: 10px;"></div>

					<div id="pictoMarker">
						<div class="row" id="map_area">
							<div class="col-md-2 category">
								<button type="button" class="btn btn-default btn-block"
										style="cursor: default;">지도</button>
							</div>
							<div class="col-md-3">
								<select class="form-control" name="map_type" id="input_map_type" onchange="mapTypeSelected($(this).val())">
									<option value="0">표시없음</option>
									<option value="1">Pictogram</option>
									<option value="2">Symbol</option>
									<option value="3">상단SVG</option>
									<option value="5">하단SVG</option>
									<option value="4">Object</option>
								</select>
							</div>
							<div id="map_symbol_file_field">
								<div class="col-md-7">
									<input type="file" class="filestyle" data-btnClass="btn-primary"
										   name="map_res_url"
										   id="input_map_res_url" />
								</div>
							</div>
							<div id="map_pictogram_field">
								<div class="col-md-7">
									<select class="form-control" name="pictogram_id" id="input_pictogram_id">
										<c:forEach var="pic" items="${pictograms}" >
											<option value="${pic.pid}">${pic.title}</option>
										</c:forEach>
									</select>
								</div>
							</div>

						</div>
						<div style="height: 10px;"></div>

						<div class="row" id="map_area">
							<div class="col-md-3 category">
								<button type="button" class="btn btn-default btn-block"
										style="cursor: default;">표시줌레벨</button>
							</div>
							<div id="map_imap_field">
								<div class="col-md-3">
									<input type="number" class="form-control" value="" min="7" max="20" name="display_zoom" id="input_display_zoom">
								</div>
							</div>
							<div class="col-md-3 category">
								<button type="button" class="btn btn-default btn-block"
										style="cursor: default;">표시줌 최대레벨</button>
							</div>
							<div id="map_imap_field">
								<div class="col-md-3">
									<input type="number" class="form-control" value="" min="7" max="20" name="display_zoom_max" id="input_display_zoom_max">
								</div>
							</div>
						</div>
						<div style="height: 10px;"></div>

						<div class="row" id="map_area">
							<div class="col-md-3 category">
								<button type="button" class="btn btn-default btn-block"
										style="cursor: default;">텍스트 표시</button>
							</div>
							<div id="map_imap_field">
								<div class="col-md-3">
									<select class="form-control" name="name_display" id="input_name_display">
										<option value="">선택</option>
										<option value="true">true</option>
										<option value="false">false</option>
									</select>
								</div>
							</div>
							<div class="col-md-2 category">
								<button type="button" class="btn btn-default btn-block"
										style="cursor: default;">text 방향</button>
							</div>
							<div id="map_imap_field">
								<div class="col-md-4">
									<select class="form-control" name="text_direction" id="input_text_direction">
										<option value="bottom">bottom</option>
										<option value="top">top</option>
										<option value="left">left</option>
										<option value="right">right</option>
									</select>
								</div>
							</div>
						</div>
						<div style="height: 10px;"></div>

						<div class="row" id="map_area">
							<div class="col-md-2 category">
								<button type="button" class="btn btn-default btn-block"
										style="cursor: default;">width</button>
							</div>
							<div id="map_imap_field">
								<div class="col-md-4">
									<input type="text" class="form-control" name="width" id="input_w" />

								</div>
							</div>
							<div class="col-md-2 category">
								<button type="button" class="btn btn-default btn-block"
										style="cursor: default;">height</button>
							</div>
							<div id="map_imap_field">
								<div class="col-md-4">
									<input type="text" class="form-control" name="height" id="input_h" />

								</div>
							</div>
						</div>
						<div style="height: 10px;"></div>
					</div>
					<div id="textMarker">
						<div class="row">
							<div class="col-md-3">
								<button type="button" class="btn btn-default btn-block" style="cursor: default;">text회전</button>
							</div>
							<div class="col-md-6">
								<input type="number" class="form-control" min="0" max="360" name="text_rotate" id="input_text_rotate" onchange="rotate()" />
							</div>
							<div class="col-md-3">
								<div id="rotateDiv" style="transform-origin: 50% 50%;width: 25px;margin-top: 10px;">test</div>
							</div>
						</div>
						<div style="height: 10px;"></div>
						<div class="row">
							<div class="col-md-3">
								<button type="button" class="btn btn-default btn-block" style="cursor: default;">폰트 Size</button>
							</div>
							<div class="col-md-6">
								<input type="number" class="form-control" min="10" max="50" value="14" name="font_size" id="input_font_size" onchange="fontSize()" />
							</div>
							<div class="col-md-3">
								<div id="fontSizeDiv" style="transform-origin: 50% 50%;width: 25px;margin-top: 10px;">test</div>
							</div>
						</div>
						<div style="height: 10px;"></div>
					</div>
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
	var thisPid = -1;

	function rotate() {
        $("#rotateDiv").css({"transform":"rotate("+$("#input_text_rotate").val()+"deg)"});
	}

	function fontSize() {
        $("#fontSizeDiv").css({"font-size":$("#input_font_size").val()+"px"});
	}

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
	
	function openPicker() {
		if (thisPid <= 0)
			return;

		var params = {
				pid: thisPid,
				p: ${p},
				u: ${u}
		};
		post('spot/picker', params, 'post');
	}

	
	function mapTypeSelected(val) {
		var mru = $('#input_map_res_url');
		if (val == 1) {			// pictogram			// TODO 얘는 선택하는 녀석으로 바꿔야 하는데...
			$('#map_symbol_file_field').hide();
			$('#map_pictogram_field').show();
			mru.val('');
		} else if (val >= 2) {			// symbol
			$('#map_symbol_file_field').show();
			$('#map_pictogram_field').hide();
			mru.val('');
		} else {
			// accept="image/*|text/html"
			// TODO mru의 값을 없애버리자.... 업로드 될 수도 있으니까...
			$('#map_symbol_file_field').hide();
			$('#map_pictogram_field').hide();
			mru.val('');
		}
	}

	var curLang = '';
	function selectLang(lang) {
		// console.log("selectLang", "${categories}");
		if (lang == null)
			lang = curLang;
		else
			curLang = lang;
		
		$(".lang_tabs").removeClass("active");
		$("#lang_tab_" + lang).addClass("active");

		$(".lang_display").hide();
		$(".lang_dis_" + lang).show();
	}

	function selectType() {
	    var type = $("#input_type").val();

        if(type === "text") {
            $("#pictoMarker").css("display", "none");
            $("#textMarker").css("display", "block");
        }else{
            $("#pictoMarker").css("display", "block");
            $("#textMarker").css("display", "none");
        }
	}

	function newForm() {
		$('#editModalTitle').html('지도 장소 추가');
		$('#edit_form').attr('action', 'spot/add');
		
		thisPid = -1;
		$('#input_pid').val(0);

		$('#picker_button').attr("disabled", "disabled");

		$('#input_lat').val(0.0);			// TODO 현재 위치??
		$('#input_lng').val(0.0);
		$('#input_map_type').val(1);
		mapTypeSelected(1);
		$('#input_map_res_url').val('');
		$('#map_symbol_file_field .form-control').val('');
		$('#map_symbol_file_field .form-control').attr('placeholder', '');
		$("#input_mid").val(1);
		
		$("#input_h, #input_w").val("");
		$("#input_text_direction").val("bottom");

        $("#input_name_display").val("");
		$("#input_display_zoom").val(0);
        $("#input_display_zoom_max").val(0);
		$("#input_mid").val(1);
        $("#input_type").val('');
        $("#input_text_rotate").val('');
		
		langs.forEach(function(lang, index) {
			$('#input_name_'+lang).val("");
		});

        selectType();
		selectLang('ko');
	}

	function initEditForm(id) {
		newForm();
		console.log("id:", id);
		thisPid = id;

		$('#editModalTitle').html('지도 장소 변경');
		$('#edit_form').attr('action', 'spot/update?p=${p}&u=${u}');
		$('#input_pid').val(id);

		$('#picker_button').removeAttr("disabled");

		$('#input_lat').val($('#lat-row-' + id).attr('value'));
		$('#input_lng').val($('#lng-row-' + id).attr('value'));
		var map_type = $('#map_type-row-' + id).attr('value');
		$('#input_map_type').val(map_type);
		mapTypeSelected(map_type);

		if (map_type == 1)
			$('#input_pictogram_id').val(parseInt($('#map_res_url-row-' + id).attr('value')));
		else if (map_type >= 2) {
			$('#input_map_res_url').val('');
			$('#map_symbol_file_field .form-control').val('');
			$('#map_symbol_file_field .form-control').attr('placeholder', $('#map_res_url-row-' + id).attr('value'));
		}
		
		$('#input_mid').val($('#mid-row-' + id).attr('value'));
		$('#input_text_direction').val($('#text_direction-row-' + id).attr('value'));		
		$('#input_display_zoom').val($('#display_zoom-row-' + id).attr('value'));
        $('#input_display_zoom_max').val($('#display_zoom_max-row-' + id).attr('value'));
        $('#input_name_display').val($('#name_display-row-' + id).attr('value'));

		$('#input_w').val($('#w-row-' + id).attr('value'));
		$('#input_h').val($('#h-row-' + id).attr('value'));

        $("#input_type").val($('#type-row-' + id).attr('value'));
        $("#input_text_rotate").val($('#text_rotate-row-' + id).attr('value'));

		langs.forEach(function(lang, index) {
			$('#input_name_'+lang).val($('#name_' + lang + '-row-' + id).attr('value'));
		});

        selectType();
		selectLang('ko');
	}
	
	function adjustForm() {
    	// 사용 여부 처리...
    	// 이름 확인.
		var has_name = false;
		langs.some(function(lang, li) {
			has_name = ($('#input_name_' + lang).val() != "");
			return has_name;
		});
		if (!has_name)
			return "이름이 비었어요.";
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