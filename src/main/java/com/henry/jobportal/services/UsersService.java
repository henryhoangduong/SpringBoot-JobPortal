package com.henry.jobportal.services;

import com.henry.jobportal.entity.JobSeekerProfile;
import com.henry.jobportal.entity.RecruiterProfile;
import com.henry.jobportal.entity.Users;
import com.henry.jobportal.repository.JobSeekerProfileRepository;
import com.henry.jobportal.repository.RecruiterProfileRepository;
import com.henry.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, JobSeekerProfileRepository jobSeekerProfileRepository, RecruiterProfileRepository recruiterProfileRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        System.out.println("savedUser: "+savedUser);
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if (userTypeId == 1) {
            System.out.println("userTypeId"+userTypeId);
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            System.out.println("userTypeId"+userTypeId);
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return usersRepository.save(users);
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
