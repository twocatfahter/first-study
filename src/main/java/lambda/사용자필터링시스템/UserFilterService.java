package lambda.사용자필터링시스템;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class UserFilterService {

    private List<User> users;

    public UserFilterService() {
        initializeUsers();
    }


    private void initializeUsers() {
        users = new ArrayList<>();
        // 다양한 특성을 가진 사용자 추가
        users.add(new User("Alice", 28, "USER", Arrays.asList("Java", "SQL", "HTML")));
        users.add(new User("Bob", 35, "ADMIN", Arrays.asList("Java", "Python", "AWS", "Docker")));
        users.add(new User("Charlie", 42, "ADMIN", Arrays.asList("C#", "SQL", "Azure", "JavaScript", "React")));
        users.add(new User("Diana", 31, "USER", Arrays.asList("JavaScript", "React", "CSS")));
        users.add(new User("Eric", 45, "ADMIN", Arrays.asList("Java", "Kotlin", "Spring", "MySQL")));
        users.add(new User("Fiona", 26, "USER", Arrays.asList("Python", "Django", "PostgreSQL")));
    }

    // 기존 방식(람다 사용전)
    // 전통적인 for 문사용 if 조건문
    public List<User> findAdminByAgeOld(int minAge) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getAge() >= minAge && "ADMIN".equals(user.getRole())) {
                result.add(user);
            }
        }
        return result;
    }

    // 람다 방식
    public List<User> filterUsers(Predicate<User> criteria) {
        return users.stream()
                .filter(criteria) // 조건에 맞는 항목만 필터링
                .toList();
    }

    // 사용자 목록 출력하는 유틸리티 메서드
    private void printUsers(String description, List<User> userList) {
        System.out.println("\n" + description + " (" + userList.size() + "명");
        userList.forEach(user -> System.out.println(" - " + user));
    }

    public void example() {
        System.out.println("=== 사용자 필터링 예시 ====");

        // 1. 기존 방식- 30세 이상인 관리자 찾기
        List<User> adminOldWay = findAdminByAgeOld(30);
        printUsers("기존 방식: 30세 이상 관리자", adminOldWay);

        // 2. 람다식 사용 - 30세 이상인 관리자 찾기
        List<User> admins = filterUsers(user ->
                user.getAge() >= 30 && "ADMIN".equals(user.getRole()));
        printUsers("람다식 활용: 30세 이상 관리자", admins);

        // 3. 람다식 활용 - Java 스킬을 가진 사용자 찾기
        List<User> javaDevs = filterUsers(user ->
            user.getSkills().contains("Java"));
        printUsers("Java 개발자", javaDevs);

        // 4. 복합 조건 사용 - 40 세이상 + 관리자 + 스킬 3개 초과
        Predicate<User> seniorAdmin = user ->
                user.getAge() >= 40 &&
                "ADMIN".equals(user.getRole()) &&
                user.getSkills().size() > 3;

        List<User> seniorAdmins = filterUsers(seniorAdmin);
        printUsers("시니어 관리자 (40세 이상, 스킬 3개 초과)", seniorAdmins);

        // 5. Predicate 조합 - and 연산 35세 초과 and ADMIN
        Predicate<User> isAdmin = user -> "ADMIN".equals(user.getRole());
        Predicate<User> isOver35 = user -> user.getAge() > 35;

        List<User> adminOver35 = filterUsers(isAdmin.and(isOver35));
        printUsers("Predicate 조합(AND): 35세 초과 관리자", adminOver35);

        // 6. Predicate 조합 - or 연산 Java 또는 Python 개발자찾기
        Predicate<User> hasJavaSkill = user -> user.getSkills().contains("Java");
        Predicate<User> hasPythonSkill = user -> user.getSkills().contains("Python");

        List<User> javaOrPythonDevs = filterUsers(hasJavaSkill.or(hasPythonSkill));
        printUsers("Predicate 조합(OR): Java 또는 Python 개발자", javaOrPythonDevs);
        // 7, Predicate 조합 - not 연산 일반 사용자

        List<User> nonAdmins = filterUsers(isAdmin.negate());
        printUsers("Predicate 부정(NOT): 일반 사용자", nonAdmins);
    }

    public static void main(String[] args) {
        UserFilterService service = new UserFilterService();
        service.example();
    }
}
