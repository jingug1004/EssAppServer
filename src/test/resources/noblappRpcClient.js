var noblapp = (function (axios) {
	var rootPath = "";
	var _showLog = true;
	var _axios;					// axios 인스턴스

	var setCookie = function(data) {
		if (!data['success'])
			return;
		
		var response = JSON.parse(data['response']);
		var uid = response['uid'];
		var access_token = response['access_token'];
			
		if (uid == null || access_token == null)
			return;

		// 시간은 현재 웹 세션이 유지되는 동안만...
		var expires = ";path=/";

		showLog("setting uid [" + uid + "]");
		showLog("setting uuid [" + access_token + "]");
		document.cookie = "uid="+uid+expires;
		document.cookie = "access_token="+access_token+expires;
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
			_axios = axios.create({
				method: 'post',
				withCredentials: true,		// 쿠키가 전송되도록...
				// crossdomain: true,
				headers:{
					// 'Access-Control-Allow-Origin': '*',
					// 'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
					'Content-type': 'application/json',
					'Accept': 'application/json'
				}
			});
		},

		request: function(module, param, callback) {
			if (_axios == null)
				return;
			
			_axios.request({
				url: rootPath + module,
				data: {
					module: module,
					parameter: JSON.stringify(param)
				}
			})
			.then(function (response) {
				// axios가 넘겨준 구조에서 data만 뽑기.
				var data = response['data'];

				showLog("result is? " + data['success']);
				if (!data['success'])
					showLog("error... code: " + data['ecode']);
				else
					setCookie(data);

				if (callback != null)
					callback(data);
			})
			.catch(function (error) {
				// 이건 axios가 넘겨주는 에러인데... 흠냥...
				showLog(error);
			});
		}
	}

}(axios));