String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
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
  initEvents();
})

function initEvents() {
  const answerBtn = $(".submit-write .btn");

  console.log(answerBtn);
  answerBtn.addEventListener("click", registerAnswerHandler);
  $('.qna-comment-slipp-articles').addEventListener('click',deleteAnswerHandler);
}

function fetchManager({ url, method, body, headers, callback}) {
  fetch(url, {method,body,headers,credentials: "same-origin"})
      .then((response) => {
        return response.json()
      }).then((result) => {
        callback(result)
      })
}

function registerAnswerHandler(evt) {
  evt.preventDefault();
  const contents = $(".submit-write textarea").value;
  $(".submit-write textarea").value = "";
  const url = $(".submit-write").action;

  fetchManager({
    url: url,
    method: 'POST',
    headers: { 'content-type': 'application/json'},
    body: JSON.stringify({contents}),
    callback: appendAnswer
  })
}

function appendAnswer({id, contents, question, writer, createdDate}) {
  const html = `
    <article class="article-comment" data-id=${id}>
       <div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                <a href="#" class="article-author-name">${writer.name}</a>
                <div class="article-header-time">${createdDate}</div>
            </div>
        </div>
        <div class="article-doc comment-doc">
            ${contents}
        </div>
        <div class="article-util">
        <ul class="article-util-list">
            <li>
                <a class="link-modify-article" href="/questions/${question.index}/answers/${id}">수정</a>
            </li>
            <li>
                <form class="delete-answer-form" action="/api/questions/${question.index}/answers/${id}" method="POST">
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
  if(evt.target.className !== "delete-answer-button") return;
  evt.preventDefault();

  const url = $(".delete-answer-form").action;
  console.log(url);

  fetchManager({
    url: url,
    method: 'DELETE',
    headers: { 'content-type': 'application/json'},
    callback: deleteAnswer
  })
}

function deleteAnswer({data}) {
  console.log(data);
  console.log(data.id);
  const target = $(`.article-comment[data-id='${data.id}']`);
  console.log(target);
  console.log(target.parentNode);
  target.parentNode.removeChild(target);
}