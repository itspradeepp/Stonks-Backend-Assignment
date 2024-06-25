package com.stonks.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.stonks.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Remapper findById(UUID id);
}
