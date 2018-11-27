<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 이건 지도 위치 찍기용... ${tc_id}, ${g_idx} -->
<html>
<head>
<link href="../../../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<script src="../../../resources/vendor/jquery/jquery.min.js"></script>
<script src="../../../resources/noblappXHRClient.js"> </script>
<style>
html, body {
	height: 100%;
	margin: 0px;
}

#ctrl {
	position: fixed;
	background-color: #ffffff;
	bottom: 0px;
	left: 0px;
	margin: 10px;
	padding: 10px;
}

#map {
	height: 100%;
	width: 100%;
}

.controls {
        margin-top: 10px;
        border: 1px solid transparent;
        border-radius: 2px 0 0 2px;
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        height: 32px;
        outline: none;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
      }

      #pac-input {
        background-color: #fff;
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
        margin-left: 12px;
        padding: 0 11px 0 13px;
        text-overflow: ellipsis;
        width: 300px;
      }

      #pac-input:focus {
        border-color: #4d90fe;
      }

      .pac-container {
        font-family: Roboto;
      }

      #type-selector {
        color: #fff;
        background-color: #4d90fe;
        padding: 5px 11px 0px 11px;
      }

      #type-selector label {
        font-family: Roboto;
        font-size: 13px;
        font-weight: 300;
      }
      #target {
        width: 345px;
      }
</style>
</head>
<body>
	<input id="pac-input" class="controls" type="text" placeholder="Search Box" value="${address}">
	<div id="map"></div>
	<div id="ctrl">
		<div class="row">
			<div class="col-md-12">${name}</div>
		</div>
		<div style="height: 10px;"></div>
		<div class="row">
			<div class="col-md-12">
				<button type="button" class="col-md-12 btn btn-default"
					style="cursor: default;" onclick="uploadLocation();">확인</button>
			</div>
		</div>
	</div>

	<script>
		noblapp.setup("../../..");
		
		var thisPid = ${pid};
		var thisCid = ${cid};
		var target = '${target}';
		// var address = '${address}';
		var map;
		var marker_icon = null;
		var targetLocation = null;
		
		function initMap() {
			var def_lat = ${def_lat};
			var def_lng = ${def_lng};

			// TODO 만약 prev_sectors가 있으면 그것들의 중간에 맞춰줘??? 아니면 시작점???
        	map = new google.maps.Map(document.getElementById('map'), {
        		zoom: 17,
        		disableDoubleClickZoom: true,
        		draggableCursor:'crosshair',
        		center: {
        			lat: def_lat,
        			lng: def_lng
        		}
        	});
        
			// 검색
			var input = document.getElementById('pac-input');
			var searchBox = new google.maps.places.SearchBox(input);
			map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
			map.addListener('bounds_changed', function() {
				searchBox.setBounds(map.getBounds());
			});
			var markers = [];
			// Listen for the event fired when the user selects a prediction and retrieve
			// more details for that place.
			searchBox.addListener('places_changed', function() {
				var places = searchBox.getPlaces();

				if (places.length == 0) {
					return;
				}

				// Clear out the old markers.
				markers.forEach(function(marker) {
					marker.setMap(null);
				});
				markers = [];

				// For each place, get the icon, name and location.
				var bounds = new google.maps.LatLngBounds();
				places.forEach(function(place) {
					if (!place.geometry) {
						console.log("Returned place contains no geometry");
						return;
					}
					var icon = {
						url : place.icon,
						size : new google.maps.Size(71, 71),
						origin : new google.maps.Point(0, 0),
						anchor : new google.maps.Point(17, 34),
						scaledSize : new google.maps.Size(25, 25)
					};

					// Create a marker for each place.
					markers.push(new google.maps.Marker({
						map : map,
						icon : icon,
						title : place.name,
						position : place.geometry.location
					}));

					if (place.geometry.viewport) {
						// Only geocodes have viewport.
						bounds.union(place.geometry.viewport);
					} else {
						bounds.extend(place.geometry.location);
					}
				});
				map.fitBounds(bounds);
			});

			marker_icon = {
				path : "M-20,0 a20,20 0 1,0 40,0 a20,20 0 1,0 -40,0",
				fillColor : '#0000FF',
				fillOpacity : .6,
				anchor : new google.maps.Point(0, 0),
				strokeWeight : 0,
				scale : 1
			};

			var prev_loc = ${prev_loc};
			
			if (prev_loc != undefined) {
				var latLng = new google.maps.LatLng(prev_loc[0], prev_loc[1]);
				targetLocation = new google.maps.Marker({
					icon : marker_icon,
					position : latLng,
					map : map
				});
			}

			map.addListener('click', function(ev) {
				if (targetLocation != null) {
					targetLocation.setMap(null);
				}

				targetLocation = new google.maps.Marker({
					icon : marker_icon,
					position : ev.latLng,
					map : map
				});
			});
			
			map.addListener('rightclick', function(ev) {
				if (markers != null) {
					markers.forEach(function(m) {
						m.setMap(null);
					});
					markers.clear();
				}
			});
			
			
			// 이미 있는 주소???
			// if (address === '') {
			//	$(input).val(address);
			//}
		}

		// 서버에 업로드...
		function uploadLocation() {
			// console.log("addSectors");
			// var path = sectorPath.getPath();
			if (targetLocation == null) {
				alert('입력할 장소의 GPS 정보가 없네요');
				return;
			}

			var position = targetLocation.getPosition();
			var module = '/cms/process/${target}/updateLoc';
			var param = {
				cid : thisCid,
				pid : thisPid,
				lat : position.lat(),
				lng : position.lng()
			};

			// console.log('param', param);
			noblapp.request(module, param, function(data) {
				// console.log(data);
				if (data['success'])
					document.location
							.replace('../${target}?p=${page}&u=${unit}');
			});
		}
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjkVjzKyMzaVZV8sXuniTWOWZMC0CI1KM&libraries=places&callback=initMap">
    </script>

</body>
</html>
