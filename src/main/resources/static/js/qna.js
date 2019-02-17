function appendAnswer({content, writer, date, answerId}) {
    const html = `
    <li class="answer" data-id=${answerId}>
        <div class="answer-content"> ${content} </div>
        <div class="answer-metainfo">
            <div class="answer-id">${writer.id}</div>
            <div class="answer-date">${date}</div>
            <div class="answer-util">
                <a class="answer-delete" href="/api/questions/2/answers/${answerId}">삭제</a>
            </div>
        </div>
    </li> `

    $(".answers").insertAdjacentHTML("beforeend", html);
}

function deleteAnswer({answerid}) {
    const selector = `.answer[data-id='${answerid}']`;
    const target = $(selector);
    target.parentNode.removeChild(target);
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const content = $(".form-control").value;
    $(".form-control").value = "";

    fetchManager({
        url: '/api/questions/1/answers',
        method: 'POST',
        contentType: 'application/json',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({content}),
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