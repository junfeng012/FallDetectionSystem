const express = require('express')
const router = express.Router()
const session = require('express-session')
const { Pool } = require('pg')

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

function getDataHistory(callback) {
    pool.connect((err, client, done) => {
        if (err) {
            console.log(err)
        } else {
            client.query('SELECT b.userid, b.name, a.date, a.time, a.emergencycall FROM fallrecord a JOIN users b ON a.userid = b.userid ORDER BY a.date DESC', (err, res) => {
                done()

                if (err) {
                    console.log(err)
                } else {
                    callback(res)
                }
            })
        }
    })
}

router.route("/DataHistory")
.get((req, res) => {
    var caretaker = req.session.caretaker
    var userid = caretaker.userid
    userid = String(userid)
    var useridLength = userid.length
    var DataHistoryArray = []
    getDataHistory(function (result) {
        for (let i = 0; i < result.rowCount; i++) {
            var useridSub = result.rows[i].userid
            if (useridSub.substring(0,useridLength) == userid) {
                DataHistoryArray.push(result.rows[i])
            }
        }
        res.render("DataHistory", { DataHistoryArray })
    })
})

module.exports = router