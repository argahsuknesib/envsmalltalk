 +temperature(W).

+!setup : true <-
	!setupCounters(Id);
	      makeArtifact("announce","tools.Announce",[15],D);
          .broadcast(tell,artifact_announce_is(announce));
	+s424(Id);
	!gettemperature.
	
/* Plans */
+artifact_announce_is(Name,Name2)[source(Sender)] : true <-
                                           .println("Ready")
                                           .send(Sender,tell,ready);
                                            +sender(Sender);
                                            !setupCounters(Id)
                                           !observe(Name);
                                             !observe(Name2);
                                           .

+!gettemperature : s424(Id) <-
            .wait(1000);
	for (.range(I,1,199)) {
		.wait(10000);
		subscribe("s424/temp","S424");
		
		
		}.

	
	

	
+!observe(Name) : true <-
                       lookupArtifact(Name,Id);
                     
                       focus(Id).
-!observe(Name) <- .print("Error in looking for artifact ", Name).

+subscribeS424(V)  <-      +temperature(V);  .print("Hello!"); .wait(1000);
	for (.range(I,1,199)) {
		.wait(10000);
		subscribe("s424/temp","S424");
	    
		
		}.

+temperatureS424(V) : temperature(W)  & sender(S) <- .println(" The temperature in S424 is ",V);
                                           
               
                 
		
		
		
	
                       if(V<=21.00  & W == "cold") { .print("Accepted!It's cold for me too");}
                        elif(V<=21.00 & W   \== "cold") {.print("Reject!For me its cold.Can you monitor another location?");.send(S,tell,activitystream("as:Offer"));.send(S,achieve,Contradiction)}
                       if(V>21.00 & V<=25.00 & W == "mild") {.print("Accepeted! my temperature is mild too")}
                       elif (V>21.00 & V<=25.00 & W \== "mild") {.print("Reject!For me its mild.Can you monitor another location?");.send(S,tell,activitystream("as:Offer"));.send(S,achieve,Contradiction)}
                       if(V>25.00  & W  == "hot") {.print("Accepeted! my temperature is hot too");}
                        elif (V>25.00 & W == "hot") {.print("Reject!For me its hot.Can you monitor another location?");.send(S,tell,activitystream("as:Offer"));.send(S,achieve,Contradiction)}.
                    
		
                       
           
                       
                     

+temperatureS424(V)[artifact_id(Id)] : V>60 <- stopFocus(Id).   

	
	+!increment : not ready[source(Ag)] <-
	!increment.
	
	 	
	
	
	
	
	
	
	
+!setupCounters(D) : true <-
	makeArtifact("s424","tools.MqttSubscriber",["emse/fayol/e4/S424/sensors/24a89ddc-23c8-4d9f-9f5e-cff4eba32fb5/metrics/TEMP","S424"],D);

	 !observe(s424).
	/* .broadcast(tell,artifact_counter_is(mqttsubscriber)). */
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
