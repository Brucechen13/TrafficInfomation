var express = require('express');
var http = require('http');
var url = require('url');
var router = express.Router();

var mongoose = require('mongoose');
var models = {
	Account:require('../models/Account')(mongoose)
};

var pool=[];//连接池

/* GET users listing. */
router.get('/', function(req, res) {
  User.find({}, function(err, docs){
	 console.log(docs); 
	 res.send(docs);
  });
});
router.get('/login', function(req, res) {//登陆，检查是否为新用户
	var qq = req.param('qq');
	models.Account.login(qq, function(doc){
		if(doc == null){
			res.json({"suc":"false"});
			return;
		}
		res.json(doc);
	});
});

router.get('/newuser', function(req, res) {//添加新用户
	var qq = req.param('qq');
	var name = req.param('name');
	var pic = req.param('pic');
	var gender = req.param('gender');
	console.log(qq+name+pic);
	models.Account.newUser(qq, name, pic, gender, function(err){
		console.log(err);
		if(err == null){
			res.json({"suc":"true"});
			return;
		}
		res.json({"suc":err});
	});
});

router.get('/updateinfo', function(req, res){//更新用户的信息
	models.Account.updateUser(req.param('qq'), req.param('name'), req.param('value'), function(err){
		if(err == null){
			res.json({"suc":"true"});
			return;
		}
		res.json({"suc":err});
	});
});

router.get('/getuserinfo', function(req, res){//获取用户的公开信息
	models.Account.userInfo(req.param('qq'), function(doc){
			if(doc == null){
				res.json({"suc":"false"});
				return;
			}
			res.json(doc);	
		});
});

router.get('/getuserfriends', function(req, res){//获取用户的公开信息
	models.Account.userFriends(req.param('qq'), function(doc){
			if(doc == null){
				res.json({"suc":"false"});
				return;
			}
			console.log(doc);
			res.json(doc);	
		});
});

router.get('/usermsgs', function(req, res){//获取用户的所有交通信息
	models.Account.userMsgs(req.param('qq'),req.param('skip'), function(doc){
		if(doc == null){
			res.json({"suc":"false"});
			return;
		}
		res.json(doc);	
	});
});

router.post('/addmsg', function(req, res){//添加新的交通信息
	models.Account.addMsg(req.param('qq'), req.param('country'), req.param('province'), req.param('city'), req.param('road'), req.param('tra_level'), req.param('address'),function(err){
		if(err == null){
			res.json({"suc":"true"});
			return;
		}
		res.json({"suc":err});
	});
});

router.get('/getmsgs', function(req, res){//获取上传的交通信息，可分页
	models.Account.allMsgs(req.param('skip'), function(doc){
		if(doc == null){
			res.json({"suc":"false"});
			return;
		}
		res.json(doc);	
	});
});

router.get('/getrecentmsgs', function(req, res){//获取最新上传的交通信息
	console.log(req.param('date'));
	var date = new Date(req.param('date'));
	models.Account.recentMsgs(date,function(doc){
		if(doc == null){
			res.json({"suc":"false"});
			return;
		}
		res.json(doc);	
	});
});

router.get('/addcomment', function(req, res){//获取交通信息的评论，可分页
	models.Account.addComment(req.param('user'),req.param('msg'),req.param('content'),
	function(err){
		if(err == null){
			res.json({"suc":"true"});
			return;
		}
		res.json({"suc":err});
	});
});

router.get('/getcomments', function(req, res){//获取交通信息的评论，可分页
	models.Account.MsgComments(req.param('msg'),req.param('skip'), 
	function(doc){
		console.log(doc);
		if(doc == null){
			res.json({"suc":"false"});
			return;
		}
		res.json(doc);	
	});
});



router.get('/removefriend', function(req, res){//获取相关用户，添加为好友
	models.Account.removeFriend(req.param('qq'), req.param('friend'),
	function(err){
		if(err == null){
			res.json({"suc":"true"});
			return;
		}
		res.json({"suc":err});
	});
});

router.get('/addfriend', function(req, res){//获取相关用户，添加为好友
	models.Account.addFriend(req.param('qq'), function(doc){
		if(doc == null){
			res.json({"suc":"false"});
			return;
		}
		for(var i = 0; i < pool.length; i ++){
				var func = pool[i];
				if(func[0] == doc._id){
					func[1]();
					pool.splice(i, 1);
					break;
				}
		}
		res.json(doc);	
	});
});

router.get('/publishtask', function(req, res){//发布任务
	models.Account.publishTask(req.param('qq'), req.param('content'), function(err){
		if(err==null){
			while(f=pool.shift())f[1]();
			res.json({"suc":"true"});
			return;
		}
		res.json({"suc":"false"});
	});
});

router.get('/gettasks', function(req, res){//获取任务，包括好友请求，好友消息，任务发布等
	models.Account.getTask(req.param('qq'), function(doc){
		if(doc == null){
			pool.unshift([req.param('qq'), function(e){
			  models.Account.getTask(req.param('qq'), function(doc){
				  console.log("开始推送:"+doc);
				  if(doc == null){
						res.json({"suc":"false"});
						return;
					}
					res.json(doc);
			  });
			}]);
			return;
		}
		res.json(doc);	
	});
});

module.exports = router;
