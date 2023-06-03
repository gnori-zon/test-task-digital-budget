package org.gnori.testtaskdigitalbudget.dao;

import org.gnori.testtaskdigitalbudget.model.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDao<E extends BaseEntity> extends JpaRepository<E, Integer> {

}
