String.prototype.format = function () {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined' ? args[number] : match;
  });
};

function $(selector) {
  return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
  initEvents();
})

function initEvents() {
  const answerBtn = $(".submit-write .btn");
  console.log(answerBtn);
  if (answerBtn == null) return;
  //deleteAnswerHandler
  answerBtn.addEventListener("click", registerAnswerHandler);
}

function fetchManager({url, method, body, headers, callback}) {
  fetch(url, {method, body, headers, credentials: "same-origin"})
      .then((response) => {
        return response.json();
      }).then((result) => {
    callback(result);
  })
}

function registerAnswerHandler(evt) {
  evt.preventDefault();
  const comment = $(".submit-write textarea").value;
  document.querySelectorAll(".form-control")[1].value = "";

  const url = $(".submit-write").action;
  console.log(url);

  fetchManager({
    url: url,
    method: "POST",
    headers: {'content-type': 'application/json'},
    body: JSON.stringify({comment}),
    callback: appendAnswer
  })
}

function appendAnswer({id, comment, question, writer}) {
  const html = `
            <article class="article-hi" data-id="${id}">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture"
                         class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="/users/1/자바지기" class="article-author-name">${writer}</a>
                    <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                        2016-01-12 14:06
                    </a>
                </div>
            </div>
            <div class="article-doc comment-doc">
                <p>${comment}</p>
            </div>
            <div class="article-util">
                <ul class="article-util-list">
                    <li>
                        <a class="link-modify-article"
                           href="/questions/413/answers/1405/form">수정</a>
                    </li>
                    <li>
                        <form class="delete-answer-form" action="/questions/${question.id}/answers/${id}"
                              method="POST">
                            <input type="hidden" name="_method" value="DELETE">
                                <button type="submit" class="delete-answer-button">삭제</button>
                        </form>
                    </li>
                </ul>
            </div>
        </article>`

  $(".qna-comment-slipp-articles").insertAdjacentHTML("beforeend", html);
}