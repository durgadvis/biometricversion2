package com.biometric.Dao;

import com.biometric.controller.AadhaarController;
import com.biometric.forms.CardDetails;
import com.biometric.forms.User;
import com.biometric.util.BankNames;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by suvp on 11/28/2016.
 */
@Repository("userDao")
public class UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void saveUser(User employee) {
        getSession().persist(employee);
    }

    public User findUser(User aInUser) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("pk",aInUser.getPk()));
        return (User) criteria.uniqueResult();
    }

    public void updateUser(User employee) {
        getSession().update(employee);
    }

    public List<User> findAllUser(){
        Criteria criteria = getSession().createCriteria(User.class);
        return (List<User>) criteria.list();
    }

    public User findUserOnBankDetails(User aInUser, BankNames aInBankName){
        /*Criteria c = getSession().createCriteria(User.class, "user");
        c.add(Restrictions.eq("pk",aInUser.getPk()));
        c.createAlias("user.listCardDetails", "card");
        c.add(Restrictions.eq("card.bankName", aInBankName));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);*/

        Criteria crit = getSession().createCriteria(User.class);
        crit.add(Restrictions.eq("pk",aInUser.getPk()));
        Criteria cardCriteria = crit.createCriteria("listCardDetails");
        cardCriteria.add(Restrictions.eq("bankName", aInBankName));
        return  (User)crit.uniqueResult();
    }
}
