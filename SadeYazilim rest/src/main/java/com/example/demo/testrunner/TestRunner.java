package com.example.demo.testrunner;

import com.example.demo.model.CoinInfo;
import com.example.demo.repository.ICrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

//@Component
public class TestRunner implements ApplicationRunner {

    private final ICrudRepository m_crudRepository;

    @Autowired
    private CoinInfo[] m_coinInfos;
    //CoinRepository
    public TestRunner(@Qualifier("coinRepository") ICrudRepository crudRepository) {
        m_crudRepository = crudRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("------Test runner------");




        System.out.println("------Test runner------");
    }


}
