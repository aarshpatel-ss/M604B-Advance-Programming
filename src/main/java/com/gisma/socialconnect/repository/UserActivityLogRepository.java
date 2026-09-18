package com.gisma.socialconnect.repository;

import com.gisma.socialconnect.model.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
    List<UserActivityLog> findByUserIdOrderByActivityDateDesc(Integer userId);
}
