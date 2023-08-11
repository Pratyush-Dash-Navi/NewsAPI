package com.navi.NewsAPI.repository;

import com.navi.NewsAPI.entity.Apicalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiCallsRepository extends JpaRepository<Apicalls, Long> {
}
