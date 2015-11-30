
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

public class HttpCount extends AbstractHandler {

	Map<String, Integer> metrics = new HashMap<>();

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.setHandler(new HttpCount());
		server.start();
		server.join();
	}

	private String getCurrentMinute() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(cal.getTime());
	}
		
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String key = getCurrentMinute();
		
		if (metrics.containsKey(key)) {
			Integer k1 = metrics.get(key);
			metrics.put(key, k1+1);
		} else {
			metrics.put(key, 1);
		}
		JSONObject json = new JSONObject(metrics);
		
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		System.out.println("Got hit"); 
		response.getWriter().println(json.toString());
	}

}
