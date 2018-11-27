<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<h4 class="modal-title" id="editModalTitle"></h4>
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
				<input type="hidden" id="input_id" name="cid" value="">

				<input type="hidden" id="input_ccu" name="ccu" value="">
				<input type="hidden" id="input_num_of_fields" name="num_of_fields" value="">

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
				
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">이미지</button>
						</div>
						<div id="uploaded_image_area" class="col-md-1">
							<img style="height:34px;"/>
						</div>
						<div id="upload_image_field" class="col-md-9">
							<input type="file" class="filestyle" data-btnClass="btn-primary" accept="image/*" name="icon_file" id="input_icon_file" />
						</div>
					</div>

					<div style="height: 10px;"></div>

					<div class="row" id="parent_area_head">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block detailer"
								style="cursor: default;">부모 카테고리</button>
						</div>
						<div class="col-md-10">
							<select class="form-control" name="pcid" id="input_pcid">
							<!-- TODO 자신은 빼야 할텐데... -->
							<c:forEach var="cat" items="${roots}" >
                              <option id="category_option_${cat.cid}" value="${cat.cid}">${cat.title}</option>
							</c:forEach>
                          </select>
						</div>
					</div>
					
					<div style="height: 10px;"></div>

					<div class="row" id="parent_area_head">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block detailer"
									style="cursor: default;">정렬 번호</button>
						</div>
						<div class="col-md-10">
							<input type="number" class="form-control" name="sort" id="input_sort" >
						</div>
					</div>
					<div style="height: 10px;"></div>

					<div class="row" id="parent_area_head">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block detailer" style="cursor: default;">링크 URL</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" name="link" id="input_link" >
						</div>
					</div>

					<div style="height: 10px;"></div>

					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block detailer" style="cursor: default;">도움말 파일</button>
						</div>
						<div class="col-md-8">
							<input type="file" class="filestyle" data-btnClass="btn-primary" accept="application/pdf" name="help_file" id="input_help_file" />
						</div>
						<div class="col-md-2">
							<button type="button" class="btn btn-default" style="right: 15px;position: absolute;" onclick="deleteHelpFile()">삭제</button>
						</div>
					</div>

					<div style="height: 10px;"></div>
					
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="col-md-12 btn btn-default"
								style="cursor: default;" onclick="addCustomField(); selectLang('ko');">카테고리 전용 필드 추가</button>
						</div>
					</div>
					<div style="height: 10px;"></div>
					<div id="custom_field_area"></div>

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

	// 필드 추가 기능..
	function addCustomField() {
		var cfa = $('#custom_field_area');

		// +1은 사용 체크 버튼
		var cfi = cfa.children().length / (langs.length + 1);
		var elements = '';
		
		// 언어별 필드 이름들...
		langs.forEach(function(lang, index) {
			elements += '<div class="row lang_display lang_dis_' + lang + '">';
			elements += '  <div class="col-md-2 category"> <button type="button" class="btn btn-default btn-block" style="cursor: default;">' + (cfi+1) + '.필드이름</button></div>';
			elements += '  <div class="col-md-1 detailer" style="padding-right:0px;">(' + lang_names[index] + ')</div>';
			elements += '  <div class="col-md-9"><input type="text" class="form-control" value="" name="ccf_' + lang + '_' + cfi + '" id="input_field_name_' + lang + '_' + cfi + '"></div>';
			elements += '</div>';
		});

		// 사용 여부 체크..
		elements += '<div class="row">';
		elements += '  <div class="col-md-3">&nbsp;</div>';
		elements += '  <div class="col-md-9"><label class="checkbox-inline"><input type="checkbox" checked id="input_use_yn_' + cfi + '" name="ccu_' + cfi + '">사용</label></div>';
		elements += '</div>';

		cfa.append(elements);
	}
	
	function clearCustomField() {
		var cfa = $('#custom_field_area');
		cfa.empty();
	}

	function deleteHelpFile() {
        var help_file_delete = $('#help_file-row-' + $("#input_id").val()).attr("value");

		if(help_file_delete && confirm("도움말 파일을 삭제하시겠습니까?")) {
            $('#input_help_file').next().find(":text").attr("placeholder", "");
            $("#edit_form").append('<input type="hidden" name="help_file_delete" value="'+help_file_delete+'">');
		}
	}

	function newForm() {
		$('#editModalTitle').html('카테고리 추가');
		$('#edit_form').attr('action', 'category/add');
		
		$('#input_id').val(0);

		var image_area = $('#uploaded_image_area');
		image_area.hide();
		image_area.find("img").attr('src', '');
		$('#upload_image_field > div > input').attr('placeholder', '');

		var image_field = $('#upload_image_field');
		image_field.removeClass("col-md-9");
		image_field.addClass("col-md-10");

		langs.forEach(function(lang, index) {
			$('#input_name_' + lang).val("");
		});



		$('#input_pcid').val(0);

        $('#input_sort').val(100);
        $('#input_link').val("");

		$('#input_pcid option').show();
		
		$('#input_ccu').val("");
		$('#input_num_of_fields').val(0);

        $('#input_help_file').next().find(":text").attr("placeholder", "");

		clearCustomField();
		selectLang('ko');
	}

	function initEditForm(id) {
		console.log("id:", id);

		$('#editModalTitle').html('카테고리 변경');
		// $('#edit_form').attr('action', 'category/update');
		$('#edit_form').attr('action', 'category/update?p=${p}&u=${u}');
		$('#input_id').val(id);

		var icon_url = $('#icon_url-row-' + id).attr('value');
		var image_area = $('#uploaded_image_area');
		image_area.show();
		image_area.find("img").attr('src', '../../assets/category/' + id + '/' + icon_url);

		$('#upload_image_field > div > input').attr('placeholder', icon_url);

		var image_field = $('#upload_image_field');
		image_field.removeClass("col-md-10");
		image_field.addClass("col-md-9");
		
		$('#input_pcid').val($('#pcid-row-' + id).attr('value'));
		
		// 내꺼는 숨기기...
		$('#input_pcid option').show();
		$('#category_option_' + id).hide();

		// 이름 넣기.
		langs.forEach(function(lang, index) {
			$('#input_name_' + lang).val($('#title_' + lang + '-row-' + id).attr('value'));
		});

        $('#input_sort').val($('#sort-row-' + id).attr("value"));
        $('#input_link').val($('#link-row-' + id).attr("value"));

        $('#input_help_file').next().find(":text").attr("placeholder", $('#help_file-row-' + id).attr("value"));

		// 전용 필드 처리
		clearCustomField();
		
		$('#input_num_of_fields').val(0);

		var field_use_str = $('#ccu-row-' + id).attr('value');
		try {
			var ccu = JSON.parse(field_use_str);

			if (ccu.length > 0) {

				$('#input_num_of_fields').val(ccu.length);

				var field_names = [];			// [lang][index]
				langs.forEach(function(lang, index) {
					var names = JSON.parse($('#ccf_' + lang + '-row-' + id).attr('value'));
					field_names.push(names);
				});

				ccu.forEach(function(use, ui) {
					// 필드 추가.
					addCustomField();
					
					// 언어 별로 값 넣기.
					langs.forEach(function(lang, li) {
						var input_field_name = $('#input_field_name_' + lang + '_' + ui);
						input_field_name.val(field_names[li][ui]);
					});
				
					$('#input_use_yn_' + ui).prop("checked", use);
				});
			}
		} catch (err) { }

		$('#input_ccu').val(field_use_str);

		selectLang('ko');
	}
	
	function adjustForm() {
		// 이름 확인.
		var has_name = false;
		langs.some(function(lang, li) {
			has_name = ($('#input_name_' + lang).val() != "");
			return has_name;
		});
		if (!has_name)
			return "이름이 비었어요.";
		
		// checking image
		// if ($('#input_icon_file').val() == "" && $('#uploaded_image_area img').attr('src') == "") {
		//	return "이미지가 없어요.";
		//}
		
    	// 사용 여부 처리...
    	var cfa = $('#custom_field_area');

		// +1은 사용 체크 버튼
		var cfi = cfa.children().length / (langs.length + 1);
		if (cfi > 0) {
			var field_use_str = [];
			for (var i = 0; i < cfi; i++) {
				field_use_str.push($('#input_use_yn_' + i).prop("checked") ? 1 : 0);
			}
			$('#input_ccu').val(JSON.stringify(field_use_str));
			$('#input_num_of_fields').val(cfi);
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