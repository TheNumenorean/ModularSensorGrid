#include <SPI.h>
#include <Ethernet.h>

byte mac[] = {  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress server(192,168,1,9);

EthernetClient client;

void setup() {
  // start the serial library:
  Serial.begin(9600);
  // start the Ethernet connection:
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    for(;;)
      ;
  }

  delay(1000);
  Serial.println("connecting...");

  // if you get a connection, report back via serial:
  if (client.connect(server, 1234)) {
    Serial.println("connected");
  } 
  else {

    Serial.println("connection failed");
  }
}

void loop()
{
  
  String s = "";

  while (client.available()) {
    char c = client.read();
    s = s + c;
  }
  
  Serial.print(s);
  
  client.println("Arduino");
  
  while (client.available()) {
    char c = client.read();
    s = s + c;
  }
  
  Serial.print(s);

  // if the server's disconnected, stop the client:
  if (!client.connected()) {
    Serial.println();
    Serial.println("disconnecting.");
    client.stop();

    // do nothing forevermore:
    for(;;)
      ;
  }
}

