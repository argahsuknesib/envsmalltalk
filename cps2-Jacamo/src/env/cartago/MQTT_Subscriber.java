package cartago;

import cartago.*;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.*;
import java.util.*;
import utils.MQTT;

public class MQTT_Subscriber extends Artifact {
	
  int room1_temp_data =  5;
  int room2_temp_data = 10;
  int room3_temp_data = 15;
  int room4_temp_data = 20;
  
  int value ;
  
  int value1 ;
  int value2 ;
	
  // names of observable property as well as actions must start with downcase letter
  void init() {
	
    defineObsProperty("room_number","");
    defineObsProperty("room_observation", value);
    
    defineObsProperty("response_agent1", value1);
    defineObsProperty("response_agent2", value2);

  }

  @OPERATION void announce(String msg) {
	  
     	int room_number = new Random().nextInt(4) ;
	    msg += String.valueOf(room_number);
        // System.out.println("[Announce] " + msg);
    	
    	if (msg.equals("room1")) {
        	
        	getObsProperty("room_number").updateValue(msg);
        	getObsProperty("room_observation").updateValue(room1_temp_data);

        }else if (msg.equals("room2")) {
        	
        	getObsProperty("room_number").updateValue(msg);
        	getObsProperty("room_observation").updateValue(room2_temp_data);
        	
        }else if (msg.equals("room3")) {
        	
        	getObsProperty("room_number").updateValue(msg);
        	getObsProperty("room_observation").updateValue(room3_temp_data);

        }else{
        	
        	getObsProperty("room_number").updateValue(msg);
        	getObsProperty("room_observation").updateValue(room4_temp_data);

        }   
  }

  
  @OPERATION void offer1() {
	  
   	int random_number1 = new Random().nextInt(2) ;

      	getObsProperty("response_agent1").updateValue(random_number1);
 
  }
  
  @OPERATION void offer2() {
	  
	int random_number2 = new Random().nextInt(2) ;
 	
	    getObsProperty("response_agent2").updateValue(random_number2);	   
	     
	}
  
  
}