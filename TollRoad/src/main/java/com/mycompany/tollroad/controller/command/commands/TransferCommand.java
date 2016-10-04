/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tollroad.controller.command.commands;

import com.mycompany.tollroad.controller.command.Command;
import com.mycompany.tollroad.service.TollRoadLogic;

/**
 *
 * @author Zakhar
 */
public class TransferCommand implements Command{
    private TollRoadLogic tollRoadLogic = new TollRoadLogic();

    @Override
    public void execute(String inputedLine) {
        tollRoadLogic.transferDriving(inputedLine);
    }   
}
