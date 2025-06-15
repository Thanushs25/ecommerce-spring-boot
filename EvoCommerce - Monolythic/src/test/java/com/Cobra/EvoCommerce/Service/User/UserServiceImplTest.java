package com.Cobra.EvoCommerce.Service.User;

import com.Cobra.EvoCommerce.DTO.User.AddressDTO;
import com.Cobra.EvoCommerce.DTO.User.UserLoginDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpResponseDTO;
import com.Cobra.EvoCommerce.Exception.CustomDuplicateEmailException;
import com.Cobra.EvoCommerce.Exception.CustomDuplicateUsernameException;
import com.Cobra.EvoCommerce.Exception.CustomValidationException;
import com.Cobra.EvoCommerce.Mapper.User.UserMapper;
import com.Cobra.EvoCommerce.Model.User.Address;
import com.Cobra.EvoCommerce.Model.User.Users;
import com.Cobra.EvoCommerce.Repository.UserRepository;
import com.Cobra.EvoCommerce.Security.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private JWTService jwtService;
    @Mock private BCryptPasswordEncoder encoder;
    @Mock private AuthenticationProvider authenticationProvider;
    @Mock private Authentication authentication;

    @InjectMocks private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(authenticationProvider.supports(any())).thenReturn(true); // Important for ProviderManager
        userService = new UserServiceImpl(authenticationProvider);
        userService.userRepository = userRepository;
        userService.userMapper = userMapper;
        userService.jwtService = jwtService;
        userService.encoder = encoder;
    }

    @Test
    void testSaveUser_Success() throws CustomValidationException, CustomDuplicateUsernameException, CustomDuplicateEmailException {
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .name("john")
                .userName("john1")
                .email("email@example.com")
                .password("password")
                .confirmPassword("password")
                .build();

        Users user = new Users();
        Users savedUser = new Users();
        savedUser.setUserId(1L);
        savedUser.setUserName("john1");
        savedUser.setName("john");
        savedUser.setEmail("email@example.com");
        savedUser.setCreatedAt(new Date());

        when(userMapper.mapUserSignUpDTOToUser(dto)).thenReturn(user);
        when(encoder.encode("password")).thenReturn("encodedPass");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapUserSignUpResponseDTOToUser(savedUser)).thenReturn(
                UserSignUpResponseDTO.builder()
                        .userName("john1")
                        .name("john")
                        .email("email@example.com")
                        .createdAt(savedUser.getCreatedAt())
                        .build()
        );

        UserSignUpResponseDTO response = userService.saveUser(dto);

        assertEquals("john1", response.getUserName());
        verify(userRepository).save(user);
    }

    @Test
    void testSaveUser_PasswordMismatch() {
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .name("john")
                .userName("john1")
                .email("email@example.com")
                .password("password")
                .confirmPassword("pass")
                .build();

        assertThrows(CustomValidationException.class, () -> userService.saveUser(dto));
    }

    @Test
    void testUserLogin_Success() {
        UserLoginDTO loginDTO = UserLoginDTO.builder()
                .userName("john")
                .password("password")
                .build();

        when(authenticationProvider.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken("john", "USER", 1L, 1L)).thenReturn("jwt-token");

        String token = userService.userLogin(loginDTO);

        assertEquals("jwt-token", token);
    }

    @Test
    void testUserLogin_Failure() {
        UserLoginDTO loginDTO = UserLoginDTO.builder()
                .userName("john")
                .password("wrongpassword")
                .build();

        when(authenticationProvider.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid Credentials"));

        assertThrows(BadCredentialsException.class, () -> userService.userLogin(loginDTO));
    }

    @Test
    void testAddAddress_Success() {
        Long userId = 1L;
        Address address = Address.builder()
                .flatNumber("12")
                .street("market")
                .city("chennai")
                .country("india")
                .pincode("600000")
                .build();
        AddressDTO addressDTO = AddressDTO.builder()
                .address(address)
                .build();

        Users user = new Users();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        AddressDTO result = userService.addAddress(userId, addressDTO);

        assertEquals(addressDTO.getAddress(), result.getAddress());
    }

    @Test
    void testAddAddress_UserNotFound() {
        Long userId = 99L;
        AddressDTO addressDTO = AddressDTO.builder()
                .address(Address.builder().city("Chennai").build())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.addAddress(userId, addressDTO));
        assertEquals("User not found", exception.getMessage());
    }
}
