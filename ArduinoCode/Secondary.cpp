#include "Secondary.h"

Secondary::Secondary(int RX, int TX, int baud)
{
 uart = new SoftwareSerial(RX, TX);
 uart->begin(baud);
}
