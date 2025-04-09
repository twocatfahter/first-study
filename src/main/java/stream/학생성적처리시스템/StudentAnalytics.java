package stream.학생성적처리시스템;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  Student 객체에 Stream API 를 사용해서 다양한 학생들의 데이터 분석을 수행하는 클래스
 *  주요 기능: 평균점수, 고득점 학생 필터링, 성적별 학생 수 집계
 */
public class StudentAnalytics {
    public static void main(String[] args) {
        // 테스트를 위한 데이터 생성
        List<Student> students = Arrays.asList(
                new Student("John", 95),
                new Student("Jane", 85),
                new Student("Tom", 75),
                new Student("Mary", 65),
                new Student("Dane", 95)
        );

        // 1. 평균 점수 계산
        // Stream API 를 사용합니다ㅓ.
        double averageScore = students.stream()
                .mapToInt(Student::getScore)// Student 객체에서 점수만 추출해서 IntStream 형태로 변경
                .average()
                .orElse(0.0);

        System.out.println("평균 점수: " + averageScore);

        // 2.  80 점이상 학생들
        List<Student> highScoreStudents = students.stream()
                .filter(student -> student.getScore() >= 80) // 조건에 맞는 녀석만 추출
                .toList();

        System.out.println("80점 이상 학생: " + highScoreStudents);

        highScoreStudents.forEach(student -> System.out.println(student.getName() + ": " + student.getScore() +" 점, " + student.getGrade() + " 등급"));

        // 3. 성적별 학생 수 집계
        // groupingBy 메서드가 존재합니다.
        Map<String, Long> gradeCount = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getGrade,
                        Collectors.counting()
                ));

        System.out.println("성적별 학생 수: " + gradeCount);

        // 추가예제: 이름의 길이가 3 이하인 학생 이름만 대문자로 변환
        List<String> shortNames = students.stream()
                .map(Student::getName)
                .filter(name -> name.length() <= 3)
                .map(String::toUpperCase)
                .toList();

        System.out.println(shortNames);

    }
}
