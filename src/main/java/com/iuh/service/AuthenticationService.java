package com.iuh.service;

import com.iuh.dto.request.AuthenticationRequest;
import com.iuh.dto.request.IntrospectRequest;
import com.iuh.dto.request.LogoutRequest;
import com.iuh.dto.request.RefreshRequest;
import com.iuh.dto.response.AuthenticationResponse;
import com.iuh.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    public AuthenticationResponse authenticate(AuthenticationRequest request);

    public void logout(LogoutRequest request) throws ParseException, JOSEException;

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
