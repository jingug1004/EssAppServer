<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="fontModal" tabindex="-1" role="dialog" aria-labelledby="fontModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeDialog()">&times;</button> -->
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="fontModalTitle"></h4>
			</div>

			<form role="form" enctype="multipart/form-data" method="post" id="font_form">
				<!-- <input type="hidden" id="input_action" name="action" value=""> -->
				<input type="hidden" id="font_input_id" name="id">

				<div class="modal-body">
					<div class="row" style="border-bottom: 1px solid #eee">
						<div class="col-md-12 " style="cursor:pointer;">
							<label><input type="radio" name="setFont" value="false" checked/> 없음</label>
						</div>
					</div>
					<div style="height: 10px;"></div>
					<div class="row" style="border-bottom: 1px solid #eee">
						<div class="col-md-12" style="font-family: 'Nanum Gothic';cursor:pointer;">
							<label><input type="radio" name="setFont" value="Nanum Gothic"/> 나눔고딕</label>
						</div>
					</div>
					<div style="height: 10px;"></div>
					<div class="row">
						<div class="col-md-12 " style="font-family: 'Noto Sans KR';cursor:pointer;">
							<label><input type="radio" name="setFont" value="Noto Sans KR"/> Noto Sans KR</label>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<!-- <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeDialog()">취소</button> -->
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" onclick="uploadDataFont()">변경</button>
				</div>
			</form>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<script>

	function newFormFont() {
		// 여기로 오면 안됨...
        $('#fontModalTitle').html('폰트 설정 변경');
        $('#font_form').attr('action', 'font/update?p=${p}&u=${u}');

        if($(".table").find("[value=setFont]").length > 0){
            $("#font_input_id").val($(".table").find("[value=setFont]").attr("id").split("-")[2]);

            var value = $(".table").find("[value=setFont]").parent().find("[id^=the_value]").attr("value");
            $("#font_form").find(":radio[value='"+value+"']").prop("checked", true);
        }
	}

    function uploadDataFont() {
		$("#font_form")[0].submit();
    }
</script>