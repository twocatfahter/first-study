package exception.은행계좌이체시스템;

public class BankDemo {

    public static void main(String[] args) {
        System.out.println("===== 은행계좌이체시스템 예외 처리 데모 =====\n");

        // 계좌 생성
        BankAccount account1 = new BankAccount("홍길동", 10000);
        BankAccount account2 = new BankAccount("김철수", 5000);
        BankAccount account3 = new BankAccount("이영희", 20000);

        System.out.println("초기 계좌 상태:");
        System.out.println(account1);
        System.out.println(account2);
        System.out.println(account3);
        System.out.println();

        // 시나리오 1: 정상 입금 및 출금
        System.out.println("시나리오 1: 정상 입금 및 출금");
        try {
            System.out.println("계좌1에 5000원 입금 시도...");
            account1.deposit(5000);
            System.out.println("계좌1에서 2000원 출금 시도...");
            account1.withdraw(2000);
            System.out.println("성공: " + account1);
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
        System.out.println();

        // 시나리오 2: 잔액 부족 예외
        System.out.println("시나리오 2: 잔액 부족 예외");
        try {
            System.out.println("계좌2에서 10000원 출금 시도... (잔액 부족)");
            account2.withdraw(10000);
        } catch (BankAccount.InsufficientBalanceException e) {
            System.out.println("예외 발생: " + e.getMessage());
            System.out.println("요청 금액: " + e.getRequested() + ", 사용 가능 금액: " + e.getAvailable());
        } catch (Exception e) {
            System.out.println("기타 예외 발생: " + e.getMessage());
        }
        System.out.println();

        // 시나리오 3: 유효하지 않은 송금액 예외
        System.out.println("시나리오 3: 유효하지 않은 송금액 예외");
        try {
            System.out.println("계좌1에서 -1000원 입금 시도... (음수 금액)");
            account1.deposit(-1000);
        } catch (BankAccount.InvalidTransferAmountException e) {
            System.out.println("예외 발생: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("기타 예외 발생: " + e.getMessage());
        }
        System.out.println();

        // 시나리오 4: 계좌 잠금 예외
        System.out.println("시나리오 4: 계좌 잠금 예외");
        try {
            System.out.println("계좌3 잠금...");
            account3.lockAccount();
            System.out.println("잠긴 계좌3에서 출금 시도...");
            account3.withdraw(1000);
        } catch (BankAccount.AccountLockedException e) {
            System.out.println("예외 발생: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("기타 예외 발생: " + e.getMessage());
        }
        System.out.println("계좌3 잠금 해제...");
        account3.unlockAccount();
        System.out.println();

        // 시나리오 5: 정상 계좌 이체
        System.out.println("시나리오 5: 정상 계좌 이체");
        try {
            System.out.println("계좌1에서 계좌2로 3000원 이체 시도...");
            account1.transfer(account2, 3000);
            System.out.println("이체 후 계좌1: " + account1);
            System.out.println("이체 후 계좌2: " + account2);
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
        System.out.println();

        // 시나리오 6: 동일 계좌 이체 예외
        System.out.println("시나리오 6: 동일 계좌 이체 예외");
        try {
            System.out.println("계좌1에서 계좌1로 이체 시도... (동일 계좌)");
            account1.transfer(account1, 1000);
        } catch (BankAccount.AccountValidationException e) {
            System.out.println("예외 발생: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("기타 예외 발생: " + e.getMessage());
        }
        System.out.println();

        // 다중 예외 처리 시나리오
        System.out.println("시나리오 7: 다중 예외 처리");
        transferWithRetry(account1, account2, 50000, 3);
        System.out.println();

        // 최종 계좌 상태
        System.out.println("최종 계좌 상태:");
        System.out.println(account1);
        System.out.println(account2);
        System.out.println(account3);
    }

    /**
     * 재시도 로직이 포함된 이체 메서드
     * 예외 처리의 고급 패턴을 보여줍니다.
     */
    private static void transferWithRetry(BankAccount from, BankAccount to, double amount, int maxRetries) {
        int attempts = 0;
        boolean success = false;

        while (!success && attempts < maxRetries) {
            attempts++;
            System.out.println("이체 시도 " + attempts + "/" + maxRetries + ": " + amount + "원");

            try {
                from.transfer(to, amount);
                success = true;
                System.out.println("이체 성공!");
            } catch (BankAccount.InsufficientBalanceException e) {
                System.out.println("잔액 부족: " + e.getMessage());

                // 잔액 부족시 가능한 금액으로 재시도
                double newAmount = from.getBalance() * 0.9; // 현재 잔액의 90%로 재시도
                if (newAmount > 0) {
                    System.out.println("가능한 금액 " + newAmount + "원으로 재시도합니다.");
                    amount = newAmount;
                } else {
                    System.out.println("이체 가능한 금액이 없습니다.");
                    break;
                }
            } catch (BankAccount.AccountLockedException e) {
                System.out.println("계좌 잠금: " + e.getMessage());

                // 계좌가 잠겨있는 경우 잠금 해제 후 재시도
                try {
                    System.out.println("계좌 잠금을 해제하고 재시도합니다.");
                    if (e.getMessage().contains(from.getAccountNumber())) {
                        from.unlockAccount();
                    } else {
                        to.unlockAccount();
                    }
                } catch (Exception unlockEx) {
                    System.out.println("계좌 잠금 해제 실패: " + unlockEx.getMessage());
                    break;
                }
            } catch (Exception e) {
                System.out.println("이체 실패: " + e.getMessage());
                if (e instanceof BankAccount.InvalidTransferAmountException ||
                        e instanceof BankAccount.AccountValidationException) {
                    // 이런 예외는 재시도해도 동일한 결과가 예상되므로 즉시 중단
                    System.out.println("오류 수정이 필요하여 재시도하지 않습니다.");
                    break;
                }
            }
        }

        if (!success) {
            System.out.println(maxRetries + "번 시도 후 이체 실패");
        }
    }
}
