package vn.edu.leading.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.leading.shop.controllers.api.dto.UserDTO;
import vn.edu.leading.shop.dtos.ApiResponse;
import vn.edu.leading.shop.dtos.JwtAuthenticationResponse;
import vn.edu.leading.shop.dtos.LoginRequest;
import vn.edu.leading.shop.errors.ObjectNotFoundException;
import vn.edu.leading.shop.models.UserModel;
import vn.edu.leading.shop.repositories.UserRepository;
import vn.edu.leading.shop.securities.JwtTokenProvider;
import vn.edu.leading.shop.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticaUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);


        UserModel userModel = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new ObjectNotFoundException("user_not_found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userModel.getUsername());
        userDTO.setEmail(userModel.getEmail());
        userDTO.setImageUrl(userModel.getImageUrl());
        userDTO.setAccessToken(jwt);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser (@Valid @RequestBody UserModel user) throws Exception {
        userService.register(user);
        return new ResponseEntity(new ApiResponse(true,"User registed successfully"), HttpStatus.CREATED);
    }
}
