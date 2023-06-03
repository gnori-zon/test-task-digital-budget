package org.gnori.testtaskdigitalbudget.model.entity.impl;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
@Table(name = "_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

  @Column(length = 128, nullable = false, unique = true)
  protected String email;

  @Column(length = 128, nullable = false, unique = true)
  protected String username;

  @Column(length = 128)
  protected String name;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH})
  @JoinTable(
      name = "favorites",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "movie_id")})
  protected List<MovieEntity> favorites;
}
