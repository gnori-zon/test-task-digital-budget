package org.gnori.testtaskdigitalbudget.service.search;

import java.util.List;

public interface Searcher<D> {

  List<D> search(Integer userId);
}
