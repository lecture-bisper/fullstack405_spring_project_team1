const onFavoriteImg = "/images/favorites_on.svg";
const offFavoriteImg = "/images/favorites_off.svg"

function loadFavorites() {
    $.ajax({
        url: "/isFavorites",
        type: "GET",
        data: {userId: userId, wcId: wcId},
        success: function (isFavorite) {
            if (isFavorite) {
                $("#favorite-button").attr("src", "/images/favorites_on.svg");
            } else {
                $("#favorite-button").attr("src", "/images/favorites_off.svg");
            }
        },
        error: function (errData) {
            console.log(errData);
        }
    });
}

if (userId != null) {
    loadFavorites();
}

$("#favorite-button").on("click", function () {

    $.ajax({
        url: "/removeFavorites",
        type: "GET",
        data: {userId: userId, wcId: wcId},
        success: function (removeFavorite) {
            if (removeFavorite) {
                $("#favorite-button").attr("src", "/images/favorites_on.svg");
                alert("즐겨찾기 추가 되었습니다.")
            } else {
                $("#favorite-button").attr("src", "/images/favorites_off.svg");
            }
        },
        error: function (errData) {
            console.log(errData);
        }
    });

})