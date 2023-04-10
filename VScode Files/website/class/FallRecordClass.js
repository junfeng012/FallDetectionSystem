class FallRecord {
    constructor(){
        this.fallrecordid = fallrecordid;
        this.userid = userid;
        this.date = date;
        this.time = time;
        this.emergencycall = emergencycall;
    }
    getFallRecordId(){
        return this.fallrecordid;
    }
    getUserId(){
        return this.userid;
    }
    getDate(){
        return this.date;
    }
    getTime(){
        return this.time;
    }
    getEmergencyCall(){
        return this.emergencycall;
    }
}
module.exports = FallRecord;