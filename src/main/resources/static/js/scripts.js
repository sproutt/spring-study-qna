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

  answerBtn.addEventListener("click", registerAnswerHandler);

  const answerList = document.querySelectorAll(".article-hi");
  console.log("answerList 길이 = " + answerList.length);

  answerList.forEach(answer => {
    console.log(answer.getElementsByClassName("delete-answer-button")[0]);
    console.log(answer.getElementsByClassName("delete-answer-button")[1]);
    answer.getElementsByClassName("delete-answer-button")[0].addEventListener("click", deleteAnswerHandler);
  });
}

function fetchManager({url, method, body, headers, callback}) {
  fetch(url, {method, body, headers, credentials: "same-origin"})
      .then((response) => {
        const data = response.json();
        console.log(data);
        return data.then(result =>  {
          return {
            'result' : result,
            'status' : response.status
          }
        })
      }).then( ({result, status}) => {
    if(status >= 400) {
      console.log('error 가 발생했네요 ', result.error);
    }else{
      console.log(result);
      callback(result);
    }
  }).catch(err => {
    console.log("oops..", err);
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

function appendAnswer({result}) {
  console.log("result = " + result);
  id = result.id;
  comment = result.comment;
  questionId = result.questionId;
  writer = result.writer;

  console.log("id = " + id);
  console.log("comment = " + comment);
  console.log("question = " + questionId);
  console.log("writer = " + writer);

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
                        <form class="delete-answer-form" action="/questions/${questionId}/answers/${id}"
                              method="POST">
                            <input type="hidden" name="_method" value="DELETE">
                                <button type="button" class="delete-answer-button"
                                onclick="deleteAnswerHandler(this)" value="${id}/${questionId}">삭제</button>
                        </form>
                    </li>
                </ul>
            </div>
        </article>`

  $(".qna-comment-slipp-articles").insertAdjacentHTML("beforeend", html);
}

function deleteAnswerHandler(target) {
  if(target.className !== "delete-answer-button") return;
  console.log("target = " + target);
  const value = target.value.split("/");
  console.log("value = " + value);
  const url = "/questions/" + value[1] + "/answers/" + value[0];
  console.log(url);
  console.log("deleteAnswerHandler = " + url);

  fetchManager({
    url,
    method: 'DELETE',
    headers: {'content-type': 'application/json'},
    callback: deleteAnswer
  })
}

function deleteAnswer({result}) {
  console.log("result = " + result);
  console.log("result.id = " + result.id);
  const selector = `.article-hi[data-id='${result.id}']`;
  console.log("deleteAnswer" + selector);
  const target = $(selector);
  target.parentNode.removeChild(target);
}