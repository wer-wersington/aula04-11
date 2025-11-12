package br.com.senacsp.tads.stads4ma.library.presentation;


import br.com.senacsp.tads.stads4ma.library.domainmodel.springSecurity.JwtHelper;
import br.com.senacsp.tads.stads4ma.library.presentation.dto.AuthRequest;
import br.com.senacsp.tads.stads4ma.library.presentation.dto.AuthResponse;
import br.com.senacsp.tads.stads4ma.library.presentation.dto.TokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AutoController {
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;


    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String acessToken = this.jwtHelper.generateToken(userDetails);
        final String refreshToken = this.jwtHelper.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(acessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRefresh tokenRefresh){
        final String token = tokenRefreshRequest.refreshToken();
        String username = this.jwtHelper.extractUsername();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);


        if(this.jwtHelper.isTokenValid()){
            final String newAccessToken = this.jwtHelper.generateToken(userDetails);
            return new ResponseEntity<>.ok(new AuthRequest(newAccessToken, token));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh Token invalido");
        }
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username())
        );


        final String acessToken = this.jwtHelper.generateToken(userDetails);
        final String refreshToken = this.jwtHelper.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(acessToken, refreshToken));
    }



}
