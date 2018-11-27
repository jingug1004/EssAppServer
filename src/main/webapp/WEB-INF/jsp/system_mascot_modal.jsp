<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="mascotModal" tabindex="-1" role="dialog" aria-labelledby="mascotModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeDialog()">&times;</button> -->
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="mascotModalTitle"></h4>
			</div>

			<form role="form" enctype="multipart/form-data" action="/process/update_category/" method="post" id="mascot_form">
				<!-- <input type="hidden" id="input_action" name="action" value=""> -->
				<input type="hidden" id="input_id" name="id" value="">

				<div class="modal-body">
					<div class="row">
						<div class="col-md-3 category">
							<button type="button" class="btn btn-default btn-block"style="cursor: default;">파일 선택</button>
						</div>
						<div class="col-md-9">
							<input type="file" class="filestyle" data-btnClass="btn-primary" name="mascot" id="input_mascot" />
						</div>
					</div>
					<div style="height: 10px;"></div>

				</div>
				<div class="modal-footer">
					<!-- <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeDialog()">취소</button> -->
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" onclick="uploadDataMascot()">변경</button>
				</div>
			</form>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<script>

	function newFormMascot() {
		// 여기로 오면 안됨...
        $('#mascotModalTitle').html('로딩화면 로고 변경');
        $('#mascot_form').attr('action', 'mascot/update?p=${p}&u=${u}');
	}

	function adjustFormMascot() {
		// 이름 확인.
		if ($('#input_mascot').val() == "")
			return "파일이 없습니다.";
		
		return null;
	}

    function uploadDataMascot() {
		var err = adjustFormMascot();
		if (err != null) {
			alert(err);
			return;
		}

		$("#mascot_form")[0].submit();
    }
</script>