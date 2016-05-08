var http = require('http');
var express = require('express');
var app = express();
var server = http.createServer(app).listen(process.env.PORT || 5000);

app.get('/', function(req, res) {
	console.log('실행');
});

var io = require('socket.io').listen(server);
var mongoose = require('mongoose');
mongoose.connect('mongodb://ironfactory:12345678@ds055594.mongolab.com:55594/heroku_4ms0gkqn');

var mongooseRandom = require("mongoose-simple-random");

var wordSchema = mongoose.Schema({
	feel : Number,
	word : String
});
wordSchema.plugin(mongooseRandom);

var wordModel = mongoose.model('word', wordSchema);

var HAPPY = 1;
var ANGRY = 2;
var SAD = 3;
var EXCITE = 4;
var SHY = 5;

io.on('connection', function(socket) {
	
	socket.on('getWord', function(data) {
		var feel = data.feel;
		
		console.log('글귀 요청');
		console.info('feel = ' + feel);
		
		if (!feel) {
			console.error('데이터 누락');
			socket.emit('getWord', {
				'code' : 410
			});
		} else {
			wordModel.findOneRandom({'feel' : feel}, function(err, result) {
				var word = result.word;
				console.info('result = ' + result);
				console.info('word = ' + word);
				
				socket.emit('getWord', {
					'code' : 200,
					'word' : word
				});
			});
		}
	});
	
	
	socket.on('insertWord', function(data) {
		var feel = data.feel;
		var word = data.word;
		
		console.log('글귀 추가');
		console.info('feel = ' + feel);
		console.info('word = ' + word);
		
		if (!feel || !word) {
			console.error('값 누락');
			socket.emit('insertWord', {
				'code' : 400
			});
		} else {
			var wordData = new wordModel({
				'feel' : feel,
				'word' : word
			});
			
			wordData.save(function(err) {
				if (err) {
					console.error('글귀 추가 DB 입력 에러');
					socket.emit('insertWord', {
						'code' : 401
					});
				} else {
					console.log('글귀 추가 완료');
					socket.emit('insertWord', {
						'code' : 200
					});
				}
			});
		}
	});
});