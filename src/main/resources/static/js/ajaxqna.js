function $(selector) {
    //잘 안됨...ㅠ
    return document.querySelector(selector);
}

function fetchManager({
    url,
    method,
    body,
    headers,
    callback
}) {
    fetch(url, {
        method,
        body,
        headers,
        credentials: "same-origin"
    }).then((response) => {
        const data = response.json();
        return data.then(result => {
            return {
                'result': result,
                'status': response.status
            }
        })
    }).then(({
        result,
        status
    }) => {
        if (status >= 400) {
            console.log('error 가 발생했네요 ', result.error);
        } else {
            callback(result)
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}

function appendAnswer(
    result
) {
    console.log(result);

    const html = `
            <article class="article" id="answer-${result.id}">
                <div class="article-header">
                    <div class="article-header-thumb">
                        <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                    </div>
                    <div class="article-header-text">
                        <a href="#" class="article-author-name">${result.writer.name}</a>
                        <div class="article-header-time">${result.time}</div>
                    </div>
                </div>
                <div class="article-doc comment-doc">
                    <p>${result.contents}222</p>
                </div>
                <div class="article-util">
                <ul class="article-util-list">
                    <li>
                        <a class="link-modify-article" href="/api/questions/${result.question.id}/answers/${result.id}">수정</a>
                    </li>
                    <li>
                        <form class="delete-answer-form" action="/api/questions/${result.question.id}/answers/${result.id}" method="POST">
                            <input type="hidden" name="_method" value="DELETE">
                            <button type="submit" class="delete-answer-button">삭제</button>
                        </form>
                    </li>
                </ul>
                </div>
            </article> `

    document.querySelector(".qna-comment-slipp-articles").insertAdjacentHTML("afterbegin", html);
    document.querySelector(".length").innerHTML = document.querySelectorAll('.comment-doc').length;
}

function deleteAnswer({
    answerid
}) {
    const selector = `.answer[data-id='${answerid}']`;
    const target = $(selector);
    target.parentNode.removeChild(target);
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const content = document.querySelector(".descrip").value;
    console.log(content);
    document.querySelector(".descrip").value = "";

    const url = document.querySelector(".submit-write").getAttribute("action");
    console.log(url);


    fetchManager({
        url: url,
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            content
        }),
        callback: appendAnswer
    })
}

function deleteAnswerHandler(evt) {
    if (evt.target.className !== "answer-delete")
        return;
    evt.preventDefault();
    const url = evt.target.href;
    const id = url.replace(/.+\/(\d+)$/, "$1");

    fetchManager({
        url,
        method: 'DELETE',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            id
        }),
        callback: deleteAnswer
    })
}

function initEvents() {
    console.log("initEvents실행");
    const answerBtn = document.querySelector(".pull-right");

    answerBtn.addEventListener("click", registerAnswerHandler);
}

document.addEventListener("DOMContentLoaded", () => {

    initEvents();
})