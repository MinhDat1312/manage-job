package dao;

import entity.Employee;
import entity.Invoice;
import entity.Recruiter;
import entity.constant.Level;
import entity.constant.WorkingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InvoiceDAO extends GenericDAO<Invoice, Integer>{
    private EmployeeDAO employeeDAO;
    private RecruiterDAO recruiterDAO;

    public InvoiceDAO(Class<Invoice> clazz) {
        super(clazz);
        this.employeeDAO = new EmployeeDAO(Employee.class);
        this.recruiterDAO = new RecruiterDAO(Recruiter.class);
    }

    public InvoiceDAO(Class<Invoice> clazz, EntityManager em) {
        super(clazz, em);
        this.employeeDAO = new EmployeeDAO(Employee.class, em);
        this.recruiterDAO = new RecruiterDAO(Recruiter.class, em);
    }

    public List<Invoice> getAllWithApplicantID(long id) {
        String sql = "SELECT e FROM invoices e WHERE e.applicant_id = ?";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, id);

        return query.getResultList();
    }

    public List<Invoice> getAllWithRecruiterID(long id) {
        String sql = "SELECT e FROM invoices e WHERE e.recruiter_id = ?";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, id);

        return query.getResultList();
    }


    public double getTotalApplicationInvoices() {
        String sql = "SELECT SUM(fee) FROM ApplicationInvoice";
        Query query = em.createQuery(sql);

        return ((Number) query.getSingleResult()).doubleValue();
    }

    public double getTotalPostedInvoices() {
        String sql = "SELECT SUM(applicationCount * applicationFee) FROM PostedInvoice";
        Query query = em.createQuery(sql);

        return ((Number) query.getSingleResult()).doubleValue();
    }

    public double getTotalInvoicesFee() {
        return getTotalApplicationInvoices() + getTotalPostedInvoices();
    }

    public Map<Employee, Double> getTotalApplicationInvoicesByEmployee() {
        String sql = "SELECT e.id, SUM(r.fee) " +
                "FROM Employee e JOIN ApplicationInvoice r ON e.id = r.employee.id\n" +
                "GROUP BY e.id";
        Query query = em.createQuery(sql);
        List<Object[]> result = query.getResultList();

        EmployeeDAO employeeDAO = new EmployeeDAO(Employee.class);

        return result.stream()
                .collect(Collectors.toMap(
                        row -> employeeDAO.findById(((Number) row[0]).intValue()),
                        row -> ((Number) row[1]).doubleValue()
                ));
    }

    public Map<Employee, Double> getTotalPostedInvoicesByEmployee() {
        String sql = "SELECT e.id, SUM(r.applicationCount * r.applicationFee) " +
                "FROM Employee e JOIN PostedInvoice r ON e.id = r.employee.id\n" +
                "GROUP BY e.id";
        Query query = em.createQuery(sql);
        List<Object[]> result = query.getResultList();

        EmployeeDAO employeeDAO = new EmployeeDAO(Employee.class);

        return result.stream()
                .collect(Collectors.toMap(
                        row -> employeeDAO.findById(((Number) row[0]).intValue()),
                        row -> ((Number) row[1]).doubleValue()
                ));
    }

    public Map<Recruiter, Long> postedInvoicesByRecruiterStatistics() {
        String sql = "SELECT r.id, COUNT(*) " +
                "FROM Recruiter r JOIN PostedInvoice p ON r.id = p.recruiter.id\n" +
                "GROUP BY r.id";
        Query query = em.createQuery(sql);
        List<Object[]> result = query.getResultList();

        RecruiterDAO recruiterDAO = new RecruiterDAO(Recruiter.class);

        return result.stream()
                .collect(Collectors.toMap(
                        row -> recruiterDAO.findById(((Number) row[0]).intValue()),
                        row -> ((Number) row[1]).longValue()
                ));
    }

    public Map<WorkingType, Long> statisticsInvoiceByWorkingType() {
        String jpql = "SELECT j.workingType, COUNT(i) " +
                "FROM Invoice i " +
                "JOIN i.job j " +
                "GROUP BY j.workingType";

        List<Object[]> resultList = em.createQuery(jpql, Object[].class)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(
                        row -> (WorkingType)row[0],
                        row -> ((Number) row[1]).longValue()
                ));
    }

    public Map<Level, Long> statisticsInvoiceByLevel() {
        String jpql = "SELECT j.level, COUNT(i) " +
                "FROM Invoice i " +
                "JOIN i.job j " +
                "GROUP BY j.level";

        List<Object[]> resultList = em.createQuery(jpql, Object[].class)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(
                        row -> (Level) row[0],
                        row -> ((Number) row[1]).longValue()
                ));
    }

    public Map<String, Long> statisticsInvoiceByProfession() {
        String jpql = "SELECT p.name, COUNT(i) " +
                "FROM Invoice i " +
                "JOIN i.job j " +
                "JOIN j.professions p " +
                "GROUP BY p.name";

        List<Object[]> resultList = em.createQuery(jpql, Object[].class)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()
                ));
    }

    public List<Invoice> getInvoicesWithFilter(Integer recruiterId, Integer applicantId, LocalDate startDate, LocalDate endDate) {
        StringBuilder jpql = new StringBuilder("1=1");
        List<Object> params = new ArrayList<>();
        int paramIndex = 1;

        if (recruiterId != null) {
            jpql.append(" AND TYPE(a) = PostedInvoice AND a.recruiter.id = ?").append(paramIndex);
            params.add(recruiterId);
            paramIndex++;
        }
        if (applicantId != null) {
            jpql.append(" AND TYPE(a) = ApplicationInvoice AND a.applicant.id = ?").append(paramIndex);
            params.add(applicantId);
            paramIndex++;
        }
        if (startDate != null && endDate != null) {
            jpql.append(" AND a.createdDate BETWEEN ?").append(paramIndex).append(" AND ?").append(paramIndex + 1);
            params.add(startDate);
            params.add(endDate);
            paramIndex += 2;
        }

        return getAllWithFilter(jpql.toString(), params.toArray());
    }

    public Map<Integer, List<Double>> calculateInvoiceTotalsByMonthAndYear(List<Invoice> invoices, int year) {

        List<Employee> employees = em.createQuery("SELECT e from Employee e").getResultList();

        Map<Integer, Map<Employee, Double>> grouped = invoices.stream()
                .filter(invoice -> invoice.getCreatedDate().getYear() == year)
                .collect(Collectors.groupingBy(
                        invoice -> invoice.getCreatedDate().getMonthValue(),
                        Collectors.groupingBy(
                                Invoice::getEmployee,
                                Collectors.summingDouble(Invoice::getFee)
                        )
                ));

        Map<Integer, List<Double>> fullData = new LinkedHashMap<>();
        for (int month = 1; month <= 12; month++) {
            Map<Employee, Double> monthData = grouped.getOrDefault(month, Collections.emptyMap());
            List<Double> monthFees = employees.stream()
                    .map(emp -> monthData.getOrDefault(emp, 0.0))
                    .toList();
            fullData.put(month, monthFees);
        }

        return fullData;
    }

}
