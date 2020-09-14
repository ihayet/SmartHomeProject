#include "Appliances.h"
#include "Data.h"

Appliances::Appliances(String n, int id, bool pControl, int button, int output, int dLower, int dUpper)
{
  applianceName = n;
  applianceID = id;

  powerControl = pControl;

  buttonInput = button;
  outputPin = output;

  pinMode(buttonInput, INPUT);
  pinMode(outputPin, OUTPUT);

  delayLowerRange = dLower;
  delayUpperRange = dUpper;
}

void Appliances::initAppliance()
{
  alphaDelayTimeOver = false;
  pulseDelayTimeOver = false;
  halfPeriodDone = true;

  statusOnOff = false;
  alreadyOn = false;
  digitalWrite(outputPin, LOW);
}

void Appliances::setOutputParameters(bool onOff, double pwmDelay)
{
  statusOnOff = onOff;
  alreadyOn = false;

  if (powerControl == true)
  {
    statusPWMDelay = map(pwmDelay, Data::lower, Data::upper, delayLowerRange, delayUpperRange);
    
    Serial.println(statusPWMDelay);
  }
}

void Appliances::setZeroDetectionTime(unsigned long zDetectionTime)
{
  if (powerControl == true)
  {
    zeroDetectionTime = zDetectionTime;

    alphaDelayTimeOver = false;
    pulseDelayTimeOver = false;
    halfPeriodDone = false;

    digitalWrite(outputPin, LOW);
  }
}

void Appliances::output(unsigned long timeNow)
{
  if (statusOnOff == true)
  {
    if (powerControl == true)
    {      
      if (halfPeriodDone == false)
      {
        if (alphaDelayTimeOver == false)
        {
          if ((timeNow - zeroDetectionTime) >= statusPWMDelay)
          {
            zeroDetectionTime = timeNow;
            alphaDelayTimeOver = true;

            digitalWrite(outputPin, HIGH);
          }
        }
        else
        {
          if (pulseDelayTimeOver == false)
          {
            if ((timeNow - zeroDetectionTime) >= (8000-statusPWMDelay))
            {
              pulseDelayTimeOver = true;

              digitalWrite(outputPin, LOW);
            }
          }
          else
          {
            halfPeriodDone = true;
          }
        }
      }
    }
    else
    {
      if(alreadyOn == false)
      {
        digitalWrite(outputPin, HIGH);
      }
    }
  }
  else
  {
    digitalWrite(outputPin, LOW);
    alreadyOn = false;
  }
}
