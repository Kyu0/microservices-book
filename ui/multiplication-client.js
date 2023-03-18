var SERVER_URL = 'http://localhost:8000/api'

function updateMultiplication() {
    $.ajax({
        url: SERVER_URL + "/multiplications/random",
        headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
    }).then(function (data) {
        // 폼 비우기
        $("#attempt-form").find("input[name='result-attempt']").val("");
        $("#attempt-form").find("input[name='user-alias']").val("");

        // 무작위 문제 API에서 가져와 추가하기
        $('.multiplication-a').empty().append(data.factorA)
        $('.multiplication-b').empty().append(data.factorB)
    })
}

function updateResults(alias) {
    var userId = -1

    $.ajax({
        async: false,
        url: SERVER_URL + "/results?alias=" + alias,
        headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
        success: function (data) {
            $('#results-div').show()
            $('#results-body').empty()

            data.forEach( function (row) {
                $('#results-body').append('<tr><td>' + row.id + '</td>' +
                    '<td>' + row.multiplication.factorA + ' x ' +
                           + row.multiplication.factorB + '</td>' +
                    '<td>' + row.resultAttempt + '</td>' +
                    '<td>' + (row.correct === true ? 'YES' : 'NO') + '</td></tr>')
            })
            userId = data[0].user.id
        }
    })

    return userId
}

$(document).ready(function () {
    updateMultiplication()

    $("#attempt-form").submit(function (event) {

        // 기본 폼 제출 방지
        event.preventDefault();

        // 페이지에서 값 가져오기
        var a = $('.multiplication-a').text()
        var b = $('.multiplication-b').text()
        var $form = $(this),
            attempt = $form.find("input[name='result-attempt']").val(),
            userAlias = $form.find("input[name='user-alias']").val()

        var data = {user: {alias: userAlias}, multiplication: {factorA: a, factorB: b}, resultAttempt: attempt}

        // POST 를 이용해 데이터 보내기
        $.ajax({
            url: SERVER_URL + '/results',
            headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: false,
            success: function (result) {
                if (result.correct) {
                    $('.result-message').empty().append("It's correct! Congratulation!");
                }
                else {
                    $('.result-message').empty().append("It's wrong! But, Don't give up!")
                }
            }
        })

        updateMultiplication()

        setTimeout(function () {
            var userId = updateResults(userAlias)
            updateStats(userId)
            updateLeaderBoard()
        }, 300)
    })
})