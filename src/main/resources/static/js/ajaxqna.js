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
        console.log("result = " + result + ", status = " + status);

        if (status >= 400) {
            console.log('error 가 발생했네요 ', result.error);
        } else {
            callback(result)
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}

function appendAnswer(result) {
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
    answerCount();
}

function registerAnswerHandler(evt) {

    evt.preventDefault();

    const content = document.querySelector(".descrip").value;
    document.querySelector(".descrip").value = "";

    const url = document.querySelector(".submit-write").getAttribute("action");
    console.log(url + '으로 요청');

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

    evt.preventDefault();

    const url = evt.target.parentNode.getAttribute("action");
    console.log(url + '으로 요청');

    fetchManager({
        url: url,
        method: 'DELETE',
        headers: {
            'content-type': 'application/json'
        },
        callback: deleteAnswer
    })

    answerCount();
}

function answerCount() {
    document.querySelector(".length").innerHTML = document.querySelectorAll('.comment-doc').length;
}

function deleteAnswer(answer) {
    console.log('answerId =' + answer);

    const selector = `.article[id="answer-${answer.id}"]`;
    const target = document.querySelector(selector);
    target.parentNode.removeChild(target);
}

function initEvents() {
    const answerBtn = document.querySelector(".qna-comment-slipp-articles");

    answerBtn.addEventListener("click", event => {
        if (event.target.className === "delete-answer-button") {
            deleteAnswerHandler(event);
        }
        if (event.target.className === "btn btn-success pull-right") {
            registerAnswerHandler(event);
        }
        return;
    });


}

document.addEventListener("DOMContentLoaded", (event) => {
    initEvents();
})