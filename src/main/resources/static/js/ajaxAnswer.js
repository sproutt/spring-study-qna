const $ = function (selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    $(".qna-comment-slipp-articles").addEventListener("click", event => {
        if(event.target.className==="delete-answer-button"){

            console.log("삭제시작");
            deleteAnswerHandler(event);
        }
        if(event.target.className==="btn btn-success pull-right"){
            console.log("등록시작");

            registerAnswerHandler(event);
        }
        return;
    });
}

function fetchManager({url, method, body, headers, callback}) {
    fetch(url, {method, body, headers, credentials: "same-origin"})
        .then((response) => {
            var data = response.json();
            console.log(data);
            return data
        }).then((result) => {
        callback(result)
    })
}

function registerAnswerHandler(event) {
    event.preventDefault();
    textbox = $(".submit-write textarea");
    const contents = textbox.value;
    textbox.value = "";
    let url = $(".submit-write").getAttribute("action");
    fetchManager({
        url,
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({contents}),
        callback: appendAnswer
    })
}


function appendAnswer({result, data}) {
    const html = `
        <article class="article" id="answer-${data.id}">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${data.writer.name}</a>
                    <div class="article-header-time">${data.createYearToSecond}</div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                <p>${data.contents}</p>
            </div>
            <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/api/questions/${data.question.id}/answers/${data.id}">수정</a>
                </li>
                <li>
                    <form class="delete-answer-form" action="/api/questions/${data.question.id}/answers/${data.id}" method="POST">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" class="delete-answer-button">삭제</button>
                    </form>
                </li>
            </ul>
            </div>
        </article> `

    $(".qna-comment-count strong").innerText = `${data.question.size}`;
    $(".qna-comment-slipp-articles").insertAdjacentHTML("afterbegin", html);
}

function deleteAnswerHandler(event){
    event.preventDefault();
    let url = $(".delete-answer-button").parentElement.getAttribute("action");
    fetchManager({
        url,
        method: 'DELETE',
        headers: {'content-type': 'application/json'},
        callback: deleteAnswer
    })
}
function deleteAnswer({result, data}){
    const query = `.article[id="answer-${data}"]`;
    console.log(query);
    const target = $(query);
    target.parentNode.removeChild(target);
}