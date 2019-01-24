package com.artschool.service.util;

import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;

public interface PageableService<T> {

    PageImpl<T> paginate(List<T> list, Integer pageNumber, Integer pageSize);

    PageImpl<T> paginate(Collection<T> collection, Integer pageNumber, Integer pageSize);
}
