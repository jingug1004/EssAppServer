<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
	aria-labelledby="editModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeDialog()">&times;</button> -->
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="editModalTitle"></h4>
			</div>

			<form role="form" enctype="multipart/form-data"
				action="/process/update_category/" method="post" id="edit_form">
				<!-- <input type="hidden" id="input_action" name="action" value=""> -->
				<input type="hidden" id="input_pid" name="pid" value="">

				<div class="modal-body">
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">이름</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="title" id="input_title">
						</div>
					</div>
					<div style="height: 10px;"></div>
				
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;">이미지</button>
						</div>
						<div id="uploaded_image_area" class="col-md-1">
							<img style="height:34px;"/>
						</div>
						<div id="upload_image_field" class="col-md-9">
							<input type="file" class="filestyle" data-btnClass="btn-primary" accept="image/*" name="icon_file" id="input_icon_file" />
						</div>
					</div>
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

	function newForm() {
		$('#editModalTitle').html('픽토그램 추가');
		$('#edit_form').attr('action', 'pictogram/add');
		
		$('#input_pid').val(0);

		var image_area = $('#uploaded_image_area');
		image_area.hide();
		image_area.find("img").attr('src', '');
		$('#upload_image_field > div > input').attr('placeholder', '');

		var image_field = $('#upload_image_field');
		image_field.removeClass("col-md-9");
		image_field.addClass("col-md-10");

		$('#input_title').val("");
	}

	function initEditForm(id) {
		console.log("id:", id);

		$('#editModalTitle').html('픽토그램 변경');
		$('#edit_form').attr('action', 'pictogram/update?p=${p}&u=${u}');
		
		$('#input_pid').val(id);

		var icon_url = $('#icon_url-row-' + id).attr('value');
		var image_area = $('#uploaded_image_area');
		image_area.hide();
		image_area.find("img").attr('src', '../../assets/pictogram/' + id + '/' + icon_url);
		$('#upload_image_field > div > input').attr('placeholder', icon_url);

		var image_field = $('#upload_image_field');
		image_field.removeClass("col-md-10");
		image_field.addClass("col-md-9");

		$('#input_title').val($('#title-row-' + id).attr('value'));
	}
	
	function adjustForm() {
		// 이름 확인.
		if ($('#input_title').val() == "")
			return "이름이 비었어요.";
		
		// checking image
		if ($('#input_icon_file').val() == "" && $('#uploaded_image_area img').attr('src') == "") {
			return "이미지가 없어요.";
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