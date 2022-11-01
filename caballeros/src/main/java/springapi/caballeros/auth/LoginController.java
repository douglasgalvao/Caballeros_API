package springapi.caballeros.auth;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import springapi.caballeros.dtos.ClienteLoginDTO;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<HttpStatus> login(@RequestBody ClienteLoginDTO clienteLoginDTO, HttpSession httpSession) {
        return ResponseEntity.ok(loginService.login(clienteLoginDTO, httpSession));
    }

}
