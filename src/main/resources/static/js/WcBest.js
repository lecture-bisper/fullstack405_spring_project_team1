var wcIdTags = $(".inputWcId");
var wcIdList = [];

for (var i = 0; i < wcIdTags.length; i++) {
    var wcId = $(wcIdTags[i]).val();
    wcIdList.push(wcId);
}

function loadCounts() {
    $.ajax({
        url: "/best/getCountList",
        type: "GET",
        data: {wcIdList: wcIdList},
        dataType: "json",
        success: function (resData) {
            if (resData != null) {
                var likeList = resData.likeList;
                var hateList = resData.hateList;

                for (var i = 0; i < likeList.length; i++) {
                    parentTag = $(wcIdTags[i]).parent();
                    likeTag = parentTag.find("span.like");
                    hateTag = parentTag.find("span.hate");

                    $(likeTag).text(likeList[i].cnt);
                    $(hateTag).text(hateList[i].cnt);
                }
            }
        },
        error: function (errData) {
            console.log(errData);
        }
    });
}

loadCounts();


function clickBest(where) {
    var kind = (where === "like");
    if (userId != null) {
        $.ajax({
            url: "/best/countChange",
            type: "POST",
            data: {userId: userId, wcId: wcId, kind: kind},
            success: function (resData) {

                parentTag = $(wcIdTags[0]).parent();
                likeTag = parentTag.find("span.like");
                hateTag = parentTag.find("span.hate");

                switch (resData) {

                    case 1:
                        // 무 -> 좋아요
                        $(likeTag).text( parseInt($(likeTag).text(), 10) + 1);
                        break;
                    case 2:
                        // 무 -> 싫어요
                        $(hateTag).text( parseInt($(hateTag).text(), 10) + 1);
                        break;
                    case 3:
                        // 좋아요 -> 좋아요
                        $(likeTag).text( parseInt($(likeTag).text(), 10) - 1);
                        break;
                    case 4:
                        // 좋아요 -> 싫어요
                        $(likeTag).text( parseInt($(likeTag).text(), 10) - 1);
                        $(hateTag).text( parseInt($(hateTag).text(), 10) + 1);
                        break;
                    case 5:
                        // 싫어요 -> 좋아요
                        $(hateTag).text( parseInt($(hateTag).text(), 10) - 1);
                        $(likeTag).text( parseInt($(likeTag).text(), 10) + 1);
                        break;
                    case 6:
                        // 싫어요 -> 싫어요
                        $(hateTag).text( parseInt($(hateTag).text(), 10) - 1);
                        break;
                }
            },
            error: function (errData) {
                alert("로그인 해주세요.");
            }
        });
    } else {
        var answer = confirm("로그인 페이지로 이동하시겠습니까?");
        if (answer) {
            location.href = "/auth/login";
        }
    }
};
