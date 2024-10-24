$(document).ready(function () {

    var searchInput = $("#address-input");

    // 마커를 담을 배열입니다
    var markers = [];

    // 목록에 나온 화장실 아이디들을 담은 배열
    var wcIdList = [];
    // var wcIdTags = $(".inputWcId");

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    // 지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);

    // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
    var infowindow = new kakao.maps.InfoWindow({zIndex: 1});

    // 키워드로 장소를 검색합니다
    searchPlaces();

    // 키워드 검색을 요청하는 함수입니다
    function searchPlaces() {
        var keyword = document.getElementById("address-input").value;
        // if (!keyword.replace(/^\s+|\s+$/g, '')) {
        //     alert('키워드를 입력해주세요!');
        //     return false;
        // }

        $.ajax({
            url: "/location/wcInfoList",
            type: "POST",
            data: {juso: searchInput.val()},
            dataType: "json",
            success: function (response) {
                var wcInfoList = response.data || response;
                placesSearchCB(wcInfoList, kakao.maps.services.Status.OK);
            },
            error: function () {
                console.log("Request failed: " + textStatus);
                console.log("Error thrown: " + errorThrown);
                console.log("Response text: " + jqXHR.responseText);
                alert("An error occurred while processing your request. Please check the console for more details.");
                placesSearchCB(null, kakao.maps.services.Status.ERROR())
            }
        });
    }

    function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            displayPlaces(data);
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
            alert('검색 결과가 존재하지 않습니다.');
            return;
        } else if (status === kakao.maps.services.Status.ERROR) {
            alert('검색 결과 중 오류가 발생했습니다.');
            return;
        }
    }

    // 검색 결과 목록과 마커를 표출하는 함수입니다
    function displayPlaces(places) {
        var listEl = document.getElementById('underList'),
            fragment = document.createDocumentFragment(),
            bounds = new kakao.maps.LatLngBounds();

        // 검색 결과 목록에 추가된 항목들을 제거합니다
        removeAllChildNods(listEl);

        // 지도에 표시되고 있는 마커를 제거합니다
        removeMarker();

        for (var i = 0; i < places.length; i++) {
            var placePosition = new kakao.maps.LatLng(places[i].latitude, places[i].longitude),
                marker = addMarker(placePosition, i),
                itemEl = underListItem(i, places[i]);

            bounds.extend(placePosition);

            (function (marker, title) {
                kakao.maps.event.addListener(marker, 'mouseover', function () {
                    displayInfowindow(marker, title);
                });

                kakao.maps.event.addListener(marker, 'mouseout', function () {
                    infowindow.close();
                });

                itemEl.onmouseover = function () {
                    displayInfowindow(marker, title);
                };

                itemEl.onmouseout = function () {
                    infowindow.close();
                };
            })(marker, places[i].detailAddr);

            fragment.appendChild(itemEl);
        }

        // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
        listEl.appendChild(fragment);

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);

        // HTML 생성이 완료된 후 loadCounts 호출
        loadCounts(wcIdList);
    }

    function underListItem(index, place) {
        var wcId = place.id;
        var timeText = place.time;
        var addressText = place.addr1 + place.detailAddr;
        var level = place.level;
        var keyText = (level === 3) ? "*".repeat(place.wcpass.length) : place.wcpass;

        wcIdList.push(wcId);

        const imgScrs = [
            "/images/step_icon01.svg",
            "/images/step_icon02.svg",
            "/images/step_icon03.svg"
        ];

        const div = document.createElement('div');
        div.className = 'col-sm-6 mb-5';

        var itemStr = `
        <div class="d-flex position-relative">
            <div class="box_img">
                <span><img id="levelImg" src=${imgScrs[level - 1]} alt="레벨아이콘"></span>
            </div>
            <div class="iconWrap">
                <div class="d-flex icon">
                <input type="hidden" class="inputWcId" th:value="${wcId}">
                    <p class="me-3">
                        <img src="/images/thumb_up.svg" alt="좋아요 아이콘">
                        <span class="ms-1 like">10</span>
                    </p>
                    <p>
                        <img src="/images/thumb_down.svg" alt="싫어요 아이콘">
                        <span class="ms-1 hate">0</span>
                    </p>
                </div>
            </div>
        </div>
        <hr>
        <div class="listBox d-flex">
            <img src="/images/time.svg" alt="시간 아이콘">
            <p>개방시간 : <span>${timeText}</span></p>
        </div>
        <div class="listBox d-flex">
            <img src="/images/address.svg" alt="위치 아이콘">
            <p>주소 : <span>${addressText}</span></p>
        </div>
  
        <div class="listBox d-flex">
            <img src="/images/key.svg" alt="키 아이콘">
            <p>키 : <span>${keyText}</span></p>
        </div>

        <div>
        <a href="/location/wcDetail?wcId=${wcId}" class="btn btn-primary mt-2">자세히 보기</a>
    </div>
        `;

        div.innerHTML = itemStr;

        return div;
    }

    // 좋아요, 싫어요 갯수 ajax
    function loadCounts(wcIdList) {
        $.ajax({
            url: "/best/getCountList",
            type: "GET",
            data: {wcIdList: wcIdList},
            dataType: "json",
            success: function (resData) {
                if (resData != null) {
                    var likeList = resData.likeList;
                    var hateList = resData.hateList;

                    // HTML 요소가 업데이트되기 전에 모든 wcIdTags를 다시 가져옵니다
                    var wcIdTags = $(".inputWcId");

                    for (var i = 0; i < likeList.length; i++) {
                        var parentTag = $(wcIdTags[i]).parent();
                        var likeTag = parentTag.find("span.like");
                        var hateTag = parentTag.find("span.hate");

                        $(likeTag).text(likeList[i].cnt);
                        $(hateTag).text(hateList[i].cnt);
                    }
                }
            },
            error: function (errData) {
                console.log(errData);
            }
        });
    };

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    function addMarker(position, idx, title) {
        var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',
            imageSize = new kakao.maps.Size(39, 40),
            imgOptions = {
                spriteSize: new kakao.maps.Size(36, 691),
                spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10),
                offset: new kakao.maps.Point(13, 37)
            },
            markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
                position: position,
                image: markerImage
            });

        marker.setMap(map);
        markers.push(marker);

        return marker;
    }

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    function addMarker(position, idx, title) {
        var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
            // var imageSrc = "/images/marker.gif", // 마커 이미지 url, 스프라이트 이미지를 씁니다
            //     imageSize = new kakao.maps.Size(70, 70),  // 마커 이미지의 크기
            imageSize = new kakao.maps.Size(39, 40),  // 마커 이미지의 크기
            imgOptions = {
                spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
                spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
                offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
            },
            markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
                position: position, // 마커의 위치
                image: markerImage
            });

        marker.setMap(map); // 지도 위에 마커를 표출합니다
        markers.push(marker);  // 배열에 생성된 마커를 추가합니다

        return marker;
    }

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
    function removeMarker() {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
        }
        markers = [];
    }

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
    function displayPagination(pagination) {
        var paginationEl = document.getElementById('pagination'),
            fragment = document.createDocumentFragment(),
            i;

        // 기존에 추가된 페이지번호를 삭제합니다
        while (paginationEl.hasChildNodes()) {
            paginationEl.removeChild(paginationEl.lastChild);
        }

        for (i = 1; i <= pagination.last; i++) {
            var el = document.createElement('a');
            el.href = "#";
            el.innerHTML = i;

            if (i === pagination.current) {
                el.className = 'on';
            } else {
                el.onclick = (function (i) {
                    return function () {
                        pagination.gotoPage(i);
                    }
                })(i);
            }

            fragment.appendChild(el);
        }
        paginationEl.appendChild(fragment);
    }

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
    function displayInfowindow(marker, title) {
        var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

        infowindow.setContent(content);
        infowindow.open(map, marker);
    }

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
    function removeAllChildNods(el) {
        while (el.hasChildNodes()) {
            el.removeChild(el.lastChild);
        }
    }

});
