package com.Cobra.EvoCommerce.Service.User;

import com.Cobra.EvoCommerce.DTO.User.AddressDTO;
import com.Cobra.EvoCommerce.DTO.User.UserLoginDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpResponseDTO;
import com.Cobra.EvoCommerce.Exception.CustomDuplicateEmailException;
import com.Cobra.EvoCommerce.Exception.CustomDuplicateUsernameException;
import com.Cobra.EvoCommerce.Exception.CustomValidationException;
import com.Cobra.EvoCommerce.Exception.UserNotFoundException;
import com.Cobra.EvoCommerce.Mapper.User.UserMapper;
import com.Cobra.EvoCommerce.Model.User.Users;
import com.Cobra.EvoCommerce.Repository.UserRepository;
import com.Cobra.EvoCommerce.Security.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;
    @Autowired
    BCryptPasswordEncoder encoder;

    public UserServiceImpl(@Qualifier("userAuthenticationProvider") AuthenticationProvider authenticationProvider){
        Objects.requireNonNull(authenticationProvider,
                "userAuthenticationProvider must not be null");
        this.authenticationManager = new ProviderManager(List.of(authenticationProvider));
    }

    @Override
    public UserSignUpResponseDTO saveUser(UserSignUpDTO userSignUpDTO) throws CustomValidationException, CustomDuplicateEmailException, CustomDuplicateUsernameException {
        if(!userSignUpDTO.getPassword().equals(userSignUpDTO.getConfirmPassword())){
            throw new CustomValidationException("Passwords do not match");
        }

        Users user = userMapper.mapUserSignUpDTOToUser(userSignUpDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());

        if(userRepository.existsByEmail(userSignUpDTO.getEmail())){
            throw new CustomDuplicateEmailException("Email Id already exists!");
        }
        else if(userRepository.existsByUserName(userSignUpDTO.getUserName())){
            throw new CustomDuplicateUsernameException("Username already exists!");
        }

        Users savedUser = userRepository.save(user);
        return userMapper.mapUserSignUpResponseDTOToUser(savedUser);
    }

    @Override
    public String userLogin(UserLoginDTO userLoginDTO) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userLoginDTO.getUserName(), userLoginDTO.getPassword()));
        System.out.println(authentication);

        Users user = userRepository.findByUserName(userLoginDTO.getUserName())
                .orElseThrow(() -> new UserNotFoundException("User name not found"));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(userLoginDTO.getUserName(), "USER", user.getUserId(), null);
        }
        else{
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    public AddressDTO getAddress(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.mapUserToAddressDTO(user);
    }

    @Override
    public AddressDTO addAddress(Long userId, AddressDTO addressDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setAddress(addressDTO.getAddress());
        userRepository.save(user);

        return AddressDTO.builder()
                .address(user.getAddress())
                .build();
    }

}
