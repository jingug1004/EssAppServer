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
				<input type="hidden" id="input_uid" name="uid" value="">

				<div class="modal-body">
			
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">아이디</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="id" id="input_id">							
						</div>
					</div>
					<div style="height: 10px;"></div>
					
					<div class="row" id="passwordDiv">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">패스워드</button>
						</div>
						<div class="col-md-10">
							<input type="password" class="form-control" value="" name="password" id="input_password">
							<button type="button" class="btn btn-default btn-block" style="cursor: default;">초기화</button>
						</div>
					</div>
					<div style="height: 10px;"></div>
				
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">타입</button>
						</div>
						<div class="col-md-10">
							<select class="form-control" name="type" id="input_type">
                              <option value="1">일반 유저</option>
                              <option value="2">상점 주인</option>
                              <option value="8">관리자</option>
                              <option value="9">최고 관리자</option>
                          </select>
						</div>
					</div>
					<div style="height: 10px;"></div>	
				
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">이름</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="name" id="input_name" >
						</div>
					</div>
					<div style="height: 10px;"></div>
				
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">전화번호</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="mobile" id="input_mobile" >
						</div>
					</div>
					<div style="height: 10px;"></div>	
				
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">생년월일</button>
						</div>
						<div class="col-md-10">
							<input type="date" class="form-control" value="" name="birth" id="input_birth" >
						</div>
					</div>
					<div style="height: 10px;"></div>	
					
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">성별</button>
						</div>
						<div class="col-md-10">
							<select class="form-control" name="gender" id="input_gender">
                              <option value="0">입력 안함</option>
                              <option value="1">남성</option>
                              <option value="2">여성</option>
                          </select>
						</div>
					</div>
					<div style="height: 10px;"></div>	
				
					<div class="row">
						<div class="col-md-2 category">
							<button type="button" class="btn btn-default btn-block"
								style="cursor: default;">포인트</button>
						</div>
						<div class="col-md-10">
							<input type="text" class="form-control" value="" name="point" id="input_point" >
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
		$('#editModalTitle').html('사용자 추가');
		$('#edit_form').attr('action', 'user/add');
		
		$('#input_uid').val(0);
		$('#input_id').prop('disabled', false);
		$('#input_id').val('');
		$('#input_password').val('').prop("disabled", false);
		$("#passwordDiv").show().children().eq(1).find("button").prop("disabled", true);
		$('#input_type').val(1);
		$('#input_name').val('');
		$('#input_mobile').val('');
		$('#input_birth').val('');
		$('#input_gender').val(0);
		$('#input_point').val('');
	}

	function initEditForm(id) {
		console.log("id:", id);

		$('#editModalTitle').html('사용자 변경');
		$('#edit_form').attr('action', 'user/update?p=${p}&u=${u}');
			
		$('#input_uid').val(id);
		$('#input_id').prop('disabled', true);
		$('#input_id').val($('#id-row-' + id).attr('value'));
				
		$('#input_password').val('').prop("disabled", true);
		$("#passwordDiv").hide().children().eq(1).find("button").prop("disabled", false);		
		$('#input_type').val($('#type-row-' + id).attr('value'));
		$('#input_name').val($('#name-row-' + id).attr('value'));
		$('#input_mobile').val($('#mobile-row-' + id).attr('value'));
		$('#input_birth').val($('#birth-row-' + id).attr('value'));
		var gender = $('#gender-row-' + id).attr('value');
		if (gender == '')
			$('#input_gender').val(0);
		else
			$('#input_gender').val(gender);
		$('#input_point').val(parseInt($('#point-row-' + id).attr('value')));
	}
	
	function adjustForm() {
		// 이름 확인.
		if ($('#input_name').val() == "")
			return "이름이 비었어요.";

		if ($('#input_id').val() == "")
			return "아이디가 비었어요.";

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