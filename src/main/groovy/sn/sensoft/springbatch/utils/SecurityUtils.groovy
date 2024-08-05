package sn.sensoft.springbatch.utils

import grails.web.servlet.mvc.GrailsHttpSession
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import org.grails.web.util.WebUtils
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.filter.RequestContextFilter
import sn.sensoft.springbatch.securite.User

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession


//import grails.plugin.springsecurity.keycloakHabilitationService
//import org.springframework.beans.factory.annotation.Autowired

/**
 *
 * Gest cuurent user à partir des classes de domaines
 *
 * @author Mamadou Lamine NIANG (lniang)
 * Date: 10/03/17
 */
@Slf4j
class SecurityUtils {

    User getCurrentUser() {
        try {
            HttpServletRequest request = WebUtils.retrieveGrailsWebRequest().currentRequest
            HttpSession session = request.session
            return session?.currentUser
        }
        catch (Exception ex) {
            // catcher en attendant de trouver la solution pour   "No thread-bound request found: Are you referring to request attributes outside of an actual web request, or processing a request outside of the originally receiving thread? If you are actually operating within a web request and still receive this message, your code is probably running outside of DispatcherServlet/DispatcherPortlet: In this case, use RequestContextListener or RequestContextFilter to expose the current request.
            log.debug(JsonOutput.toJson([method: "getCurrentUser", message: ex.message]))
            return null

        }
    }

    String getCurrentUsername() {

        try {
            HttpServletRequest request = WebUtils.retrieveGrailsWebRequest().currentRequest
            HttpSession session = request.session
            return session?.currentUser?.username
        }
        catch (Exception ex) {
            // catcher en attendant de trouver la solution pour   "No thread-bound request found: Are you referring to request attributes outside of an actual web request, or processing a request outside of the originally receiving thread? If you are actually operating within a web request and still receive this message, your code is probably running outside of DispatcherServlet/DispatcherPortlet: In this case, use RequestContextListener or RequestContextFilter to expose the current request.
            log.error(JsonOutput.toJson([method: "getCurrentUsername", message: ex.getMessage()]))
            return null
        }
    }

    String getUri() {
        def uri

        try {
            HttpServletRequest request = WebUtils.retrieveGrailsWebRequest().currentRequest
            uri = request.forwardURI
            return uri
        }
        catch (Exception ex) {
            // catcher en attendant de trouver la solution pour   "No thread-bound request found: Are you referring to request attributes outside of an actual web request, or processing a request outside of the originally receiving thread? If you are actually operating within a web request and still receive this message, your code is probably running outside of DispatcherServlet/DispatcherPortlet: In this case, use RequestContextListener or RequestContextFilter to expose the current request.
            log.error(JsonOutput.toJson([method: "getUri", message: ex.getMessage()]))
            return null
        }


        uri = "TESTMODE"
//        if (Environment.getCurrent() == Environment.TEST) {
//            uri = "TESTMODE"
//        }
//        else{
//            uri = WebUtils.retrieveGrailsWebRequest().currentRequest.forwardURI
//        }
//        return uri
    }

    private GrailsHttpSession getRequestSession() {
//        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        RequestAttributes attribs = RequestContextFilter.get
        if (attribs instanceof NativeWebRequest) {
//            HttpServletRequest request = (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
            HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
            return request.getSession()
        }
        return null;
    }

}
