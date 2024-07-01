function getUserUsageRecords(){

    // Get input values
    var dateSearchUserID = document.getElementById('dateSearchUserID').value.trim();
    var lowerBound = document.getElementById('lowerBound').value.trim();
    var upperBound = document.getElementById('upperBound').value.trim();

    // Validate input fields
    if (dateSearchUserID === "") {
        alert("Please enter a user Id to search.");
        return;
    }
    if (lowerBound === "") {
        alert("Please enter a lower bound date.");
        return;
    }
    if (upperBound === "") {
        alert("Please enter an upper bound date.");
        return;
    }

    // Construct URL for fetching data
    var url = '/demo/dateSearch?searchUserID=' + encodeURIComponent(dateSearchUserID) + '&lbDate=' + encodeURIComponent(lowerBound) + '&ubDate=' + encodeURIComponent(upperBound);

    // Fetch data
    fetch(url)
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        // Call function to display search result
        displayDateSearchResult(data);
    })
    .catch(error => console.error(error));
}

function displayDateSearchResult(records) {
    var getUserUsageResultDiv = document.getElementById('getUserUsageResult');
    getUserUsageResultDiv.innerHTML = "";

    // If no records found, display message
    if (records.length === 0) {
        getUserUsageResultDiv.textContent = "No records found.";
    } else {
        // Otherwise, display the records
        var recordsList = document.createElement('ul');
        records.forEach(record => {
            var listItem = document.createElement('li');
            // Access properties of the record object
            listItem.textContent = record.userName + ' - Device ID: ' + record.deviceId + ' - Device Name: ' +  record.deviceName 
                + ' - Usage Date: ' + record.usageDate + ' - Usage Duration: ' + record.usageDuration + " minutes";
            recordsList.appendChild(listItem);
        });
        getUserUsageResultDiv.appendChild(recordsList);
    }
}