const express = require('express')
const router = express.Router()
const session = require('express-session')

router.use(session({
    secret: 'becarefall',
    resave: true,
    saveUninitialized: true
}))

router.route("/LinkedUser")
.get((req, res) => {
    console.log("welcome from router(LinkedUser)")
    var account = req.session.account
    var users = req.session.users
    console.log(account.accountid)
    console.log(users.userid)
    console.log(users.name)
    console.log(users.phone)
    console.log(users.status)
    console.log(users.address)

    var UserProfileArray = []
    UserProfileArray = [users.userid, users.name, users.phone, users.status,users.address]
    res.render('LinkedUser', { UserProfileArray })
})

module.exports = router