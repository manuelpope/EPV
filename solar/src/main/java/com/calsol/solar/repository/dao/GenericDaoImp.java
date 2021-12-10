package com.calsol.solar.repository.dao;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericDaoImp<T extends Serializable> extends AbstractJpaDao<T> implements IGenericDao<T> {}



