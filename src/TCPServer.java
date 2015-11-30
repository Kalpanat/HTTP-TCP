import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

class TCPServer {
	static Map<String, Integer> metrics = new HashMap<>();

	public static void main(String argv[]) {
		process();
	}

	private static void process() {
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = null;
		try {
			welcomeSocket = new ServerSocket(6789);
			while (true) {
				try {
					Socket connectionSocket = welcomeSocket.accept();
					BufferedReader inFromClient = new BufferedReader(
							new InputStreamReader(
									connectionSocket.getInputStream()));
					DataOutputStream outToClient = new DataOutputStream(
							connectionSocket.getOutputStream());

					String key = getCurrentMinute();
					if (metrics.containsKey(key)) {
						Integer k1 = metrics.get(key);
						metrics.put(key, k1 + 1);
					} else {
						metrics.put(key, 1);
					}
					JSONObject json = new JSONObject(metrics);
					System.out.println(json.toString());

					clientSentence = inFromClient.readLine();
					// System.out.println("Received: " + clientSentence);
					capitalizedSentence = clientSentence.toUpperCase() + '\n';
					outToClient.writeBytes(capitalizedSentence);
				} catch (Exception e) {
					System.err.println("Exception in while loop: ");
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.err.println("Exception while opening socket: ");
			e.printStackTrace();
		} finally {
			try {
				welcomeSocket.close();
			} catch (IOException e) {
				System.err.println("Exception while closing socket: ");
				e.printStackTrace();
			}
		}
	}

	private static String getCurrentMinute() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss")
				.format(cal.getTime());
	}
}
