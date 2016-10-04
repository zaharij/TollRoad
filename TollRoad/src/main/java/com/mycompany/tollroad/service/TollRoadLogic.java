/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tollroad.service;

import static com.mycompany.tollroad.constants.TollRoadConstants.*;
import com.mycompany.tollroad.daopack.TollRoadDao;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * TollRoadLogic
 * @author Zakhar
 */
public class TollRoadLogic {
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS";
    public static final int TO_GET_MINUTES_CONST = 60000;
    public static final int MINUTES_IN_HOUR_CONST = 60;
    public static final int FIRST_ARRAY_ELEM = 0;
    public static final int SECOND_ARRAY_ELEM = 1;
    public static final int DEFAULT_VALUE_NUMBER = 0;
    public static final String TIME_SEPARATOR = ":";
    
    private Pattern idPattern = Pattern.compile(ID_REG_EX);
    private Pattern checkpointNumberPattern = Pattern.compile(CHECKPOINT_NUMBER_REG_EX);
    private TollRoadDao dao = new TollRoadDao();
    
    /**
     * inputs user's id number and the number of the checkpoint to the db, using dao
     * @param inputedLine - inputed info
     */
    public void startDriving(String inputedLine){
        dao.createDriverData(getUserIdFromInput(inputedLine));
        dao.updateDriverData(getUserIdFromInput(inputedLine), CHECKPOINTS_PASSED_COLLECTION, getCheckpointNumberFromInput(inputedLine));
        dao.updateDriverData(getUserIdFromInput(inputedLine), DATETIME_IN, getCurrentTime().toString());
    }
    
    /**
     * inputs new checkpoint number to the db, using dao
     * @param inputedLine - inputed info
     */
    public void transferDriving(String inputedLine){
        dao.updateDriverData(getUserIdFromInput(inputedLine), CHECKPOINTS_PASSED_COLLECTION, getCheckpointNumberFromInput(inputedLine));
    }
    
    /**
     * gets driving time, price, kilometers
     * @param inputedLine - inputed info
     */
    public void endDriving(String inputedLine){
        transferDriving(inputedLine);
        String drivingTime = getDrivingTimeStr(inputedLine);       
        double money = getPrice(inputedLine);
        double drivedKm = money/dao.getPricePerKm();
        dao.deleteDriverData(getUserIdFromInput(inputedLine));
    }

    /**
     * returns price
     * @param inputedLine - inputed info
     * @return double
     * @throws NumberFormatException 
     */
    private double getPrice(String inputedLine) throws NumberFormatException {
        double money = DEFAULT_VALUE_NUMBER;
        ArrayList<ArrayList> graphMatrix = dao.getCheckpointsMatrix();
        ArrayList passedCheckpoints = dao.getPassedCheckpoints(getUserIdFromInput(inputedLine));
        int matrixIndexFirst = DEFAULT_VALUE_NUMBER;
        int matrixIndexSecond = DEFAULT_VALUE_NUMBER;
        for(int indexFirst = FIRST_ARRAY_ELEM, indexSecond = SECOND_ARRAY_ELEM; indexSecond < passedCheckpoints.size(); indexFirst++, indexSecond++){
            matrixIndexFirst = (int) passedCheckpoints.get(indexFirst) - SECOND_ARRAY_ELEM;
            matrixIndexSecond = (int) passedCheckpoints.get(indexSecond) - SECOND_ARRAY_ELEM;
            money += (double) graphMatrix.get(matrixIndexFirst).get(matrixIndexSecond);
        }
        return money;
    }

    /**
     * returns driving time
     * @param inputedLine - inputed info
     * @return String
     * @throws NumberFormatException 
     */
    private String getDrivingTimeStr(String inputedLine) throws NumberFormatException {
        Timestamp endTime = getCurrentTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(dao.getStartTime(getUserIdFromInput(inputedLine)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp startTime = new java.sql.Timestamp(parsedDate.getTime());
        long allTimeInMinutes = (endTime.getTime() - startTime.getTime())/TO_GET_MINUTES_CONST;
        Long minutes = allTimeInMinutes%MINUTES_IN_HOUR_CONST;
        Long hours = allTimeInMinutes/MINUTES_IN_HOUR_CONST;
        String timeStr = hours.toString().concat(TIME_SEPARATOR).concat(minutes.toString());
        return timeStr;
    }
    
    /**
     * returns object of current time 
     * @return (LocalTime)
     */
    private Timestamp getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        return currentTimestamp;
    }

    /**
     * parses inputed line and returns the number of the checkpoint
     * @param inputedLine - inputed info
     * @return int (the number of the checkpoint)
     * @throws NumberFormatException 
     */
    private int getCheckpointNumberFromInput(String inputedLine) throws NumberFormatException {
        int result = DEFAULT_VALUE_NUMBER;
        Matcher matchetCheckpointNumber = checkpointNumberPattern.matcher(inputedLine);
        while (matchetCheckpointNumber.find()) {
            result = Integer.parseInt(matchetCheckpointNumber.group());
        }
        return result;
    }

    /**
     * parses inputed line and returns the user's id
     * @param inputedLine - inputed info
     * @return int (the user's id)
     * @throws NumberFormatException 
     */
    private int getUserIdFromInput(String inputedLine) throws NumberFormatException {
        int result = DEFAULT_VALUE_NUMBER;
        Matcher matchetId = idPattern.matcher(inputedLine);
        while (matchetId.find()) {
            result = Integer.parseInt(matchetId.group());
        }
        return result;
    }
}
