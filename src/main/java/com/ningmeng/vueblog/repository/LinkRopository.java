package com.ningmeng.vueblog.repository;

import com.ningmeng.vueblog.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRopository extends JpaRepository<Link, Integer> {
}
