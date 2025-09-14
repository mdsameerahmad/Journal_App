package com.sam.newsapp.Rspository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.newsapp.entity.journalEntity;

public interface journalEntityRepository extends JpaRepository<journalEntity, Long> {

}
