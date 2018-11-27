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
				<input type="hidden" id="input_num_of_fields" name="num_of_fields" value="">

				<input type="hidden" id="input_custom_field_ko" name="custom_field_ko" value="">
				<input type="hidden" id="input_custom_field_en" name="custom_field_en" value="">
				<input type="hidden" id="input_custom_field_cn" name="custom_field_cn" value="">
				<input type="hidden" id="input_custom_field_jp" name="custom_field_jp" value="">

				<input type="hidden" id="input_custom_value_ko" name="custom_value_ko" value="">
				<input type="hidden" id="input_custom_value_en" name="custom_value_en" value="">
				<input type="hidden" id="input_custom_value_cn" name="custom_value_cn" value="">
				<input type="hidden" id="input_custom_value_jp" name="custom_value_jp" value="">

				<input type="hidden" id="input_c_custom_value_ko" name="c_custom_value_ko" value="">
				<input type="hidden" id="input_c_custom_value_en" name="c_custom_value_en" value="">
				<input type="hidden" id="input_c_custom_value_cn" name="c_custom_value_cn" value="">
				<input type="hidden" id="input_c_custom_value_jp" name="c_custom_value_jp" value="">

				<input type="hidden" id="input_incoming_image" name="incoming_image" value="">
				<input type="hidden" id="input_incoming_video" name="incoming_video" value="">
				<input type="hidden" id="input_incoming_other" name="incoming_other" value="">
				<input type="hidden" name="custom_field_num" value="0">

				<div class="modal-body">
					<div class="row" id="parent_area_head">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">카테고리</button>
						</div>
						<div class="col-md-10">
							<select class="form-control" name="cid" id="input_category" onchange="categoryChanged($(this).val()); selectLang(null);">
							<c:forEach var="cat" items="${categories}" >
                              <option value="${cat.cid}">${cat.title}</option>
							</c:forEach>
                          </select>
						</div>
					</div>
					<div style="height: 10px;"></div>
					
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
					
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="addUpload('image');">이미지</button>
						</div>
						<div class="col-md-8" id="image_main_field">
							<input type="file" class="filestyle" data-btnClass="btn-primary" accept="image/*" name="image_main" id="input_image_main" />
						</div>
						<div class="col-md-2">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="deleteUpload('image', 0);">삭제</button>
						</div>
					</div>
					<div id="image_more_area">
					</div>
					<div style="height: 10px;"></div>

					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;" onclick="addUpload('video');">비디오</button>
						</div>
						<div class="col-md-8" id="video_main_field">
							<input type="file" class="filestyle" data-btnClass="btn-primary"
								accept="video/*" name="video_main" id="input_video_main" />
						</div>
						<div class="col-md-2">
							<div id="input_delete1">
								<button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="deleteUpload('video', 0);">삭제</button>
							</div>
						</div>
					</div>
					<div id="video_more_area">
					</div>
					<div style="height: 10px;"></div>

					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="addUpload('other');">기타파일</button>
						</div>
						<div class="col-md-8" id="other_main_field">
							<input type="file" class="filestyle" data-btnClass="btn-primary" name="other_main" id="input_other_main" />
						</div>
						<div class="col-md-2">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="deleteUpload('other', 0);">삭제</button>
						</div>
					</div>
					<div id="other_more_area">
					</div>
					<div style="height: 10px;"></div>

					<div class="row" id="phone_area">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">전화번호</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="phone"
								id="input_phone">
						</div>
					</div>
					<div style="height: 10px;"></div>

					<c:forEach var="l" items="${languages}" varStatus="loop">
					<div class="row lang_display lang_dis_${l}">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">주소</button>
						</div>
						<div class="col-md-1 detailer" style="padding-right:0px;">(${lang_names[loop.index]})</div>
						<div class="col-md-9">
							<input type="text" class="form-control" value="" name="address_${l}"
								id="input_address_${l}">
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
					
					<c:forEach var="l" items="${languages}" varStatus="loop">
					<div class="row lang_display lang_dis_${l}">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">설명</button>
						</div>
						<div class="col-md-1 detailer" style="padding-right:0px;">(${lang_names[loop.index]})</div>
						<div class="col-md-9">
							<%-- <input type="textarea" class="form-control" value="" name="text_${l}" id="input_text_${l}"> --%>
							<textarea class="form-control" rows="3" name="text_${l}" id="input_text_${l}"></textarea>
						</div>
					</div>
					</c:forEach>
					<div style="height: 10px;"></div>
					
					<div class="row" id="map_area">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">지도</button>
						</div>
						<div class="col-md-3">
							<select class="form-control" name="map_type" id="input_map_type" onchange="mapTypeSelected($(this).val())">
							  <option value="0">표시 안함</option>
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
					
					<div class="row" id="display_zoom_area">
						<div class="col-md-3 clabel" style="padding-right:0px;">표시 줌레벨</div>
						<div class="col-md-9">
							<input type="number" class="form-control" value="" min="7" max="20" name="display_zoom" id="input_display_zoom">
						</div>
					</div>
					<div style="height: 10px;"></div>
					<!-- 카테고리 별 항목들... -->
					<div id="category_custom_area">
					</div>
					
					<div style="height: 10px;"></div>
					<div style="height: 10px;"></div>
					<div class="row">
						<div class="col-md-9">
							<button type="button" class="col-md-12 btn btn-default"
								style="cursor: default;" onclick="addPlaceCustomField(); selectLang('ko');">장소 전용 필드 추가</button>
						</div>
						<div class="col-md-3" style="padding-top:10px;">
							<label class="radio-inline"><input type="radio" name="custom_field_type" value="textarea" checked />기본</label>&nbsp;
							<label class="radio-inline"><input type="radio" name="custom_field_type" value="file" />파일</label>
						</div>
					</div>
					<div style="height: 10px;"></div>
					<div id="custom_field_area"></div>
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
	var thisPid = -1;

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
		post('place/picker', params, 'post');
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
		elements += '    <input type="file" class="filestyle" data-btnClass="btn-primary" accept="'+type+'/*" name="'+name + miidx + '" id="input_'+name + key + '" />';
		// elements += '    <div class="bootstrap-filestyle input-group"><input type="text" class="form-control " placeholder="" disabled=""> <span class="group-span-filestyle input-group-btn" tabindex="0"><label for="input_more_image_' + miidx + '" style="margin-bottom: 0;" class="btn btn-primary "><span class="buttonText">파일선택</span></label></span></div>';
		elements += '  </div>';
		elements += '  <div class="col-md-2">';
		elements += '    <button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="deleteUpload(\'' + type + '\', ' + key + ');">삭제</button>';
		elements += '  </div>';
		elements += '</div>';
		
		mia.append(elements);
		
		// bootstrap 적용하기....
        applyFilestyle($('#' + name + 'area_' + key + ' .filestyle'));

		return key;
	}

	function applyFilestyle(target) {
        target.filestyle({
            'input' : target.attr('data-input') !== 'false',
            'htmlIcon' : target.attr('data-icon'),
            'buttonBefore' : target.attr('data-buttonBefore') === 'true',
            'disabled' : target.attr('data-disabled') === 'true',
            'size' : target.attr('data-size'),
            'text' : target.attr('data-text'),
            'btnClass' : target.attr('data-btnClass'),
            'badge' : target.attr('data-badge') === 'true',
            'badgeName' : target.attr('data-badgeName'),
            'placeholder': target.attr('data-placeholder')
        });
	}

	function deleteUpload(type, idx) {
		//var mia = $('#more_image_area');
		//var miidx = (mia.children().length) + 1;
		if (idx == 0) {
			// 애는 좀 다르게....
			//$('#main_' + type + '_field .filestyle').clear();
			//$('#main_' + type + '_field .filestyle').fileinput('clear');
			$('#' + type + '_main_field .filestyle').val('');
			$('#' + type + '_main_field .form-control').attr('placeholder', '');
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


	// 카테고리 변경 이벤트
	function categoryChanged(cid) {
		// TODO 수정중인 것이라면... 전용 필드 날라갈 수 있으니 조심 하라는 팝업을 띄워줘도 될 듯...
		
		// console.log('changed to', $(obj).val());
		// var cid = $(obj).val();
		console.log('changed to', cid);
		
		var cca = $("#category_custom_area");
		cca.empty();

		if (cid == 0)
			return;
		
		var categories = ${categories_js};
		var tc = null;
		for (var i = 0; i < categories.length; i++) {
			if (categories[i]['cid'] == cid) {
				tc = categories[i];
				break;
			}
		}
		
		if (tc == null || tc['ccu'].length <= 0)
			return;
		
		cca.append('<div style="height: 10px;"></div>');
		cca.append('<div class="row" style="padding-left:10px;">카테고리 전용 항목</div>');
		var elements = '<div id="c_custom_values">';

		var field_yn = JSON.parse(tc['ccu']);
		var field_names = JSON.parse(tc['ccf']);
		
		for (var i = 0; i < field_yn.length; i++) {
			langs.forEach(function(lang, index) {
				elements += '<div class="row lang_display lang_dis_' + lang + '">';
				elements += '  <div class="col-md-2 category"><button type="button" class="btn btn-default btn-block" style="cursor: default;">' + field_names[i] + '</button></div>';
				elements += '  <div class="col-md-1 detailer" style="padding-right:0px;">(' + lang_names[index] + ')</div>';
				//elements += '  <div class="col-md-9"><input type="text" class="form-control" value="" name="ccv_' + i + '" id="input_ccv_' + i + '"' + (field_yn[i] ? '' : 'disabled') + '></div>';
				//elements += '  <div class="col-md-9"><input type="text" class="form-control" value="" id="input_ccv_' + lang + '_' + i + '"' + (field_yn[i] ? '' : 'disabled') + '></div>';
				elements += '  <div class="col-md-9"><textarea class="form-control" rows="1" id="input_ccv_' + lang + '_' + i + '"' + (field_yn[i] ? '' : 'disabled') + '></textarea></div>';
				elements += '</div>';
			});
			
			// cca.append(elements);
		}

		elements += '</div>';
		cca.append(elements);
		// selectLang(null);
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

	function newForm() {
		$('#editModalTitle').html('장소 추가');
		$('#edit_form').attr('action', 'place/add');
		
		thisPid = -1;
		$('#input_pid').val(0);

		/* $('#picker_button').attr("disabled", "disabled"); */

		// $('#input_category').val(0);		// TODO 첫 카테고리를 선택해줘야 할텐데...
		console.log(${cid});
		//$('#input_category').val(${cid});		// TODO 첫 카테고리를 선택해줘야 할텐데...
		$('#input_lat').val(0.0);			// TODO 현재 위치??
		$('#input_lng').val(0.0);
		$("#input_display_zoom").val("");
		$('#input_map_type').val(0);
		mapTypeSelected(0);
		$('#input_map_res_url').val('');
		$('#map_symbol_file_field .form-control').val('');
		$('#map_symbol_file_field .form-control').attr('placeholder', '');

		$('#input_phone').val("");

		langs.forEach(function(lang, index) {
			$('#input_name_'+lang).val("");
			$('#input_text_'+lang).val("");
			$('#input_address_'+lang).val("");

			$('#input_c_custom_value_' + lang).val("");
			$('#input_custom_field_' + lang).val("");
			$('#input_custom_value_' + lang).val("");
		});
		
		//categoryChanged(0);
		//clearPlaceCustomField();

		selectLang('ko');
		
		var types = ['image', 'video', 'other'];
		types.forEach(function(type) {
			clearMoreUploads(type);
			$('#input_incoming_' + type).val('');
		});
        $("[name=file_delete]").val("");
	}

	function initEditForm(id) {
		console.log("id:", id);
		thisPid = id;

		$('#editModalTitle').html('장소 변경');
		// $('#edit_form').attr('action', 'place/update');
		$('#edit_form').attr('action', 'place/update?p=${p}&u=${u}&c=${cid}');
		$('#input_pid').val(id);

		$('#picker_button').removeAttr("disabled");

		var cid = $('#cid-row-' + id).attr('value');
		$('#input_category').val(cid);
		
		$('#input_display_zoom').val($('#display_zoom-row-' + id).attr('value'));
		
		$('#input_lat').val($('#lat-row-' + id).attr('value'));
		$('#input_lng').val($('#lng-row-' + id).attr('value'));
		var map_type = Number($('#map_type-row-' + id).attr('value'));
		$('#input_map_type').val(map_type);
		mapTypeSelected(map_type);

		//$('#input_map_res_url').val($('#map_res_url-row-' + id).attr('value'));
		if (map_type === 1)
			$('#input_pictogram_id').val(parseInt($('#map_res_url-row-' + id).attr('value')));
		else if (map_type >= 2) {
			$('#input_map_res_url').val('');
			$('#map_symbol_file_field .form-control').val('').attr('placeholder', $('#map_res_url-row-' + id).attr('value'));
		}

		$('#input_phone').val($('#phone-row-' + id).attr('value'));

		categoryChanged(cid);
		langs.forEach(function(lang, index) {
			$('#input_name_'+lang).val($('#name_' + lang + '-row-' + id).attr('value'));
			$('#input_text_'+lang).val($('#text_' + lang + '-row-' + id).attr('value'));
			$('#input_address_'+lang).val($('#address_' + lang + '-row-' + id).attr('value'));

			// 어짜피 아래 것들은 submit 하기전에 주어진다...
			$('#input_c_custom_value_' + lang).val("");
			$('#input_custom_field_' + lang).val("");
			$('#input_custom_value_' + lang).val("");

			// ccv_
			try {
				var ccv = JSON.parse($('#ccv_' + lang + '-row-' + id).attr('value'));
				if (ccv.length > 0) {
					// var cfai = $('#c_custom_values input');
					var cfai = $('#c_custom_values textarea');
					for (var i = 0; i < ccv.length; i++) {
						// cfa.children()[index + (i * langs.length)].value = ccv[i];
						// cfa.children()[index + (i * langs.length)].set("value", ccv[i]);
						cfai[index + (i * langs.length)].value = ccv[i];
						//cfa.find('#input_ccv_' + lang + '_' + i).val(ccv[i]);
					}
				}
			} catch (err) {}
			
			// custom_field_name
			// custom_field_value
		});
		
		clearPlaceCustomField();
		try {
			var num_of_cfs = JSON.parse($('#cf_ko-row-' + id).attr('value')).length;
            var cv = JSON.parse($('#cv_ko-row-' + id).attr('value'));
			if (num_of_cfs > 0) {
				// 일단 갯수만큼 추가....
				for (var fi = 0; fi < num_of_cfs; fi++) {
					addPlaceCustomField((/^cv_/.test(cv[fi]))?"file":"");		// TODO key check!!! 시간이라서...
				}

				var cfa_children = $('#custom_field_area').children();
				langs.forEach(function(lang, li) {
					var cf = JSON.parse($('#cf_' + lang + '-row-' + id).attr('value'));
					var cv = JSON.parse($('#cv_' + lang + '-row-' + id).attr('value'));

                    cfa_children.each(function(idx) {
                        var target = $(this).find(".lang_dis_"+lang);
                        var value = cv[idx];
                        target.find("[name=input_custom_field_name_"+lang+"]").val(cf[idx]);

                        if((/^cv_/.test(value))?"file":""){
                            target.find("[name=input_custom_field_value_"+lang+"]").parent().find(":text").attr("placeholder", value);
						}else{
                            target.find("[name=input_custom_field_value_"+lang+"]").val(value);
						}
					});
				});
			}

		} catch (err) {
			console.log("장소 전용 추가 에러...", err);
		}
		
		selectLang('ko');

		// TODO images
		var types = ['image', 'video', 'other'];
		types.forEach(function(type) {
			clearMoreUploads(type);
			$('#input_incoming_' + type).val('');

			var array_str = $('#' + type + 's-row-' + id).attr('value') || "[]";
			try {
				var arr = JSON.parse(array_str);
				if (arr.length > 0) {
					$('#' + type + '_main_field .form-control').attr('placeholder', arr[0]);
					// $('#input_image_main').val(arr[0]);
					for (var i = 1; i < arr.length; i++) {
						addUpload(type);
						$('#upload_'+type+'_field_'+i+' .form-control').attr('placeholder', arr[i]);
					}
				}
			} catch (err) {
				console.log(err);
			}
		});
        $("[name=file_delete]").val("");
	}
	
	// TODO input_map_type 이녀석의 값에 따라 input_map_res_url를 활성화/비활성화
	function clearPlaceCustomField() {
		$('#custom_field_area').empty();
	}
	
	function addPlaceCustomField(type) {
		var elements = '<div id="lang_row" style="margin-bottom:10px;">';
		if(!type) {
            type = $("[name=custom_field_type]:checked").val();
		}

		langs.forEach(function(lang, index) {
			elements += '<div class="row lang_display lang_dis_' + lang + '">';
			elements += '  <div class="col-md-2 category"> <button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="removeField(this)">필드 삭제</button></div>';
			elements += '  <div class="col-md-1 detailer" style="padding-right:0px;">' + lang_names[index] + '</div>';
			elements += '  <div class="col-md-4"><input type="text" class="form-control name" placeholder="제목" name="input_custom_field_name_' + lang + '"></div>';
			if(type === "textarea") {
                elements += '  <div class="col-md-5"><textarea class="form-control value" rows="1" placeholder="내용" name="input_custom_field_value_' + lang + '"></textarea></div>';
			}else if(type === "file") {
                elements += '  <div class="col-md-5"><input type="file" class="filestyle value" data-btnClass="btn-primary" placeholder="내용" name="input_custom_field_value_' + lang + '" /></div>';
            }
			elements += '</div>';
		});
        elements += '</div>';

        $('#custom_field_area').append(elements);

        if(type === "file") {
            var target = $('#custom_field_area').children().last();
            langs.forEach(function(lang, index) {
                applyFilestyle(target.find("[name=input_custom_field_value_"+lang+"]"));
            });
		}
	}
	
	function removeField(that) {
	    $(that).parents("#lang_row").remove();
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
		
		// 카테고리..
		//console.log($('#input_category').val());
		if ($('#input_category').val() == null) {
			return "카테고리를 선택하세요.";
		}

		// 이미지 또는 비디오 input 이름들을 좀 바꾸자... 순차적으로 처리 하도록...
		var types = ['image', 'video', 'other'];
		types.forEach(function(type) {
			var incoming = [0];
			$('#' + type + '_more_area .input_holder').each(function(index) {
				var holderId = $(this).attr('id');
				incoming.push(parseInt(holderId.split('_')[3]));
			});
			$('#input_incoming_' + type).val(JSON.stringify(incoming));

			$('#' + type + '_more_area .filestyle').each(function(index) {
				$(this).attr('name', type + '_more_' + (index+1));
			});
		});

		// checking image.. 나중에..
		//if ($('#input_icon_file').val() == "" && $('#uploaded_image_area img').attr('src') == "") {
		//	return "이미지가 없어요.";
		//}
	
		
    	// c_custom_value_ : 카테고리 전용 필드 값
    	// custom_field_ : 장소 전용 필드 이름
    	// custom_value_ : 장소 전용 필드 이름
    	
    	// 카테고리 전용 필드 값....
    	var ccv = $("#c_custom_values");
		var ccfi = ccv.children().length / langs.length;
    	if (ccfi > 0) {
			// var cvalue_inputs = ccv.find('input');
			var cvalue_inputs = ccv.find('textarea');
			langs.forEach(function(lang, index) {
				var v = [];
				for (var i = 0; i < ccfi; i++) {
					v.push(cvalue_inputs[index + (i * 4)].value);
				}
				$('#input_c_custom_value_' + lang).val(JSON.stringify(v));
			});
    	} else {
			langs.forEach(function(lang, index) {
				$('#input_c_custom_value_' + lang).val('');
			});
    	}
		
		// 장소 전용 필드...
		// var cfa = $('#custom_field_area');
		// var cfi = cfa.children().length / langs.length;
		// if (cfi > 0) {
		// 	var name_inputs = cfa.find('input.name');
		// 	var value_inputs = cfa.find('textarea.value');
		// 	//var value_inputs = cfa.find('input.value');
		//
		// 	langs.forEach(function(lang, index) {
		// 		var n = [];
		// 		var v = [];
		// 		for (var i = 0; i < cfi; i++) {
		// 			n.push(name_inputs[index + (i * langs.length)].value);
		// 			v.push(value_inputs[index + (i * langs.length)].value);
		// 		}
		//
		// 		$('#input_custom_field_' + lang).val(JSON.stringify(n));
		// 		$('#input_custom_value_' + lang).val(JSON.stringify(v));
		// 	});
		//
		// 	$('#input_num_of_fields').val(cfi);
		// } else {
		// 	// 없는 경우 강제로 빈 필드를 줘야 겠음..
		// 	langs.forEach(function(lang, index) {
		// 		$('#input_custom_field_' + lang).val("");
		// 		$('#input_custom_value_' + lang).val("");
		// 	});
		// }

        if($('#custom_field_area').children().length > 0) {
            $('#custom_field_area').children().each(function(idx){
                function nameChange () {
                    var name = $(this).attr("name");
                    name+= "_" + (idx + 1);
                    $(this).attr("name", name);
                }

                function beforeValue () {
                    var text = $(this).parent().find(":text");
                    var placeholder = text.attr("placeholder");

                    if(text.length > 0 && placeholder !== "") {
                        $("#edit_form").append('<input type="hidden" name="before_'+$(this).attr("name")+'" value="'+placeholder+'">');
                    }
                }
                $(this).find("[name^=input_custom_field_name_]").each(nameChange);
                $(this).find("[name^=input_custom_field_value_]").each(nameChange);
                if(thisPid >-1) {
                    $(this).find("[name^=input_custom_field_value_]").each(beforeValue);
				}

                $("[name=custom_field_num]").val(idx + 1);
			});
		} else{
            langs.forEach(function(lang, index) {
                $('#input_custom_field_' + lang).val("");
                $('#input_custom_value_' + lang).val("");
            });
            $("[name=custom_field_num]").val(0);
		}
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