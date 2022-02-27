
 +temperature(0).



+!setup : true <-
	!setupCounters(Id);
	      makeArtifact("announce","tools.Announce",[14],D);
          .broadcast(tell,artifact_announce_is(announce));
	+s423(Id);
	!gettemperature.
	
/* Plans */
+artifact_announce_is(Name,Name2)[source(Sender)] : true <-
                                           .println("Ready")
                                           .send(Sender,tell,ready);
                                           +sender(Sender);
                                           !observe(Name);
                                           !observe(Name2);
                                           !setupCounters(Id)
                                           .

+!gettemperature : s423(Id) <-
            .wait(1000);
	for (.range(I,1,199)) {
		.wait(10000);
		subscribe("s423/temp","S423");
		
		
		}.

	
	

	
+!observe(Name) : true <-
                       lookupArtifact(Name,Id);
                       focus(Id).
-!observe(Name) <- .print("Error in looking for artifact ", Name).

+subscribeS423(V)  <-     +temperature(V);  .print("Hello!"); .wait(1000);
	for (.range(I,1,199)) {
		.wait(10000);
		subscribe("s423/temp","S423");
	
		
		}.
		



+temperatureS423(V) : temperature(W) & sender(S)  <- .println("The temperature in S423 is ", V); 
         
                      
                      
                       if(V<=18.00 & W == "cold") { .print("Accepted!It's cold");}
                       elif(V<18.00 & W   \== "cold") {.print("Reject!For me its cold.Can you monitor another location?");.send(S,tell,activitystream("as:Offer"));.send(S,achieve,Contradiction)}
                       if(V>18.00 & V<=22.00 & W == "mild") {.print("Accepeted! my temperature is mild too")}
                       elif (V>18.00 & V<=22.00 & W \== "mild") {.print("Reject!For me its mild.Can you monitor another location?");.send(S,tell,activitystream("as:Offer"));.send(S,achieve,Contradiction)}
                       if(V>22.00  & W  == "hot") {.print("Accepeted! my temperature is hot too");}
                        elif (V>22.00 & W == "hot") {.print("Reject!For me its hot.Can you monitor another location?");.send(S,tell,activitystream("as:Offer"));.send(S,achieve,Contradiction)}.
                    
		
                       
           
                       
                     

+temperatureS423(V)[artifact_id(Id)] : V>60 <- stopFocus(Id).   

	
	
	
	
	
	
	
	
	
	
	
+!setupCounters(D) : true <-
	makeArtifact("s423","tools.MqttSubscriber",["emse/fayol/e4/S423/sensors/8aa60f58-fc6f-49e2-a53a-f5cc96bb9021/metrics/TEMP","S423"],D);
	 !observe(s423).

	/* .broadcast(tell,artifact_counter_is(mqttsubscriber)). */
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
