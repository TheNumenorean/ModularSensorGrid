#define statusG 23
#define statusR 22
#define weatherOn 53
#define weatherOff 52
#define day 51
#define night 50

#include <SPI.h>
#include <Ethernet.h>

byte mac[] = {  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress server(192,168,1,10);

EthernetClient client;
boolean cnctd = false;

void setup(){
  
  Serial.begin(9600);
  
  pinMode(weatherOn, INPUT);
  pinMode(weatherOff, INPUT);
  pinMode(day, INPUT);
  pinMode(night, INPUT);
  pinMode(statusG, OUTPUT);
  pinMode(statusR, OUTPUT);
  
  digitalWrite(statusR, HIGH);
  
  
  //Ethernet Startup
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    for(;;)
      ;
  }

  delay(1000);
  Serial.println("connecting...");

  // if you get a connection, report back via serial:
  if (!client.connect(server, 25567)) {
    Serial.println("connection failed");
  } 
  else {
    
    Serial.println("connected");
    
    String s = "";
    delay(1000);

    while (client.available()) {
      char c = client.read();
      s = s + c;
    }
    Serial.print(s);
    
    client.println("USER admin");
    delay(1000);
    
    while (client.available()) {
      char c = client.read();
      s = s + c;
    }
    Serial.print(s);
    
    client.println("PASS password");
    delay(1000);
    
    while (client.available()) {
      char c = client.read();
      s = s + c;
    }
    Serial.println(s);
    
    Serial.println(s.charAt(0));
    if (s.charAt(0) == '0'){
      for(;;);
    }
      
    cnctd = true;
  }
  
  
}

void loop(){
  if(!client.connected()){
    Serial.println("Disconnected");
    digitalWrite(statusG, cnctd);
    digitalWrite(statusR, !cnctd);
    cnctd = false;
  }
  digitalWrite(statusG, cnctd);
  digitalWrite(statusR, !cnctd);
  
  //Serial.print("A: ");
  //Serial.println(digitalRead(day));
  //Serial.print("B: ");
  //Serial.print(digitalRead(night));
  
  
}
