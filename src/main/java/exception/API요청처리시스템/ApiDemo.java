package exception.API요청처리시스템;

import java.util.List;

public class ApiDemo {

    public static void main(String[] args) {
        System.out.println("===== API요청처리시스템 예외 처리 데모 =====\n");

        // UserController 인스턴스 생성
        UserController userController = new UserController();

        // 요청 경로 - 실제 API에서는 요청 URL
        String requestPath = "/api/users";

        System.out.println("시나리오 1: 유효한 사용자 생성");
        try {
            // 유효한 사용자 데이터
            UserController.UserDTO validUser = new UserController.UserDTO(
                    "John Doe",
                    "john.doe@example.com",
                    "password123",
                    30
            );

            // 사용자 생성 API 호출
            UserController.ApiResponse<UserController.User> response = userController.createUser(validUser);

            // 응답 출력
            System.out.println("상태: " + response.getStatus());
            System.out.println("생성된 사용자:");
            System.out.println("  ID: " + response.getData().getId());
            System.out.println("  이름: " + response.getData().getName());
            System.out.println("  이메일: " + response.getData().getEmail());
            System.out.println("  나이: " + response.getData().getAge());
            System.out.println("  생성 시간: " + response.getData().getCreatedAt());

        } catch (Exception e) {
            // 예외 발생 시 오류 처리
            UserController.ErrorResponse errorResponse = userController.handleException(e, requestPath);
            printErrorResponse(errorResponse);
        }
        System.out.println();

        System.out.println("시나리오 2: 유효하지 않은 사용자 생성 (검증 실패)");
        try {
            // 유효하지 않은 사용자 데이터
            UserController.UserDTO invalidUser = new UserController.UserDTO(
                    "",  // 이름 없음
                    "invalid-email",  // 유효하지 않은 이메일
                    "short",  // 짧은 비밀번호
                    -5  // 음수 나이
            );

            // 사용자 생성 API 호출
            userController.createUser(invalidUser);

        } catch (Exception e) {
            // 예외 발생 시 오류 처리
            UserController.ErrorResponse errorResponse = userController.handleException(e, requestPath);
            printErrorResponse(errorResponse);

            // ValidationErrorResponse인 경우 상세 오류 출력
            if (errorResponse instanceof UserController.ValidationErrorResponse) {
                UserController.ValidationErrorResponse validationError =
                        (UserController.ValidationErrorResponse) errorResponse;

                System.out.println("검증 오류 상세:");
                for (String field : validationError.getErrors().keySet()) {
                    System.out.println("  " + field + ": " + validationError.getErrors().get(field));
                }
            }
        }
        System.out.println();

        System.out.println("시나리오 3: 중복 이메일로 사용자 생성");
        try {
            // 첫 번째 사용자 생성 (성공)
            UserController.UserDTO user1 = new UserController.UserDTO(
                    "Alice Smith",
                    "alice@example.com",
                    "password123",
                    25
            );
            userController.createUser(user1);

            // 같은 이메일로 두 번째 사용자 생성 시도 (실패 예상)
            UserController.UserDTO user2 = new UserController.UserDTO(
                    "Alice Jones",  // 다른 이름
                    "alice@example.com",  // 같은 이메일
                    "different123",
                    30
            );
            userController.createUser(user2);

        } catch (Exception e) {
            // 예외 발생 시 오류 처리
            UserController.ErrorResponse errorResponse = userController.handleException(e, requestPath);
            printErrorResponse(errorResponse);
        }
        System.out.println();

        System.out.println("시나리오 4: 존재하지 않는 사용자 조회");
        try {
            // 존재하지 않는 ID로 사용자 조회
            String nonExistentId = "non-existent-id";
            userController.getUser(nonExistentId);

        } catch (Exception e) {
            // 예외 발생 시 오류 처리
            UserController.ErrorResponse errorResponse = userController.handleException(e, requestPath + "/" + "non-existent-id");
            printErrorResponse(errorResponse);
        }
        System.out.println();

        System.out.println("시나리오 5: 모든 사용자 조회");
        try {
            // 모든 사용자 조회
            UserController.ApiResponse<List<UserController.User>> response = userController.getAllUsers();

            // 응답 출력
            System.out.println("상태: " + response.getStatus());
            System.out.println("총 사용자 수: " + response.getData().size());

            if (!response.getData().isEmpty()) {
                System.out.println("사용자 목록:");
                for (UserController.User user : response.getData()) {
                    System.out.println("  " + user.getName() + " (" + user.getEmail() + ")");
                }
            }

        } catch (Exception e) {
            // 예외 발생 시 오류 처리
            UserController.ErrorResponse errorResponse = userController.handleException(e, requestPath);
            printErrorResponse(errorResponse);
        }
        System.out.println();

        // 추가 사용자 생성
        String bobId = createTestUser(userController, "Bob Johnson", "bob@example.com", "password456", 40);

        System.out.println("시나리오 6: 존재하는 사용자 조회");
        try {
            if (bobId != null) {
                // 존재하는 ID로 사용자 조회
                UserController.ApiResponse<UserController.User> response = userController.getUser(bobId);

                // 응답 출력
                System.out.println("상태: " + response.getStatus());
                System.out.println("조회된 사용자:");
                System.out.println("  ID: " + response.getData().getId());
                System.out.println("  이름: " + response.getData().getName());
                System.out.println("  이메일: " + response.getData().getEmail());
            }

        } catch (Exception e) {
            // 예외 발생 시 오류 처리
            UserController.ErrorResponse errorResponse = userController.handleException(e, requestPath + "/" + bobId);
            printErrorResponse(errorResponse);
        }
        System.out.println();

        System.out.println("시나리오 7: 사용자 삭제");
        try {
            if (bobId != null) {
                // 사용자 삭제
                UserController.ApiResponse<Void> response = userController.deleteUser(bobId);

                // 응답 출력
                System.out.println("상태: " + response.getStatus());
                System.out.println("사용자 삭제 완료");

                // 삭제된 사용자를 다시 조회해 보기 (실패 예상)
                userController.getUser(bobId);
            }

        } catch (Exception e) {
            // 예외 발생 시 오류 처리
            UserController.ErrorResponse errorResponse = userController.handleException(e, requestPath + "/" + bobId);
            printErrorResponse(errorResponse);
        }

        System.out.println("\nAPI요청처리시스템 데모 완료!");
    }

    /**
     * 오류 응답 출력
     */
    private static void printErrorResponse(UserController.ErrorResponse errorResponse) {
        System.out.println("오류 응답:");
        System.out.println("  상태 코드: " + errorResponse.getStatus());
        System.out.println("  메시지: " + errorResponse.getMessage());
        System.out.println("  시간: " + errorResponse.getTimestamp());
        System.out.println("  경로: " + errorResponse.getPath());
    }

    /**
     * 테스트 사용자 생성 도우미 메서드
     */
    private static String createTestUser(UserController controller, String name, String email, String password, Integer age) {
        try {
            UserController.UserDTO userDTO = new UserController.UserDTO(name, email, password, age);
            UserController.ApiResponse<UserController.User> response = controller.createUser(userDTO);
            return response.getData().getId();
        } catch (Exception e) {
            System.out.println("테스트 사용자 생성 실패: " + e.getMessage());
            return null;
        }
    }
}

