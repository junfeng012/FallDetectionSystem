const express = require('express')
const router = express.Router()
const session = require('express-session')
const { Client } = require('pg')
const { Pool } = require('pg')
const Account =  require('../class/AccountClass')
const Caretaker =  require('../class/CaretakerClass')
const Users =  require('../class/UsersClass')

function trimString(str)
{
    var temp_str;
    var char_str;
    var next_char;
    var next =0;

    temp_str=""

    for(let i = 0 ;i<str.length-1 ;i++)
    {
        char_str = str.substring(i, i + 1);
        next_char = str.substring(i+1,i+2);

        if(char_str == " " && next_char != " ")
        {
            next+=1;
        }

    }

    for (let i = 0 ;i<str.length ;i++) {
        char_str = str.substring(i, i + 1);


        if (char_str != " ")
        {
            temp_str = temp_str+ char_str;
        }
        else if (char_str == " " && next >0)
        {
            temp_str = temp_str+" ";
            next -=1;
        }

    }

    return temp_str;

}

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

var accountArray = {}
var caretakerArray = {}
var usersArray = {}
var accountId = ""
var caretakerId = ""
var userId = ""

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

getAccountData(function (res) {

    //assign accountid
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

function getUsersData(callback) {
    pool.connect((err, client, done) => {
        if (err) {
            console.log(err)
        } else {
            client.query('SELECT * FROM users', (err, res) => {
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

getUsersData(function (res) {

    //assign accountid
    console.log("getUsersData(Beginning)")
    usersArray = res
})

getAccountData(function (res) {

    //assign accountid
    console.log("getAccountData(Beginning)")
    accountArray = res
})

getCaretakerData(function (res) {

    //assign caretakerid
    console.log("getCaretakerData(Beginning)")
    caretakerArray = res
})


router.route("/Welcome")
.get((req, res) => {
    console.log("welcome from router(Welcome)")

    getAccountData(function (res) {

        //assign accountid
        console.log("getAccountData(Welcome)")
        accountArray = res
    })

    getCaretakerData(function (res) {

        //assign caretakerid
        console.log("getCaretakerData(Welcome)")
        caretakerArray = res
    })

    getUsersData(function (res) {

        //assign accountid
        console.log("getUsersData(Beginning)")
        usersArray = res
    })

    let account = req.session.account
    var WelcomeArray = []
    try{
        var count = Object.keys(account).length
        if (count > 0){
            WelcomeArray = [account.username]
        }
    }
    catch(err) {
        console.log(err.message)
    }
    res.render('Welcome', { WelcomeArray })
})
.post((req, res) => {
    var id = req.body.id
    id = String(id)
    var idLength = id.length

    var password = req.body.password
    password = String(password)
    var passwordLength = password.length

    var accountID;
    var caretakerID;
    var userID;
    var validUser = false;
    var chosenAccountIndex;
    var chosenCaretakerIndex;
    var chosenUserIndex;

    var account;
    var caretaker;
    var users

    console.log(id)
    console.log(password)
    //console.log(accountArray)
    //console.log(caretakerArray)

    if(idLength>0 && passwordLength>0)
    {

        for (let i = 0; i < accountArray.rowCount; i++) {
            var accountIDSub = accountArray.rows[i].accountid
            var usernameSub = accountArray.rows[i].username
            var passwordSub = accountArray.rows[i].password

            accountIDSub = trimString(accountIDSub)
            usernameSub = trimString(usernameSub)
            passwordSub = trimString(passwordSub)

            //console.log(accountIDSub)
            //console.log(usernameSub)
            //console.log(passwordSub)

            if(usernameSub==id 
                && passwordSub==password
                && accountIDSub.substring(0,2)=="AC")
            {
                accountID = accountIDSub;
                validUser = true;
                chosenAccountIndex = i; 

                account = new Account(accountIDSub,id,password)
                
            }

        }
    }

    if(validUser)
    {
        for (let i = 0; i < caretakerArray.rowCount; i++)
        {
            var caretakerIDSub = caretakerArray.rows[i].caretakerid
            var accountIDSub = caretakerArray.rows[i].accountid
            var userIDSub=caretakerArray.rows[i].userid
            var nameSub=caretakerArray.rows[i].name
            var emailSub=caretakerArray.rows[i].email
            var phoneSub=caretakerArray.rows[i].phone
            var availabilitySub=caretakerArray.rows[i].availability
            var prioritySub = caretakerArray.rows[i].priority

            if(accountIDSub ==accountID)
            {
                userID = userIDSub
                caretakerID = caretakerIDSub
                chosenCaretakerIndex = i
                console.log("CaretakerID found , ID =" + caretakerID )
                caretaker = new Caretaker(caretakerIDSub,accountIDSub,userIDSub,nameSub,emailSub,phoneSub,availabilitySub,prioritySub)
            }
        }
    }

    if(validUser)
    {
        for (let i = 0; i < usersArray.rowCount; i++)
        {
            var UuserIDSub = usersArray.rows[i].userid
            var UaccountIDSub = usersArray.rows[i].accountid
            var UnameSub=usersArray.rows[i].name
            var UphoneSub=usersArray.rows[i].phone
            var UstatusSub=usersArray.rows[i].status
            var UaddressSub=usersArray.rows[i].address


            if(UuserIDSub ==userID)
            {

                chosenUserIndex = i
                console.log("userID found , ID =" + userID )
                users = new Users(UuserIDSub,UaccountIDSub,UnameSub,UphoneSub,UstatusSub,UaddressSub)
                break
            }
            else {

            var UuserIDSub = ""
            var UaccountIDSub = ""
            var UnameSub= "No Linked User"
            var UphoneSub= ""
            var UstatusSub= ""
            var UaddressSub= ""
            console.log("userID not found , ID =" + userID )
            users = new Users(UuserIDSub,UaccountIDSub,UnameSub,UphoneSub,UstatusSub,UaddressSub)

            }
        }
    }

    var WelcomeArray = [id,password]

    if (validUser){
        console.log(accountID)
        //pass account and caretaker class into next page using session
        req.session.account = account
        req.session.caretaker = caretaker
        req.session.users = users
        res.redirect("/Home")
    
    }
    else{
        res.render("Welcome", { WelcomeArray })
    }
 
})


module.exports = router