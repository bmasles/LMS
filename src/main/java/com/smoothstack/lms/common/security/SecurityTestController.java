package com.smoothstack.lms.common.security;

import com.smoothstack.lms.common.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/security-test")
public class SecurityTestController {

    @RequestMapping(path = {"/admin","/librarian","/borrower"}  )
    public ResponseEntity testJwtAccessControl(Principal principal, HttpServletRequest servletRequest) {
        Response response = new Response();
        response.getPayload().setStatus(HttpStatus.OK);
        response.getPayload().getRequest().put("principal", principal);

        response.getPayload().getRequest().put("HttpServletRequest.getMethod()", servletRequest.getMethod());
        response.getPayload().getRequest().put("HttpServletRequest.getRequestURI()", servletRequest.getRequestURI());

        response.getPayload().getRequest().put("HttpServletRequest.isUserInRole(''TEST_ADMIN'')",
                                servletRequest.isUserInRole("TEST_ADMIN"));
        response.getPayload().getRequest().put("HttpServletRequest.isUserInRole(''TEST_LIBRARIAN'')",
                servletRequest.isUserInRole("TEST_LIBRARIAN"));
        response.getPayload().getRequest().put("HttpServletRequest.isUserInRole(''TEST_BORROWER'')",
                servletRequest.isUserInRole("TEST_BORROWER"));

        response.getPayload().getResponse().put("message", "Success!");

        return response.buildResponseEntity();
    }
}
