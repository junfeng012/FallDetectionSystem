const express = require('express')
const router = express.Router()
const session = require('express-session')
const Caretaker =  require('../class/CaretakerClass')
const Users =  require('../class/UsersClass')
const { Pool } = require('pg')

var selectedCaretaker = {}
var selectedUsers = {}

router.use(session({
    secret: 'becarefall',
    resave: true,
    saveUninitialized: true
}))

const pool = new Pool({
    user: 'postgres',
    host: 'db.eoeeixyzjyunmdtbqgss.supabase.co',
    database: 'postgres',
    password: 'AsisenSosi012@',
    port: 5432,
})



router.route("/Home")
.get((req, res) => {
    console.log("welcome from router(Home)")
    
    var account = req.session.account
    var caretaker = req.session.caretaker
    console.log(account.accountid)
    console.log(caretaker)
    
    console.log('SELECT * FROM caretaker WHERE caretakerid =\''+caretaker.caretakerid+'\'')

    var newCaretakerUID;

    function getUsersData(callback) {
        pool.connect((err, client, done) => {
            if (err) {
                console.log(err)
            } else {
                console.log('SELECT * FROM users WHERE userid =\''+newCaretakerUID+'\'')
                client.query('SELECT * FROM users WHERE userid =\''+newCaretakerUID+'\'', (err, res) => {
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
    
    function getCaretakerData(callback) {
        pool.connect((err, client, done) => {
            if (err) {
                console.log(err)
            } else {
                client.query('SELECT * FROM caretaker WHERE caretakerid =\''+caretaker.caretakerid+'\'', (err, res) => {
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

    getCaretakerData(function (result) {

        //assign caretakerid
        console.log("getCaretakerData(Home)")
        selectedCaretaker = result
        //console.log(selectedCaretaker.rows[0].caretakerid)

        var newCaretakerID=selectedCaretaker.rows[0].caretakerid;
        var newCaretakerAID=selectedCaretaker.rows[0].accountid;
        newCaretakerUID=selectedCaretaker.rows[0].userid;
        var newCaretakerName=selectedCaretaker.rows[0].name;
        var newCaretakerEmail=selectedCaretaker.rows[0].email;
        var newCaretakerPhone=selectedCaretaker.rows[0].phone;
        var newCaretakerAvailability=selectedCaretaker.rows[0].availability;
        var newCaretakerPriority=selectedCaretaker.rows[0].priority;

        var newCaretaker = new Caretaker(newCaretakerID,newCaretakerAID,newCaretakerUID,newCaretakerName,newCaretakerEmail,newCaretakerPhone,newCaretakerAvailability,newCaretakerPriority)
        req.session.caretaker = newCaretaker
        req.session.account = account;
        if (newCaretakerUID != "    ")
        {
            console.log("userid is not empty")
            getUsersData(function (result) {

                //assign accountid
                console.log("getUsersData(Home)")
                selectedUsers = result
                //console.log(selectedUsers.rows[0].userid)

                var newUserID=selectedUsers.rows[0].userid;
                var newUserAID=selectedUsers.rows[0].accountid;
                var newUserName=selectedUsers.rows[0].name;
                var newUserPhone=selectedUsers.rows[0].phone;
                var newUserStatus=selectedUsers.rows[0].status;
                var newUserAddress=selectedUsers.rows[0].address;
            
                var newUser=new Users(newUserID,newUserAID,newUserName,newUserPhone,newUserStatus,newUserAddress);
                console.log(newUser)
                req.session.users = newUser
                res.render("Home")
            })
        }
        else
        {
            res.render("Home")
        }
    })





})

.post((req, res) => {
    res.redirect("/Dashboard")
})
module.exports = router