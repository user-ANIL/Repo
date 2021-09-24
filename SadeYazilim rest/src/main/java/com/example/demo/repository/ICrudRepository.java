package com.example.demo.repository;


import com.example.demo.model.CoinInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

//for later use if some external methods wanted to be added
public interface ICrudRepository extends CrudRepository<CoinInfo, Long> {

    <T> ArrayList<T> findAllAsList();

    <S extends CoinInfo> S update(S coin);

}
