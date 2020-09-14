#include "BTComm.h"
#include "Data.h"
#include <string.h>

BTComm::BTComm(int RX, int TX, int baud)
{
  btComm = new SoftwareSerial(RX, TX);
  btComm->begin(baud);
}

SoftwareSerial* BTComm::getBT()
{
  return btComm;
}

int BTComm::getFromSubStation(char** filteredPacket)
{
  commandType = 0;
  
  btComm->listen();

  if (btComm->available())
  {
    char c = btComm->read();
    
    if(c==-1)
    {
       
    }
    else if(c=='~')
    {
      //packet reception starts
      i = 0;
      filteredPacketIndex = 0;
      
      for(j=0;j<5;j++)
      {
        for(k=0;k<10;k++)
        {
          filteredPacket[j][k]=-1;
        } 
      }
    }
    else if(c==',')
    {
      //new parameter-next
      bufferData[i] = '\0';
      strncpy(filteredPacket[filteredPacketIndex],bufferData,i+1);
      
      i = 0;
      filteredPacketIndex++;
    }
    else if(c=='!')
    {
      bufferData[i] = '\0';
      strncpy(filteredPacket[filteredPacketIndex],bufferData,i+1);
      
      return getCommandType(filteredPacket[0]);
    }
    else
    {
      bufferData[i] = c;
      i++;
    }
  }
  
  return commandType;
}

void BTComm::sendToSubStation(char* dataToSend)
{
  btComm->write(dataToSend);
}

//--------------------------------------------------- Private ----------------------------------------------------------------

int BTComm::getCommandType(char* command)
{
  if(strcmp(command,"ACT")==0)
  {
    return Data::ACTUATION;
  } 
}
