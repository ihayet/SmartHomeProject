#ifndef Appliances_H
#define Appliances_H
#endif

#if ARDUINO >= 100
#include "Arduino.h"
#else
#include "WProgram.h"
#endif

class Appliances
{
  private:
    String applianceName;
    int applianceID;
    
    bool alreadyOn;
    bool powerControl;
    bool statusOnOff;
    long statusPWMDelay;
    
    int delayLowerRange;
    int delayUpperRange;
    unsigned long zeroDetectionTime;
    
    int buttonInput;
    int outputPin;
    
    bool alphaDelayTimeOver;
    bool pulseDelayTimeOver;
    bool halfPeriodDone;
  public:
    Appliances(String n, int id, bool pControl, int button, int output, int dLower, int dUpper);
    void initAppliance();
    void setOutputParameters(bool onOff, double pwmDelay);
    void setZeroDetectionTime(unsigned long zDetectionTime);
    void output(unsigned long timeNow);
};
