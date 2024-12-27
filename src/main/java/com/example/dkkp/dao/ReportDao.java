package com.example.dkkp.dao;

import com.example.dkkp.model.*;
import com.example.dkkp.model.EnumType;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReportDao {
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public ReportDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Tạo báo cáo
    public boolean createReport(Report_Bug report) {
        entityManager.persist(report);
        return true;
    }

    public List<Report_Bug> getFilteredReports(
            String userId,
            String EMAIL_ACC,
            Integer reportId,
            EnumType.Bug_Type status,
            LocalDateTime dateReport,
            String typeDate,
            String sortField,
            String sortOrder
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Report_Bug> query = cb.createQuery(Report_Bug.class);
        Root<Report_Bug> root = query.from(Report_Bug.class);
        Join<Report_Bug, User_Entity> userJoin = root.join("uzer", JoinType.INNER);
        Predicate conditions = cb.conjunction();

        boolean hasConditions = false;

        if (userId != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_USER"), userId));
            hasConditions = true;
        }

        if (EMAIL_ACC != null) {
            conditions = cb.and(conditions, cb.like(userJoin.get("EMAIL_ACC"), "%" + EMAIL_ACC + "%"));
            hasConditions = true;
        }

        if (reportId != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_REPORT"), reportId));
            hasConditions = true;
        }

        if (status != null) {
            conditions = cb.and(conditions, cb.equal(root.get("TYPE_BUG"), status));
            hasConditions = true;
        }

        if (dateReport != null && typeDate != null) {
            conditions = switch (typeDate) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("DATE_REPORT"), dateReport));
                case "<=" -> cb.and(conditions, cb.lessThanOrEqualTo(root.get("DATE_REPORT"), dateReport));
                case "=" -> cb.and(conditions, cb.equal(root.get("DATE_REPORT"), dateReport));
                case ">=" -> cb.and(conditions, cb.greaterThanOrEqualTo(root.get("DATE_REPORT"), dateReport));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("DATE_REPORT"), dateReport));
                default -> throw new IllegalArgumentException("Invalid typeDate: " + typeDate);
            };
            hasConditions = true;
        }

        if (!hasConditions) {
            query.select(root);
        } else {
            query.where(conditions);
        }

        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField.toUpperCase());
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(cb.desc(sortPath));
            } else {
                query.orderBy(cb.asc(sortPath));
            }
        }

        query.select(cb.construct(
                Report_Bug.class,
                root.get("ID_REPORT"),
                root.get("TYPE_BUG"),
                root.get("SCRIPT_BUG"),
                root.get("DATE_REPORT"),
                root.get("ID_USER"),
                userJoin.get("EMAIL_ACC")

        ));

        TypedQuery<Report_Bug> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }


    public void deleteReportById(Integer reportId) {

        Report_Bug reportToDelete = entityManager.find(Report_Bug.class, reportId);
        if (reportToDelete != null) {
            entityManager.remove(reportToDelete);
            return;
        }
        throw new RuntimeException("Can not find Report to delete");

    }

    public boolean deleteAllReports() {
        String jpql = "DELETE FROM Report_Bug";
        int deletedCount = entityManager.createQuery(jpql).executeUpdate();
        return deletedCount > 0;
    }

    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
