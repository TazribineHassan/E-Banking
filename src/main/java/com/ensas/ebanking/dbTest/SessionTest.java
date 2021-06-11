package com.ensas.ebanking.dbTest;

import com.ensas.ebanking.exceptions.domain.AccountNotFoundException;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path= {"/session"})
public class SessionTest {

    @GetMapping("session/{msg}")
    public Object test(@PathVariable String msg, HttpServletRequest request) throws Exception {
        String sessionMsg = (String) request.getSession().getAttribute("msg");
        if (sessionMsg == null)
            throw new AccountNotFoundException("session expired");
        request.getSession().setAttribute("msg", msg);
        return "old = " + sessionMsg + " new = " + msg;
    }

}
