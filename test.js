var mongoose = require('mongoose');

// default database config
var host = 'mongodb://localhost/test';

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
	_id:{type:String, unique:true,required:true,index: true},
	signature:{type:String},
	toupic:{type:String},
	name:{type:String},
	score:{type:Number,default: 0},
	gender:{type:String},
	city:{type:String},
	company:{type:String},
	msgs:[{type:ObjectId, ref:'Msg'}],
	commments:[{type:ObjectId, ref:'Commment'}],
	friends:[{type:String, ref:'User'}],
	tasks:[{type:ObjectId, ref:'Task'}]
});

var User = mongoose.model('User', User);

var Msg = new Schema({
	content:{
		county:{type:String},
		province:{type:String},
		city:{type:String},
		street:{type:String},
		tra_level:{type:String},
		address:{type:String}
	},
	time:{type:Date, default: Date.now},
	user:{type:String, ref:'User'},
	upCount:{type:Number, default:0},
	ctCount:{type:Number, default:0},
	commments:[{type:ObjectId, ref:'Commment'}]
});
var Msg = mongoose.model('Msg', Msg);

var Commment = new Schema({
	msg:{type:ObjectId, ref:'Msg'},
	user:{type:String, ref:'User'},
	content:{type:String},
	time:{type:Date, default: Date.now}
});
var Commment = mongoose.model('Commment', Commment);

var Task = new Schema({
		user:{type:String, ref:'User'},
		content:{type:String},
		type:{type:String},
		time:{type:Date, default: Date.now}
	});
var Task = mongoose.model('Task', Task);

var login = function(qq, callback){
	User.findById(qq, function(err, doc){
		if(err){
			console.log(err);
			return;
		}
		callback(doc);
	});
};

var newUser = function(qq, name, toupic){
	var user = new User({_id:qq, name:name, toupic:toupic});
	user.save(function(err){
          if (err) {
            console.log('faile: '+err);  
			return;
          }
          console.log('save success'); 
     });
};

var updateUser = function(qq, name, value, callback){
	User.findById(qq,function(err,user){  
           if(err){
				callback(err);
				return;
			}  
		   user[name] = value;
		   user.save(callback);
      });
};

var addMsg = function(qq, county, province, city, street, tra_level, address){
	console.log(county);
	User.findById(qq,function(err,user){  
           if(err){
				callback(err);
				return;
			}
		var msg = new Msg({user:user, 
			content:{county:county,
					province:province,
					city:city,
					street:street,
					tra_level:tra_level,
					address:address}});
		msg.save(function(err){
			if(err){
				console.log(err);
				return;
			}
			user.msgs.push(msg);
			user.save();
		});
	});
};


var addComment = function(qq, msgid, content){
	var commment = new Comment({content:content, user:qq, msg:msgid});
	commment.save(function(err){
		if(err){
			console.log(err);
			return;
		}
		User.findById(qq, function (err, user) {
			if(err){
				console.log(err);
				return;
			}
			user.commments.push(commment);
		});
		Msg.findById(msgid, function (err, msg) {
			if(err){
				console.log(err);
				return;
			}
			msg.commments.push(commment);
		});
	});
};

var removeMsg = function(msgid){
	Msg.findById(msgid, function (err, msg) {
		if(err){
			console.log(err);
			return;
		}
		User.findById(msg.user, function (err, user) {
			if(err){
				console.log(err);
				return;
			}
			user.msgs.remove(msg);
		});
		msg.commments.foreach(function(commment){
			commment.remove();
		});
		msg.remove();
	});
};

var removeComment = function(commentid){
	Comment.findById(commentid, function (err, commment) {
		User.findById(commment.user, function (err, user) {
			if(err){
				console.log(err);
				return;
			}
			user.commments.remove(commment);
		});
		Msg.findById(commment.msg, function (err, msg) {
			if(err){
				console.log(err);
				return;
			}
			msg.commments.remove(commment);
		});
		commment.remove();
	});
};

var removeFriend = function(qq, friendid){
	User.findById(qq, function (err, user) {
		if(err){
			console.log(err);
			return;
		}
		user.friends.remove(friendid);
		user.save();
	});
};

var userMsgs = function(qq, skip, callback){
	User.findById(qq, "msgs").populate({path: 'msgs',
	options: {limit: 10, skip: skip}}).exec(function(err, doc){
		if(err){
			console.log(err);
		}
		callback(doc);
	});
};

