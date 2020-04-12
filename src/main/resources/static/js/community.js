/**
 * 提交一级评论
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment-content").val();

    comment(questionId, content, 1);
    console.log(questionId);
    console.log(content);
}

function subcomment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment(commentId, content, 2);
}

function comment(parentId, content, type) {
    if (!content) {
        alert("回复内容不能为空~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":parentId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if (response.code === 200) {
                // $("#comment-section").hide();
                window.location.reload();
            } else {
                if (response.code === 2002) {
                    var isAccept = confirm(response.message);
                    if (isAccept) {
                        window.open("https://github.com/login/oauth/authorize?client_id=de49460a9e6165f8d817&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response)
        },
        dataType: "json"
    });
}

/**
 * 展开二级评论
 */
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);   //获取二级评论element
    if (comments.hasClass("in")) {
        //折叠二级评论
        comments.removeClass("in");
        e.classList.remove("active");
    }else {     //展开二级评论
        var subCommentContainer = $("#comment-"+id);
        if ($(subCommentContainer).children().length <= 1) {
            // 1.获取后端传来的ResultDTO json串
            $.getJSON("/comment/"+id, function (data) {
                console.log(id);
                console.log(data);

                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "width": "40",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name+"："
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment"
                    }).append(mediaElement).append($("<hr>", {"style": "margin-bottom: 0;margin-top: 10px"}));

                    subCommentContainer.prepend(commentElement);
                });
            });
        }

        //2.展开二级评论
        comments.addClass("in");
        e.classList.add("active");
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function maskSelectTag() {
    $("#select-tag").hide();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();

    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }
}