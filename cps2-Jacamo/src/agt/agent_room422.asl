+activitystream("as:Announce").



+!setup : true <-
	!setupCounters(Id);
	       +activitystream("as:Announce");
	      makeArtifact("announce","tools.Announce",[13],DS);
	      makeArtifact("response","tools.Response",[14],D);
          .broadcast(tell,artifact_announce_is(announce,response));
          
	+s422(Id);
	!gettemperature.
	
/* Plans */
+artifact_announce_is(Name)[source(Sender)] : true <-
                                           .println("Ready")
                                           .send(Sender,tell,ready);
                                            !setupCounters(Id);
                                           !observe(Name);
                                           .

+!gettemperature : s422(Id) <-
            
	for (.range(I,1,199)) {
		.wait(10000);
		
		subscribe("s422/temp","S422");
		
		
		}.
    
	
	

	
+!observe(Name) : true <-
                       lookupArtifact(Name,Id);
                     
                       focus(Id).
-!observe(Name) <- .print("Error in looking for artifact ", Name).

+subscribeS422(V)  <-        .wait(1000);
	for (.range(I,1,199)) {
		.wait(10000);
		subscribe("s422/temp","S422");
		+sender_temperature(V);
		
		
		}.

+temperatureS422(V) : activitystream(W) <- 
               
                 
		
		               
		               if(W =="as:Announce")
		               {
	                   .println("Hello The temperature in S422 is ",V);
                       if(V<=20.00) { .print("It's cold");announce("S422","cold");}
                       if(V>20.00 & V<=24.00) {.print("mild");announce("S422","mild"); }
                       if(V>24.00) {.print("hot");announce("S422","hot");};
                    }.
           
   +!Contradiction :activitystream(W)[source(Reciever)] <- 
                                         
                                           response("S422","mild",Reciever).
                                           
                                           
                                   
                                   
                                
                     

+temperatureS422(V)[artifact_id(Id)] : V>60 <- stopFocus(Id).   

	
	+!increment : not ready[source(Ag)] <-
	!increment.
	
	 	
	
	
	
	
	
	
	
+!setupCounters(D) : true <-
	makeArtifact("s422","tools.MqttSubscriber",["emse/fayol/e4/Hall4Nord/sensors/757e0b46-0efe-4f36-bf2c-e8008e49d950/metrics/TEMP","S422"],D);

	 !observe(s422).
	/* .broadcast(tell,artifact_counter_is(mqttsubscriber)). */
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
