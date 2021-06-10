package util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerUtil {

    public static void errorPage(HttpServletResponse response, Exception e){
        try {
            response.sendRedirect("error?msg="+e.getMessage());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
