/**
 * 
 */
package ak.websocketchat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * @author Tiamat
 *
 */
public class AkHomeServlet extends HttpServlet {

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("jsp/chat.jsp").forward(request, response);
	}

}
