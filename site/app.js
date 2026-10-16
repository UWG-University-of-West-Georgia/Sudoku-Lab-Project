//setup express to handle routing and rendering
var express = require('express');
var app = express();
app.set('view engine', 'ejs');

//setup bodyparser to extra data from form posts
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true })); 

//setup static public directory (to link images from view pages)
app.use(express.static('public'))

// route to index page
app.get('/', function(req, res) {
    res.render('page/index');
});

// route to documentation page
app.get('/documentation', function(req, res) {
	res.render('page/documentation');
});

// route to quickstart page
app.get('/quickstart', function(req, res) {
	res.render('page/quickstart');
});

// route to features new game page
app.get('/features/new_game', function(req, res) {
	res.render('page/features/new_game');
});

// route to features gameplay page
app.get('/features/gameplay', function(req, res) {
	res.render('page/features/gameplay');
});

// route to features cell validation page
app.get('/features/cell_validation', function(req, res) {
	res.render('page/features/cell_validation');
});

// route to tests page
app.get('/tests', function(req, res) {
	res.render('page/tests');
});

//start the web app
app.listen(8080, function () {
	console.log('App listening on port 8080!');
});

