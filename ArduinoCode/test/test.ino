int s = 11;


void setup(){
  
  Serial.begin(9600);
  
  pinMode(s, INPUT);

}

void loop(){
  Serial.println(digitalRead(s));
}
