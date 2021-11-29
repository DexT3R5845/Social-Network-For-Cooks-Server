package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.service.interfaces.FriendshipService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/friends")
public class FriendshipController {
    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/new")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The invite has been sent"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public  ResponseEntity<String> sentInvite(@RequestParam(value = "friendId") long friendId, Principal principal) {
       return  ResponseEntity.ok(friendshipService.createInvite(principal.getName(), friendId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "/new")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The invite has been sent"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public  ResponseEntity<String> unsentInvite(@RequestParam(value = "friendId") long friendId, Principal principal) {
        return  ResponseEntity.ok(friendshipService.deleteFriendship(principal.getName(), friendId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "/invite")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The invite has been accepted"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public  ResponseEntity<String> acceptInvite(@RequestParam(value = "friendId") long friendId, Principal principal) {
        return  ResponseEntity.ok(friendshipService.acceptInvite(principal.getName(), friendId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "/invite")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The invite has been declined"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public  ResponseEntity<String> declineInvite(@RequestParam(value = "friendId") long friendId, Principal principal) {
        return  ResponseEntity.ok(friendshipService.deleteFriendship(principal.getName(), friendId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping()
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Friend has been removed"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public  ResponseEntity<String> removeFriend(@RequestParam(value = "friendId") long friendId, Principal principal) {
        return  ResponseEntity.ok(friendshipService.deleteFriendship(principal.getName(), friendId));
    }
}

