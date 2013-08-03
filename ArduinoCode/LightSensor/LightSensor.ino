#include <SPI.h>
#include <Ethernet.h>
#include <EthernetUdp.h>

#define STATUS 7
#define EXTRA 6
#define BUTTON 2
#define BROAD_PORT 22001
#define SERV_PORT 22000

// Enter a MAC address and IP address for your controller below.
// The IP address will be dependent on your local network:
byte mac[] = { 
  0x90, 0xA2, 0xDA, 0x0D, 0x54, 0x0B };
IPAddress ip(192,168,1,177);

EthernetUDP Udp;

String name = "LightSensor1";
String vers = "1.0";

EthernetServer server(SERV_PORT);
EthernetClient client;
String clientCommand;

boolean last_press = false;
long millOld;

void setup() {
  
  Serial.begin(9600);
   while (!Serial) {
    ; 
  }
  
  
  Serial.println("Trying to get an IP address using DHCP");
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    
    Ethernet.begin(mac, ip);
  }
  
  //Ethernet.begin(mac, ip);
  
  server.begin();
  Serial.print("Server is at ");
  Serial.println(Ethernet.localIP());
  
  Udp.begin(BROAD_PORT);
  
  pinMode(STATUS, OUTPUT);
  pinMode(EXTRA, OUTPUT);
  pinMode(BUTTON, INPUT);
}


void loop() {
  
  millOld = millis();
  
  if(client == null){
    //No connection so check for new Connection
    EthernetClient tmp = server.available();
    
    if(tmp == true){  
      Serial.println("New client");
      digitalWrite(STATUS, HIGH);
      client = tmp;
      clientCommand = "";
      numClients++;
      tmp.println("Arduino LightSensor v" + vers);
      
    } else {
      
      //If no new connection broadcast location
      Udp.beginPacket("255.255.255.255", BROAD_PORT);
    
      String tmp = "Arduino LightSensor " + vers + " " + name + " ";
    
      boolean start = true;
      for (byte thisByte = 0; thisByte < 4; thisByte++) {
        if(start) start = false;
        else
          tmp.concat("."); 
        tmp.concat(Ethernet.localIP()[thisByte]);
      }
    
      tmp.concat(" ");
      tmp.concat(SERV_PORT);
      tmp.concat(" "); //Cuts off last char
    
      char chars[tmp.length()];
      tmp.toCharArray(chars, tmp.length());
      Udp.write(chars);
      Udp.endPacket();
      
      //Flash status
      digitalWrite(STATUS, !digitalRead(STATUS));
      
      //No need to flood the network
      delay(500);
    }
    
  } else {
    //The client is connected
    
    if(client.connected()){
      
      //Read next command
      while(client.connected() && client.available()){
        char c = client.read();
        if(c == '\n'){
          interpretCommand(clientCommand, client);
          clientCommand = "";
        } else
          clientCommand = clientCommand + c;
      }
      
      
    } else {
      
      //If client was disconnected remove them.
      Serial.println("Client disonnected");
      client.stop();
      client = null;
      digitalWrite(STATUS, LOW);
      
    }
    
  }
  
  if(digitalRead(BUTTON) != last_press && digitalRead(BUTTON)){
    //Button pressed
  }
  
  last_press = digitalRead(BUTTON);
  
  Serial.println(millis() - millOld);
  
  
  delay(10);
  
}

//Command Interpreter
void interpretCommand(String com, EthernetClient client){
  
  com.trim();
  
  Serial.println("Command: " + com);
  String args[10];
  int cnt = split(com, ' ', args);
  
  if (args[0] == "data") {
    int sensorReading = analogRead(0);
    client.println(sensorReading);
  } else if(args[0] == "light"){
    
    if(args[1] == "on"){
      digitalWrite(EXTRA, HIGH);
      Serial.println("Turning light on");
    } else if(args[1] == "off"){
      digitalWrite(EXTRA, LOW);
      Serial.println("Turning light off");
    } else if(args[1] == "") {
      digitalWrite(EXTRA, !digitalRead(EXTRA));
      Serial.println("Toggling light");
    } else {
      client.println("Unknown parameter " + args[1]);
    }
    
  } else if(args[0] == "exit"){
    client.println("Live long and prosper!");
    client.stop();
  } else {
    client.println("Unknown command " + com);
  }
}

int split(String s, char c, String strs[]){
  
  int index, cnt = 0;
  while((index = s.indexOf(c)) != -1){
    strs[cnt] = s.substring(0, index);
    s = s.substring(index + 1, s.length());
    cnt++;
  }
  
  strs[cnt] = s;
  cnt++;
  
  return cnt;
  
}
