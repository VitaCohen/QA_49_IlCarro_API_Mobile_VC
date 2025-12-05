package rest_tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dto.CarDto;
import dto.ErrorMessageDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import rest_api.CarController;

import java.util.Random;

public class AddNewCarTestsRest extends CarController {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void  addNewCarPositiveTest(){
        int i = new Random().nextInt(1000) + 1000;
        CarDto car = CarDto.builder()
                .serialNumber("123-" +i)
                .manufacture("Toyota")
                .model("Cross")
                .year("2024")
                .fuel("Hybrid")
                .seats(5)
                .carClass("A")
                .pricePerDay(25.3)
                .city("Haifa")
                .build();
        Response response = addNewCar(car);
        System.out.println(car);
        System.out.println(response.getBody().print());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void  addNewCarNegativeTest_DuplicateCar(){
        int i = new Random().nextInt(1000) + 1000;
        CarDto car = CarDto.builder()
                .serialNumber("123-1421")
                .manufacture("Toyota")
                .model("Cross")
                .year("2024")
                .fuel("Hybrid")
                .seats(5)
                .carClass("A")
                .pricePerDay(25.3)
                .city("Haifa")
                .build();
        Response response = addNewCar(car);
        System.out.println(response.getBody().print());
        softAssert.assertEquals(response.getStatusCode(), 400);
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("already exists"), "validate error message");
        softAssert.assertAll();
    }


    @Test
    public void  addNewCarNegativeTest_WrongAuthorization(){
        int i = new Random().nextInt(1000) + 1000;
        CarDto car = CarDto.builder()
                .serialNumber("563 -" +i)
                .manufacture("Toyota")
                .model("Cross")
                .year("2024")
                .fuel("Hybrid")
                .seats(5)
                .carClass("A")
                .pricePerDay(25.3)
                .city("Haifa")
                .build();
        Response response = addNewCar_WrongToken(car, "gy4545uhgyhugjhbhj");
        System.out.println(response.getBody().print());
       softAssert.assertEquals(response.getStatusCode(), 401, "validate status code");
       softAssert.assertTrue(response.getBody().print().contains("strings must contain exactly 2 period characters."), "validate error message");
       softAssert.assertAll();
    }

    @Test
    public void  addNewCarNegativeTest_WOAuthorization(){
        int i = new Random().nextInt(1000) + 1000;
        CarDto car = CarDto.builder()
                .serialNumber("563 -" +i)
                .manufacture("Toyota")
                .model("Cross")
                .year("2024")
                .fuel("Hybrid")
                .seats(5)
                .carClass("A")
                .pricePerDay(25.3)
                .city("Haifa")
                .build();
        Response response = addNewCar_WOToken(car);
        System.out.println(response.getBody().print());
        softAssert.assertEquals(response.getStatusCode(), 403, "validate status code");
        softAssert.assertAll();
    }

    @Test
    public void  addNewCarNegativeTest_EmptyField(){
        int i = new Random().nextInt(1000) + 1000;
        CarDto car = CarDto.builder()
                .serialNumber("")
                .manufacture("Toyota")
                .model("Cross")
                .year("2024")
                .fuel("Hybrid")
                .seats(5)
                .carClass("A")
                .pricePerDay(25.3)
                .city("Haifa")
                .build();
        Response response = addNewCar(car);
        System.out.println(response.getBody().print());
        softAssert.assertEquals(response.getStatusCode(), 400);
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"), "validate error message");
        softAssert.assertAll();
    }


    @Test
    public void  addNewCarNegativeTest_WrongField(){
        int i = new Random().nextInt(1000) + 1000;
        CarDto car = CarDto.builder()
                .serialNumber("563 -" +i)
                .manufacture("Toyota") // max length
                .model("Cross") // max length
                .year("20 25") // 2026, // -1(BUG) only these; double 20.25(BUG); yyyy(BUG); 20 25(BUG); BUG!!!!!
                .fuel("Hybrid") //"crtic"(BUG); add all Strings
                .seats(5) // 1, 21; 0; -1; BUG!!!!!!!!!! only empty or int
                .carClass("A")
                .pricePerDay(25.3) //-0.00001 1000.00001
                .city("Haifa")// wrong city
                .build();
        Response response = addNewCar(car);
        System.out.println(response.getBody().print());
        softAssert.assertEquals(response.getStatusCode(), 400);
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"), "validate error message");
        softAssert.assertAll();
    }




}
