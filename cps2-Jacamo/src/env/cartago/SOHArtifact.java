package cartago;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class SOHArtifact extends Artifact {
    public void init(int init) {
        defineObsProperty("room", "....");
    }

    @OPERATION
    public void GetAll(String agent, String query) {
        Process process = Runtime.getRuntime().exec(
                "s-query --service=endpointURL 'SELECT ?subject ?property ?object WHERE {?subject ?property ?object} LIMIT 100'");
        try {
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        catch (Exception e) {
            System.out.println("The exception encountered is" + e);
        }

        finally {
            process.destroy();
        }

    }

    @OPERATION
    public void GetSubject(String agent, String query){
        Process process = Runtime.getRuntime().exec("s-query --service=endpointURL 'SELECT ?subject WHERE {?subject ?property ?object} LIMIT 100'");
        try{
            process.waitFor();
            BufferedReader reader = new Bufferedreader(new InputStreamReader(process.getInputStream()));
            String line;

            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }

        catch(Exception e){
            System.out.println("The exception encountered is" + e);
        }

        finally{
            process.destroy();
        }
    }

    @OPERATION
    public void GetValues(String agent, String query){
        Process process = Runtime.getRuntime().exec("s-get http://localhost:3030/dataset default")
        try{
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }

        catch(Exception e){
            System.out.println("The exception encountered is" + e);
        }

        finally{
            process.destroy();
        }
    }
}