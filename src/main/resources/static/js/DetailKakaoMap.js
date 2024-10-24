$(document).ready(function () {

    const userId = $("#hiddenUserId").val();
    const wcId = $("#hiddenWcId").val();

    const lati = $("#hiddenLati").val();
    const longi = $("#hiddenLongi").val();
    const detailAddr = $("#hiddenDetail").val();

    var wcLatLng = new kakao.maps.LatLng(lati, longi);

// 마커를 담을 배열입니다
    var markers = [];

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: wcLatLng, // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

// 지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
    var infowindow = new kakao.maps.InfoWindow({zIndex: 1});

    if (navigator.geolocation) {

        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function (position) {

            // var lat = position.coords.latitude, // 진짜 위도
            //     lon = position.coords.longitude; // 진짜 경도
            var lat = 35.1560448378911, // 임의 위도
                lon = 129.059564755963; // 임의 경도

            var locPosition = new kakao.maps.LatLng(lat, lon),
                message = '<div style="padding:5px;">내 위치</div>'; // 인포윈도우에 표시될 내용입니다


            var bounds = new kakao.maps.LatLngBounds();


            // 마커와 인포윈도우를 표시합니다

            var points = [
                wcLatLng,
                locPosition
            ];


            for (i = 0; i < points.length; i++) {
                bounds.extend(points[i]);
            }

            displayMarker(locPosition, message);

            map.setBounds(bounds, 70, 0, 10, 0);
        });
    }


// 지도에 마커와 인포윈도우를 표시하는 함수입니다
    function displayMarker(userLocPosition, message) {

        // 화장실 마커
        var imageSrc = "/images/marker.gif" // 마커 이미지 url, 스프라이트 이미지를 씁니다
        var imageSize = new kakao.maps.Size(70, 70)  // 마커 이미지의 크기
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize)

        var wcMarker = new kakao.maps.Marker({
            map: map,
            position: wcLatLng, // 마커의 위치
            image: markerImage
        });

        (function (infoMarker, title) {
            kakao.maps.event.addListener(infoMarker, 'mouseover', function () {
                displayInfowindow(infoMarker, title);
            });

            kakao.maps.event.addListener(infoMarker, 'mouseout', function () {
                infowindow.close();
            });

        })(wcMarker, detailAddr);




        // 내 위치 마커
        var userMarker = new kakao.maps.Marker({
            map: map,
            position: userLocPosition
        });

        (function (infoMarker, title) {
            kakao.maps.event.addListener(infoMarker, 'mouseover', function () {
                displayInfowindow(infoMarker, title);
            });

            kakao.maps.event.addListener(infoMarker, 'mouseout', function () {
                infowindow.close();
            });

        })(userMarker, message);

    }

// 인포윈도우에 장소명을 표시합니다
    function displayInfowindow(marker, title) {
        var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

        infowindow.setContent(content);
        infowindow.open(map, marker);
    }

});
