package com.socialNetwork.users.repository;

import com.socialNetwork.users.entity.Sub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubRepository  extends JpaRepository<Sub, Integer> {
    List<Sub> findAll();
    Optional<Sub> findByUserIdAndTargetId(int userId, int targetId);
}
