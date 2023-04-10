class Caretaker {
    constructor(caretakerid, accountid, userid, name, email, phone, availability, priority) {
        this.caretakerid = caretakerid;
        this.accountid = accountid;
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.availability = availability;
        this.priority = priority;
    }
    getCaretakerId(){
        return this.caretakerid;
    }
    getAccountId(){
        return this.accountid;
    }
    getUserId(){
        return this.userid;
    }
    getName(){
        return this.name;
    }
    getEmail(){
        return this.email;
    }
    getPhone(){
        return this.phone;
    }
    getAvailability(){
        return this.availability;
    }
    getPriority(){
        return this.priority;
    }
}
module.exports = Caretaker;