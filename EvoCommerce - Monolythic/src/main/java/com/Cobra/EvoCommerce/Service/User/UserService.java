package com.Cobra.EvoCommerce.Service.User;

import com.Cobra.EvoCommerce.DTO.User.AddressDTO;
import com.Cobra.EvoCommerce.DTO.User.UserLoginDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpResponseDTO;
import com.Cobra.EvoCommerce.Exception.CustomDuplicateEmailException;
import com.Cobra.EvoCommerce.Exception.CustomDuplicateUsernameException;
import com.Cobra.EvoCommerce.Exception.CustomValidationException;
import jakarta.validation.Valid;

public interface UserService {

    UserSignUpResponseDTO saveUser(UserSignUpDTO userSignUpDTO) throws CustomValidationException, CustomDuplicateEmailException, CustomDuplicateUsernameException;

    String userLogin(UserLoginDTO userLoginDTO);

    AddressDTO getAddress(Long userId);

    AddressDTO addAddress(Long userId, AddressDTO addressDTO);
}
