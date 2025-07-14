package dao;

import entity.Job;

import entity.Profession;
import entity.Resume;
import entity.constant.Level;
import entity.constant.WorkingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JobDAO extends GenericDAO<Job, Integer> {

    public JobDAO(Class<Job> clazz) {
        super(clazz);
    }

    public JobDAO(Class<Job> clazz, EntityManager em) {
        super(clazz, em);
    }

    public List<Job> getJobsByRecruiter(String recruiterName) {
        String sql = "select * \n" +
                "from jobs j join recruiters r on j.recruiter_id = r.recruiter_id\n" +
                "where r.name = ?";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, recruiterName);
        List<Object[]> result = query.getResultList();

        return  result
                .stream()
                .map(
            row -> {
                        Job job = new Job();

                        job.setId(((Number) row[0]).intValue());
                        job.setDescription((String) row[1]);
                        job.setEndDate(((Date) row[2]).toLocalDate());
                        job.setLevel(Level.valueOf(((String) row[3])));
                        job.setNumberOfPositions(((Number) row[4]).intValue());
                        job.setSalary(((Number) row[5]).doubleValue());
                        job.setStartDate(((Date) row[6]).toLocalDate());
                        job.setTitle((String) row[7]);
                        job.setVisible((Boolean) row[8]);
                        job.setWorkingType(WorkingType.valueOf((String) row[9]));

                        return job;
                    }
                )
                .collect(Collectors.toList());
    }

    public Map<Level, Long> levelStatistics() {
        String sql = "select level, COUNT(*) from jobs group by level";
        Query query = em.createNativeQuery(sql);
        List<Object[]> result = query.getResultList();

        return result.stream()
                .collect(Collectors.toMap(
                        row -> Level.valueOf((String) row[0]),
                        row -> ((Number) row[1]).longValue()
                ));
    }

    public Map<Profession, Long> professionStatistics() {
        String sql = "SELECT \n" +
                "p.id, p.name, \n" +
                "COUNT(j.job_id) AS total\n" +
                "FROM\n" +
                "jobs j JOIN job_profession jp ON j.job_id = jp.job_id\n" +
                "JOIN professions p ON jp.profession_id = p.profession_id\n" +
                "GROUP BY p.id, p.name";
        Query query = em.createNativeQuery(sql);
        List<Object[]> result = query.getResultList();

        return result.stream()
                .collect(Collectors.toMap(
                        row -> {
                            Profession profession = new Profession();
                            profession.setId(((Number) row[0]).intValue());
                            profession.setName((String) row[1]);
                            return profession;
                        },
                        row -> ((Number) row[2]).longValue()
                ));
    }

    public Map<WorkingType, Long> workingTypeStatistics() {
        String sql = "select workingType, COUNT(*) from jobs group by workingType";
        Query query = em.createNativeQuery(sql);
        List<Object[]> result = query.getResultList();

        return result.stream()
                .collect(Collectors.toMap(
                        row -> WorkingType.valueOf((String) row[0]),
                        row -> ((Number) row[1]).longValue()
                ));
    }

    public Map<Profession, Map<Integer, Long>> professionMonthStatistics() {
        String sql = "SELECT \n" +
                "p.id, p.name, \n" +
                "MONTH(j.startDate) AS month, \n" +
                "COUNT(j.job_id) AS total\n" +
                "FROM\n" +
                "jobs j JOIN job_profession jp ON j.job_id = jp.job_id\n" +
                "JOIN professions p ON jp.profession_id = p.profession_id\n" +
                "GROUP BY p.name, month\n" +
                "ORDER BY p.id, p.name, month, total DESC";
        Query query = em.createNativeQuery(sql);
        List<Object[]> result = query.getResultList();

        Map<Profession, Map<Integer, Long>> stats = new LinkedHashMap<>();

        for (Object[] row : result) {
            int professionId = ((Number) row[0]).intValue();
            String professionName = (String) row[1];
            int month = ((Number) row[2]).intValue();
            long count = ((Number) row[3]).longValue();

            Profession profession = new Profession();
            profession.setId(professionId);
            profession.setName(professionName);

            stats.computeIfAbsent(profession, k -> new LinkedHashMap<>())
                    .put(month, count);
        }

        return stats;
    }

    public List<Job> findMatchingJobs(Resume resume) {
        String sql = "SELECT DISTINCT j FROM Job j " +
                "LEFT JOIN j.professions p " +
                "WHERE (j.visible = true) AND (j.level = :level " +
                "OR p IN :professions)";

        Query query = em.createQuery(sql, Job.class);
        query.setParameter("level", resume.getLevel());
        query.setParameter("professions", resume.getProfessions());

        return query.getResultList();
    }

    public List<Job> getJobsByResumeId(int resumeId) {
        TypedQuery<Job> query = em.createQuery(
                "SELECT j FROM Job j " +
                        "JOIN j.resumes r " +
                        "WHERE r.id = :resumeId",
                Job.class
        );
        query.setParameter("resumeId", resumeId);
        List<Job> jobs = query.getResultList();
        return jobs;
    }

    public List<Job> getJobsVisible(){
        return getAllWithFilter("a.visible = ?1", true);
    }

    public List<Job> getJobsByRecruiterVisible(String nameRecruiter) {
        return getAllWithFilter(
                "a.recruiter.name = ?1 AND a.visible = ?2",
                nameRecruiter, true
        );
    }

    public List<Job> getJobsWithFilterOption(int option, Object... searchText) {
        if(option == 1){
            return getAllWithFilter(
                    "a.title LIKE ?1 AND a.recruiter.id = ?2",
                    "%"+searchText[0].toString()+"%", (int)searchText[1]
            );
        } else if (option == 2){
            return getAllWithFilter(
                    "a.visible = ?1 AND a.recruiter.id = ?2",
                    (boolean) searchText[0], (int)searchText[1]
            );
        } else if(option == 3){
            return getAllWithFilter(
                    "a.visible = ?1 AND a.title LIKE ?2 AND a.recruiter.id = ?3",
                    (boolean) searchText[0], "%"+searchText[1].toString()+"%",
                    (int)searchText[2]
            );
        } else {
            return new ArrayList<>();
        }
    }

    public List<Job> getJobsFilter(Object... has) {
        boolean hasTitle = (boolean) has[0];
        boolean hasSalary = (boolean) has[1];
        boolean hasRecruiter = (boolean) has[2];
        boolean hasLevel = (boolean) has[3];

        String title = String.valueOf(has[4]);
        String salary = String.valueOf(has[5]);
        String recruiter = String.valueOf(has[6]);
        String level = String.valueOf(has[7]);
        Pattern pattern = Pattern.compile("^[0-9]+$");

        StringBuilder where = new StringBuilder();
        List<Object> params = new ArrayList<>();

        if (hasTitle) {
            where.append("LOWER(a.title) LIKE ?").append(params.size() + 1);
            params.add("%" + title.toLowerCase() + "%");
        }

        if (hasSalary) {
            if(pattern.matcher(salary).matches()) {
                double sal = Double.parseDouble(salary);
                if (where.length() > 0) where.append(" AND ");
                where.append("a.salary = ?").append(params.size() + 1);
                params.add(sal);
            }
        }

        if (hasRecruiter) {
            if (where.length() > 0) where.append(" AND ");
            where.append("LOWER(a.recruiter.name) LIKE ?").append(params.size() + 1);
            params.add("%" + recruiter.toLowerCase() + "%");
        }

        if (hasLevel) {
            Level lev = null;
            for(Level l : Level.class.getEnumConstants()) {
                if(level.equalsIgnoreCase(l.getValue())) {
                    lev = l;
                    break;
                }
            }
            if (where.length() > 0) where.append(" AND ");
            where.append("a.level = ?" + (params.size() + 1));
            params.add(lev);
        }


        return getAllWithFilter(where.toString(), params.toArray());
    }
}
