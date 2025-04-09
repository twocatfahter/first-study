package exception.API요청처리시스템;

import java.time.LocalDateTime;
import java.util.*;

// 스프링부트
// 백엔드 웹을 구성하기 위한 프레임워크
// controller ->  service  -> Repository -> DB

public class UserController {

    private UserService userService = new UserService();

    /**
     * API 응답 기본 클래스
     */
    public static class ApiResponse<T> {
        private String status; // http status 200 ~ 500번대 200 ok 201 created 클라이언트 (프론트엔드) 400 401 402 403 404 405 500 서버에러
        private T data;

        public ApiResponse(String status, T data) {
            this.status = status;
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public T getData() {
            return data;
        }
    }

    /**
     * API 기본 예외 - API 관련 모든 예외의 부모 클래스
     */
    public static class ApiException extends RuntimeException {
        private final int statusCode;

        public ApiException(String message, int statusCode) {
            super(message);
            this.statusCode = statusCode;
        }

        public ApiException(String message, int statusCode, Throwable cause) {
            super(message, cause);
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    /**
     * 리소스를 찾을 수 없는 예외
     */
    public static class ResourceNotFoundException extends ApiException {
        private final String resourceType;
        private final String resourceId;

        public ResourceNotFoundException(String resourceType, String resourceId) {
            super(resourceType + " not found with id: " + resourceId, 404);
            this.resourceType = resourceType;
            this.resourceId = resourceId;
        }

        public String getResourceType() {
            return resourceType;
        }

        public String getResourceId() {
            return resourceId;
        }
    }

    /**
     * 요청 검증 실패 예외
     */
    public static class ValidationException extends ApiException {
        private final Map<String, String> errors;

        public ValidationException(Map<String, String> errors) {
            super("Validation failed", 400);
            this.errors = errors;
        }

        public Map<String, String> getErrors() {
            return errors;
        }
    }

    /**
     * 중복 리소스 예외
     */
    public static class DuplicateResourceException extends ApiException {
        private final String field;
        private final String value;

        public DuplicateResourceException(String resourceType, String field, String value) {
            super(resourceType + " with " + field + " '" + value + "' already exists", 409);
            this.field = field;
            this.value = value;
        }

        public String getField() {
            return field;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 권한 없음 예외
     */
    public static class UnauthorizedException extends ApiException {
        public UnauthorizedException(String message) {
            super(message, 401);
        }
    }

    /**
     * 접근 금지 예외
     */
    public static class ForbiddenException extends ApiException {
        public ForbiddenException(String message) {
            super(message, 403);
        }
    }

    /**
     * 서버 내부 오류 예외
     */
    public static class InternalServerException extends ApiException {
        public InternalServerException(String message, Throwable cause) {
            super(message, 500, cause);
        }
    }

    /**
     * 오류 응답 클래스
     */
    public static class ErrorResponse {
        private final int status;
        private final String message;
        private final LocalDateTime timestamp;
        private final String path;

        public ErrorResponse(int status, String message, String path) {
            this.status = status;
            this.message = message;
            this.timestamp = LocalDateTime.now();
            this.path = path;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * 검증 오류 응답 클래스
     */
    public static class ValidationErrorResponse extends ErrorResponse {
        private final Map<String, String> errors;

        public ValidationErrorResponse(int status, String message, String path, Map<String, String> errors) {
            super(status, message, path);
            this.errors = errors;
        }

        public Map<String, String> getErrors() {
            return errors;
        }
    }

    /**
     * 사용자 DTO 클래스
     */
    public static class UserDTO {
        private String name;
        private String email;
        private String password;
        private Integer age;

        public UserDTO() {
        }

        public UserDTO(String name, String email, String password, Integer age) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    /**
     * 사용자 클래스
     */
    public static class User {
        private String id;
        private String name;
        private String email;
        private Integer age;
        private LocalDateTime createdAt;

        public User(String name, String email, Integer age) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.email = email;
            this.age = age;
            this.createdAt = LocalDateTime.now();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public Integer getAge() {
            return age;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }

    /**
     * 사용자 서비스 클래스
     */
    public static class UserService {
        private final Map<String, User> users = new HashMap<>();
        private final Map<String, User> usersByEmail = new HashMap<>();

        /**
         * 사용자 생성
         */
        public User createUser(UserDTO userDTO) {
            // 이메일 중복 검사
            if (usersByEmail.containsKey(userDTO.getEmail())) {
                throw new DuplicateResourceException("User", "email", userDTO.getEmail());
            }

            // 사용자 생성
            User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getAge());
            users.put(user.getId(), user);
            usersByEmail.put(user.getEmail(), user);

            return user;
        }

        /**
         * ID로 사용자 조회
         */
        public User getUserById(String userId) {
            User user = users.get(userId);
            if (user == null) {
                throw new ResourceNotFoundException("User", userId);
            }
            return user;
        }

        /**
         * 이메일로 사용자 조회
         */
        public User getUserByEmail(String email) {
            User user = usersByEmail.get(email);
            if (user == null) {
                throw new ResourceNotFoundException("User", "with email " + email);
            }
            return user;
        }

        /**
         * 모든 사용자 조회
         */
        public List<User> getAllUsers() {
            return new ArrayList<>(users.values());
        }

        /**
         * 사용자 삭제
         */
        public void deleteUser(String userId) {
            User user = getUserById(userId);
            users.remove(userId);
            usersByEmail.remove(user.getEmail());
        }
    }

    /**
     * 전역 예외 처리기
     * 모든 API 예외를 일관된 형식의 응답으로 변환
     *
     * @param e 발생한 예외
     * @param path 요청 경로
     * @return 오류 응답
     */
    public ErrorResponse handleException(Exception e, String path) {
        if (e instanceof ValidationException) {
            ValidationException ve = (ValidationException) e;
            return new ValidationErrorResponse(
                    ve.getStatusCode(),
                    ve.getMessage(),
                    path,
                    ve.getErrors()
            );
        } else if (e instanceof ApiException) {
            ApiException ae = (ApiException) e;
            return new ErrorResponse(
                    ae.getStatusCode(),
                    ae.getMessage(),
                    path
            );
        } else {
            // 예상치 못한 예외는 내부 서버 오류로 처리
            return new ErrorResponse(
                    500,
                    "Internal server error",
                    path
            );
        }
    }

    /**
     * 사용자 생성 API
     *
     * @param userDTO 사용자 정보
     * @return API 응답
     */
    public ApiResponse<User> createUser(UserDTO userDTO) {
        try {
            // 요청 검증
            Map<String, String> validationErrors = validateUserDTO(userDTO);
            if (!validationErrors.isEmpty()) {
                throw new ValidationException(validationErrors);
            }

            // 사용자 생성
            User user = userService.createUser(userDTO);
            return new ApiResponse<>("success", user);

        } catch (ApiException e) {
            // API 예외는 그대로 던지기
            throw e;
        } catch (Exception e) {
            // 기타 예외는 InternalServerException으로 포장
            throw new InternalServerException("Failed to create user", e);
        }
    }

    /**
     * 사용자 조회 API
     *
     * @param userId 사용자 ID
     * @return API 응답
     */
    public ApiResponse<User> getUser(String userId) {
        try {
            // ID 검증
            if (userId == null || userId.trim().isEmpty()) {
                throw new ValidationException(
                        Collections.singletonMap("userId", "User ID is required")
                );
            }

            // 사용자 조회
            User user = userService.getUserById(userId);
            return new ApiResponse<>("success", user);

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException("Failed to retrieve user", e);
        }
    }

    /**
     * 모든 사용자 조회 API
     *
     * @return API 응답
     */
    public ApiResponse<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ApiResponse<>("success", users);
        } catch (Exception e) {
            throw new InternalServerException("Failed to retrieve users", e);
        }
    }

    /**
     * 사용자 삭제 API
     *
     * @param userId 사용자 ID
     * @return API 응답
     */
    public ApiResponse<Void> deleteUser(String userId) {
        try {
            // ID 검증
            if (userId == null || userId.trim().isEmpty()) {
                throw new ValidationException(
                        Collections.singletonMap("userId", "User ID is required")
                );
            }

            // 사용자 삭제
            userService.deleteUser(userId);
            return new ApiResponse<>("success", null);

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException("Failed to delete user", e);
        }
    }

    /**
     * 사용자 DTO 검증
     *
     * @param userDTO 검증할 사용자 DTO
     * @return 검증 오류 목록
     */
    private Map<String, String> validateUserDTO(UserDTO userDTO) {
        Map<String, String> errors = new HashMap<>();

        // null 검사
        if (userDTO == null) {
            errors.put("userDTO", "User data is required");
            return errors;
        }

        // 이름 검증
        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            errors.put("name", "Name is required");
        } else if (userDTO.getName().length() < 2) {
            errors.put("name", "Name must be at least 2 characters");
        }

        // 이메일 검증
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            errors.put("email", "Email is required");
        } else if (!userDTO.getEmail().contains("@")) {
            errors.put("email", "Invalid email format");
        }

        // 비밀번호 검증
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            errors.put("password", "Password is required");
        } else if (userDTO.getPassword().length() < 8) {
            errors.put("password", "Password must be at least 8 characters");
        }

        // 나이 검증
        if (userDTO.getAge() != null && userDTO.getAge() < 0) {
            errors.put("age", "Age cannot be negative");
        }

        return errors;
    }
}
