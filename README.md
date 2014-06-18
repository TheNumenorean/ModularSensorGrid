ModularSensorGrid
=================

A modular plug-and-play arduino-based sensor system.



The current cersion is ArduinoUSB. This will eventually become the main project, and is not actually limited to only USB. It does a much better job of making sensors contactable through any means, even in the same modular sensor grid. It also does not make the Ardino distinction; even though it is designed around and for the arduino, it theoretically could work with any device whatsoever, as long as it has some sort of communcation protocol.

Custom communication protocols must extend COnnector.
Custom Sensors must extend Sensor.

Examples can be found under java/ArduinoTester


In an old version (NO LONGER NECCESSARY):
In order for the Arduino code to work, you must follow the directions found at 
http://subethasoftware.com/2013/04/09/arduino-ethernet-and-multiple-socket-server-connections/
under "Ethernet Library Modifications".
