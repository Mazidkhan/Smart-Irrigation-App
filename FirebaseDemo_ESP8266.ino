#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include<DHT.h>

// Set these to run example.
#define FIREBASE_HOST "esp8266-5c13b-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "y6otq3jBZxjr87uo8urUMoSgmNur7lh4WF6WceB9"
#define WIFI_SSID "Irshadkaularikar"
#define WIFI_PASSWORD "irshad787"

#define soilSensor A0
#define DHTPIN D4
#define DHTTYPE DHT11
DHT dht(DHTPIN,DHTTYPE);

const int relay = 5;

void setup() {
  Serial.begin(9600);
  delay(1000);                
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);                                     //try to connect with wifi
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  pinMode(relay, OUTPUT);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());                                            //print local IP address
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);                              // connect to firebase
  dht.begin();                                                               //Start reading dht sensor
}

void motor(int m){
  if(m==1)
  {
  digitalWrite(relay, LOW);
  Serial.println("Current Flowing");
  delay(100); 
  }
  else if(m==0){
  digitalWrite(relay, HIGH);
  Serial.println("Current not Flowing");
  delay(100);
  }  
}

void loop() { 
  int mot=Firebase.getInt("Motor");                                  //setup path and send readings  
  motor(mot);
  float h = dht.readHumidity();                                              // Reading temperature or humidity takes about 250 milliseconds!
  float t = dht.readTemperature();                                           // Read temperature as Celsius (the default)
  int   s=analogRead(soilSensor);
  
  if (isnan(h) || isnan(t)) {                                                // Check if any reads failed and exit early (to try again).
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }

  String fireSoil = String(s);
  String fireHumid = String(h) + String("%");                                         //convert integer humidity to string humidity 
  String fireTemp = String(t) + String("°C");                                                     //convert integer temperature to string temperature
  delay(4000);
  
  Firebase.setString("Humidity", fireHumid);                                  //setup path and send readings
  Firebase.setString("Temperature", fireTemp);                                //setup path and send readings
  Firebase.setString("Moisture", fireSoil);                                //setup path and send readings
  
   
}
