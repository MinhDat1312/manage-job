package dao;

import entity.Employee;
import entity.Job;
import entity.Resume;
import entity.constant.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ResumeDAO extends GenericDAO<Resume, Integer> {

    public ResumeDAO(Class<Resume> clazz) {
        super(clazz);
    }

    public ResumeDAO(Class<Resume> clazz, EntityManager em) {
        super(clazz, em);
    }

    public Map<Employee, Long> getTotalResumesByEmployee() {
        String sql = "SELECT e.employee_id, COUNT(*)\n" +
                "FROM employees e JOIN resumes r ON e.employee_id = r.employee_id\n" +
                "GROUP BY e.employee_id";
        Query query = em.createNativeQuery(sql);
        List<Object[]> result = query.getResultList();

        EmployeeDAO employeeDAO = new EmployeeDAO(Employee.class);

        return result.stream()
                .collect(Collectors.toMap(
                        row -> employeeDAO.findById(((Number) row[0]).intValue()),
                        row -> ((Number) row[1]).longValue()
                ));
    }

    public List<Resume> findMatchingResumes(Job job){
        String sql = "SELECT DISTINCT r FROM Resume r " +
                "LEFT JOIN r.professions p " +
                "WHERE r.level = :level " + "OR p IN :professions";

        Query query = em.createQuery(sql, Resume.class);
        query.setParameter("level", job.getLevel());
        query.setParameter("professions", job.getProfessions());

        return query.getResultList();
    }

    public List<Resume> getByApplicantName(String name) {
        return getAllWithFilter(
                "a.applicant.name LIKE ?1", "%"+name+"%"
        );
    }

    public Resume getReusmeByFullNameApplicant(String fullName) {
        return getAllWithFilter("a.applicant.name = ?1",fullName).get(0);
    }

    public List<Resume> findSuitableResumesByJobId(int jobId) {
        String sql = "SELECT DISTINCT r FROM Resume r " +
                "JOIN r.professions rp " +
                "WHERE (r.level = (SELECT j.level FROM Job j WHERE j.id = :jobId) " +
                "OR rp IN (SELECT jp FROM Job j JOIN j.professions jp WHERE j.id = :jobId)) " +
                "AND NOT EXISTS (SELECT jr FROM JobResume jr WHERE jr.resume.id = r.id AND jr.job.id = :jobId)";

        TypedQuery<Resume> query = em.createQuery(sql, Resume.class);
        query.setParameter("jobId", jobId);
        List<Resume> resumes = query.getResultList();
        return resumes;
    }
}
