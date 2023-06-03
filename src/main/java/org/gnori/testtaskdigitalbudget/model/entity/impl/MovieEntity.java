package org.gnori.testtaskdigitalbudget.model.entity.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gnori.testtaskdigitalbudget.model.entity.BaseEntity;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "movie",
    uniqueConstraints = @UniqueConstraint(columnNames = {"title", "poster_path"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieEntity extends BaseEntity {

  @Column(length = 128, nullable = false)
  protected String title;

  @Column(name = "poster_path", nullable = false)
  protected String posterPath;
}
