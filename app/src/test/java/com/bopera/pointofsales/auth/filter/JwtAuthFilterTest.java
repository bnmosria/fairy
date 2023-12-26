package com.bopera.pointofsales.auth.filter;

import com.bopera.pointofsales.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @Test
    public void shouldNotDoFilterInternalWhenAuthorizationHeaderIsNull() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUsername(anyString());
    }

    @Test
    public void shouldDoFilterInternalWhenAuthorizationHeaderDoesNotStartWithBearer() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Invalid header");

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUsername(anyString());
    }

    @Test
    public void shouldNotDoFilterInternalWhenJwtTokenIsInvalid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        when(jwtService.extractUsername("invalidToken")).thenReturn("username");
        when(userDetailsService.loadUserByUsername("username")).thenReturn(mock(UserDetails.class));
        when(jwtService.validateToken(eq("invalidToken"), any(UserDetails.class))).thenReturn(false);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(1)).extractUsername("invalidToken");
        verify(jwtService, times(1)).validateToken(eq("invalidToken"), any(UserDetails.class));
    }

    @Test
    public void shouldDoFilterInternalWhenJwtTokenIsValid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(jwtService.extractUsername("validToken")).thenReturn("username");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(jwtService.validateToken("validToken", userDetails)).thenReturn(true);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(1)).extractUsername("validToken");
        verify(jwtService, times(1)).validateToken("validToken", userDetails);
        verify(userDetailsService, times(1)).loadUserByUsername("username");
    }
}
