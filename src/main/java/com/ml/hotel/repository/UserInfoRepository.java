package com.ml.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ml.hotel.model.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	Optional<UserInfo> findByName(String username);
}
