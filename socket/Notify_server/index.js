var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(process.env.PORT || 3000);

// app.get("/", function(req, res){
// 	res.sendFile(__dirname + "/index.html");	
// });

io.sockets.on('connection', function (socket) {
	console.log("Co nguoi connect ne", new Date());

	socket.on('turn on notify lock screen', function (data) {
		// emit toi tat ca moi nguoi
		console.log("Turn on 0: ",data);
		io.sockets.emit('e_Notification_screen', { noidung: "notify" });
	});

	socket.on('turn on notify 1', function (data) {
		console.log("Turn on 1: ",data);
		io.sockets.emit('e_Notification1', { noidung: "notify1" });
	});

	socket.on('turn on notify 2', function (data) {
		console.log("Turn on 2: ",data);
		io.sockets.emit('e_Notification2', { noidung: "notify2" });
	});

	socket.on('message', function (data) {
		console.log("Message: ",data);
		io.sockets.emit('e_message', { noidung: data });
	});

	// io.sockets.emit('turnon_led',{noidung : "Bật đèn"});
});

