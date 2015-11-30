import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

class TCPClient {

	private static String servername = "localhost";
	private static int port = 6789;

	public static void main(String argv[]) {
		long minSleep = 30000l;
		long maxSleep = 120000l;

		try {
			while (true) {
				 URL url = new URL("http://localhost:8080/httpcount");
		         URLConnection urlConnection = url.openConnection();
		         HttpURLConnection connection = null;
		         if(urlConnection instanceof HttpURLConnection)
		         {
		            connection = (HttpURLConnection) urlConnection;
		         }
		         else
		         {
		            System.out.println("Please enter an HTTP URL.");
		            return;
		         }
		         BufferedReader in = new BufferedReader(
                 new InputStreamReader(connection.getInputStream()));
                 String urlString = "";
                 String current;
                 while((current = in.readLine()) != null)
                 {
                    urlString += current;
                 }
                 System.out.println(urlString);
				process();
				long sleep = minSleep
						+ Double.valueOf(Math.random() * (maxSleep - minSleep))
								.longValue();
				//System.out.println("sleeping for " + sleep + " milliseconds.");
				Thread.sleep(sleep);
			}
		} catch (Exception e) {
			System.err.println("Exception in main method");
			e.printStackTrace();
		}
	}
	
	private static void httpClient(){
		
	}

	private static void process() {
		String sentence;
		// String modifiedSentence;
		// BufferedReader inFromUser = new BufferedReader(new
		// InputStreamReader(System.in));
		Socket clientSocket = null;
		try {

			clientSocket = new Socket(servername, port);
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			// sentence = inFromUser.readLine();
			sentence = "small case line";
			outToServer.writeBytes(sentence + '\n');
			inFromServer.readLine();
			// modifiedSentence = inFromServer.readLine();
			// System.out.println("FROM SERVER: " + modifiedSentence);

		} catch (Exception e) {
			System.err.println("Exception in process method");
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				System.err.println("Exception while closing: ");
				e.printStackTrace();
			}
		}

	}

}