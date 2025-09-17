package com.sam.newsapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.newsapp.entity.journalEntity;

public interface journalEntityRepository extends JpaRepository<journalEntity, Long> {

}
