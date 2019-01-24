package com.artschool.service.util;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class PageableServiceImpl<T> implements PageableService<T> {

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 2;

    @Override
    public PageImpl<T> paginate(List<T> list, Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber <= 0) pageNumber = PAGE_NUMBER;
        if (pageSize == null || pageSize <= 0) pageSize = PAGE_SIZE;

        pageNumber = pageNumber - 1;
        int totalSize = list.size();
        int startItem = pageNumber * pageSize;

        if (list.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            list = list.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(pageNumber, pageSize), totalSize);
    }

    @Override
    public PageImpl<T> paginate(Collection<T> collection, Integer pageNumber, Integer pageSize) {
        return paginate(new ArrayList<>(collection), pageNumber, pageSize);
    }
}
