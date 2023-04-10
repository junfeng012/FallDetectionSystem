class ContactRecord {
    constructor(contactrecordid, fallrecordid, userid, name, phone, time, pickup){
        this.contactrecordid = contactrecordid;
        this.fallrecordid = fallrecordid;
        this.userid = userid;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.pickup = pickup;
    }
    getContactRecordId(){
        return this.contactrecordid;
    }
    getFailRecordId(){
        return this.fallrecordid;
    }
    getUserId(){
        return this.userid;
    }
    getName(){
        return this.name;
    }
    getPhone(){
        return this.phone;
    }
    getTime(){
        return this.time;
    }
    getPickup(){
        return this.pickup;
    }
}
module.exports = ContactRecord;