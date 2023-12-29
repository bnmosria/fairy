package com.bopera.pointofsales.exception;

import problem.member.Detail;
import problem.member.Status;
import problem.member.Title;
import org.springframework.http.HttpStatus;

@Status(HttpStatus.NOT_FOUND)
@Detail("Resource not found")
@Title("Not Found")
public class ResourceNotFoundException extends RuntimeException {}
