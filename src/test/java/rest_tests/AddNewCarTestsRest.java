package rest_tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dto.CarDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import rest_api.CarController;

import java.util.Random;

public class AddNewCarTestsRest extends CarController {

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
        System.out.println(response.getBody().print());
        Assert.assertEquals(response.getStatusCode(), 200);
    }


}