var MsgComments = function(qq, skip, callback){
	User.findById(qq, "comments").populate({path: 'comments',
	options: {limit: 10, skip: skip}}).exec(function(err, doc){
		if(err){
			console.log(err);
		}
		callback(doc);
	});
};

var allMsgs = function(skip, callback){
	Msg.find().skip(skip).limit(10).populate(
		{path: 'user', select: '_id name toupic'}).exec(function(err, doc){
		if(err){
			console.log(err);
		}
		callback(doc);
	});
};

var recentMsgs = function(date, callback){
		Msg.find().where('time').gt(date)
			.populate({path: 'user', select: '_id name toupic'}).exec(function(err, doc){
			if(err){
				console.log(err);
			}
			callback(doc);
		});
	};

var addTasks = function(sourceUser, type, content, destUser){
	if(destUser!=null && sourceUser._id == destUser._id){
			return;
		}
	var task = new Task({user:sourceUser._id, content:content, type:type});
	task.save(function(err){
		if (err) {
			console.log('faile: '+err);  
			return;
		}
		if(destUser != null){
				destUser.tasks.push(task);
				destUser.save();
		}else{
			console.log("update");
			User.update({}, {"$push": {"tasks":task}}, { multi: true },
			//User.update({}, {"$set": {"score":10}}, { multi: true },
			function (err, raw) {
			  if (err){
				  console.log(err);
				  return;
			  }
			  console.log('The raw response from Mongo was ', raw);
			});
		}	
	});
};

var addFriend = function(qq, callback){
	User.findById(qq, function(err, user){
		if(err){
			callback(err);
			return;
		}
		User.findOne({city:user.city,
		_id:{$ne:user._id,$nin:user.friends}}, function(err, doc){
			if(err){
				callback(err);
				return;
			}
			if(doc == null){//如果不存在符合条件的用户
				User.findOne({_id:{$ne:user._id,$nin:user.friends}}, 
				function(err, friend){
					if(err){
						callback(err);
						return;
					}
					if(friend == null){
						callback(null);
						return;
					}
					user.friends.push(friend);
					user.save();
					addTasks(user, '好友', '', friend);
					callback(friend);
					return;
				});
			}else{
				user.friends.push(doc);
				user.save();
				addTasks(user, '好友', '', doc);
				callback(doc);
			}
		});
	});
};



var getTasks = function(qq, callback){
	User.findById(qq).populate("tasks").exec(function(err, doc){
			if(err){
				callback(err);
				return;
			}
			console.log(doc);
			if(doc != null){
				callback(doc.tasks);
				for(var i = 0; i < doc.tasks.length; i++){
					doc.tasks.remove(doc.tasks[i]);
				}
				doc.save();
				return;
			}
			callback(null);
		});
};

/*getTasks("2225", function(doc){
		if(doc == null){
			console.log("空");
		}else{
			console.log(doc);
		}
});*/

/*allMsgs(-1, function(docs){
	console.log("****"+docs);
	if(docs == null){
		console.log("出错");
	}else if(docs == ""){
		console.log("没有啦");
	}
})*/

//newUser("2225", "qq", "http://");
//addFriend("2224", function(doc){
//	console.log("***"+doc);});
//removeFriend("2224", "2225");
/*updateUser("2224", "city", "大连", function(err){
	console.log(err);
});*/
//login("2224", function(user){
//	console.log(user);	
	//addMsg(user._id,"中国","辽宁","大连","中山街","拥堵","比较堵，慎入");
//});
/*userMsgs("2224", 0, function(docs){
//	console.log("****"+docs);
	var msgs = docs.msgs;
	var time;
	for(var i = 1; i < msgs.length; i ++){
		var date = msgs[i].time;
		console.log(msgs[i].content+" "+date);
		var time = date.getFullYear() + "-" + (date.getMonth() < 10 ? '0' + (date.getMonth()+1) : (date.getMonth()+1)) + "-" + (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + " " + date.toLocaleTimeString();
		console.log(time);
		break;
	}
	time = new Date(time);
	console.log(JSON.stringify(docs));
	recentMsgs("2224",time,0,function(docs){
		console.log(docs);
	});
});*/

