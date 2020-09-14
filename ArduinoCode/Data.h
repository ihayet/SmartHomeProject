#ifndef Data_H
#define Data_H
#endif

#if ARDUINO >= 100
#include "Arduino.h"
#else
#include "WProgram.h"
#endif

class Data
{
  private:
    
  public:
    //--------------------- HOUSE_INFORMATION -----------------------
  
    static char* house_id;
    static char* room_id;
    
    //--------------------- HOUSE_INFORMATION -----------------------
    
    //--------------------- COMMAND TYPES ---------------------------
    
    static int ACTUATION;
    
    //--------------------- COMMAND TYPES ---------------------------
    
    //---------------- Appliance PWM Value Range --------------------
    
    static int lower;
    static int upper;
    
    //---------------- Appliance PWM Value Range --------------------
};
