package pt.ulisboa.tecnico.milestone1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.milestone1.dto.UserReport;
import pt.ulisboa.tecnico.milestone1.dto.UserReportRequest;
import pt.ulisboa.tecnico.milestone1.service.UserService;

import java.util.List;

@RestController
public class UserLocationController {
    @Autowired
    private UserService userService;

    @GetMapping("/obtainLocationReport/{userId}/{epoch}/{callerUserId}/{isHa}")
    public UserReport obtainLocationReport(@PathVariable("userId") long userId,
                                           @PathVariable("epoch") long epoch,
                                           @PathVariable("callerUserId") long callerUserId,
                                           @PathVariable("isHa") boolean isHa) throws Exception {
        return userService.obtainLocationReport(userId, epoch, callerUserId, isHa);
    }

    @PostMapping("/submitLocationReport")
    public UserReport submitLocationReport(@RequestBody UserReportRequest userReportRequest) throws Exception {
        return userService.submitUserLocation(userReportRequest);
    }

    @GetMapping("/obtainUsersAtLocation/{pos}/{epoch}/{isHa}")
    public List<UserReport> obtainUsersAtLocation(@PathVariable("pos") String pos,
                                                  @PathVariable("epoch") long epoch,
                                                  @PathVariable("isHa") boolean isHa) throws Exception {
        return userService.obtainUsersAtLocation(epoch, pos, isHa);
    }
}
