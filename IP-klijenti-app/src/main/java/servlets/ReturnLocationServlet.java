package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReturnLocationServlet
 */
@WebServlet("/ReturnLocationServlet")
public class ReturnLocationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnLocationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String iznajmljivanjeId = request.getParameter("iznajmljivanjeId");
        String trajanjeUSekundama = request.getParameter("trajanjeUSekundama");
        String ukupnaCena = request.getParameter("ukupnaCena");

        System.out.println("ReturnLocationServlet - iznajmljivanjeId: "+ iznajmljivanjeId +", trajanje: " + trajanjeUSekundama + ", cena: " + ukupnaCena);

        
        request.setAttribute("iznajmljivanjeId", iznajmljivanjeId);
        request.setAttribute("trajanjeUSekundama", trajanjeUSekundama);
        request.setAttribute("ukupnaCena", ukupnaCena);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/return_location.jsp");
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
