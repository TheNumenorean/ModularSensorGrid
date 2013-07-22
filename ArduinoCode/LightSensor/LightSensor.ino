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
int numClients = 0;
const int possibleConnections = 4;
EthernetClient clients[possibleConnections];
String clientCommands[possibleConnections];

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
  
  EthernetClient tmp = server.available();
  
  if(tmp){
    digitalWrite(STATUS, HIGH);
    if(numClients == possibleConnections){
      Serial.println("Had to discard new client!");
      tmp.println("Sorry, too many concurrent connections!");
      tmp.stop();
    } else {
      Serial.println("New client");
      clients[numClients] = tmp;
      clientCommands[numClients] = "";
      numClients++;
      tmp.println("Arduino LightSensor v1.0");
      tmp.flush();
    }
  }
  
  for(int y = 0; y < numClients; y++){
    EthernetClient client = clients[y];
    
    if(client.connected()){
      
      while(client.connected() && client.available()){
        char c = client.read();
        if(c == '\n'){
          interpretCommand(clientCommands[y], client);
          clientCommands[y] = "";
        } else
          clientCommands[y] = clientCommands[y] + c;
      
      }
      
      
    } else {
      
      Serial.println("Client disonnected");
      client.stop();
      
      Serial.println("Correcting");
      numClients--;
      for(int x = y; x < numClients; x++){
        Serial.println(x);
        clients[x] = clients[x + 1];
      }
      
      if(numClients == 0)
        digitalWrite(STATUS, LOW);
      
      y--;
    }
  }
  
}

void interpretCommand(String com, EthernetClient client){
      com.trim();
      
      Serial.println("Command: " + com);
      Serial.flush();
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
      
      client.flush();
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
