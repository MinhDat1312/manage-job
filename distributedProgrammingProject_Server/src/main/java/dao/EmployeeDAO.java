package dao;

import entity.Employee;
import entity.constant.Gender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeDAO extends GenericDAO<Employee, Integer> {

    public EmployeeDAO(Class<Employee> clazz) {
        super(clazz);
    }

    public EmployeeDAO(Class<Employee> clazz, EntityManager em) {
        super(clazz, em);
    }

    public Map<Gender, Long> genderStatistics(){
        String sql = "select e.gender, count(e) from Employee e group by e.gender";
        Query query = em.createQuery(sql);
        List<Object[]> result = query.getResultList();

        return result.stream()
                .collect(Collectors.toMap(
                        row -> Gender.valueOf((String) row[0]),
                        row -> ((Number) row[1]).longValue()
                ));
    }

    public List<Employee> getAllWithFilterOption(int option, String searchText){
        if(option == 0){
            return getAllWithFilter("a.name LIKE ?1", "%" + searchText + "%");
        } else {
            return getAllWithFilter("a.contact.phone LIKE ?1", "%" + searchText + "%");
        }
    }
}
