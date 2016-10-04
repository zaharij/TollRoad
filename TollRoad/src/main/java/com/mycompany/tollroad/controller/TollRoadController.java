/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tollroad.controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.mycompany.tollroad.constants.TollRoadConstants.*;
import com.mycompany.tollroad.controller.command.Command;
import com.mycompany.tollroad.controller.command.CommandFactory;
        
/**
 *
 * @author Zakhar
 */
public class TollRoadController {
    private Pattern commandPattern = Pattern.compile(COMMAND_REG_EX);
    private String inputedLine;
    private CommandFactory commandFactory = new CommandFactory();
    private Command command = null;
    private String inputedCommand; 
            
    /**
     * starts program
     */
    public void startProg(){
        setInputedValues();
    }

    /**
     * parses the inputed line and sets values to variables 
     * @throws NumberFormatException 
     */
    private void setInputedValues() throws NumberFormatException {
        do{
            Scanner scanIn = new Scanner(System.in);
            inputedLine = scanIn.nextLine();           
            Matcher matcherExitCommand = commandPattern.matcher(inputedLine);
            while (matcherExitCommand.find()) {
                inputedCommand = matcherExitCommand.group();
            }
            command = commandFactory.getCommand(inputedCommand);
            if(command != null){
                command.execute(inputedLine);
                command = null;
            } else {
                inputedLine = END_PROGRAM_COMMAND;
            }
        } while (!inputedLine.equalsIgnoreCase(END_PROGRAM_COMMAND));
    }
}
