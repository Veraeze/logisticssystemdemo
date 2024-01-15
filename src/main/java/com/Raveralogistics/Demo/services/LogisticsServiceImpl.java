package com.Raveralogistics.Demo.services;

import com.Raveralogistics.Demo.data.model.Booking;
import com.Raveralogistics.Demo.data.model.Feedback;
import com.Raveralogistics.Demo.data.model.User;
import com.Raveralogistics.Demo.data.model.Wallet;
import com.Raveralogistics.Demo.data.repository.BookingRepository;
import com.Raveralogistics.Demo.data.repository.FeedbackRepository;
import com.Raveralogistics.Demo.data.repository.UserRepository;
import com.Raveralogistics.Demo.dtos.request.*;
import com.Raveralogistics.Demo.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.Raveralogistics.Demo.utils.Mapper.*;

@Service
public class LogisticsServiceImpl implements LogisticService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookingServiceImpl bookingService;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    FeedbackServiceImpl feedbackService;
    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public User register(RegisterRequest registerRequest) {
        if (validateUsername(registerRequest.getName())) throw new UserNameNotAvailable(registerRequest.getName() + " already exists!");

        User user = map(registerRequest.getName(), registerRequest.getPassword(),registerRequest.getPhoneNumber(),
                registerRequest.getEmail(), registerRequest.getHomeAddress(), "URV" + (userRepository.count()+1));

        Wallet wallet = new Wallet();
        user.setWallet(wallet);

        userRepository.save(user);
        return user;
    }

    @Override
    public void login(LoginRequest loginRequest) {
      User user = userRepository.findUserBy(loginRequest.getName());
      if (!validateUsername(loginRequest.getName())){
          throw new AccountDoesNotExist("Account with this username does not exist!");
      }
      if (!loginRequest.getName().equals(user.getName())){
          throw new IncorrectDetails("Invalid username or password");
      }
      if (!loginRequest.getPassword().equals(user.getPassword())){
          throw new IncorrectDetails("Invalid username or password");
      }
      user.setLoggedIn(true);
      userRepository.save(user);
    }



    @Override
    public User findAccountBelongingTo(String name) {
        User user = userRepository.findUserBy(name);
        if (user == null) throw new AccountDoesNotExist("Account with this username does not exist!");
        return user;
    }

    @Override
    public void logout(LoginRequest loginRequest) {
        User user = userRepository.findUserBy(loginRequest.getName());
        user.setLoggedIn(false);
        userRepository.save(user);
    }

    @Override
    public void depositMoneyIntoWallet(DepositMoneyRequest depositMoneyRequest) {

        User user = userRepository.findUserBy(depositMoneyRequest.getUserId());

            validateUser(user, user.getUserId());
            validateLogin(user);

        Wallet wallet = user.getWallet();
        BigDecimal walletBalance = wallet.getBalance();

        validateAmount(depositMoneyRequest.getAmount());
        wallet.setBalance(walletBalance.add(depositMoneyRequest.getAmount()));

        userRepository.save(user);
    }

    @Override
    public void withdrawMoneyFromWallet(String userId, BigDecimal amount) {
        User user = userRepository.findUserBy(userId);

        validateUser(user, userId);
        validateLogin(user);

        Wallet wallet = user.getWallet();
        BigDecimal walletBalance = wallet.getBalance();

        validateAmount(amount);
        validateSufficientFund(walletBalance, amount);
        wallet.setBalance(walletBalance.subtract(amount));

        userRepository.save(user);
    }

    @Override
    public Booking bookService(BookingRequest bookingRequest) {
        User user = userRepository.findUserBy(bookingRequest.getUserId());

        validateUser(user, bookingRequest.getUserId());
        validateLogin(user);

        withdrawMoneyFromWallet(bookingRequest.getUserId(), bookingRequest.getCost());

        return bookingService.book("RVA" + (bookingRepository.count() + 1), bookingRequest.getSenderInfo(),
                bookingRequest.getReceiverInfo(), bookingRequest.getUserId(),
                bookingRequest.getParcelName(), LocalDateTime.now()
        );
    }

    @Override
    public Feedback addFeedback(FeedbackRequest feedbackRequest) {
        User user = userRepository.findUserBy(feedbackRequest.getUserId());

        validateUser(user, feedbackRequest.getUserId());
        validateLogin(user);

        Booking booking = bookingRepository.findBookingByBookingId(feedbackRequest.getBookingId());
        if (booking == null) throw new BookingNotFound("This booking ID cannot be found");
        if (!feedbackRequest.getUserId().equals(user.getUserId())){
            throw new InvalidUserId("User ID incorrect, try again!");
        }
        return feedbackService.feedback("AB" + (feedbackRepository.count()+1),feedbackRequest.getUserId(),
                feedbackRequest.getBookingId(),feedbackRequest.getFeedBack());

    }

    @Override
    public BigDecimal checkWalletBalance(String userId) {
        User user = userRepository.findUserBy(userId);

        validateUser(user, userId);
        validateLogin(user);

        if (!userId.equals(user.getUserId())){
            throw new InvalidUserId("User ID incorrect, try again!");
        }
        return user.getWallet().getBalance();
    }

    @Override
    public Object findListOfBookingOf(String username) {
        User user = findAccountBelongingTo(username);

        List<Booking> bookingList = new ArrayList<>();

        for (Booking booking: bookingRepository.findAll()){
            if (booking.getUserId().equals(user.getUserId())) bookingList.add(booking);
        }
        return bookingList;
    }

    public boolean validateUsername(String userName){
        User user = userRepository.findUserBy(userName);
        return user != null;
    }

    private void validateUser(User user, String userId){
        if (user == null) {
            throw new AccountDoesNotExist("Account with this username does not exist!");
        }
    }

    private void validateLogin(User user){
        if (!user.isLoggedIn()) {
            throw new LoginError("Login to perform this action");
        }
    }
    private void validateAmount(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmount("Error!, amount must be greater than 0 \nPlease try again");
    }

    private void validateSufficientFund(BigDecimal balance,BigDecimal amount){
        if (balance.compareTo(amount)  < 0) throw new InsufficientFunds("Error! wallet balance is not sufficient to withdraw this amount");
    }

}
