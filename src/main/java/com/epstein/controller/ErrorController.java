package com.epstein.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null) {
            int code = Integer.parseInt( status.toString() );

            if( code == HttpStatus.NOT_FOUND.value() ) {
                model.addAttribute("errorTitle", "Błąd 404");
                model.addAttribute("errorText", "Nie znaleziono strony ");
            }

            else if( code == HttpStatus.FORBIDDEN.value() ) {
                model.addAttribute("errorTitle", "Błąd 403");
                model.addAttribute("errorText", "Brak uprawnień");
            }

            else if( code == HttpStatus.BAD_REQUEST.value() ) {
                model.addAttribute("errorTitle", "Błąd 400");
                model.addAttribute("errorText", "The server could not understand the request due to invalid syntax");
            }


            else if( code == HttpStatus.INTERNAL_SERVER_ERROR.value() ) {
                model.addAttribute("errorTitle", "Błąd 500");
                model.addAttribute("errorText", "Błąd serwera");
            }


            else {
                model.addAttribute("errorTitle", "Błąd " + code);
                model.addAttribute("errorText", "Nieznany błąd");
            }



        }

        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
