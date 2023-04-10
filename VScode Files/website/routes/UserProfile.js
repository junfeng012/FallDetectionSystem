const express = require('express')
const router = express.Router()
const session = require('express-session')

router.use(session({
    secret: 'becarefall',
    resave: true,
    saveUninitialized: true
}))

router.route("/UserProfile")
.get((req, res) => {
    console.log("welcome from router(UserProfile)")

    var account = req.session.account
    var caretaker = req.session.caretaker
    console.log(account.accountid)
    console.log(caretaker.caretakerid)
    console.log(caretaker.name)
    console.log(caretaker.phone)
    console.log(caretaker.email)


    var CaretakerProfileArray = []
    CaretakerProfileArray = [caretaker.caretakerid, caretaker.name, caretaker.phone, caretaker.email]
    res.render('UserProfile', { CaretakerProfileArray })
})

router.post("/UserProfile",(req,res) => {
    res.redirect("/EditProfile");
});

module.exports = router