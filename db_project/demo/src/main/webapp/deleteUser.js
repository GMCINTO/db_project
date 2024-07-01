function deleteUser() {
    var deleteInput = document.getElementById('deleteUserID').value.trim();
    if (deleteInput === "") {
        alert("Please enter a user to delete.");
        return;
    }

    var url = '/demo/delete?deleteUserID=' + encodeURIComponent(deleteInput);

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
        displayDeleteResult(data);
    })
    .catch(error => console.error('Error:', error));
}

function displayDeleteResult(message) {
    var deleteUserResultDiv = document.getElementById('deleteUserResult');
    deleteUserResultDiv.innerHTML = "";

    var resultMessage = document.createElement('p');
    resultMessage.textContent = message;
    deleteUserResultDiv.appendChild(resultMessage);
}
