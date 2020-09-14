#ifndef BTComm_H
#define BTComm_H
#endif

#include <SoftwareSerial.h>

#if ARDUINO >= 100
#include "Arduino.h"
#else
#include "WProgram.h"
#endif

class BTComm
{
  private:
    SoftwareSerial *btComm;
    
    int filteredPacketIndex;
    int i,j,k;
    char bufferData[10];
    int commandType;
    
    int getCommandType(char* command);
  public:
    BTComm(int RX, int TX, int baud);
    SoftwareSerial* getBT();
    int getFromSubStation(char** filteredPacket);      //returns - command type defined in Data.h //arguments - values received from the SubStation
    void sendToSubStation(char* dataToSend);
};
