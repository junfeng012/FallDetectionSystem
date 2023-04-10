// Import the functions you need from the SDKs you need
const express = require('express');
const admin = require('firebase-admin');
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries


var serviceAccount = require("becarefall-83c57-firebase-adminsdk-1h638-95859f7244.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://becarefall-83c57-default-rtdb.asia-southeast1.firebasedatabase.app"
});

const db = admin.database();
const accelerometerRef = db.ref("accelerometer");
const userIDRef = accelerometerRef.child("U001");
var test;
var online = false;
var counter = 1;

let counting = null;

let startCounting = () => {
  counting = setInterval(() => {
      counter += 1;
      console.log(counter)
      if (counter === 10) {
          clearInterval(counting);
          console.log("Interval stopped.  Offline");
          online = false;
      }
  }, 1000);
};

userIDRef.on("value", (snapshot) => {
    //console.log(snapshot.val());
    test =snapshot.val();
    console.log(test.value)
    if (online == false)
    {
       startCounting();
    }
    online = true;
    counter = 0;

  });



