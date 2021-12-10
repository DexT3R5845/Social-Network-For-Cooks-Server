package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.PageDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.service.interfaces.FriendshipService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    public void sentInvite(@RequestParam(value = "friendId") long friendId, Principal principal) {
        friendshipService.createInvite(principal.getName(), friendId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "/new")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The invite has been sent"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public void unsentInvite(@RequestParam(value = "friendId") long friendId, Principal principal) {
        friendshipService.deleteFriendship(principal.getName(), friendId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "/invites")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The invite has been accepted"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public void acceptInvite(@RequestParam(value = "friendId") long friendId, Principal principal) {
        friendshipService.acceptInvite(principal.getName(), friendId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "/invites")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The invite has been declined"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public void declineInvite(@RequestParam(value = "friendId") long friendId, Principal principal) {
        friendshipService.deleteFriendship(principal.getName(), friendId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping()
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Friend has been removed"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public void removeFriend(@RequestParam(value = "friendId") long friendId, Principal principal) {
        friendshipService.deleteFriendship(principal.getName(), friendId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of friends received"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public PaginationDTO<AccountPersonalInfoDTO> getFriends(
            @RequestParam(value = "size") int size,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order,
            @RequestParam(value = "gender", defaultValue = "", required = false) String gender,
            Principal principal) {
        return friendshipService.getFriends(principal.getName(), search, gender, currentPage, size, order);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/new")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of viable friends received"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public PaginationDTO<AccountPersonalInfoDTO> getViableFriends(
            @RequestParam(value = "size") int size,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order,
            @RequestParam(value = "gender", defaultValue = "", required = false) String gender,
            Principal principal) {
        return friendshipService.getAllViableFriends(principal.getName(), search, gender, currentPage, size, order);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/invites")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of invites received"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public PaginationDTO<AccountPersonalInfoDTO> getInvites(
            @RequestParam(value = "size") int size,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order,
            @RequestParam(value = "gender", defaultValue = "", required = false) String gender,
            Principal principal) {
        return friendshipService.getInvites(principal.getName(), search, gender, currentPage, size, order);
    }
}

