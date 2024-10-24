$(document).ready(function () {

    let userLat = null;
    let userLon = null;

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition, showError);
    } else {
        $('#address-input').html("Geolocation is not supported by this browser.");
    }

    function showPosition(position) {
        const lat = position.coords.latitude;
        const lon = position.coords.longitude;
        $('#address-input').html(`위도: ${lat}, 경도: ${lon}`);

        userLat = lat;
        userLon = lon;

        // getKakaoAddress(lat, lon);
    }

    function showError(error) {
        switch (error.code) {
            case error.PERMISSION_DENIED:
                $('#address-input').html("User denied the request for Geolocation.");
                break;
            case error.POSITION_UNAVAILABLE:
                $('#address-input').html("Location information is unavailable.");
                break;
            case error.TIMEOUT:
                $('#address-input').html("The request to get user location timed out.");
                break;
            case error.UNKNOWN_ERROR:
                $('#address-input').html("An unknown error occurred.");
                break;
        }
    }

    $('#address-input').on('input', function () {
        const apiKey = '29ef09afe41f4631c7f4d665587bc23f';
        const query = $(this).val().trim();
        const url = `https://dapi.kakao.com/v2/local/search/keyword.json?query=${encodeURIComponent(query)}&x=${userLon}&y=${userLat}&radius=2000`;
        if (query.length < 2) {
            $('#autocomplete-suggestions').empty();
            return;
        }
        $.ajax({
            url: url,
            method: 'GET',
            data: {query: query},
            headers: {'Authorization': `KakaoAK ${apiKey}`},
            success: function (data) {
                $('#autocomplete-suggestions').empty();
                data.documents.forEach(function (address) {
                    const suggestion = $('<div>')
                        .addClass('autocomplete-suggestion')
                        .html(`${address.place_name}<br>${address.address_name}`)
                        .on('click', function () {
                            $('#address-input').val(address.address_name);
                            $('#autocomplete-suggestions').empty();
                        });
                    $('#autocomplete-suggestions').append(suggestion);
                });
            },
            error: function (error) {
                console.error('Error:', error);
            }
        });
    });

})
;