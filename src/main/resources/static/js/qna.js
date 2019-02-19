function appendAnswer({id, answerWriter, question, contents, enrollTime}) {
    const html = `
        <article class="article" data-id="${id}">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${answerWriter.name}</a>
                    <div class="article-header-time">${enrollTime}</div>
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
                        <button type="submit" class="delete-answer-button" data-id="${id}">삭제</button>
                    </form>
                </li>
            </ul>
            </div>
        </article> `

    $('div[name="comment"]').insertAdjacentHTML("beforeend", html);
}

function deleteAnswer({answerid}) {
    const selector = `.answer[data-id='${answerid}']`;
    const target = $(selector);
    target.parentNode.removeChild(target);
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const questionId = $(".submit-write input").value;
    const contents = $(".submit-write textarea").value;
    $(".submit-write textarea").value = "";

    fetchManager({
        url: '/api/questions/' + questionId + '/answers',
        method: 'POST',
        headers: {'content-type': 'application/json'},
        callback: appendAnswer
    })

}

function deleteAnswerHandler(evt) {
    if (evt.target.className !== "answer-delete") return;
    evt.preventDefault();
    const url = evt.target.href;
    const id = url.replace(/.+\/(\d+)$/, "$1");

    fetchManager({
        url,
        method: 'DELETE',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({id}),
        callback: deleteAnswer
    })
}

function initEvents() {
    const answerBtn = $(".answer-form .btn");
    const answer = $(".answers");
    answerBtn.addEventListener("click", registerAnswerHandler);
    answer.addEventListener("click", deleteAnswerHandler);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function fetchManager({ url, method, body, headers, callback }) {
    fetch(url, {
        method,
        body,
        headers,
        credentials: "same-origin"
    }).then((response) => {
        return response.json()
    }).then((result) => {
        callback(result)
    })
}