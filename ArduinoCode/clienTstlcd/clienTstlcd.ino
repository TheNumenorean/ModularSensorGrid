#include <SPI.h>
#include <Ethernet.h>
#include <LiquidCrystal.h>

byte mac[] = {  0x90, 0xA2, 0xDA, 0x00, 0x50, 0x33 };
IPAddress server(192,168,1,10);

LiquidCrystal lcd(27, 26, 22, 23, 24, 25);

EthernetClient client;

void setup() {
  // start the serial library:
  lcd.begin(16, 4);
  lcd.print("Loading...");
  Serial.begin(9600);
  // start the Ethernet connection:
  if (Ethernet.begin(mac) == 0) {
    lcd.clear();
    lcd.print("Ethernet Failed");
  }
  
  lcd.clear();
  lcd.print("Connecting...");
  delay(1000);
  
  // if you get a connection, report back via serial:
  if (client.connect(server, 25567)) {
    lcd.clear();
    lcd.print("Connected");
  } 
  else {
    lcd.clear();
    lcd.print("Connection failed");
    for(;;)
      ;
  }
  
  String s = "";

  while (client.available()) {
    char c = client.read();
    s = s + c;
  }
  Serial.println(s);
  
  delay(1000);
  authenticate();
  
  s = "";
  
  while (client.available()) {
    char c = client.read();
    s = s + c;
  }
  
  //lcd.clear();
  lcd.print(s);
  
  delay(4000);
}

void loop()
{

  // if the server's disconnected, stop the client:
  if (!client.connected()) {
    lcd.clear();
    lcd.print("Disconnected");
    client.stop();

    // do nothing forevermore:
    for(;;)
      ;
  }
  
  
}

boolean authenticate() {
  client.println("user admin");
  
  String s = "";
  
  while (client.available()) {
    char c = client.read();
    s = s + c;
  }
  
  //lcd.setCursor(1,0);
  lcd.clear();
  lcd.print(s);
  
  client.println("pass password");
  
  s = "";
  
  while (client.available()) {
    char c = client.read();
    s = s + c;
  }
  
  //lcd.setCursor(1,0);
  lcd.clear();
  lcd.print(s);
}

