var express = require('express');
var http = require('http');
var url = require('url');
var router = express.Router();

var mongoose = require('mongoose');

// default database config
var host = 'mongodb://localhost/users';

// read database config form VCAP_SERVICES env
if (process.env.VCAP_SERVICES) {
  var mongodb_config = JSON.parse(process.env.VCAP_SERVICES).mongodb[0].credentials;
  host = mongodb_config.host;
  console.log('connect to coding net mongodb')
}
mongoose.connect(host, function(err){
	if(!err){
		console.log('connect to mongodb');
	}else{
		console.log('failed connect to mongodb');
		throw err;
	}
});
var Schema = mongoose.Schema, 
	ObjectId = Schema.ObjectId;
var User = new Schema({
	name:{type:String}
});
var User = mongoose.model('user', User);

/* GET users listing. */
router.get('/', function(req, res) {
  User.find({}, function(err, docs){
	 console.log(docs); 
	 res.send(docs);
  });
});
router.get('/add', function(req, res) {
	var user = new User({name:req.param('name')});
	user.save(function(err){
		if(!err){
			console.log('add new user');
			res.send('add new user');
		}else{
			throw err;
		}
	});
});

module.exports = router;
