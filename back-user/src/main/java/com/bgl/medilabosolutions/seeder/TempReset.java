//package com.bgl.medilabosolutions.seeder;
//
//import com.bgl.medilabosolutions.repositories.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//
//@Component
//class TempReset implements CommandLineRunner {
//    private final UserRepository repo;
//    private final PasswordEncoder encoder;
//
//    TempReset(UserRepository repo, PasswordEncoder encoder) {
//        this.repo = repo;
//        this.encoder = encoder;
//    }
//
//    @Override
//    public void run(String... args) {
//        repo.findByEmail("ben@exemple.com").ifPresent(u -> {
//            u.setPassword(encoder.encode("Motdepasse1!"));
//            repo.save(u);
//            System.out.println(">>> TEMP mot de passe réinitialisé");
//        });
//    }
//}