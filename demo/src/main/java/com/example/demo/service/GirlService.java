package com.example.demo.service;

import java.util.List;

import com.example.demo.pojo.Girl;

public interface GirlService {

	public void saveGirl(Girl girl);

	public void updateGirl(Girl girl);

	public void deleteGirl(String girlId);

	public Girl queryGirlById(String girlId);

	public List<Girl> queryGirlList(Girl girl);

	public List<Girl> queryGirlListPaged(Girl girl, Integer page, Integer pageSize);
	
	public List<Girl> queryGirlListByCupSizePaged(String cupSize, Integer page, Integer pageSize);
	
	
	public void saveGirlTransactional(Girl girl) throws Exception;
}
