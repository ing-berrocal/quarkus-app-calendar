/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author DELL
 */
interface IConfigurationDays {
    public boolean isValidDate(LocalDate queryDate);
    public int[] getConfigHours(Integer configID,LocalDate queryDate);
    public LocalTime getConfigInitHours();        
    public LocalTime getConfigEndHours();
}
