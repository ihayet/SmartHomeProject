#ifndef Secondary_H
#define Secondary_H
#endif

#include <SoftwareSerial.h>

#if ARDUINO >= 100
#include "Arduino.h"
#else
#include "WProgram.h"
#endif

class Secondary
{
 private:
   SoftwareSerial* uart;
 public:
   Secondary(int RX, int TX, int baud);
};
