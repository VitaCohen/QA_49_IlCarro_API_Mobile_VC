package rest_api;

import dto.CarDto;
import dto.RegistrationBodyDto;
import dto.TokenDto;
import interfaces.Base_Api;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import static io.restassured.RestAssured.given;

import java.util.Locale;

public class CarController implements Base_Api {

    public TokenDto tokenDto;


    @BeforeSuite
    public void login(){
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("eva_b537@mail.com")
                .password("123456Ee!")
                .build();
        tokenDto = given()
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + LOGIN_URL)
                .thenReturn()
                .getBody()
                .as(TokenDto.class);
        System.out.println(tokenDto.getAccessToken());
    }

    public Response addNewCar(CarDto car){
        return given()
                .body(car)
                .contentType(ContentType.JSON)
                .header("Authorization", tokenDto.getAccessToken())
                .when()
                .post(BASE_URL + ADD_NEW_CAR_URL)
                .thenReturn();
    }

    public Response addNewCar_WrongToken(CarDto car, String token){
        return given()
                .body(car)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post(BASE_URL + ADD_NEW_CAR_URL)
                .thenReturn();
    }

    public Response addNewCar_WOToken(CarDto car){
        return given()
                .body(car)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + ADD_NEW_CAR_URL)
                .thenReturn();
    }


    public Response getAllUserCars(){
        return  given()
                .contentType(ContentType.JSON)
                .header("Authorization", tokenDto.getAccessToken())
                .when()
                .get(BASE_URL + GET_ALL_USER_CAR_URL)
                .thenReturn();

    }



}
