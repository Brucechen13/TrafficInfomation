var express = require('express');
var router = express.Router();

var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/users', function(err){
	if(!err){
		console.log('connect to mongodb');
	}else{
		console.log('failed connect to mongodb');
		//throw err;
	}
});
var Schema = mongoose.Schema, 
	ObjectId = Schema.ObjectId;
var User = new Schema({
	name:String
});
var User = mongoose.model('User', User);

/* GET users listing. */
router.get('/', function(req, res) {
  User.find({}, function(err, docs){
	 console.log(docs); 
  });
});
router.get('/add', function(req, res) {
	var user = new User(req.params.name);
	user.save(function(err){
		if(!err){
			console.log('add new user');
		}else{
			throw err;
		}
	});
});

module.exports = router;
