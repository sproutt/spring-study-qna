function $(selector) {
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
        //fetch("http://xxx.com/dd", {
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