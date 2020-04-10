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
        //1.获取后端传来的ResultDTO json串
        // $.getJSON("/comment/"+id, function (data) {
        //     console.log(data);
        //     var commentBody = $("comment-body-"+id);
        //     var items = [];
        //     // commentBody.appendChild();
        //
        //     $.each(data.data, function (comment) {
        //        items.push();
        //     });
        //     $("<div/>", {
        //         "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
        //         "id": "sub-comment-"+id,
        //         html: items.join("")
        //     }).appendTo(commentBody);
        // });
        //2.展开二级评论
        comments.addClass("in");
        e.classList.add("active");
    }
}