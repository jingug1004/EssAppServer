package com.noblapp.service.support;

import org.springframework.beans.factory.annotation.Autowired;

import com.noblapp.dao.DbMapper;

public abstract class AbstractService {

	@Autowired protected DbMapper db; //db

}
