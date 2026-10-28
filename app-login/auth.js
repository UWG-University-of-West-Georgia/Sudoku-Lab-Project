function loginUsers(db, username, password, callback) {
    db.get('SELECT * FROM Users WHERE USERNAME = ? AND PASSWORD = ?', [username, password], (err, row) => {
        if (err) {
            return callback(err, null);
        }
        if (row) {
            return callback(null, { success: true, username: row.USERNAME });
        } else {
            return callback(null, { success: false, message: 'Invalid username or password' });
        }
    });
}

function registerUsers(db, username, password, callback) {
    db.run('INSERT INTO Users(USERNAME, PASSWORD) VALUES (?, ?)', [username, password], function(err) {
        if (err) {
            return callback(err, { success: false, message: 'Error : This username already exists.' });
        }
        return callback(null, { success: true });
    });
}

module.exports = { loginUsers, registerUsers }