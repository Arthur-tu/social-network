package com.socialNetwork.users.service;

import com.socialNetwork.users.entity.Sub;
import com.socialNetwork.users.entity.User;
import com.socialNetwork.users.repository.SubRepository;
import com.socialNetwork.users.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SubService {

    private final SubRepository subRepository;
    private final UserRepository userRepository;

    public SubService(SubRepository subRepository, UserRepository userRepository) {
        this.subRepository = subRepository;
        this.userRepository = userRepository;
    }

    public String follow(Sub sub) {
        Optional<Sub> existingSub = subRepository.findByUserIdAndTargetId(sub.getUserId(), sub.getTargetId());
        Optional<User> currentUser = userRepository.findById(sub.getUserId());
        Optional<User> targetUser = userRepository.findById(sub.getTargetId());
        if (existingSub.isPresent() || currentUser.isEmpty() || targetUser.isEmpty()) {
             throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Sub subscription = subRepository.save(sub);
        return String.format("Пользователь с id = %s подписался на пользователя с id = %s", subscription.getUserId(),
                subscription.getTargetId());
    }
    public Sub getSub(int id) {
        return subRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String updateSub(Sub sub, int id) {
        if (!subRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Sub savedSubscription = subRepository.save(sub);
        return String.format("Подписка с id = %s успешно обновлена", savedSubscription.getId());
    }

    public String unfollow(int id) {
        if (!subRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        subRepository.deleteById(id);
        return String.format("Подписка с id = %s удалена", id);
    }
    public List<Sub> getAllSubs() {
        return subRepository.findAll();
    }
}
