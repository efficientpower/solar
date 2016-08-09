<!DOCTYPE html>
<html>
<head>
<title>websockets</title>
</head>
<body>
	<div>
		<input type="submit" value="start" onclick="test()" />
	</div>
	<div id="messages"></div>
	<script type="text/javascript">
	/*
        var webSocket =  new WebSocket('ws://localhost:8080/solar/socket/message.do');

        webSocket.onerror = function(event) {
            onError(event)
        };

        webSocket.onopen = function(event) {
            onOpen(event)
        };

        webSocket.onmessage = function(event) {
            onMessage(event)
        };
        */

        function onMessage(event) {
            document.getElementById('messages').innerHTML 
                += '<br />' + event.data;
        }

        function onOpen(event) {
            document.getElementById('messages').innerHTML 
                = 'Connection established';
        }

        function onError(event) {
            alert(event.data);
            alert("error");
        }

        /*
        function start() {
            webSocket.send('hello');
            return false;
        }
        */
        
        function test(){
        	for(var i=0; i<10000; i++){
        		var webSocket =  new WebSocket('ws://localhost:8080/solar/socket/message.do');
        		webSocket.onopen = function(event) {
                    onOpen(event)
                };
                webSocket.onmessage = function(event) {
                    onMessage(event)
                };
                webSocket.send('hello' + i);
        	}
        }
    </script>
</body>
</html>