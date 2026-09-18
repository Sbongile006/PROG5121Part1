/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.prog5121part1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    @Test
public void testCheckUserNameCorrect() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertTrue(login.checkUserName());
}

@Test
public void testCheckUserNameIncorrect() {
    Login login = new Login(
            "kyle!!!!!!",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertFalse(login.checkUserName());
}

@Test
public void testCheckPasswordComplexityCorrect() {
    Login login = new Login(
            "kyl_1",
           "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertTrue(login.checkPasswordComplexity());
}
@Test
public void testCheckPasswordComplexityIncorrect() {
    Login login = new Login(
            "kyl_1",
            "password",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertFalse(login.checkPasswordComplexity());
}
@Test
public void testCheckCellPhoneNumberCorrect() {
    Login login = new Login(
            "kyl_1",
           "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertTrue(login.checkCellPhoneNumber());
}


    @Test
public void testCheckCellPhoneNumberIncorrect() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "08966553",
            "Sbongile",
            "Coka"
    );

    assertFalse(login.checkCellPhoneNumber());
}
@Test
public void testLoginSuccessful() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
}
@Test
public void testLoginFailed() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertFalse(login.loginUser("wrongUser", "wrongPassword"));
}

@Test
public void testUsernameCorrectAssertEquals() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertEquals(true, login.checkUserName());
}
@Test
public void testUsernameIncorrectAssertEquals() {
    Login login = new Login(
            "kyle!!!!!!",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertEquals(false, login.checkUserName());
}
@Test
public void testPasswordCorrectAssertEquals() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertEquals(true, login.checkPasswordComplexity());
}
@Test
public void testPasswordIncorrectAssertEquals() {
    Login login = new Login(
            "kyl_1",
            "password",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertEquals(false, login.checkPasswordComplexity());
}
@Test
public void testCellPhoneCorrectAssertEquals() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertEquals(true, login.checkCellPhoneNumber());
}
@Test
public void testCellPhoneIncorrectAssertEquals() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "08966553",
            "Sbongile",
            "Coka"
    );

    assertEquals(false, login.checkCellPhoneNumber());
}
@Test
public void testReturnLoginStatusSuccessful() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertEquals(
            "Welcome Sbongile Coka, it is great to see you again.",
            login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!")
    );
}
@Test
public void testReturnLoginStatusFailed() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertEquals(
            "Username or password incorrect, please try again.",
            login.returnLoginStatus("wrongUser", "wrongPassword")
    );
}
@Test
public void testRegisterUserSuccessful() {
    Login login = new Login(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertEquals(
            "User has been registered successfully.",
            login.registerUser()
    );
}
@Test
public void testRegisterUserIncorrectUsername() {
    Login login = new Login(
            "kyle!!!!!!",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Sbongile",
            "Coka"
    );

    assertEquals(
            "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
            login.registerUser()
    );
}



    
    
}
