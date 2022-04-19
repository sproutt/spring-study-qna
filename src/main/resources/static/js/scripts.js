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
    initCreateEvents();
})

function initCreateEvents() {
    const answerBtn = $(".submit-writer .btn");
    if (answerBtn === null) return;
    answerBtn.addEventListener("click", registerAnswerHandler);

    const answerList = document.querySelectorAll(".article-i");
    answerList.forEach(answer => {
        answer.getElementsByClassName("delete-answer-button")[0].addEventListener("click", deleteAnswerHandler)
    });
}

function fetchManager({url, method, body, headers, callback}) {
    fetch(url, {method, body, headers, credentials: "same-origin"})
        .then((response) => {
            return response.json()
        }).then((result) => {
        callback(result)
    })
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const contents = $(".submit-writer textarea").value;
    const url = $(".submit-writer").getAttribute("action");
    $(".submit-writer textarea").value = "";

    fetchManager({
        url: url,
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: contents,
        callback: appendAnswer
    })
}

function appendAnswer({index, contents, question, writer}) {
    const html = `

  <article class="article-i" data-id="${index}">
    <div class="article-header">
        <div class="article-header-thumb">
            <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
        </div>
        <div class="article-header-text">
            <a href="#" class="article-author-name">${writer.name}</a>
        </div>
    </div>
    <div class="article-doc comment-doc">
        <p>${contents}</p>
    </div>
    <div class="article-util">
        <ul class="article-util-list">
            <li>
                <a class="link-modify-article" href="api/questions/{{{index}}}/answers/{{{index}}}/form">수정</a>
            </li>
            <li>
                <form class="delete-answer-form" action="/api/questions/${question.index}/answers/${index}" method="POST">
                    <input type="hidden" name="_method" value="DELETE">
                    <button type="submit" class="delete-answer-button">삭제</button>
            </form>
            </li>
        </ul>
    </div>
  </article>

  `
    $(".qna-comment-slipp-articles").insertAdjacentHTML("afterbegin", html);
}

function deleteAnswerHandler(evt){
    evt.preventDefault();
    const url = $(".delete-answer-form").action;
    const index = url.replace(/.+\/(\d+)$/, "$1");
    fetchManager({
        url: url,
        method: 'DELETE',
        body: JSON.stringify({index}),
        headers: {'content-type': 'application/json'},
        callback: deleteAnswer
    })
}

function deleteAnswer({index}) {
    const selector = `.article-i[data-id='${index}']`;
    const target = $(selector);
    target.parentNode.removeChild(target);
}