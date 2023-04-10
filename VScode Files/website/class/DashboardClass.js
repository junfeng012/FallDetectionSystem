class Dashboard {
    constructor(dashboardid, contactrecordid, fallrecordid, userid){
        this.dashboardid = dashboardid;
        this.contactrecordid = contactrecordid;
        this.fallrecordid = fallrecordid;
        this.userid = userid;
    }
    getDashboardId(){
        return this.dashboardid;
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
}
module.exports = Dashboard;