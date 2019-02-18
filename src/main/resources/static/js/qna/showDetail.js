function $(selector){
    return document.querySelector(selector);
}

document.onload=function(){
    const answerButton = $(".submit-write .btn");
    if(answerButton === null) return;
    answerButton.addEventListener("click", registerAnswerHandler);
}

function registerAnswerHandler(event) {
    event.preventDefault();
    const contents= $(".form-control").value;
    $(".form-control").value="";

    fetchManager({
        url: '/api/questions/1/answer',
        method: 'POST',
        headers:{ 'content-type': 'application/json'},
        body: JSON.stringify({contents}),
        callback: appendAnswer
    })
}

function fetchManager(url, method, headers, body, callback){
    fetch(url, {method, body, headers, credentials: "same-origin" })
        .then(response=>{
            return response.json();
        }).then(result =>{
            callback(result);
    })
}