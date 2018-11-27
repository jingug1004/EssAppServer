<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 이건 지도 위치 찍기용... ${tc_id}, ${g_idx} -->
<html>
<head>
<link href="../../../resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="../../../resources/vendor/jquery/jquery.min.js"></script>
<script src="../../../resources/noblappXHRClient.js"> </script>
<script src="//cdnjs.cloudflare.com/ajax/libs/mobile-detect/1.4.1/mobile-detect.min.js"></script>

<style>
html, body {
	height: 100%;
	margin: 0px;
}

#ctrl {
	position: fixed;
	background-color: #ffffff;
	top: 0px;
	left: 0px;
	margin: 10px;
	padding: 10px;
}

#map {
	height: 100%;
	width: 100%;
}
</style>
</head>
<body>
	<div id="map"></div>
	<div id="ctrl">
		<div class="row">
			<div class="col-md-12">
				${g_name}
			</div>
		</div>
		<div style="height: 10px;"></div>

		<div class="row">
			<div class="col-md-4">tc_id</div>
			<div class="col-md-8">
				<input type="text" class="form-control" value="${tc_id}"
					id="input_tc_id" disabled />
			</div>
		</div>
		<div style="height: 10px;"></div>

		<div class="row">
			<div class="col-md-4">g_idx</div>
			<div class="col-md-8">
				<input type="text" class="form-control" value="${g_idx}"
					id="input_g_idx" disabled />
			</div>
		</div>
		<div style="height: 10px;"></div>

		<div class="row">
			<div class="col-md-4">섹터</div>
			<div class="col-md-8"><input type="text" class="form-control" value="0" id="input_count" disabled /></div>
		</div>
		<div style="height: 10px;"></div>

		<div class="row">
			<div class="col-md-4">체크 포인트</div>
			<div class="col-md-8"><input type="text" class="form-control" value="0" id="input_checks" disabled /></div>
		</div>
		<div style="height: 10px;"></div>

		<div class="row">
			<div class="col-md-12">
				<button type="button" class="col-md-12 btn btn-default"
					style="cursor: default;" onclick="uploadSectors();">섹터 목록
					추가</button>
			</div>
		</div>
	</div>
	
	<div id="addButtons" style="position:fixed;padding:7px;bottom:0;right:0;background-color:white;">
		<div class="row">
			<div class="col-md-12">
				<button type="button" class="col-md-12 btn btn-default"
					style="cursor: default;" onclick="panToCurrent();">현재 위치</button>
			</div>
		</div>
		<div style="height: 10px;"></div>

		<c:set var="course_type" value="${tc_type}" />
		<div class="row">
			<div class="col-md-12">
				<button type="button" class="col-md-12 btn btn-default"
					style="cursor: default;" onclick="addSectorAtCurrent();">
					섹터 추가
					<!-- 
					<c:choose>
						<c:when test="${course_type} == 1">섹터 추가</c:when>
						<c:otherwise>체크 포인트 추가</c:otherwise>
					</c:choose>
					 -->
				</button>
			</div>
		</div>
		<div style="height: 10px;"></div>

		<div class="row">
			<div class="col-md-12">
				<button type="button" class="col-md-12 btn btn-default"
					style="cursor: default;" onclick="addCheckPointAtCurrent();">체크 포인트 추가</button>
			</div>
		</div>
	</div>
	
	<script>
		noblapp.setup("../../..");
		
		var md = new MobileDetect(navigator.userAgent);
		console.log("who am I?", navigator.userAgent);
		console.log("mobile?", md.mobile());
		
		var testMode = false;
		
		// TODO 이벤트 코스인 경우는... path를 만들어 줄까???
		var tc_type = ${tc_type};
		var checkPoints = [];
		var sectorPath = null;
		var otherSectorPath = null;
		var map;
		var prev_sectors = ${prev_sectors};
		var oth_sectors = ${oth_sectors};
		var marker_icon = null;
		var other_icon = null;
		
		function initMap() {

			// TODO 만약 prev_sectors가 있으면 그것들의 중간에 맞춰줘??? 아니면 시작점???
			var def_lat = ${def_lat};
			var def_lng = ${def_lng};
			if (prev_sectors.length > 0) {
				var guide = 0;
				def_lat = prev_sectors[guide].lat;
				def_lng = prev_sectors[guide].lng;
			}
        	map = new google.maps.Map(document.getElementById('map'), {
        		zoom: 17,
        		disableDoubleClickZoom: true,
        		draggableCursor:'crosshair',
        		center: {
        			lat: def_lat,
        			lng: def_lng
        		}
        	});
        
        	initializeVectors();
        
			map.addListener('click', function(ev) {
				insertNewSector(ev.latLng);			// 새로운 좌표 추가.
			});
			map.addListener('rightclick', removeLastSector);		// 마지막 좌표 삭제.
        
			// var path = sectorPath.getPath();
			//google.maps.event.addListener(sectorPath, 'insert_at', sectorInserted);
			//google.maps.event.addListener(sectorPath, 'remove_at', sectorRemoved);
			//google.maps.event.addListener(sectorPath, 'set_at', sectorSetAt);
        
			//google.maps.event.addListener(sectorLine, 'click', addCheckPoint);
        	//google.maps.event.addListener(sectorLine, 'rightclick', removeSector);
        	
        	plotPrevSectors();
        	plotOtherSecotrs();
        	
        	if (md.mobile() != null) {
				// TODO 실시간으로 따라 다니는 걸 할까???
			}
      	}
		
		function initializeVectors() {
			if (tc_type == 1) {
				marker_icon = {
	    			    path: "M-20,0a20,20 0 1,0 40,0a20,20 0 1,0 -40,0",
	    			    fillColor: '#0000FF',
	    			    fillOpacity: .6,
	    			    anchor: new google.maps.Point(0,0),
	    			    strokeWeight: 0,
	    			    scale: 1
	    			};
	        	
	        	other_icon = {
	    			    path: "M-20,0a20,20 0 1,0 40,0a20,20 0 1,0 -40,0",
	    			    fillColor: '#00FF00',
	    			    fillOpacity: .6,
	    			    anchor: new google.maps.Point(0,0),
	    			    strokeWeight: 0,
	    			    scale: 1
	    			};

	        	var sectorLine = new google.maps.Polyline({
					geodesic: true,
					editable: true,
					draggable: false,
					strokeColor: '#FF0000',
					strokeOpacity: 1.0,
					strokeWeight: 2,
					map: map
			  	});
	        	sectorPath = sectorLine.getPath();
	        	
	        	google.maps.event.addListener(sectorLine, 'click', addCheckPoint);
	        	google.maps.event.addListener(sectorLine, 'rightclick', removeSector);
	        	
	        	google.maps.event.addListener(sectorPath, 'insert_at', sectorInserted);
				google.maps.event.addListener(sectorPath, 'remove_at', sectorRemoved);
				google.maps.event.addListener(sectorPath, 'set_at', sectorSetAt);
			} else {
				marker_icon = {
	    			    path: "M-20,0a20,20 0 1,0 40,0a20,20 0 1,0 -40,0",
	    			    fillColor: '#0000FF',
	    			    fillOpacity: .6,
	    			    anchor: new google.maps.Point(0,0),
	    			    strokeWeight: 0,
	    			    scale: 1
	    			};
				
				sectorPath = new google.maps.MVCArray();
			}
		}

		// 기존 입력된 섹터 그려주기..
		function plotPrevSectors() {
			if (prev_sectors.length <= 0)
				return;

			prev_sectors.forEach(function(s, i) {
				// console.log("inserting...", s);
				var latLng = new google.maps.LatLng(s.lat, s.lng);

				// var path = sectorPath.getPath();
				sectorPath.push(latLng);

				if (s.tcs_type == 2) {
					addCheckPoint({
						vertex: s.s_idx,
						latLng: latLng
					},
					true);
				}
			});
			changeSectorCount();
		}
		
		// 다른 그룹의 섹터들을 찍어주는 거...
		function plotOtherSecotrs() {
			if (oth_sectors.length <= 0)
				return;

			var otherLine = null;
			var prev_g_idx = -1000;

			oth_sectors.forEach(function(s, i) {
				// 일반 코스일 때만 path로 라인을 그린다...
				if (tc_type == 1) {
					if (s.g_idx != prev_g_idx && (s.g_idx - 1) != prev_g_idx) {
						prev_g_idx = s.g_idx;
					
						otherLine = new google.maps.Polyline({
							geodesic: true,
							strokeColor: '#00FF00',			// random color???
							strokeOpacity: .8,
							strokeWeight: 2
						  });
						otherLine.setMap(map);
					}

					var latLng = new google.maps.LatLng(s.lat, s.lng);
					otherLine.getPath().push(latLng);
				}

				if (s.tcs_type == 2) {
					var m = new google.maps.Marker({
						icon: other_icon,
						position: latLng,
						map: map
					});
				}
			});
		}

		function changeSectorCount() {
			$('#input_count').val(sectorPath.length);
			$('#input_checks').val(Object.keys(checkPoints).length);
		}

		function addCheckPoint(ev, norecount) {
			// console.log('addCheckPoint', ev);
			if (ev.vertex == undefined)
				return;
		
			// 여기는 check point가 추가되는 시점...
			// checkPoints.push({v:ev.vertex, latlng: ev.latLng});
			if (checkPoints[ev.vertex] == undefined) {
				checkPoints[ev.vertex] = new google.maps.Marker({
					icon: marker_icon,
					position: ev.latLng,
					map: map
				});
			} else {
				checkPoints[ev.vertex].setMap(null);
				delete checkPoints[ev.vertex];
			}
			
			if (!norecount)
				changeSectorCount();
		}
		
		function removeSector(ev) {
			// console.log('right clicked on marker', ev);
			if (ev.vertex == undefined)
				return;
			sectorPath.removeAt(ev.vertex);
			changeSectorCount();
		}
      
		// 섹터가 추가가 됬음... 체크 포인트 조정하자...
		function sectorInserted(index) {
			// console.log('sectorInserted', index, this);
			// checkPoint 목록을 봐야 함... index 보다 큰 위치에 값들이 있으면 하나씩 올려줘야 함...
			var keys = Object.keys(checkPoints);
			keys.reverse();		// 반대로 돌아야 함...
			keys.forEach(function(key) {
				key = parseInt(key);
				if (key >= index) {		// 같거나 크면...
					checkPoints[key + 1] = checkPoints[key];
					delete checkPoints[key];
				}
			});
			
			// console.log("new keys:", Object.keys(checkPoints));
			changeSectorCount();
		}

		// 섹터가 삭제 됬음... 체크 포인트 조정하자.
		function sectorRemoved(index) {
			// console.log('sectorRemoved', index, this);
			// TODO checkPoint 목록을 봐야 함... index 보다 큰 위치에 값들이 있으면 하나씩 빼줘야 함...
			// 먼저 해당 위치에 체크 포인트 없애.
			if (checkPoints[index] != null) {
				checkPoints[index].setMap(null);
				delete checkPoints[index];
			}
			
			var keys = Object.keys(checkPoints);
			keys.forEach(function(key) {
				key = parseInt(key);
				if (key > index) {
					checkPoints[key - 1] = checkPoints[key];
					delete checkPoints[key];
				}
			});

			changeSectorCount();
		}
		
		function sectorSetAt(index, element) {
			// element는 이전 값!!!
			// console.log('sectorSetAt', index, element, this);
			// TODO 해당 index에 있으면 바꿔줘...
			if (checkPoints[index] != null)
				checkPoints[index].setPosition(this.getAt(index));
		}
      
		
		function insertNewSector(location) {
			console.log(({lat:location.lat(), lng:location.lng()}));

			// 패스에 추가.
			// var path = sectorPath.getPath();
			sectorPath.push(location);
			
			// 이벤트인 경우는 바로 체크포인트를 넣어줘.
			if (tc_type == 2) {
				addCheckPoint({
					vertex: sectorPath.length - 1,
					latLng: location
				});
			}

			changeSectorCount();
		}
      
		function removeLastSector(ev) {
			if (testMode) {
				runTest(ev);
				return;
			}

			// 패스에서 삭제.
			// var path = sectorPath.getPath();
			var index = sectorPath.length - 1;
			sectorPath.pop();
			
			if (checkPoints[index] != null) {
				checkPoints[index].setMap(null);
				delete checkPoints[index];
			}
			changeSectorCount();
		}
      
		function addSectorAtCurrent(callback) {
			if ("geolocation" in navigator) {
				/* geolocation is available */
				navigator.geolocation.getCurrentPosition(function(position) {
					var latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
					// var latLng = new google.maps.LatLng(${def_lat}, ${def_lng});
					insertNewSector(latLng);
					if (callback != undefined)
						callback(latLng);
				});
			} else {
				/* geolocation IS NOT available */
				console.log("not available");
			}
		}

		function addCheckPointAtCurrent() {
			addSectorAtCurrent(function(latLng) {
				var ev = {
					vertex: sectorPath.length - 1,
					latLng: latLng
				};
				addCheckPoint(ev);
			});
		}
		
		function panToCurrent() {
			if ("geolocation" in navigator) {
				/* geolocation is available */
				navigator.geolocation.getCurrentPosition(function(position) {
					var latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
					map.panTo(latLng);
				});
			} else {
				/* geolocation IS NOT available */
				console.log("not available");
			}
		}

		// 서버에 업로드...
		function uploadSectors() {
			// console.log("addSectors");
			// var path = sectorPath.getPath();
			if (sectorPath.length <= 0) {
				alert('입력할 섹터가 없네요.');
				return;
			}
			if (Object.keys(checkPoints).length <= 0) {
				var resume = confirm('체크 포인트가 하나도 없네요. 계속 진행할까요?');
				if (resume != true)
					return;
			}

			var sectors = [];
			sectorPath.forEach(function(p, index) {
				sectors.push({
					lat:p.lat(),
					lng:p.lng(),
					tcs_type: (checkPoints[index] == undefined ? 1 : 2)
				});
			});
			
			var module = '/cms/process/trek/addSectors';
			var param = {
					tc_id: parseInt($('#input_tc_id').val()),
					g_idx: parseInt($('#input_g_idx').val()),
					sectors: sectors
			};

			// console.log('param', param);
			noblapp.request(module, param, function(data) {
				// console.log(data);
				if (data['success'])
					document.location.replace('../trek');
			});
		}
      
		function runTest(ev) {
			var module = '/leisure/trek/update';
			var tc_id = ${tc_id};
			var param = {
					uid: 78,
					lat: ev.latLng.lat(),
					lng: ev.latLng.lng()
			};

			// console.log('param', param);
			noblapp.request(module, param, function(data) {
				console.log(data);
				if (data.success) {
					var result = JSON.parse(data.response);
					var latLng = new google.maps.LatLng(result.lat, result.lng);
					var m = new google.maps.Marker({
						icon: other_icon,
						position: latLng,
						map: map
					});
				}
			});
		}

    </script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjkVjzKyMzaVZV8sXuniTWOWZMC0CI1KM&callback=initMap">
    </script>
	
</body>
</html>
