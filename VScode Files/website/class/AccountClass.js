class Account {
    constructor(accountid, username, password) {
        this.accountid = accountid;
        this.username = username;
        this.password = password;
    }
    getAccountId(){
        return this.accountid;
    }
    getUsername(){
        return this.username;
    }
    getPassword(){
        return this.password;
    }
}
module.exports = Account;