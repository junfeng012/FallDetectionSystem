const express = require('express')
const app = express()
const http = require('http').Server(app);
const io = require('socket.io')(http);

app.use(express.static("public"))
app.use(express.urlencoded({ extended: true }))

app.set('view engine', 'ejs')
app.use(logger)

app.get("/",(req, res) => {
    console.log("welcome from app")
    var WelcomeArray = []
    res.render('Welcome', { WelcomeArray })
})

const welcomeRouter = require('./routes/Welcome')
app.use('/', welcomeRouter)

const registerRouter = require('./routes/Register')
app.use('/', registerRouter)

const homeRouter = require('./routes/Home')
app.use('/', homeRouter)

const dashboardRouter = require('./routes/Dashboard')
app.use('/', dashboardRouter)


const linkedUserRouter = require('./routes/LinkedUser')
app.use('/', linkedUserRouter)

const dataHistoryRouter = require('./routes/DataHistory')
app.use('/', dataHistoryRouter)

const userProfileRouter = require('./routes/UserProfile')
app.use('/', userProfileRouter)

const editProfileRouter = require('./routes/EditProfile')
app.use('/', editProfileRouter)


app.get("/Logout", (req, res) => {
    res.render("Logout")
})

function logger(req, res, next) {
    console.log(req.originalUrl)
    next()
}




http.listen(3000)


module.exports = io;