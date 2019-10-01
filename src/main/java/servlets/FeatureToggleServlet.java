package servlets;

import com.google.gson.Gson;
import domain.FeatureToggle;
import dto.FeatureToggleDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FeatureToggleServlet extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(FeatureToggleServlet.class);

    private static String url = "http://localhost:8080/api/toggle";
    private static CloseableHttpClient httpClient;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        httpClient = HttpClientBuilder.create().build();
        Method method = determineRequestMethod(req.getParameter("method"));
        CloseableHttpResponse result = null;
        if (Method.CREATE == method) {
            HttpPost createToggleRequest = new HttpPost(url);
            createToggleRequest.addHeader("Content-Type", "application/json");
            createToggleRequest.setEntity(new StringEntity(new Gson().toJson(buildToggleDto(req))));
            result = httpClient.execute(createToggleRequest);
            resp = buildResponse(result, resp);
        } else if (Method.SWITCH == method) {
            String enable = Boolean.valueOf(req.getParameter("turnOn")) ? "enable/" : "disable/";
            HttpPost createToggleRequest = new HttpPost(url + "/" + enable + req.getParameter("key"));
            HttpPost switchToggleRequest = new HttpPost(url);
            switchToggleRequest.addHeader("Content-Type", "application/json");
            result = httpClient.execute(createToggleRequest);
            resp = buildResponse(result, resp);
        }

        if (result != null )
            result.close();
    }

    public HttpServletResponse buildResponse(CloseableHttpResponse result, HttpServletResponse resp) throws IOException {
        StatusLine statusLine = result.getStatusLine();
        resp.setStatus(statusLine.getStatusCode());
        resp.setContentType("application/json");
        resp.getWriter().write(new BasicResponseHandler().handleResponse(result));
        return resp;
    }

    public FeatureToggleDto buildToggleDto(HttpServletRequest req)
    {
        return new FeatureToggleDto().setKey(req.getParameter("key"))
                .setDescription(req.getParameter("description")).setIsEnabled(Boolean.valueOf(req.getParameter("isEnabled")));
    }

    protected Method determineRequestMethod(String method)
    {
        if ("switchToggle".equals(method))
        {
            return Method.SWITCH;
        } else if ("createSwitch".equals(method)) {
            return Method.CREATE;
        } else {
            return Method.DELETE;
        }
    }

    public enum Method {
        SWITCH, CREATE, DELETE,
    }


}