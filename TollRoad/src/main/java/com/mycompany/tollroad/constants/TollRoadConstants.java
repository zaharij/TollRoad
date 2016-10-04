/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tollroad.constants;

import com.mycompany.tollroad.controller.command.Command;
import com.mycompany.tollroad.controller.command.commands.InCommand;
import com.mycompany.tollroad.controller.command.commands.OutCommand;
import com.mycompany.tollroad.controller.command.commands.TransferCommand;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Zakhar
 */
public class TollRoadConstants {
    public final static String ID_REG_EX = "^\\d+";
    public final static String CHECKPOINT_NUMBER_REG_EX = "(?<=\\s)\\d+";
    public final static String COMMAND_REG_EX = "(?<=\\s)[a-z]+";
    
    public final static String MONGO_CLIENT_URI = "mongodb://localhost:27017";
    public final static String TOLLROAD_DB = "tollroad";
    public final static String DRIVERS_COLLECTION = "drivers";
    public final static String USER_ID = "user_id";
    public final static String SET_COMMAND = "$set";
    public final static String CHECKPOINTS_PASSED_COLLECTION = "checkpoints_passed";
    public final static String DATETIME_IN = "datetime_in";
    public final static String GRAPHCHECKPOINT_COLLECTION = "checkpointgraph";
    public final static String GRAPHCHECKPOINTS_COLLECTION = "graphcheckpoints";
    public final static String PRICE_PER_KM = "priceperkm";
    
    public final static String IN_COMMAND = "in";
    public final static String TRANS_COMMAND = "trans";
    public final static String OUT_BOOK_COMMAND = "out";
    public final static String END_PROGRAM_COMMAND = "end";
    public final static Map<String, Command> COMMAND_MAP = new HashMap<String, Command>()//existing commands
            
    {{
        put(IN_COMMAND, new InCommand());
        put(TRANS_COMMAND, new TransferCommand());
        put(OUT_BOOK_COMMAND, new OutCommand());
    }};
}
