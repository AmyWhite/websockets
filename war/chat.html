<!DOCTYPE html>
<html>
<head>
    <title>Apache Tomcat WebSocket Examples: Chat</title>
    <style type="text/css">
        input#chat {
            width: 410px
        }

        .container {
            width: 350px;
        }

        #console {
            border: 1px solid #CCCCCC;
            border-right-color: #999999;
            border-bottom-color: #999999;
            height: 170px;
            overflow-y: scroll;
            padding: 5px;
            width: 100%;
        }
        
        #participants {
            border: 1px solid;
            border-bottom-color: #006600;
            border-left-color: #006600;
            border-top-color: #ffffff;
            border-right-color: #ffffff;
            height: 300px;
            padding: 5px;
            width: 100%;
            background-color: #99ff99;
            font-weight: bold;
            overflow-y: scroll;
        }

        #console p {
            padding: 0;
            margin: 0;
        }
    </style>
    <script type="text/javascript">
        
        var participants_prefix = "Participants: ";
        var participants_delimiter = ";";
        var personal_prefix = "==";
        
        var partsList = null;
        
        var Chat = {};

        Chat.socket = null;

        Chat.connect = (function(host) {
            if ('WebSocket' in window) {
                Chat.socket = new WebSocket(host);
                Chat.socket.binaryType = "arraybuffer";
            } else if ('MozWebSocket' in window) {
                Chat.socket = new MozWebSocket(host);
                Chat.socket.binaryType = "arraybuffer";
            } else {
                Console.log('Error: WebSocket is not supported by this browser.');
                return;
            }

            Chat.socket.onopen = function () {
                Console.log('Info: WebSocket connection opened.');
                document.getElementById('chat').onkeydown = function(event) {
                    if (event.keyCode == 13) {
                        Chat.sendMessage();
                    }
                };
                document.getElementById('pm').onkeydown = function(event) {
                    if (event.keyCode == 13) {
                        Chat.sendPersonalMessage();
                    }
                };
            };

            Chat.socket.onclose = function () {
                document.getElementById('chat').onkeydown = null;
                document.getElementById('pm').onkeydown = null;
                Participants.clear();
                Console.log('Info: WebSocket closed.');
            };

            Chat.socket.onmessage = function (message) {
                
                if (typeof message.data == "string" ) {
                    var messageObj = JSON.parse(message.data);
                    if (''+messageObj.participants == "true") {
                        Participants.show(messageObj.text);
                    } else {
                        if (messageObj.recipient != '') {
                            alert(messageObj.sender + ": " + messageObj.text);
                        } else {
                            Console.log("<i>" + messageObj.sender + "</i>: " + messageObj.text);
                        }
                        /*
                        var personal = false;
                        for (var i = 0; i < partsList.length; i++) {
                            if (message.data.lastIndexOf(partsList[i] + personal_prefix) > 0) {
                                personal = true;
                                if (partsList[i].indexOf(document.getElementById('nickname').value) != -1) {
                                    alert(message.data.replace(partsList[i] + personal_prefix, ''));
                                }
                                break;
                            }
                        }
                        if (!personal) {
                            Console.log(message.data);
                        }
                        */
                    }
                } else {
                    var canva = document.getElementById('canva');
                    var ctx = canva.getContext("2d");
                    
                    var bytearray = new Uint8Array(message.data);
                    var imgdata = ctx.getImageData(0,0,canva.width,canva.height);
                    for (var i = 0; i < imgdata.data.length; i++) {
                        imgdata.data[i] = bytearray[i];
                    }
                    
                    ctx.putImageData(imgdata, 0, 0)
                }
            };
            
            Chat.socket.onerror = function (err) {
            	Console.log("Error: WebSocket communication error " + err);
            };
        });

        Chat.initialize = function (nickname) {
            var enc_nickname = encodeURIComponent(nickname);
            //alert(enc_nickname);
            if (window.location.protocol == 'http:') {
                Chat.connect('ws://' + window.location.host + '/websocketchat/chatServer?nickname='+enc_nickname);
            } else {
                Chat.connect('wss://' + window.location.host + '/websocketchat/chatServer?nickname='+enc_nickname);
            }
        };

        Chat.sendMessage = (function() {
            var message = document.getElementById('chat').value;
            var myself = document.getElementById('nickname').value;
            if (message != '') {
                Chat.socket.send(getJSONString(myself, '', message));
                document.getElementById('chat').value = '';
            }
        });
        
        Chat.sendPersonalMessage = (function() {
            if (document.getElementById('pm').value != '') {
                var myself = document.getElementById('nickname').value;
                var rec = document.getElementById('users').value;
                var message = document.getElementById('pm').value;
                //var message = document.getElementById('users').value + personal_prefix + document.getElementById('pm').value;
                Chat.socket.send(getJSONString(myself, rec, message));
                document.getElementById('pm').value = '';
            }
        });
        
        Chat.disconnect = (function() {
        	if (window.confirm('Are you sure you want to leave?')) {
	        	Chat.socket.close();	
        	}
        });

        var Console = {};

        Console.log = (function(message) {
            var console = document.getElementById('console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.innerHTML = message;
            console.appendChild(p);
            while (console.childNodes.length > 25) {
                console.removeChild(console.firstChild);
            }
            console.scrollTop = console.scrollHeight;
        });
        
        var Participants = {};
        
        Participants.show = (function(strList) {
            Participants.clear();
            var parts = document.getElementById('participants');
            var selectUsers = document.getElementById('users');
            partsList = strList.replace(participants_prefix, '').split(participants_delimiter);
            
            for (var i = 0; i < partsList.length; i++) {
                // add user to right panel
                var p = document.createElement('p');
                p.style.wordWrap = 'break-word';
                p.innerHTML = partsList[i];
                parts.appendChild(p);
                
                // add user to select list
                var opt = document.createElement('option');
                opt.value = partsList[i];
                opt.innerHTML = partsList[i];
                selectUsers.appendChild(opt);
            }
            
        });
        
        Participants.clear = (function() {
            var parts = document.getElementById('participants');
            while (parts.hasChildNodes()) {
                parts.removeChild(parts.firstChild);
            }
            var selectUsers = document.getElementById('users');
            while (selectUsers.hasChildNodes()) {
                selectUsers.removeChild(selectUsers.firstChild);
            }
        });
        
        function getJSONString(sender, recipient, text) {
            var jsonStr = '{"participants":"false","sender":"'+sender+'","recipient":"'+recipient+'","text":"'+text+'"}';
            return jsonStr;
        }
        
        function sharePicture() {
            var canva = document.getElementById('canva');
            var ctx = canva.getContext("2d");
            var imageData = ctx.getImageData(0, 0, canva.width, canva.height);
            
            var canvaSize = imageData.data.length;
            var byteArray = new Uint8Array(canvaSize);
            for (var i = 0; i < canvaSize; i++) {
                byteArray[i] = imageData.data[i];
            }
            
            Chat.socket.send(byteArray.buffer);
        }

        function connectToChat() {
        	
                Chat.initialize(document.getElementById('nickname').value);
	        document.getElementById('connect').disabled = true;
                document.getElementById('nickname').readOnly = true;
	        document.getElementById('disconnect').disabled = false;
        
        }
        
        function disconnectFromChat() {
        	
        	Chat.disconnect();
	        document.getElementById('connect').disabled = false;
                document.getElementById('nickname').readOnly = false;
	        document.getElementById('disconnect').disabled = true;
        }
        
    </script>
</head>
<body onload="init()">
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websockets rely on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div>
    <table border="0">
    <tr>
        <td>
            <p>
                My nickname: <input type="text" placeholder="type you nickname and connect" id="nickname">
                <input type="button" onclick="connectToChat()" value="Connect now!" id="connect">
                <input type="button" onclick="disconnectFromChat()" value="Leave chat" id="disconnect" disabled >
            </p>
            <p>
                <input type="text" placeholder="type and press enter to chat" id="chat">
            </p>
            <div id="console-container" class="container">
                <div id="console"></div>
            </div>
        </td>
        <td>
            <div id="participants-container" class="container">
                <div id="participants"></div>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            Send personal message: <br>
            <select id="users">
                
            </select>
            <input type="text" placeholder="type and press enter" id="pm" style="width: 250px;">
        </td>
        <td>
            <canvas id="canva" width="300" height="200" style="border:1px solid #000000;"></canvas>
            <br>
            <input type="checkbox" id="shape" value="line"> Draw line
            <br>
            RGB: #<input type="text" id="color" maxlength="6" value="000000" onchange="defaultColor();">
            <input type="button" value="Clear canvas" onclick="clearCanvas();">
            <input type="button" value="Share picture" onclick="sharePicture()">
        </td>
    </tr>
    </table>
</div>
    
<script type="text/javascript">
var x = -1;
var y = -1;

function canvaMouseDown(event) {
    var canva = document.getElementById('canva');
    //alert("clientX="+event.clientX+" canvaX="+canva.getBoundingClientRect().left);
    x = event.clientX - canva.getBoundingClientRect().left;
    y = event.clientY - canva.getBoundingClientRect().top;
}

function canvaMouseUp(event) {
	if (x != -1 && y != -1) {
		var canva = document.getElementById('canva');
		var ctx = canva.getContext("2d");

		var xEnd = event.clientX - canva.getBoundingClientRect().left;
		var yEnd = event.clientY - canva.getBoundingClientRect().top;
		
		if (document.getElementById('shape').checked) {
			ctx.strokeStyle = "#" + document.getElementById('color').value;
			ctx.beginPath();
			ctx.moveTo(x, y);
			ctx.lineTo(xEnd, yEnd);
			ctx.stroke();
		} else {
			
                        ctx.fillStyle = "#" + document.getElementById('color').value;
			ctx.fillRect(x, y, Math.abs(xEnd-x), Math.abs(yEnd-y));
		}
	}
	
	x = -1;
	y = -1;
}

function defaultColor() {
	if (document.getElementById('color').value == '' || document.getElementById('color').value.length < 6) {
		document.getElementById('color').value = '000000';
	}
}
function clearCanvas() {
	var canva = document.getElementById('canva');
	var ctx = canva.getContext("2d");
	
	ctx.clearRect(0, 0, canva.width, canva.height);
}
function init() {
	var canva = document.getElementById('canva');
	canva.addEventListener('mousedown', canvaMouseDown, false);
	canva.addEventListener('mouseup', canvaMouseUp, false);
}
</script>
    
    
</body>

</html>