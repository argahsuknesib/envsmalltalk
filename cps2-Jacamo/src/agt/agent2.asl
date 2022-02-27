// initial believes
temp1(15).
temp2(21).



+room_number(N) <-

	!announce;
. 


+room_observation(O)  <- 

	!announce;
.

+response_agent2(P)  <- 

	if(P==0){
			
			.println([Reject], " Nothing to do");
	}
	
	else{
			
			.println([Accept], " Monitoring another room");
			!announce;
	}
.


+!monitor(M) <- 
		
       for ( .range(I,1,10) ) {
    		
          	announce(M);    
          	.wait(1000);
       }
.

+!announce : room_number(N) & room_observation(O) <-
			
		if(temp1(A)& O<A){
			
			.println([Announce], " ", N, ", ", " temp:  ", O , ", ", "This room is cold");
			.send(agent1,achieve,done(N,"This room is cold"));
		}
		
	    if(temp1(A)&  temp2(B) & O>=A & O<B){
	    	
			.println([Announce], " ", N, ", ", " temp:  ", O , ", ", "This room is mild");
			.send(agent1,achieve,done(N,"This room is mild"));
		}
		
	    if(temp2(B) & O>=B){
	    	
            .println([Announce], " ", N, ", ", " temp:  ", O , ", ", "This room is hot");
            .send(agent1,achieve,done(N,"This room is hot"));
		}
.

+!done(X,Y) : room_number(N) & room_observation(O) & X=N <-

		
		if(Y=="This room is hot" & temp2(A)& O<A){
			
			.println([Contradiction]);
			.println([Offer], " Offering agent1 for monitoring another room");
			 offer1;
		}
	
.


// include of basic plans for handling cartago infra.
{ include("$jacamoJar/templates/common-cartago.asl") }