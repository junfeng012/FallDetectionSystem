console.log("test");
// npm install express
const express = require('express');
//npm install pg
const { Client }  = require('pg')

const client = new Client({
    user: 'postgres',
    host: 'db.eoeeixyzjyunmdtbqgss.supabase.co',
    database: 'postgres',
    password: 'AsisenSosi012@',
    port: 5432,
  })
  client.connect()

  client.query('SELECT * FROM account', (err, result) => {
    if (err) {
        console.log(err);
    } else {
        console.log(result.rows);
        console.log(result.rows[1]);
        console.log(result.rows[1].accountid);
    }
});
