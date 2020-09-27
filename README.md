# ImmunoBlock
As part of the Codebrew2020 challenge we developed a blockchain based distributed database technology automates the tracking and tracing of the proof of vaccination. 

Upon administering a vaccine, the clinic would submit the record via a web portal to servers hosting the blockchain database. The blockchain database would conduct the ‘proof of work’ algorithm, also known as mining, to create a unique transaction hash for the record. 

This is returned back to the clinic, and added to the proof of vaccination card issued to the patient. The transaction hash can be used to lookup the record, via a web portal, by authorised officers who have been granted access. 

This README describes the steps to run the RPC server (which connects to the blockchain database) and how to interact with it using REST API.

The prototype is hosted here http://immunoblock.azurewebsites.net/

# Requirements 

- Java 11 (https://jdk.java.net/java-se-ri/11)
- MultiChain (https://www.multichain.com/developers/creating-connecting/)
- WordPress 5.5 (https://wordpress.org/support/wordpress-version/version-5-5/)

# Running MultiChain RPC client

After configuring MultiChain blockchain on one or more nodes, the RPC client can be started as follows:

```shell
cd backend
nohup java -jar backend.jar server backendSettings.yml &
```
This RPC client will run on port 8080 can which acts as the interface for REST calls to send and retrieve data from the blockchain.

# Send and retrieve data via REST

To send a vaccination record to the blockchain, a POST command can be made like the following:

```shell
curl -d @record.json -H "Content-Type: application/json" -X POST http://45.113.235.20:8080/vaccination
```

where the record is the in the following format: 

```json
{
        "passportNumber": "B2E674",
        "fullName": "Ngoc Nguyen",
        "timestamp": 43432543,
        "vaccineType": "NORMAL",
        "clinicId": "TT3212"
}
```

Similarly this record can be sent via a webform using the HTML below

```html
<div style="text-align:center;">
        <p></p><input type="text" placeholder="Name" id="name" size="50"></p>
        <p></p><input type="text" placeholder="Passport Number" id="id" size="50"></p>
        <p></p><input type="text" placeholder="Vaccine Type" id="vaccine" size="50"></p>
        <p></p><input type="text" placeholder="Clinic ID" id="clinic" size="50"></p>
        <button type="submit" onclick="loadDoc(myFunction)" style="background:#70acb1">Submit</button>

</div>
</br>
<div id="demo" style="text-align:center;">
</div>
<div id="hash" style="text-align:center;">
 </div>
 
 
<script>

function loadDoc(cFunction) {
  console.log("trigger");
  var xhttp = new XMLHttpRequest();
  xhttp.open("POST", "http://45.113.235.20:8080/vaccination/", true);
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      cFunction(this);
    }
  };
  var name = document.getElementById("name").value;
  var id = document.getElementById("id").value;
  var vaccine = document.getElementById("vaccine").value;
  var clinic = document.getElementById("clinic").value;
  var packet = { "passportNumber":id.toString(), "fullName":name.toString(),"timestamp":Date.now(), "vaccineType":vaccine.toString(), "clinicId":clinic.toString()}
  xhttp.send(JSON.stringify(packet));
}

function myFunction(xhttp) {
  var data = xhttp.responseText;
  document.getElementById("demo").innerHTML = "<h3>The Transaction Hash is: </h3>";
  document.getElementById("hash").innerHTML = "<h4>" + data + "</h4>";
  
}
</script>
```

This webform is active on this link http://immunoblock.azurewebsites.net/submit/. The response of this POST command is a transaction hash of the block that was created.

To retrieve a record by using the transaction hash, a GET request can be performed as followed

```shell
export txid=b665968a9c54e19925cf90cd210afec7e4068bcbfa39e902123029c97679855c
export url='http://45.113.235.20:8080/vaccination/'
export get_url=${url}${txid}
curl $get_url
```

The reponse is the following, which contains the record of the data in ASCI format,

```json
{
"offchain":false,"data":"7b2270617373706f72744e756d626572223a22687a30313536373332222c2266756c6c4e616d65223a2261676d6f222c2274696d657374616d70223a313630313136383139313438332c2276616363696e6554797065223a223334333433222c22636c696e69634964223a2233343334323334227d",
"blocktime":1601168201,"keys":["hz0156732","3434234"],"available":true,"publishers":["1DVtTvHZJtet4gX3DSeBN3vp3MvgSj1NLbNgTf"],
"txid":"f99a19bc4b2f678c66a79df11290e0492524f5d1ba23b7429050c385cd8cbce0","confirmations":5
}
```

A web form for performing this GET request is at http://immunoblock.azurewebsites.net/lookup/, using the following HTML

```html
  <div style="text-align:center">
        <p><input type="text" placeholder="Enter transaction hash" id="myInput" size=50></p>
        <p><button type="submit" onclick="loadDoc(myFunction)" style="background:#70acb1">Submit</button></p>

  </div>

<div id="name"></div>
<div id="id"></div>
<div id="vaccine"></div>
<div id="clinic"></div>


<script>

function loadDoc(cFunction) {
  console.log("trigger");
  var xhttp = new XMLHttpRequest();
  var inputVal = document.getElementById("myInput").value;
  var url = "http://45.113.235.20:8080/vaccination/" + inputVal;
  xhttp.open("GET", url, true);
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      cFunction(this);
    }
  };
  
  xhttp.send();
}


function hex2a(hexx) {
    var hex = hexx.toString();//force conversion
    var str = '';
    for (var i = 0; (i < hex.length && hex.substr(i, 2) !== '00'); i += 2)
        str += String.fromCharCode(parseInt(hex.substr(i, 2), 16));
    return str;
}

function myFunction(xhttp) {
  var data = JSON.parse(xhttp.responseText);
  var fields = JSON.parse(hex2a(data.data));
  document.getElementById("name").innerHTML = "Full name: " + fields.fullName;
  document.getElementById("id").innerHTML = "Passport Number: " + fields.passportNumber;
  document.getElementById("vaccine").innerHTML = "Vaccine: " + fields.vaccineType;
  document.getElementById("clinic").innerHTML = "Clinic: " + fields.clinicId;
}


</script>
```


