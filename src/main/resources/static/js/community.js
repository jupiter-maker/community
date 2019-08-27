/**
 * 提交一级回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    //一级评论
    comment2target(questionId, 1, content);
}

/**
 * 回复ajax
 * @param targetId
 * @param type
 * @param content
 */
function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.status == 200) {
                window.location.reload();
            } else {
                if (response.status == 2003) {
                    //登录异常
                    var isAccepted = confirm(response.msg);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=2263820faf0c89533fd2&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closeable", true);
                    }
                } else {
                    alert(response.msg);
                }
            }
        },
        dataType: "json"
    });
}

/**
 * 提交二级回复
 * @param commentId
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    console.log(commentId)
    console.log(content);
    comment2target(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComment(e) {
    //一级评论id
    var id = e.getAttribute("data-id");
    var comment = $("#comment-" + id);
    //获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comment.removeClass("in");
        e.removeAttribute("data-collapse")
        e.classList.remove("active")
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            //标记二级评论展开状态
            comment.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active")
        } else {
            //ajax获取二级评论信息
            $.getJSON("/comment/" + id, function (data) {

                $.each(data.data.reverse(), function (index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body",
                    }).append(
                        $("<h5/>",{
                          "class":"media-heading",
                          html:comment.user.name
                        })
                    ).append(
                        $("<div/>", {
                            html:comment.content
                        })
                    ).append(
                        $("<div/>", {
                            "class":"menu",
                        }).append(
                            $("<span/>", {
                                "class":"pull-right",
                               html:moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                            })
                        )
                    );

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                //标记二级评论展开状态
                comment.addClass("in");
                e.setAttribute("data-collapse", "in");
                e.classList.add("active")
            });
        }
    }
}

function selectTags(e) {
    var previous = $("#tag").val();
    var tag = e.getAttribute("data-tag");
    if(previous.indexOf(tag)!=-1){

    }else{
        if(previous){
            $("#tag").val(previous+'&'+tag);
        }else{
            $("#tag").val(tag);
        }
    }

}
function showSelectTag() {
    $("#select-tag").show();
}