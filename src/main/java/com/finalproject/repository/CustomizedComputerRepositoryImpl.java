package com.finalproject.repository;

import com.finalproject.model.Computer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CustomizedComputerRepositoryImpl implements CustomizedComputerRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Computer> findByCriteria(String operatingSystem, Pageable page) {
//        EntityManager em = HibernateFactoryUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
        Root<Computer> from = cq.from(Computer.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (StringUtils.isNotEmpty(operatingSystem)) {
            predicates.add(cb.like(from.get("operatingSystem"), operatingSystem));
        }

        Predicate[] predArray = new Predicate[predicates.size()];
        predicates.toArray(predArray);

        cq.where(predArray);

        TypedQuery<Computer> query = em.createQuery(cq);

        int totalRows = query.getResultList().size();
        Page<Computer> result = new PageImpl<Computer>(query.getResultList(), page, totalRows);
        return result;
    }
}
