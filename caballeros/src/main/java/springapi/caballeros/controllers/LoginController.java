package springapi.caballeros.controllers;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import springapi.caballeros.dtos.ClienteLoginDTO;
import springapi.caballeros.dtos.ResponseTokenDTO;
import springapi.caballeros.services.LoginService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ResponseTokenDTO> login(@RequestBody ClienteLoginDTO clienteLoginDTO,
            ServletResponse response) {
        return ResponseEntity.ok(loginService.login(clienteLoginDTO, response));
    }

}
