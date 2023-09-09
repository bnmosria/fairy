package com.bopera.pointofsales.exception;

import problem.member.Status;
import problem.member.Detail;
import problem.member.Title;
import org.springframework.http.HttpStatus;

@Status(HttpStatus.CONFLICT)
@Detail("The given user name is already in use. Please select another one")
@Title("Duplicated user name")
public class DuplicatedUserNameException extends RuntimeException {}
