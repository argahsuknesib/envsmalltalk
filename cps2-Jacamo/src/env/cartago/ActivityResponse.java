import java.util.ArrayList;
import java.util.List;

public class ActivityResponse extends Artifact {
    List<String> rooms = new ArrayList<String>();
    List<String> activityStream = new ArrayList<String>();
    List<String> temperature = new ArrayList<String>();

    public void init(int init) {
        defineObsProperty("room", "...");
    }

    @OPERATION
    public void response(String sender, String V, String Receiver) {
        rooms.add("");

        activityStream.add("as:Offer");
        activityStream.add("as:Reject");
        activityStream.add("as:Accept");
        activityStream.add("as:Question");
        activityStream.add("as:Announce");

        int max_activity = 1; 
        int min_activity = 0;
        int range_activity = max_activity - min_activity + 1;

        int random_activity = (int)(Math.random() * range_activity) + min_activity;
        int remove_sender = rooms.indexOf(sender);
        int remove_reciver = rooms.indexOf(Receiver.toUpperCase());

        rooms.remove(remove_sender);
        rooms.remove(remove_reciver);

        int max_room = rooms.size() - 1;
        int min_room = 0;
        int range_room = max_room - min_room + 1;
        int random_room = (int)(Math.random() * range_room) + min_room;

        if (activityStream.get(random_activity).equals("as:Accept")){
            System.out.println("The offer is accepted.");
            System.out.println("What is the temperature of the room, " + rooms.get(random_room));

            defineObsProperty("subscribe" + rooms.get(random_room), V);
        }

        if (activityStream.get(random_activity).equals("as:Offer")){
            System.out.println();
        }

        if (activityStream.get(random_activity).equals("as:Reject")){
            System.out.println(Room_list.get(rand_room));
			System.out.println("Offer Rejected");
			if(!V.equals("cold"))
			{
			System.out.println("is it cold?");
			defineObsProperty("subscribe"+Room_list.get(rand_room),"cold");
			}
			else if(!V.equals("mild"))
			{
			System.out.println("is it mild?");
			defineObsProperty("subscribe"+Room_list.get(rand_room),"mild" );
			}
			else
			{
			System.out.println("is it hot?");
			defineObsProperty("subscribe"+Room_list.get(rand_room),"hot" );
			}
			
        }

        if(activityStream.get(random_activity).equals("as:Question")){

        }

        if (activityStream.get(random_activity).equals("as:Announce")) {
            
        }





    }

}