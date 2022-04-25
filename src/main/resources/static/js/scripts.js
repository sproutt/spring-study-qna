String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

function $(selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    console.log("호출 되는가?");
    initEvents();
})

function initEvents() {
    const answerBtn = $(".submit-write .btn");
    console.log("answerBtn =" + answerBtn);
    answerBtn.addEventListener("click", registerAnswerHandler);
    $('.qna-comment-slipp-articles').addEventListener('click', deleteAnswerHandler);
}

function fetchManager({url, method, body, headers, callback}) {
    fetch(url, {
        //fetch("http://xxx.com/dd", {
        method,
        body,
        headers,
        credentials: "same-origin"
    }).then((response) => {
        return response.json().then(result => {
            return {
                'result': result,
                'status': response.status
            }
        })
    }).then(({result, status}) => {
        if (status >= 400) {
            console.log('error 가 발생했네요 ', result.error);
        } else {
            console.log(result);
            callback(result)
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const contents = $(".submit-write textarea").value;
    document.querySelectorAll(".form-control")[1].value = "";
    // $(".submit-write textarea").value = "";
    const url = $(".submit-write").action;

    console.log("registerAnswerHandler fetchManager 호출되기 전");
    fetchManager({
        url: url,
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({contents}),
        callback: appendAnswer
    })
    console.log("registerAnswerHandler fetchManager 호출됨");
}

function appendAnswer({id, question, writer, comment, localDateTime}) {
    console.log("appendAnswer() 호출 id=" + id);
    console.log("writer =" + writer);
    console.log("question.id =" + question.id);

    const html = `
    <article class="article-comment" data-id=${id}>
       <div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                <a href="#" class="article-author-name">${writer}</a>
                <div class="article-header-time">${localDateTime}</div>
            </div>
        </div>
        <div class="article-doc comment-doc">
            ${comment}
        </div>
        <div class="article-util">
        <ul class="article-util-list">
            <li>
               <a class="link-modify-article" href="/api/questions/${question.id}/answers/${id}">수정</a>
            </li>
            <li>
                <form class="delete-answer-form" action="/api/questions/${question.id}/answers/${id}" method="POST">
                <input type="hidden" name="_method" value="DELETE">
                 <button type="submit" class="delete-answer-button">삭제</button>
                </form>
            </li>
        </ul>
        </div>
    </article>`

    $('.qna-comment-slipp-articles').insertAdjacentHTML('afterbegin', html);
}

function deleteAnswerHandler(evt) {
    console.log(evt.target.className);
    console.log("evt.target =" + evt.target);

    if (evt.target.className !== "delete-answer-button") return;
    evt.preventDefault();

    const url = $(".delete-answer-form").action;
    const id = url.replace(/.+\/(\d+)$/, "$1");

    console.log("url =" + url);
    console.log(("id =" + id));
    fetchManager({
        url: url,
        method: 'DELETE',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({id}),
        callback: deleteAnswer
    })
}

function deleteAnswer({t}) {
    console.log("answerid =" + t);
    const selector = $(`.article-comment[data-id='${t.id}']`);
    console.log("target 값 =" + selector);
    console.log("target,parentNode =" + selector.parentNode)
    selector.parentNode.removeChild(selector);
}