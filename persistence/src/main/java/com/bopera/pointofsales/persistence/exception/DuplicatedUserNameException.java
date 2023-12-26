package com.bopera.pointofsales.persistence.exception;

import org.springframework.http.HttpStatus;
import problem.member.Detail;
import problem.member.Status;
import problem.member.Title;

@Status(HttpStatus.CONFLICT)
@Detail("The given user name is already in use. Please select another one")
@Title("Duplicated user name")
public class DuplicatedUserNameException extends RuntimeException {}
