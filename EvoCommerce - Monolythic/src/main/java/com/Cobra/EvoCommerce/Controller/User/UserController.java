package com.Cobra.EvoCommerce.Controller.User;

import com.Cobra.EvoCommerce.DTO.User.AddressDTO;
import com.Cobra.EvoCommerce.DTO.User.UserLoginDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpResponseDTO;
import com.Cobra.EvoCommerce.Exception.CustomValidationException;
import com.Cobra.EvoCommerce.Service.User.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDTO> saveUser(@Valid @RequestBody UserSignUpDTO userSignUpDTO) throws CustomValidationException {
        UserSignUpResponseDTO response = userService.saveUser(userSignUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) throws UsernameNotFoundException, BadCredentialsException {
        String token = userService.userLogin(userLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/address/{userId}")
    public AddressDTO getAddressByUserId(@PathVariable Long userId){
        return userService.getAddress(userId);
    }

    @PostMapping("/address/{userId}")
    public ResponseEntity<AddressDTO> addAddressByUserId(@PathVariable Long userId, @Valid @RequestBody AddressDTO addressDTO){
        AddressDTO savedAddress = userService.addAddress(userId, addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

}
