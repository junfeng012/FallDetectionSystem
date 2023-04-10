const express = require('express')
const router = express.Router()
const session = require('express-session')
const { Pool } = require('pg')
const Account = require('../class/AccountClass')
const Caretaker = require('../class/CaretakerClass')

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
    saveUninitialized: true,
}))

const pool = new Pool({
    user: 'postgres',
    host: 'db.eoeeixyzjyunmdtbqgss.supabase.co',
    database: 'postgres',
    password: 'AsisenSosi012@',
    port: 5432,
})

var EditProfileArray = []

router.route("/EditProfile")
.get((req, res) => {
    console.log("welcome from router(EditProfile)")
    var account = req.session.account
    var caretaker = req.session.caretaker
    console.log(account.accountid)
    console.log(caretaker.caretakerid)

    //console.log(trimString("123 2    ") +"---:test TrimString")

    EditProfileArray = [caretaker.name, caretaker.phone, caretaker.email, caretaker.caretakerid]
    res.render('EditProfile', { EditProfileArray })
})

router.post("/EditProfile", (req, res) => {
    var username = req.body.userName
    var phone = req.body.contactNumber
    var email = req.body.email
    var password = req.body.newPassword
    var confirmpassword = req.body.confirmNewPassword

    EditProfileArray = [username, phone, email, password, confirmpassword]
    username = trimString(username)
    phone = trimString(phone)
    email = trimString(email)
    password = trimString(password)
    confirmpassword = trimString(confirmpassword)

    for(let i = 0 ; i <EditProfileArray.length ; i++)
    {
        EditProfileArray[i] = trimString(EditProfileArray[i])
    }
    console.log(EditProfileArray)
    
    if (!username || /\s/.test(username) || !phone || /\s/.test(phone) || !email || /\s/.test(email)) {
        console.log("empty profile info")
        res.render('EditProfile', { EditProfileArray })
    }
    else if (password != confirmpassword) {
        console.log("wrong password")
        res.render('EditProfile', { EditProfileArray })
    }
    else {
        var account = req.session.account
        var caretaker = req.session.caretaker

        pool.connect((err, client, done) => {
            if (err) {
                console.log(err)
            }
            else {
                client.query(
                    'UPDATE caretaker SET name = $1, phone = $2, email = $3 WHERE caretakerid = $4',
                    [EditProfileArray[0],
                    EditProfileArray[1],
                    EditProfileArray[2],
                    caretaker.caretakerid], (err, result) => {

                        done()

                        if (err) {
                            console.log(err)
                        } else {
                            console.log('successfully update caretaker')
                        }
                    })
            }
        })

        var newCaretaker = new Caretaker(caretaker.caretakerid,caretaker.accountid,caretaker.userid,EditProfileArray[0],EditProfileArray[2],EditProfileArray[1],caretaker.availability,caretaker.priority)
        console.log(newCaretaker)
        req.session.caretaker = newCaretaker

        if (!password || /\s/.test(password) || !confirmpassword || /\s/.test(confirmpassword)) {
            res.render('ChangeSaved')
        }
        else {
            pool.connect((err, client, done) => {
                if (err) {
                    console.log(err)
                }
                else {
                    client.query(
                        'UPDATE account SET password = $1 WHERE accountid = $2',
                        [EditProfileArray[3],
                        account.accountid], (err, result) => {
    
                            done()
    
                            if (err) {
                                console.log(err)
                            } else {
                                console.log('successfully update caretaker')
                            }
                        })
                }
            })

            var newAccount = new Account(account.accountid,account.username,EditProfileArray[3])
            console.log(newAccount)
            req.session.account = newAccount

            res.render('ChangeSaved')
        }
        
    }
})

module.exports = router