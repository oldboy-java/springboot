package com.example.demo.service;

import com.example.demo.pojo.Girl;

public interface GirlService   {

    public  void saveGirl(Girl girl);

    public  Girl findById(Long id);
}
