var stompClient = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function connect() {
    var socket = new WebSocket('ws://localhost:8081/notifications');
    ws = Stomp.over(socket);

	ws.connect({}, function(frame) {
        setConnected(true);
	    console.log('Connected: ' + frame);
	    stompClient.subscribe('/topic/events', function(message) {
	        showMessageOutput(JSON.parse(message.body));
	    });
	});
}

function disconnect() {
    if(ws != null) {
	    ws.disconnect();
	}
	setConnected(false);
	console.log("Disconnected!!");
}

function sendMessage() {
    var from = document.getElementById('from').value;
    var text = document.getElementById('text').value;
    ws.send("/app/notifications", {}, JSON.stringify({'from':from, 'text':text}));
}

function showMessageOutput(messageOutput) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(messageOutput.from + ": " + messageOutput.text + " (" + messageOutput.time + ")"));
    response.appendChild(p);
}