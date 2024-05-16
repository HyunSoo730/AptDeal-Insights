package com.ssafy.finalproject.sample;// AptSaleRepository.java

import com.ssafy.finalproject.aptsale.entity.AptSale;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AptSaleRepository {

    private final Map<Long, AptSale> aptSaleMap = new HashMap<>();
    private long nextId = 1;

    public AptSale save(AptSale aptSale) {
        if (aptSale.getId() == null) {
            aptSale.setId(nextId++);
        }
        aptSaleMap.put(aptSale.getId(), aptSale);
        return aptSale;
    }

    public List<AptSale> findAll() {
        return new ArrayList<>(aptSaleMap.values());
    }

    public AptSale findById(Long id) {
        return aptSaleMap.get(id);
    }

    public void deleteById(Long id) {
        aptSaleMap.remove(id);
    }
}