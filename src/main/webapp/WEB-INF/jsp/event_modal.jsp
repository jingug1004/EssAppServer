<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 카테고리용 모달 편집창 -->
<c:set var="lang_names" value="${['한국어', '영어', '중국어', '일본어']}" />
<c:set var="places" value="${places}" />

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

			<form role="form" enctype="multipart/form-data" action="/process/update_event/" method="post" id="edit_form">
				<!-- <input type="hidden" id="input_action" name="action" value=""> -->

				<input type="hidden" id="input_eid" name="eid" value="">

				<div class="modal-body">
					<c:forEach var="l" items="${languages}" varStatus="loop">
					<div class="row lang_display lang_dis_${l}">
						<div class="col-md-2 category">
								<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">이름</button>
						</div>
						<div class="col-md-1 detailer" style="padding-right:0px;">${lang_names[loop.index]}</div>
						<div class="col-md-9">
							<input type="text" class="form-control" value="" name="name_${l}"
								id="input_name_${l}">
						</div>
					</div>
					</c:forEach>
					<div style="height: 10px;"></div>

					<div class="row" id="map_area">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;">이미지</button>
						</div>
						<div class="col-md-10">
							<input type="file" class="filestyle" data-btnClass="btn-primary" accept="image/*" name="image" id="input_image" placeholder="행사 포스터"/>
						</div>
					</div>
					<div style="height: 10px;"></div>

					<c:forEach var="l" items="${languages}" varStatus="loop">
						<div class="row lang_display lang_dis_${l}">
							<div class="col-md-2 category">
								<button type="button" class="btn btn-default btn-block" style="cursor: default;">설명</button>
							</div>
							<div class="col-md-1 detailer" style="padding-right:0px;">${lang_names[loop.index]}</div>
							<div class="col-md-9">
								<textarea class="form-control" rows="3" name="text_${l}" id="input_text_${l}">${places}</textarea>
							</div>
						</div>
					</c:forEach>
					<div style="height: 10px;"></div>


					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;">관련 장소</button>
						</div>
						<div class="col-md-4">
							<select class="form-control" name="cid" id="input_category" onchange="makePlaceOptions(this.value)">
								<option value="">선택</option>
								<c:forEach var="cat" items="${ordered_categories}">
									<c:if test="${'1' eq cat.status}">
										<option value="${cat.cid}"><c:if test="${'0' ne cat.pcid}">&nbsp;&nbsp;&nbsp;</c:if>${cat.title}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;">관련 장소</button>
						</div>
						<div class="col-md-4">
							<select class="form-control" name="pid" id="input_place" onchange="changePlace(this.value)"></select>
						</div>
					</div>
					<div style="height: 10px;"></div>


					<div class="row">
						<div class="col-md-12">
							<button type="button" class="col-md-12 btn btn-default"
									style="cursor: default;" onclick="addCustomField(); selectLang('ko');">행사 전용 필드 추가</button>
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

