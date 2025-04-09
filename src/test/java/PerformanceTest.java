import factory.예제1.CreditCardPayment;
import factory.예제1.Payment;
import factory.예제1.PaymentFactory;
import factory.예제1.PaymentMethod;
import org.junit.jupiter.api.Test;

import 컨벤션.개선된컨벤션.UserDto;
import 컨벤션.잘못된컨벤션.userDTO;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class PerformanceTest {

    @Test
    void compareNullCheckPerformance() {
        // 기존 코드
        userDTO oldStyle = new userDTO();
        long startTime = System.nanoTime();

        for (int i = 0; i < 1000000; i++) {
            try {
                oldStyle.Setusername(null);
            } catch (Exception e) {
                // 예외 무시
            }
        }
        long endTime = System.nanoTime();

        // 개선된 코드
        UserDto newStyle = new UserDto();
        long startTime1 = System.nanoTime();

        for (int i = 0; i < 1000000; i++) {
            try {
                newStyle.setUsername(null);
            } catch (Exception e) {
                // 예외 무시
            }
        }
        long endTime1 = System.nanoTime();

        System.out.printf("Old style execution time: %d ms%n", (endTime - startTime) / 1000000);
        System.out.printf("New style execution time: %d ms%n", (endTime1 - startTime1) / 1000000);
    }

    @Test
    void codeQualityMetrics() {
        int oldStyleCyclomaticComplexity = calculateComplexity(userDTO.class);
        int newStyleCyclomaticComplexity = calculateComplexity(UserDto.class);

        assertThat(newStyleCyclomaticComplexity)
                .withFailMessage("New style should have lower complexity. Old: %d, New: %d",
                        oldStyleCyclomaticComplexity, newStyleCyclomaticComplexity)
                .isLessThanOrEqualTo(oldStyleCyclomaticComplexity);
    }


    private int calculateComplexity(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .mapToInt(method -> {
                    // 기본 복잡도 1
                    int complexity = 1;
                    // 파라미터당 복잡도 증가
                    complexity += method.getParameterCount();
                    // 예외 처리당 복잡도 증가
                    complexity += method.getExceptionTypes().length;
                    return complexity;
                })
                .sum();
    }

    @Test
    void factoryPerformanceTest() {

        long start = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            Payment payment = new CreditCardPayment();
        }

        long directTime = System.nanoTime() - start;

        // 2. 팩토리를 통한 생성
        start = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            Payment payment = PaymentFactory.createPayment(PaymentMethod.CREDIT_CARD);
        }
        long factoryTime = System.nanoTime() - start;

        System.out.printf("Direct instantiation: %d ms%n", directTime / 1_000_000);
        System.out.printf("Factory creation: %d ms%n", factoryTime / 1_000_000);
    }
}
