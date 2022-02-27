import java.util.ArrayList;
import java.util.List;

public class ActivityAnnounce extends Artifact{
    private static void Main(String args[]) {
        
        List<String> calls = new ArrayList<String>();

        public void init(int init){
            defineObsProperty("room", "...")
        }

        @OPERATION
        public void announce(String sender, String V){
            List<String> rooms = new ArrayList<String>();
            List<String> activityStream = new ArrayList<String>();
            calls.add(sender);

            rooms.add("");
            rooms.add("");

            activityStream.add("as:Offer");
            activityStream.add("as:Reject");
            activityStream.add("as:Accept");
            activityStream.add("as:Question");
            activityStream.add("as:Announce");


            defineObsProperty("subscribe"+rooms.get(random_room), V);
        }


    }
}