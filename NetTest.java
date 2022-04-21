import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.io.InputStream;
import java.util.stream.Collectors;

public class NetTest {
    public static void main(String args[]) {
        HttpURLConnection con = null;
        try {
            URL url = new URL("https://world.openfoodfacts.org/api/v0/product/016000264793.json");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());

            InputStream is = con.getInputStream();

            String response = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n")).replace("{", "\n{");

            System.out.println(response);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(con != null)
                con.disconnect();
        }
    }
}
