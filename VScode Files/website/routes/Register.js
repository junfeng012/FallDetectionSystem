const { query } = require('express')
const express = require('express')
const router = express.Router()
const session = require('express-session')
const { Client } = require('pg')
const { Pool } = require('pg')
const Account = require('../class/AccountClass')
const Caretaker = require('../class/CaretakerClass')

router.use(session({
    secret: 'becarefall',
    resave: true,
    saveUninitialized: true
}))

const client = new Client()
const pool = new Pool({
    user: 'postgres',
    host: 'db.eoeeixyzjyunmdtbqgss.supabase.co',
    database: 'postgres',
    password: 'AsisenSosi012@',
    port: 5432,
})

var accountArray = {}
var caretakerArray = {}
var accountId = ""
var caretakerId = ""

function getAccountData(callback) {
    pool.connect((err, client, done) => {
        if (err) {
            console.log(err)
        } else {
            client.query('SELECT * FROM account', (err, res) => {
                done()

                if (err) {
                    console.log(err)
                } else {
                    callback(res);
                }
            })
        }
    })
}
/*
getAccountData(function (res) {

    //assign accountid
    console.log("getAccountData")
    accountArray = res
    var newRow = res.rowCount + 1

    if (newRow < 9) {
        accountId = String("AC00" + newRow)
    }
    else if (newRow < 99) {
        accountId = String("AC0" + newRow)
    }
    else {
        accountId = String("AC" + newRow)
    }

    // accountId = res.rows[1].accountid;
    // console.log(accountArray.rows[1].accountid)
})
*/

function getCaretakerData(callback) {
    pool.connect((err, client, done) => {
        if (err) {
            console.log(err)
        } else {
            client.query('SELECT * FROM caretaker', (err, res) => {
                done()

                if (err) {
                    console.log(err)
                } else {
                    callback(res);
                }
            })
        }
    })
}

/*
getCaretakerData(function (res) {

    //assign caretakerid
    caretakerArray = res
    var newRow = res.rowCount + 1

    if (newRow < 9) {
        caretakerId = String("CT00" + newRow)
    }
    else if (newRow < 99) {
        caretakerId = String("CT0" + newRow)
    }
    else {
        caretakerId = String("CT" + newRow)
    }
})
*/

router.route("/Register")
    .get((req, res) => {
        console.log("welcome from router(Register)")

        getAccountData(function (res) {

            //assign accountid
            console.log("getAccountData")
            accountArray = res
            var newRow = res.rowCount + 1
        
            if (newRow < 10) {
                accountId = String("AC00" + newRow)
            }
            else if (newRow < 100) {
                accountId = String("AC0" + newRow)
            }
            else {
                accountId = String("AC" + newRow)
            }
        
            // accountId = res.rows[1].accountid;
            // console.log(accountArray.rows[1].accountid)
        })

        getCaretakerData(function (res) {

            //assign caretakerid
            console.log("getCaretakerData")
            caretakerArray = res
            var newRow = res.rowCount + 1
        
            if (newRow < 9) {
                caretakerId = String("CT00" + newRow)
            }
            else if (newRow < 99) {
                caretakerId = String("CT0" + newRow)
            }
            else {
                caretakerId = String("CT" + newRow)
            }
        })


        var RegisterArray = []
        res.render('Register', { RegisterArray })
    })
    .post((req, res) => {

        console.log(accountArray)
        console.log(caretakerArray)

        
        var username = req.body.fullName
        username = String(username)
        var usernameLength = username.length
        var phone = req.body.contactNumber
        phone = String(phone)
        var email = req.body.email
        email = String(email)
        var password = req.body.password
        password = String(password)
        var confirmpassword = req.body.confirmPassword
        confirmpassword = String(confirmpassword)
        var RegisterArray = [username, phone, email]
        

        if (!username || /\s/.test(username) || !phone || /\s/.test(phone) || !email || /\s/.test(email) || !password || /\s/.test(password) || !confirmpassword || /\s/.test(confirmpassword)) {
            res.render('Register', { RegisterArray })
        }
        else if (password != confirmpassword) {
            res.render('Register', { RegisterArray })
        }
        else {  

            var usernameTaken = false
            for (let i = 0; i < accountArray.rowCount; i++) {
                console.log(usernameTaken)
                var usernameSub = accountArray.rows[i].username
                if (usernameSub.substring(0,usernameLength) == username) {
                    usernameTaken = true
                    res.render('Register', {RegisterArray})
                    break
                }
                else {
                    usernameTaken = false
                    continue
                }
            }
            
            if (usernameTaken == false) {
                console.log("usernameTaken = " + usernameTaken)
                let caretaker = new Caretaker(caretakerId, accountId, "", username, email, phone, "yes", "no")
                let account = new Account(accountId, username, password)
                req.session.account = account
                req.session.caretaker = caretaker

                pool.connect((err, client, done) => {
                    if (err) {
                        console.log(err)
                    }
                    else {
                        client.query('INSERT INTO account VALUES ($1, $2, $3)',
                            [account.accountid, account.username, account.password], (err, res) => {

                                done()

                                if (err) {
                                    console.log(err)
                                } else {
                                    console.log('success create account')
                                }
                            })
                    }
                })

                pool.connect((err, client, done) => {
                    if (err) {
                        console.log(err)
                    }
                    else {
                        client.query(
                            'INSERT INTO caretaker VALUES ($1, $2, $3, $4, $5, $6, $7, $8)',
                            [caretaker.caretakerid,
                            caretaker.accountid,
                            caretaker.userid,
                            caretaker.name,
                            caretaker.email,
                            caretaker.phone,
                            caretaker.availability,
                            caretaker.priority], (err, result) => {

                                done()

                                if (err) {
                                    console.log(err)
                                } else {
                                    console.log('success create caretaker')
                                }
                            })
                    }
                })

                res.render('CreateSuccess')
            }
        }
    })

module.exports = router



