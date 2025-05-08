package com.cluz.stocktracker.controller.request;

public record UserRegisterRequest(String name, String email, String password) {
}