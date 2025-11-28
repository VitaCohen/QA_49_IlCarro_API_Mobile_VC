package rest_tests;

import dto.ErrorMessageDto;
import dto.RegistrationBodyDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import rest_api.AuthenticationController;

import java.util.Random;

public class RegistrationTestsRest extends AuthenticationController {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void registrationPositiveTest(){
        int i = new Random(). nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("eva_b" + i + "@mail.com")
                .password("123456Ee!")
                .firstName("eva")
                .lastName("brow")
                .build();
        Assert.assertEquals(registrationLogin(user, REGISTRATION_URL).getStatusCode(),200);

    }


    // "eva_b" + i + "mail.com"
    // "@mail.com"
    // "eva_b@m"
    // "eva_b@@mail.com"
    // "eva_b@m."
    // "eva_b@m. r"
    // "eva_b+"12345678901234567"+ @mail.com"
    //" eva_b@mail.com "
   // "eva b@mail.com"
    // "eva_b@mail. com"
    // "ева b+ i + @mail.com"
    // "eva_b+ i + @mail..com"

    @Test
    public void registrationNegativeTest_WrongEmail(){
        int i = new Random(). nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("eva_b" + i + "mail.com")
                .password("123456Ee!")
                .firstName("eva")
                .lastName("brow")
                .build();
        Response response = registrationLogin(user, REGISTRATION_URL);
        System.out.println(response.getBody().print());
        softAssert.assertEquals(response
                .getStatusCode(),400, "validate staus code");
        ErrorMessageDto errorMessageDto = response.getBody()
                .as(ErrorMessageDto.class);
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request", "validate ErrorName");
        softAssert.assertTrue(errorMessageDto.getMessage().toString()
                .contains(" well-formed email address"), "validate message");
        softAssert.assertAll();

    }



}
