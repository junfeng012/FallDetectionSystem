const express = require('express')
const router = express.Router()
const session = require('express-session')
const admin = require('firebase-admin');



let socket;
let socketInterval;




var userArray = {}
var userIDSub;
var userNameSub;
var userPhoneSub;



var serviceAccount = require("becarefall-83c57-firebase-adminsdk-1h638-95859f7244.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://becarefall-83c57-default-rtdb.asia-southeast1.firebasedatabase.app"
});

let counting = null;
var counter = 1;
var snapCounter =0;

var startCounting = () => {
  counting = setInterval(() => {
      counter += 1;
      console.log(counter)
      if (counter === 5) {
          clearInterval(counting);
          console.log("Interval stopped.  Offline");
          online = false;
      }
  }, 1000);
};

router.use(session({
    secret: 'becarefall',
    resave: true,
    saveUninitialized: true
}))

router.route("/Dashboard")
.get((req, res) => {
    console.log("welcome from router(Dashboard)")
    //res.render("Dashboard")
    var account = req.session.account
    var caretaker = req.session.caretaker
    var users = req.session.users
    console.log(account.accountid)
    console.log(caretaker.caretakerid)
    console.log(users.userid)
  
 
    
    if(caretaker.userid == "    ")
    {
      res.render("Dashboard",{userName:"no linked user",userPhone:" "})
      userIDSub = "    "
      userNameSub ="no linked user"
      userPhoneSub =" "
    }
    else
    {
      
      userIDSub = users.userid
      console.log(userIDSub)
      userNameSub = users.name
      userPhoneSub = users.phone


      res.render("Dashboard",{userName:userNameSub,userPhone:userPhoneSub})

    }


    const db = admin.database();
    const accelerometerRef = db.ref("accelerometer");
    console.log(caretaker.userid)

    var userIDRef;
    if(caretaker.userid == "    ")
    {
      userIDRef = accelerometerRef.child("noUser");
    }
    else
    {
      userIDRef = accelerometerRef.child(caretaker.userid);
    }
    

    var dashbaord;
    var online = false;

    snapCounter=0;
    var ValueArray =[];
    var maxValue=0;
    var onlineCounter =0;
    clearInterval(counting);

    const io = require('../server');



    userIDRef.on("value", (snapshot) => {
        //console.log(snapshot.val());
        dashbaord =snapshot.val();
        snapCounter +=1;
        //console.log(dashbaord.value)
        //console.log("snapCounter:"+snapCounter)
        if (online == false && snapCounter >=2)
        {
          snapCounter=0;
          startCounting();
          online = true;
          
          
        }
        
        ValueArray.push(dashbaord.value)
        counter = 0;

      });

    
      let testcounter =0;
      
      //if(socket) socket.disconnect();
      
      io.on('connection', (_socket) => {
        if(socketInterval) clearInterval(socketInterval);
        socket = _socket;
        
        socketInterval = setInterval(() => {
          ValueArray.push(0)
          maxValue = Math.max(...ValueArray);
          console.log(ValueArray)
          console.log(maxValue)
          if(maxValue==0)
          {
            
            onlineCounter +=1;
    

            if(onlineCounter >5)

            {
              online=false;
            }
          }
          else if(maxValue>0)
          {
            onlineCounter = 0;
          }

       

          if(online)
          {
            console.log(online)
            _socket.emit('new data', [maxValue,online]);
            ValueArray = [];
            
          }
          else
          {
            console.log(online)
            _socket.emit('new data', [0,online]);
            ValueArray = [];
            
          }
          testcounter +=1;
          console.log(testcounter)
        }, 1000);
      });

})
.post((req, res) => {
  var caretaker = req.session.caretaker
  if(caretaker.userid != "    ")
  {
    const db = admin.database();
    const fallRequestRef = db.ref("fallRequest");
    const key =caretaker.caretakerid;
    fallRequestRef.child(key).set({
      request: "true",
      userid: caretaker.userid,
      caretakername:caretaker.name,
      caretakerphone:caretaker.phone
    }, function(error) {
      if (error) {
        console.error("Data could not be saved." + error);
      } else {
        console.log("Data saved successfully. caretakerid:" +key);
      }
    });

  }
  res.render("Dashboard",{userName:userNameSub,userPhone:userPhoneSub})
})


module.exports = router