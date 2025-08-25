package com.pranav.eCommerce.service;

import com.pranav.eCommerce.model.User;
import com.pranav.eCommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User registerUser(User user)
    {
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        try {
                User user = userRepository.findByEmail(email);

                if (user != null && user.getPassword().equals(password))
                {
                    return user;
                }
                else {
                    return null; // invalid username
                }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }
}
