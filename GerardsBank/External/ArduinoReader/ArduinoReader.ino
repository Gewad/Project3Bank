#include <Keypad.h>
#define password_length 5 // 4 cijfers en een NULL
#include<SPI.h>
#include<MFRC522.h>

//creating mfrc522 instance
#define RSTPIN 9
#define SSPIN 10
MFRC522 rc(SSPIN, RSTPIN);

byte readcard[4]; //stores the UID of current tag which is read

const byte ROWS = 4;      // vier rijen
const byte COLS = 4;      // vier kolommen

char keys[ROWS][COLS] = {
  {'1' , '2' , '3' , 'A'},
  {'4' , '5' , '6' , 'B'},
  {'7' , '8' , '9' , 'C'},
  {'*' , '0' , '#' , 'D'}
};
byte rowPins[ROWS] = {6, 7, 8, A5}; // digitale inputs voor de rijen
byte colPins[COLS] = {2, 3, 4, 5}; // digitale inputs voor de kolommen

char pass[password_length] = "1391";  // hard-coded pincode
char passdata[password_length] ;         // de array waar de invoer in weggezet wordt
byte data_index = 0, pass_index = 0;     // bijhouders voor de invoer

// instantie maken van Keypad object
Keypad keypad = Keypad( makeKeymap(keys), colPins, rowPins, ROWS, COLS );


void setup() {
  Serial.begin(9600);
  SPI.begin();
  rc.PCD_Init(); //initialize the receiver

}

void loop() {
  getid();
  char key = keypad.getKey();
  if (key){
    Serial.println("KEY:"+String(key));
  }
}




int getid() {
  if (!rc.PICC_IsNewCardPresent()) {
    return 0;
  }
  if (!rc.PICC_ReadCardSerial()) {
    return 0;
  }


  //THE UID OF THE SCANNED CARD IS

  for (int i = 0; i < 4; i++) {
    readcard[i] = rc.uid.uidByte[i]; //storing the UID of the tag in readcard
    //Serial.print(readcard[i], HEX);

  }
  char str[32] = "";
  array_to_string(readcard, 4, str);
  Serial.println("UID:"+String(str));
  

  return 1;
}

void array_to_string(byte array[], unsigned int len, char buffer[])
{
  for (unsigned int i = 0; i < len; i++)
  {
    byte nib1 = (array[i] >> 4) & 0x0F;
    byte nib2 = (array[i] >> 0) & 0x0F;
    buffer[i * 2 + 0] = nib1  < 0xA ? '0' + nib1  : 'A' + nib1  - 0xA;
    buffer[i * 2 + 1] = nib2  < 0xA ? '0' + nib2  : 'A' + nib2  - 0xA;
  }
  buffer[len * 2] = '\0';
}




