function usesUpdate(){

    var userID = document.getElementById('userID').value.trim();
    var deviceID = document.getElementById('deviceID').value.trim();
    var usageDate = document.getElementById('usageDate').value.trim();
    var usageDuration = document.getElementById('usageDuration').value.trim();

    if (userID === "") {
        alert("Please enter a user Id to add.");
        return;
    }
    if (deviceID === "") {
        alert("Please enter a device ID to add.");
        return;
    }
    if (usageDate === "") {
        alert("Please enter a usage date to add.");
        return;
    }
    if (usageDuration === "") {
        alert("Please enter a usage duration to add.");
        return;
    }


    var formData = new FormData();
    formData.append('userID', userID);
    formData.append('deviceID', deviceID);
    formData.append('usageDate', usageDate);
    formData.append('usageDuration', usageDuration);

    var url = '/demo/usesUpdate?userID=' + encodeURIComponent(userID)
     + '&deviceID=' + encodeURIComponent(deviceID)
     + '&usageDate=' + encodeURIComponent(usageDate)
     + '&usageDuration=' + encodeURIComponent(usageDuration);

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
        displayUsesResult(data);
    })
    .catch(error => console.error(error));
}

function displayUsesResult(message) {
    var resultDiv = document.getElementById('updateUsesResult');
    resultDiv.innerHTML = "";

    var resultMessage = document.createElement('p');
    resultMessage.textContent = message;
    resultDiv.appendChild(resultMessage);
}
