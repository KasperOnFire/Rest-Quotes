var apiLink = "http://localhost:8084/Rest-Quotes/api/quote/random";

var quote = document.getElementById("quote");

var getQuote = function () {
    var promise = fetch(apiLink);
    promise.then(function (response) {
        return response.json();
    }).then(function (data) {
        quote.innerHTML = data.quote;
    })
};

getQuote();

    