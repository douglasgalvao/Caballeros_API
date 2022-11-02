package springapi.caballeros.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import springapi.caballeros.dtos.ClienteLoginDTO;
import springapi.caballeros.dtos.ResponseTokenDTO;
import springapi.caballeros.services.LoginService;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ResponseTokenDTO> login(@RequestBody ClienteLoginDTO clienteLoginDTO, HttpSession httpSession) {
        return ResponseEntity.ok(loginService.login(clienteLoginDTO, httpSession));
    }

}
