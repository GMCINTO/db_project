function addUser(){

    var newUserID = document.getElementById('newUserID').value.trim();
    var newUserName = document.getElementById('newUserName').value.trim();
    var newUserType = document.getElementById('newUserType').value.trim();

    if (newUserID === "") {
        alert("Please enter a user Id to add.");
        return;
    }
    if (newUserName === "") {
        alert("Please enter a user name to add.");
        return;
    }
    if (newUserType === "") {
        alert("Please enter a user type to add.");
        return;
    }

    // var formData = new FormData();
    // formData.append('newUserID', newUserID);
    // formData.append('newUserName', newUserName);
    // formData.append('newUserType', newUserType);

    var url = '/demo/addUser?newUserID=' + encodeURIComponent(newUserID) + '&newUserName=' + encodeURIComponent(newUserName) + '&newUserType=' + encodeURIComponent(newUserType);

    fetch(url, {
        method: 'POST'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        displayAddResult(data);
    })
    .catch(error => console.error(error));
}

function displayAddResult(message) {
    var resultDiv = document.getElementById('addUserResult');
    resultDiv.innerHTML = "";

    var resultMessage = document.createElement('p');
    resultMessage.textContent = message;
    resultDiv.appendChild(resultMessage);
}