<script>var places = "<c:out value="${places}" />";</script>
<script>
	var langs = ['ko', 'en', 'cn', 'jp'];
	var lang_names = ['한국어', '영어', '중국어', '일본어'];
	var thisPid = -1;
    if(places !== ""){
        places = JSON.parse(places.split("&#034;").join("\"").split(/&lt;br&gt;/).join(" "));
        places = places.sort(function(a,b) {
				if(a.name > b.name){
				return 1;
			}else if(a.name < b.name){
				return -1;
			}
			return 0;
		});
	}
    makePlaceOptions();

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
	
	<%--function openPicker() {--%>
		<%--if (thisPid <= 0)--%>
			<%--return;--%>

		<%--var params = {--%>
				<%--pid: thisPid,--%>
				<%--p: ${p},--%>
				<%--u: ${u}--%>
		<%--};--%>
		<%--post('spot/picker', params, 'post');--%>
	<%--}--%>

	function makePlaceOptions(cid) {
        var tempPlaces;
		if(cid) {
            cid = Number(cid);
            tempPlaces = places.filter(function(place){return place.cid === cid});
        }else {
            tempPlaces = places;
		}
		var optionHtml = '<option value="">선택</option>';

        tempPlaces.map(function(place){
            optionHtml += '<option value="'+place.pid+'">' +place.name.split("<br>").join("")+ '</option>';
		});

        $("#input_place").html(optionHtml);
	}

    function changePlace(pid) {
	    pid = Number(pid);
        var place = places.filter(function(place){return place.pid === pid})[0];

        if(place.cid && Number($("#input_category").val()) !== place.cid) {
            $("#input_category option[value="+place.cid+"]").prop("selected", true);
		}
    }

    function addCustomField() {
        var custom_field_area = $("#custom_field_area");
        var elements = '<div id="num">';

        // 언어별 필드 이름들...
        langs.map(function(lang, index) {
            elements += '<div class="row lang_display lang_dis_' + lang + '">';
            elements += '  <div class="col-md-2 category"> <button type="button" class="btn btn-default btn-block" style="cursor: default;" onclick="removeCustomField(this)">삭제</button></div>';
            elements += '  <div class="col-md-1 detailer" style="padding-right:0px;">' + lang_names[index] + '</div>';
            elements += '  <div class="col-md-4"><input type="text" class="form-control name" placeholder="제목" value="" id="input_field_name_' + lang + '"></div>';
            elements += '  <div class="col-md-5"><textarea class="form-control value" rows="1" placeholder="내용" id="input_field_value_' + lang + '"></textarea></div>';
            elements += '</div>';
        });
        elements += '<div style="height: 10px;"></div></div>';

        custom_field_area.append(elements);
	}

    function removeCustomField(that) {
        $(that).parents("#num").remove();
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

	function makeCfData() {
        var edit_form = $("#edit_form");
        langs.map(function(lang){
            var cf = [];
            $("#custom_field_area").find(".lang_dis_"+lang).each(function(idx, dom){
                var name = $(dom).find("#input_field_name_"+lang).val();

                if(name) {
                    cf.push({
                        name: name,
                        value: $(dom).find("#input_field_value_"+lang).val()
                    });
                }
            });

            edit_form.append('<input type="hidden" name="cf_'+lang+'">');
            edit_form.find("[name=cf_"+lang+"]").val(JSON.stringify(cf));
            return cf;
        });
    }

    function setCfDatas(id) {
	    var maxSize = 0;
        var cfDataArr = langs.map(function(lang) {
            var value = JSON.parse($("#cf_" + lang + "-row-" + id).text());
            if(maxSize < value.length) {
                maxSize = value.length;
			}
            return value;
        });

        if(maxSize > 0) {
            for(var i=0; i<maxSize; i++) {
                addCustomField();
			}
		}

        var custom_field_area = $("#custom_field_area").children();
        langs.map(function(lang, idx) {
            var data = cfDataArr[idx];
            var tempData;
            for(var i=0, n=data.length; i<n; i++) {
                var target = custom_field_area.eq(i);
                tempData = data[i];
                target.find("#input_field_name_"+lang).val(tempData.name);
                target.find("#input_field_value_"+lang).val(tempData.value);
			}
        });
	}

	function newForm() {
		$('#editModalTitle').html('행사 추가');
		$('#edit_form').attr('action', 'event/add');

        $("#input_category").val("");
        $("#input_place").val("");

        $("#input_image").next().find(":text").attr("placeholder", "");
        $("#custom_field_area #num").find(".lang_dis_ko .btn").click(); // 커스텀 필드 삭제.
		
		langs.forEach(function(lang, index) {
			$('#input_name_'+lang).val("");
            $('#input_text_'+lang).val("");
		});
		
		selectLang('ko');
	}

	function initEditForm(id) {
		newForm();
		console.log("id:", id);
		$("#input_eid").val(id);

		$('#editModalTitle').html('행사 변경');
		$('#edit_form').attr('action', 'event/update?p=${p}&u=${u}');

		if($("#pid-row-"+ id).text()) {
            $("#input_place").val($("#pid-row-"+ id).text()).change();
        }

        $("#input_image").next().find(":text").attr("placeholder", $("#image-row-"+ id).text());

		langs.forEach(function(lang) {
			$('#input_name_'+lang).val($('#name_' + lang + '-row-' + id).text().split('\\"').join("'"));
            $('#input_text_'+lang).val($('#text_' + lang + '-row-' + id).text());
		});

        setCfDatas(id);
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
        makeCfData();

		$("#edit_form")[0].submit();
	}
</script>