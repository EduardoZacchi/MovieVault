package com.movievault.controller.request;

public record UserRequest(String name,
                          String email,
                          String password) {
}
