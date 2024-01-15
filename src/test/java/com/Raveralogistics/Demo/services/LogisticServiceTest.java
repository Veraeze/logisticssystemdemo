package com.Raveralogistics.Demo.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import com.Raveralogistics.Demo.data.model.*;
import com.Raveralogistics.Demo.data.repository.BookingRepository;
import com.Raveralogistics.Demo.data.repository.FeedbackRepository;
import com.Raveralogistics.Demo.data.repository.UserRepository;
import com.Raveralogistics.Demo.dtos.request.*;
import com.Raveralogistics.Demo.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogisticServiceTest {
    @Autowired
    LogisticService ravera;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    FeedbackRepository feedbackRepository;

    @AfterEach
    public void delete(){
        userRepository.deleteAll();
        bookingRepository.deleteAll();
        feedbackRepository.deleteAll();
    }

    @Test
    void testThatAUserCanRegister(){

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertEquals(1, userRepository.count());

    }

    @Test
    void testThatExceptionIsThrownWhenUserRegistersWithAnAlreadyRegisteredName() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertThrows(UserNameNotAvailable.class, ()-> ravera.register(registerRequest));

    }

    @Test
    void testThatUserCanLogInWithValidNameAndPassword() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

    }

    @Test
    void testExceptionIsThrown_UserTriesToLogInWithWrongPassword() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("passwordsss");

        assertThrows(IncorrectDetails.class, ()-> ravera.login(loginRequest));

    }

    @Test
    void testExceptionIsThrown_UserTriesToLogInWithWrongName() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("verant");
        loginRequest.setPassword("password");

        assertThrows(IncorrectDetails.class, ()-> ravera.login(loginRequest));

    }

    @Test
    void testExceptionIsThrown_UserTriesToLogInWithWrongNameAndPassword() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("verant");
        loginRequest.setPassword("passwordss");

        assertThrows(IncorrectDetails.class, ()-> ravera.login(loginRequest));

    }

    @Test
    void testThatUserCanLogOut() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        ravera.logout(loginRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

    }

    @Test
    void testThatRegisteredUserCanDepositMoneyIntoWalletAfterSuccessfulLogin() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        ravera.depositMoneyIntoWallet(depositMoneyRequest);
        assertEquals(BigDecimal.valueOf(3000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

    }

    @Test
    void testThatExceptionIsThrown_DepositMoneyIntoWalletWithoutLogin() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));

        assertThrows(LoginError.class, ()-> ravera.depositMoneyIntoWallet(depositMoneyRequest));
    }

    @Test
    void testThatExceptionIsThrown_DepositMoneyLessThan0_AfterSuccessfulLogin() {

        RegisterRequest registerRequest = request("susan", "08093280634", "veraeze18@gmail.com", "pin", address("pentville", "maitama", "abuja", "nigeria", "11002"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("susan");
        loginRequest.setPassword("pin");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("susan").isLoggedIn());

        User user = ravera.findAccountBelongingTo("susan");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(-1000));

        assertThrows(InvalidAmount.class, ()-> ravera.depositMoneyIntoWallet(depositMoneyRequest));

    }

    @Test
    void testThatRegisteredUserCanLogin_DepositMoneyIntoWallet_WithdrawMoneyFromWallet() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));

        ravera.depositMoneyIntoWallet(depositMoneyRequest);
        assertEquals(BigDecimal.valueOf(3000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

        ravera.withdrawMoneyFromWallet(user.getUserId(), BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(2000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

    }

    @Test
    void testThatExceptionIsThrown_RegisteredUserCanLogin_DepositMoneyIntoWallet_WithdrawMoneyGreaterThanBalance() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));

        ravera.depositMoneyIntoWallet(depositMoneyRequest);
        assertEquals(BigDecimal.valueOf(3000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

        assertThrows(InsufficientFunds.class, ()->ravera.withdrawMoneyFromWallet(user.getUserId(), BigDecimal.valueOf(5000)));

    }

    @Test
    void testThatExceptionIsThrown_RegisteredUserCanLogin_DepositMoneyIntoWallet_WithdrawAmountLessThanOne() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));

        ravera.depositMoneyIntoWallet(depositMoneyRequest);
        assertEquals(BigDecimal.valueOf(3000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

        assertThrows(InvalidAmount.class, ()->ravera.withdrawMoneyFromWallet(user.getUserId(), BigDecimal.valueOf(0)));

    }


    @Test
    void testThatRegisteredUserCanLogInAndBookAService() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        Sender sender = new Sender();
        sender.setName("vera");
        sender.setPhoneNumber("08093280641");
        sender.setEmailAddress("veraeze@gmail.com");
        sender.setHomeAddress(address("alaka", "lekki", "lagos", "Nigeria", "12001"));

        Customer receiver = new Customer();
        receiver.setName("susan");
        receiver.setPhoneNumber("0803280625");
        receiver.setEmail("susan@yahoo.com");
        receiver.setHomeAddress(address("pentville", "maitama", "abuja", "nigeria", "11002"));

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        ravera.depositMoneyIntoWallet(depositMoneyRequest);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getUserId());
        bookingRequest.setParcelName("Hair");
        bookingRequest.setCost(BigDecimal.valueOf(500));

        ravera.bookService(bookingRequest);
        assertEquals(1, bookingRepository.count());

    }

    @Test
    void testThatRegisteredUserCanLogInAndBookAServiceMoreThanOnce() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        Sender sender = new Sender();
        sender.setName("vera");
        sender.setPhoneNumber("08093280641");
        sender.setEmailAddress("veraeze@gmail.com");
        sender.setHomeAddress(address("alaka", "lekki", "lagos", "Nigeria", "12001"));

        Customer receiver = new Customer();
        receiver.setName("susan");
        receiver.setPhoneNumber("0803280625");
        receiver.setEmail("susan@yahoo.com");
        receiver.setHomeAddress(address("pentville", "maitama", "abuja", "nigeria", "11002"));

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        ravera.depositMoneyIntoWallet(depositMoneyRequest);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getUserId());
        bookingRequest.setParcelName("Hair");
        bookingRequest.setCost(BigDecimal.valueOf(500));

        ravera.bookService(bookingRequest);
        ravera.bookService(bookingRequest);
        Booking booking = ravera.bookService(bookingRequest);
        assertEquals(3, bookingRepository.count());
        assertTrue(booking.isBooked());

    }

    @Test
    void testThatExceptionIsThrown_BookAServiceWithoutLogin() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        Sender sender = new Sender();
        sender.setName("vera");
        sender.setPhoneNumber("08093280641");
        sender.setEmailAddress("veraeze@gmail.com");
        sender.setHomeAddress(address("alaka", "lekki", "lagos", "Nigeria", "12001"));

        Customer receiver = new Customer();
        receiver.setName("susan");
        receiver.setPhoneNumber("0803280625");
        receiver.setEmail("susan@yahoo.com");
        receiver.setHomeAddress(address("pentville", "maitama", "abuja", "nigeria", "11002"));

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        assertThrows(LoginError.class, ()->ravera.depositMoneyIntoWallet(depositMoneyRequest));

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getUserId());
        bookingRequest.setParcelName("Hair");
        bookingRequest.setCost(BigDecimal.valueOf(500));

        assertThrows(LoginError.class, ()-> ravera.bookService(bookingRequest));

    }

    @Test
    void testThatRegisteredUserCanLogInAndDropAFeedbackAfterServiceRendered() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        Sender sender = new Sender();
        sender.setName("vera");
        sender.setPhoneNumber("08093280641");
        sender.setEmailAddress("veraeze@gmail.com");
        sender.setHomeAddress(address("alaka", "lekki", "lagos", "Nigeria", "12001"));

        Customer receiver = new Customer();
        receiver.setName("susan");
        receiver.setPhoneNumber("0803280625");
        receiver.setEmail("susan@yahoo.com");
        receiver.setHomeAddress(address("pentville", "maitama", "abuja", "nigeria", "11002"));

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        ravera.depositMoneyIntoWallet(depositMoneyRequest);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getUserId());
        bookingRequest.setParcelName("Hair");
        bookingRequest.setCost(BigDecimal.valueOf(500));

        ravera.bookService(bookingRequest);
        assertEquals(1, bookingRepository.count());

        FeedbackRequest feedbackRequest = new FeedbackRequest();
        feedbackRequest.setUserId(user.getUserId());
        feedbackRequest.setBookingId("RVA1");
        feedbackRequest.setFeedBack("Nice service, order received");

        ravera.addFeedback(feedbackRequest);
        assertEquals(1,feedbackRepository.count());

    }

    @Test
    void testThatExceptionIsThrown_RegisteredUserDropsAFeedbackAfterServiceRendered_UsingWrongBookingId() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        Sender sender = new Sender();
        sender.setName("vera");
        sender.setPhoneNumber("08093280641");
        sender.setEmailAddress("veraeze@gmail.com");
        sender.setHomeAddress(address("alaka", "lekki", "lagos", "Nigeria", "12001"));

        Customer receiver = new Customer();
        receiver.setName("susan");
        receiver.setPhoneNumber("0803280625");
        receiver.setEmail("susan@yahoo.com");
        receiver.setHomeAddress(address("pentville", "maitama", "abuja", "nigeria", "11002"));

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        ravera.depositMoneyIntoWallet(depositMoneyRequest);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getUserId());
        bookingRequest.setParcelName("Hair");
        bookingRequest.setCost(BigDecimal.valueOf(500));

        ravera.bookService(bookingRequest);
        assertEquals(1, bookingRepository.count());

        FeedbackRequest feedbackRequest = new FeedbackRequest();
        feedbackRequest.setUserId(user.getUserId());
        feedbackRequest.setBookingId("RVA10t");
        feedbackRequest.setFeedBack("Nice service, order received");

        assertThrows(BookingNotFound.class, ()->ravera.addFeedback(feedbackRequest));

    }

    @Test
    void testThatExceptionIsThrown_RegisteredUserDropsAFeedbackAfterServiceRendered_UsingWrongUserId() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        Sender sender = new Sender();
        sender.setName("vera");
        sender.setPhoneNumber("08093280641");
        sender.setEmailAddress("veraeze@gmail.com");
        sender.setHomeAddress(address("alaka", "lekki", "lagos", "Nigeria", "12001"));

        Customer receiver = new Customer();
        receiver.setName("susan");
        receiver.setPhoneNumber("0803280625");
        receiver.setEmail("susan@yahoo.com");
        receiver.setHomeAddress(address("pentville", "maitama", "abuja", "nigeria", "11002"));

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        ravera.depositMoneyIntoWallet(depositMoneyRequest);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getUserId());
        bookingRequest.setParcelName("Hair");
        bookingRequest.setCost(BigDecimal.valueOf(500));

        ravera.bookService(bookingRequest);
        assertEquals(1, bookingRepository.count());

        FeedbackRequest feedbackRequest = new FeedbackRequest();
        feedbackRequest.setUserId("ABC2");
        feedbackRequest.setBookingId("RVA1");
        feedbackRequest.setFeedBack("Nice service, order received");

        assertThrows(InvalidUserId.class, ()->ravera.addFeedback(feedbackRequest));

    }

    @Test
    void testThatRegisteredUserCanLoginAndCheckWalletBalance() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        Sender sender = new Sender();
        sender.setName("vera");
        sender.setPhoneNumber("08093280641");
        sender.setEmailAddress("veraeze@gmail.com");
        sender.setHomeAddress(address("alaka", "lekki", "lagos", "Nigeria", "12001"));

        Customer receiver = new Customer();
        receiver.setName("susan");
        receiver.setPhoneNumber("0803280625");
        receiver.setEmail("susan@yahoo.com");
        receiver.setHomeAddress(address("pentville", "maitama", "abuja", "nigeria", "11002"));

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        ravera.depositMoneyIntoWallet(depositMoneyRequest);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getUserId());
        bookingRequest.setParcelName("Hair");
        bookingRequest.setCost(BigDecimal.valueOf(500));

        ravera.bookService(bookingRequest);
        assertEquals(1, bookingRepository.count());

        assertEquals(BigDecimal.valueOf(2500), ravera.checkWalletBalance(user.getUserId()));
    }

    @Test
    void testThatExceptionIsThrown_RegisteredUserTriesToCheckWalletBalanceWithWrongUserId() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        Sender sender = new Sender();
        sender.setName("vera");
        sender.setPhoneNumber("08093280641");
        sender.setEmailAddress("veraeze@gmail.com");
        sender.setHomeAddress(address("alaka", "lekki", "lagos", "Nigeria", "12001"));

        Customer receiver = new Customer();
        receiver.setName("susan");
        receiver.setPhoneNumber("0803280625");
        receiver.setEmail("susan@yahoo.com");
        receiver.setHomeAddress(address("pentville", "maitama", "abuja", "nigeria", "11002"));

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        ravera.depositMoneyIntoWallet(depositMoneyRequest);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getUserId());
        bookingRequest.setParcelName("Hair");
        bookingRequest.setCost(BigDecimal.valueOf(500));

        ravera.bookService(bookingRequest);
        assertEquals(1, bookingRepository.count());

        assertThrows(InvalidUserId.class, ()-> ravera.checkWalletBalance("uyyu"));
    }

    private static Address address(String street, String city, String state, String country, String zipCode){
        Address homeAddress = new Address();
        homeAddress.setStreet(street);
        homeAddress.setCity(city);
        homeAddress.setState(state);
        homeAddress.setCountry(country);
        homeAddress.setZipCode(zipCode);
        return homeAddress;
    }

    private static RegisterRequest request(String name, String phoneNumber, String email, String password, Address homeAddress){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(name);
        registerRequest.setPhoneNumber(phoneNumber);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setHomeAddress(homeAddress);
        return registerRequest;
    }
}