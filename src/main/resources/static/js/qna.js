function $(selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    console.log("initEvents실행");
    const answerBtn = document.querySelector(".submit-write .btn");
    console.log(answerBtn)
    if(answerBtn === null) return;
    answerBtn.addEventListener("click", registerAnswerHandler);
}

function fetchManager({ url, method, body, headers, callback }) {
    fetch(url, {method,body,headers,credentials: "same-origin"})
        .then((response) => {
            console.log(response.body)
        return response.json()
    }).then((result) => {
        callback(result)
    })
}

function registerAnswerHandler(evt) {
    console.log("핸들러 실행");
    console.log(evt)
    evt.preventDefault();
    const contents = document.querySelector(".submit-write textarea").value;
    document.querySelectorAll(".form-control")[1].value="";
    console.log(contents);
    console.log("pass")
    fetchManager({
        url: '/api' + location.pathname + '/answers',
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({'contents': contents}),
        callback: appendAnswer
    })
}

function appendAnswer({id, contents, question, writer, time}) {
    console.log("passpass")
    const html = `
        <article class="article">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${writer.name}</a>
                    <div class="article-header-time">${time}</div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                ${contents}
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
        </article> `

    document.querySelector(".qna-comment-slipp-articles").insertAdjacentHTML("afterbegin", html);
}