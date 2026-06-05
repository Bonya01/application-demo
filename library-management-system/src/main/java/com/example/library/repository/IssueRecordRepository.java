package com.example.library.repository;

import com.example.library.entity.IssueRecord;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {
    List<IssueRecord> findByUser(User user);
}
