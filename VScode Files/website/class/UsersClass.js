class Users {
    constructor(userid, accountid, name, phone, status, address) {
        this.userid = userid;
        this.accountid = accountid;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.address = address;
    }
    getUserId(){
        return this.userid;
    }
    getAccountId(){
        return this.accountid;
    }
    getName(){
        return this.name;
    }
    getPhone(){
        return this.phone;
    }
    getStatus(){
        return this.status;
    }
    getAddress(){
        return this.address;
    }
}
module.exports = Users;