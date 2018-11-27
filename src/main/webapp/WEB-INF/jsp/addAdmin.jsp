<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>${gname} 전자지도 관리자 시스템</title>

	<!-- Bootstrap Core CSS -->
	<link href="../resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

	<!-- MetisMenu CSS -->
	<link href="../resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

	<!-- Custom CSS -->
	<link href="../resources/dist/css/sb-admin-2.css" rel="stylesheet">

	<!-- Custom Fonts -->
	<link href="../resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->

	
</head>

<body>

<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">${gname} 전자지도 관리자 시스템</h3>
				</div>
				<div class="panel-body">
					<form role="form">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="아이디" name="id" id="input_id" autofocus>
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="비밀번호" name="password" id="input_password" type="password" value="">
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="이름" name="name" id="input_name" value="">
							</div>
							<!-- Change this to a button or input when using this as a form -->
							<button type="button" class="btn btn-lg btn-success btn-block" id="button_submit" onclick="addAdmin()">어드민 등록</button>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- jQuery -->
<script src="../resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="../resources/vendor/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="../resources/dist/js/sb-admin-2.js"></script>

	<script src="../resources/noblappXHRClient.js"> </script>
	<script>
		noblapp.setup("..");
		function addAdmin() {
			var id = $('#input_id').val();
			if (id == null || id == "") {
				alert('아이디를 입력하세요.');
				$('#input_id').focus();
				return;
			}

			var password = $('#input_password').val();
			if (password == null || password == "") {
				alert('비밀번호를 입력하세요.');
				$('#input_password').focus();
				return;
			}

			var name = $('#input_name').val();
			if (name == null || name == "") {
				alert('이름을 입력하세요.');
				$('#input_name').focus();
				return;
			}

			var module = "/cms/addAdmin.do";
			var param = {
				id: id,
				password: password,
				name: name
			};

			noblapp.request(module, param, function(result) {
				if (result['success']) {
					document.location.replace('login');
				} else {
					alert('error! [' + result['ecode'] + ']');
				}
			});
		}
		
		$('.form-control').keypress(function (ev) {
			if (ev.which == 13) {
				addAdmin();
				return false;
			}
		});
	</script>
</body>

</html>
