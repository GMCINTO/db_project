function updateUser(){

    var oldUserID = document.getElementById('oldUserID').value.trim();
    var updateUserName = document.getElementById('updateUserName').value.trim();
    var updateUserType = document.getElementById('updateUserType').value.trim();

    if (oldUserID === "") {
        alert("Please enter a user Id to update.");
        return;
    }

    // var formData = new FormData();
    // formData.append('oldUserID', oldUserID);
    // formData.append('updateUserName', updateUserName);
    // formData.append('updateUserType', updateUserType);

    var url = '/demo/updateUser?oldUserID=' + encodeURIComponent(oldUserID) + '&updateUserName=' + encodeURIComponent(updateUserName) + '&updateUserType=' + encodeURIComponent(updateUserType);

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
        displayUpdateResult(data);
    })
    .catch(error => console.error(error));
}

function displayUpdateResult(message) {
    var updateUserResultDiv = document.getElementById('updateUserResult');
    updateUserResultDiv.innerHTML = "";

    var resultMessage = document.createElement('p');
    resultMessage.textContent = message;
    updateUserResultDiv.appendChild(resultMessage);
}

