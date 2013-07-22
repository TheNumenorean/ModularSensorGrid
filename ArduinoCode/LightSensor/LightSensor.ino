#include <SPI.h>
#include <Ethernet.h>

#define STATUS 7
#define EXTRA 6

// Enter a MAC address and IP address for your controller below.
// The IP address will be dependent on your local network:
byte mac[] = { 
  0x90, 0xA2, 0xDA, 0x0D, 0x54, 0x0B };
IPAddress ip(192,168,1,177);

EthernetServer server(80);

void setup() {
  
  Serial.begin(9600);
   while (!Serial) {
    ; 
  }
  
  /*
  Serial.println("Trying to get an IP address using DHCP");
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    
    Ethernet.begin(mac, ip);
  }*/
  
  Ethernet.begin(mac, ip);
  
  server.begin();
  Serial.print("server is at ");
  Serial.println(Ethernet.localIP());
  
  pinMode(STATUS, OUTPUT);
  pinMode(EXTRA, OUTPUT);
}


void loop() {
  // listen for incoming clients
  EthernetClient client = server.available();
  if (client) {
    Serial.println("New client");
    digitalWrite(STATUS, HIGH);
    
    while (client.connected()) {
      
      String com = "";
      while(client.connected()){
        if(client.available()) {
          char c = client.read();
          if(c == '\n')
            break;
          com = com + c;
        }
      
      }
      
      if(!client.connected())
        break;
        
      com.trim();
      
      Serial.println("Command: " + com);
      
      if (com == "data") {
        int sensorReading = analogRead(0);
        client.println(sensorReading);
        com = "";
      } else if(com == "light"){
        digitalWrite(EXTRA, !digitalRead(EXTRA));
      } else if(com == "exit"){
        client.println("Live long and prosper!");
        client.stop();
      } else {
        client.println("Unknown command " + com);
      }
      
      delay(1);
      
    }
    
    Serial.println("Client disonnected");
    digitalWrite(STATUS, LOW);
    
  }
  
}
