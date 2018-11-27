<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeDialog()">&times;</button> -->
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="editModalTitle"></h4>
			</div>

			<form role="form" enctype="multipart/form-data" action="/process/update_category/" method="post" id="edit_form">
				<!-- <input type="hidden" id="input_action" name="action" value=""> -->
				<input type="hidden" id="input_id" name="id" value="">

				<div class="modal-body">
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">이름</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" id="input_name">
						</div>
					</div>
					<div style="height: 10px;"></div>
				
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">값</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="the_value" id="input_the_value">
						</div>
					</div>
					<div style="height: 10px;"></div>

					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">설명</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="description" id="input_description">
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
		// 여기로 오면 안됨...
	}

	function initEditForm(id) {
		console.log("id:", id);

		$('#editModalTitle').html('서비스 변수 변경');
		$('#edit_form').attr('action', 'system/update?p=${p}&u=${u}');
		
		$('#input_id').val(id);
		$("#input_name").attr("disabled", true);
		$('#input_name').val($('#name-row-' + id).attr('value'));
		$('#input_the_value').val($('#the_value-row-' + id).attr('value'));
		$('#input_description').val($('#description-row-' + id).attr('value'));
	}
	
	function adjustForm() {
		// 이름 확인.
		if ($('#input_the_value').val() == "")
			return "값이 비었어요.";
		
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