package DataGenerator;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import lombok.val;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static RegistrationDto sendRequest(RegistrationDto user) {
        // TODO: отправить запрос на указанный в требованиях path, передав в body запроса объект user
        //  и не забудьте передать подготовленную спецификацию requestSpec.
        //  Пример реализации метода показан в условии к задаче.
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post( "/api/system/users")
                .then()
                .statusCode(200);
        return user;
    }

    public static String getRandomLogin() {
        // TODO: добавить логику для объявления переменной login и задания её значения, для генерации
        //  случайного логина используйте faker
        return FAKER.name().username();
    }

    public static String getRandomPassword() {
        // TODO: добавить логику для объявления переменной password и задания её значения, для генерации
        //  случайного пароля используйте faker
        return FAKER.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            // TODO: создать пользователя user используя методы getRandomLogin(), getRandomPassword() и параметр status
            return new RegistrationDto(getRandomLogin(),getRandomPassword(),status);
        }

        public static RegistrationDto getRegisteredUser(String status) {
            // TODO: объявить переменную registeredUser и присвоить ей значение возвращённое getUser(status).
            // Послать запрос на регистрацию пользователя с помощью вызова sendRequest(registeredUser)
            return sendRequest(getUser(status));
        }
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}