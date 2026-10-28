const sqlite3 = require('sqlite3').verbose();
const { loginUsers, registerUsers } = require('./auth');

describe('Testing authentication and registration logic', () => {
    let db;

    beforeEach((done) => {
        db = new sqlite3.Database(':memory:', (err) => {
            if (err) return done(err);
            db.run('CREATE TABLE Users (USERNAME TEXT PRIMARY KEY, PASSWORD TEXT)', done);
        });
    });

    afterEach((done) => {
        db.close(done);
    });

    test('New user registered successfully', (done) => {
        registerUsers(db, 'raphael', 'mypassword', (err, result) => {
            expect(err).toBeNull();
            expect(result.success).toBe(true);
            done();
        });
    });

    test('Registration fails if the username already exists', (done) => {
        registerUsers(db, 'raphael', 'mypassword', () => {
            registerUsers(db, 'raphael', 'mypassword', (err, result) => {
                expect(result.success).toBe(false);
                done();
            });
        });
    });

    test('Login successful with valid credentials', (done) => {
        registerUsers(db, 'raphael', 'mypassword', () => {
            loginUsers(db, 'raphael', 'mypassword', (err, result) => {
                expect(err).toBeNull();
                expect(result.success).toBe(true);
                expect(result.username).toBe('raphael');
                done();
            });
        });
    });

    test('Login failed due to an incorrect password or unknown user', (done) => {
        loginUsers(db, 'raph', 'mypass', (err, result) => {
            expect(err).toBeNull();
            expect(result.success).toBe(false);
            done();
        });
    });
});