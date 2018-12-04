package com.example.alienware.project;

public class ServicesTime {

    private String id;
    private String week;
    private String startTimeHour;
    private String startTimeMin;
    private String endTimeHour;
    private String endTimeMin;



    public ServicesTime(){

    }
    public ServicesTime(String id,String week,String startTimeHour,String startTimeMin,String endTimeHour,String endTimeMin){
        this.id=id;
        this.week=week;
        this.startTimeHour=startTimeHour;
        this.startTimeMin=startTimeMin;
        this.endTimeHour=endTimeHour;
        this.endTimeMin=endTimeMin;

    }
    public String getId() {

        return id;
    }
    public String getWeek() {

        return week;
    }
    public String getStartTimeHour() {

        return startTimeHour;
    }
    public String getStartTimeMin() {

        return startTimeMin;
    }
    public String getEndTimeHour() {

        return endTimeHour;
    }
    public String getEndTimeMin() {

        return endTimeMin;
    }


}
