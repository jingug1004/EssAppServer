var noblapp = (function () {
	var rootPath = "";
	var _showLog = true;
	var savedUid = -1;

	var setCookie = function(data) {
		if (!data['success']) {

			var ecode = data['ecode'];
			// 없는 유저이거나 유효한 세션이 아닌경우... 혹시 모를 access_token 쿠키 날린다.
			if (ecode == 10003 || ecode == 10005) {
				showLog("removing access_token");
				document.cookie = "access_token=;path=/";
			}

			return;
		}
		
		var response = JSON.parse(data['response']);
		var uid = response['uid'];
		var access_token = response['access_token'];
			
		if (uid != null) {
			showLog("setting uid [" + uid + "]");
			savedUid = uid;
		}

		if (access_token != null) {
			// 시간은 현재 웹 세션이 유지되는 동안만...
			showLog("setting access_token [" + access_token + "]");
			document.cookie = "access_token="+access_token+";path=/";
		}
	};
	
	var showLog = function(data) {
		if (!_showLog)
			return;
		console.log(data);
	}
	
	return {
		setup: function(path, log = true) {
			rootPath = path;
			_showLog = log;
		},

		// 저장된 uid 반환 함수. 없으면 -1.
		getUid: function() {
			return savedUid;
		},
		
		request: function(module, param, callback) {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					// console.log(xhttp.responseText);
					// TODO 여기서 callback 날리기??
					var data = JSON.parse(xhttp.responseText);
					setCookie(data);
					callback(data);
				} else {
					// TODO some error?!?!?
				}
			}

			xhttp.open("POST", rootPath + module, true);
			xhttp.setRequestHeader("Accept", "application/json");
			xhttp.setRequestHeader("Content-type", "application/json");
			xhttp.setRequestHeader("Access-Control-Allow-Credentials", true);
			
			// 만약 파라미터가 없으면 일단 하나 만들어...
			if (param == null) {
				param = [];
			}
			
			// 저장된 uid가 있으면 그거 파라미터에 넣어.
			if (savedUid > 0) {
				param['uid'] = savedUid;
			}

			data = {
				module: module,
				parameter: JSON.stringify(param)
			}
			xhttp.withCredentials = true;
			xhttp.send(JSON.stringify(data));
		}
		
	}

}());