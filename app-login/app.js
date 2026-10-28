const express = require('express');
const path = require('path');
const crypto = require('crypto');
const sqlite3 = require('sqlite3').verbose();
const session = require('express-session');
const { register } = require('module');
const { loginUsers, registerUsers } = require('./auth');

const dbPath = path.join(__dirname, 'database.db');
const db = new sqlite3.Database(dbPath, (err) => {
    if (err) {
        console.error('Database connection error:', err.message);
        process.exit(1);
    }
    console.log('Connected to the SQLite database.');
});

db.run('CREATE TABLE IF NOT EXISTS Users (USERNAME TEXT PRIMARY KEY, PASSWORD TEXT)', (err) => {
    if (err) {
        console.error('Table creation error:', err.message);
    }
});

var app = express();
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views', 'page'));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use(express.static(path.join(__dirname, 'public')));
app.use(session({
    secret: process.env.SESSION_SECRET || crypto.randomBytes(32).toString('hex'),
    resave: false,
    saveUninitialized: false
}));

app.get('/', function (req, res) {
    res.render('login');
});

app.get('/register', function (req, res) {
    res.render('register');
});

app.get('/landing', function (req, res) {
    if (!req.session.username) {
        return res.redirect('/');
    }
    res.render('landing', { username: req.session.username });
});

app.post('/login', function (req, res) {
    const { username, password } = req.body;
    loginUsers(db, username, password, (err, result) => {
        if (err) {
            console.error('Query error:', err.message);
            return res.status(500).send('Error logging in');
        }
        if (result.success) {
            req.session.username = result.username;
        }
        res.json(result);
    });
});

app.post('/register', function (req, res) {
    const { username, password } = req.body;
    registerUsers(db, username, password, (err, result) => {
        if (err && !result.success) {
            console.error('Insertion error:', err.message);
        }
        res.json(result);
    });
});

if (require.main === module) {
    app.listen(8080, function () {
        console.log('App listening on port 8080!');
    });
}