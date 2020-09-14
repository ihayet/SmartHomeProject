#include <SoftwareSerial.h>
#include <stdio.h>
#include <stdlib.h>
#include "Appliances.h"
#include "BTComm.h"
#include "Secondary.h"
#include "Data.h"

//-------------------------------------------------------------------------------------------
#define LIGHT_ONE_TRIAC 13
#define LIGHT_ONE_BUTTON 1000

#define FAN_ONE_TRIAC 10
#define FAN_ONE_BUTTON 1000
//-------------------------------------------------------------------------------------------
#define LIGHT_TWO_TRIAC 1000
#define LIGHT_TWO_BUTTON 1000

#define FAN_TWO_TRIAC 1000
#define FAN_TWO_BUTTON 1000
//-------------------------------------------------------------------------------------------
#define LIGHT_THREE_TRIAC 1000
#define LIGHT_THREE_BUTTON 1000

#define FAN_THREE_TRIAC 1000
#define FAN_THREE_BUTTON 1000
//-------------------------------------------------------------------------------------------
#define LIGHT_FOUR_TRIAC 1000
#define LIGHT_FOUR_BUTTON 1000

#define FAN_FOUR_TRIAC 1000
#define FAN_FOUR_BUTTON 1000
//-------------------------------------------------------------------------------------------
#define LIGHT_FIVE_TRIAC 1000
#define LIGHT_FIVE_BUTTON 1000

#define FAN_FIVE_TRIAC 1000
#define FAN_FIVE_BUTTON 1000
//----------------------------------- Software Serial ---------------------------------------

#define BT_RX 5//3
#define BT_TX 6//4
#define SECONDARY_RX 7
#define SECONDARY_TX 8

BTComm* bt;
char** filteredPacket;

Secondary* secondary_uart;

//---------------------------------- Digital I/O Pins --------------------------------------

const int digitalOutputPins[] = {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
const int digitalInputPins[] = {};

//------------------------------------ Appliances -------------------------------------------

Appliances** appliancesOfThisRoom;
int receivedApplianceID, receivedApplianceValue;

//-------------------------------------------------------------------------------------------

//house_id and room_id are declared as Strings in BTComm.h

//-------------------------------------------------------------------------------------------

volatile bool zeroDetected;
unsigned long timeTest;

void setup()
{
  initialize();

  Serial.print(F("House: "));
  Serial.println(Data::house_id);
  Serial.print(F("Room: "));
  Serial.println(Data::room_id);
}

void loop()
{
//------------------------------------------------------ Set Zero Reference From Interrupt For AC PWM -----------------------------------------------------------
  
  if(zeroDetected == true)
  {
    //int tDifference = millis() - timeTest;
    //Serial.println(tDifference);
    //timeTest = millis();
    
    appliancesOfThisRoom[0]->setZeroDetectionTime(micros());
    appliancesOfThisRoom[1]->setZeroDetectionTime(micros());
  
    zeroDetected = false;
  }
    
  appliancesOfThisRoom[0]->output(micros());
  appliancesOfThisRoom[1]->output(micros());

//------------------------------------------------------------------ Get Data From SubStation ------------------------------------------------------------------------------

  int command_type = bt->getFromSubStation(filteredPacket);

  if (command_type != 0)
  {
    displayFilteredPacket();
    
    if(strcmp(filteredPacket[1], Data::house_id)==0 && strcmp(filteredPacket[2], Data::room_id)==0)
    {
      switch(command_type)
      {
        case 1:      //Data::Actuation
         sscanf(filteredPacket[3], "%d", &receivedApplianceID);
         sscanf(filteredPacket[4], "%d", &receivedApplianceValue);
         
         if(receivedApplianceValue == 0)
         {
           appliancesOfThisRoom[receivedApplianceID-1]->setOutputParameters(false, receivedApplianceValue); 
         }
         else
         {
           appliancesOfThisRoom[receivedApplianceID-1]->setOutputParameters(true, receivedApplianceValue); 
         }
         break;
        
        default:
         break; 
      }
    }
  }
}

void initialize()
{
  Serial.begin(115200);
  
  bt = new BTComm(BT_RX, BT_TX, 19200);
  secondary_uart = new Secondary(SECONDARY_RX, SECONDARY_TX, 19200);

  pinMode(2, INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(2), zeroDetectedISR, CHANGE);
  zeroDetected = false;

  digitalPinsInit();

  filteredPacket = new char *[5];
  for (int i = 0; i < 5; i++)
  {
    filteredPacket[i] = new char[10];
  }
  
  appliancesOfThisRoom = new Appliances *[2];
  
  appliancesOfThisRoom[0] = new Appliances("LIGHT1", 1, false, LIGHT_ONE_BUTTON, LIGHT_ONE_TRIAC, 0, 0);
  appliancesOfThisRoom[0]->initAppliance();
  
  appliancesOfThisRoom[1] = new Appliances("FAN1", 2, true, FAN_ONE_BUTTON, FAN_ONE_TRIAC, 1000, 8000);
  appliancesOfThisRoom[1]->initAppliance();
}

void digitalPinsInit()
{
  int i;

  for (i = 0; i < (sizeof(digitalOutputPins) / sizeof(int)); i++)
  {
    pinMode(digitalOutputPins[i], OUTPUT);
  }

  for (i = 0; i < (sizeof(digitalInputPins) / sizeof(int)); i++)
  {
    pinMode(digitalInputPins[i], INPUT);
  }
}

void displayFilteredPacket()
{
  for (int i = 0; i < 5; i++)
  {
    Serial.println(filteredPacket[i]);
  }
}

void zeroDetectedISR()
{
  zeroDetected = true;
}
