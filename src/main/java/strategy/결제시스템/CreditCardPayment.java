package strategy.결제시스템;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CreditCardPayment implements PaymentStrategy{
    private String owner;
    private String cardNumber;
    private String cvv;
    private String dateOfExpiry;

    @Override
    public void pay(int amount) {
        System.out.println(amount + "원을 신용카드로 결제합니다.");
        // 실제구현: 카드 정보를 검증 및 결제처리 api 를 호출
    }
}
