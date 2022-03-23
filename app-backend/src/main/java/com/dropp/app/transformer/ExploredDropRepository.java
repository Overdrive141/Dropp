package com.dropp.app.transformer;

import com.dropp.app.model.ExploredDrop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExploredDropRepository extends JpaRepository<ExploredDrop, Long> {
}
