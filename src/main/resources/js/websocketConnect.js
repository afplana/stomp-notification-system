function connect() {
    var socket = new WebSocket('ws://localhost:8081/notifications');
    ws = Stomp.over(socket);

    ws.connect({}, function(frame) {
            ws.subscribe("/user/queue/errors", function(message) {
                alert("Error " + message.body);
            });

            ws.subscribe("/user/queue/events", function(message) {
                alert("Message " + message.body);
            });
        }, function(error) {
            alert("STOMP error: " + error);
        });
    }

    function disconnect() {
        if (ws != null) {
            ws.close();
        }
        setConnected(false);
        console.log("Disconnected!!");
}