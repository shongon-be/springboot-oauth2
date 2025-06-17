package com.shongon.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@Tag(name = "User", description = "Manage user's information.")
public class UserController {
    @Operation(summary = "Retrieve user's information.",
                description = "Return information of login user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!"),
        @ApiResponse(responseCode = "401", description = "Unauthenticated.")
    })
    @GetMapping("/user-info")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }
}
