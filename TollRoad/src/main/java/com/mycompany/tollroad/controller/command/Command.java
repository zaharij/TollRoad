/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tollroad.controller.command;

/**
 * interface Command implements pattern "command"
 * contains method execute()
 * @author Zakhar
 */
public interface Command {
    public void execute(String inputedLine);
}
