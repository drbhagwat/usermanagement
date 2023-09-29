package com.example.auditing.service;

import com.example.auditing.model.RefreshToken;
import com.example.auditing.repository.RefreshTokenRepository;
import com.example.auditing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;


  public RefreshToken createRefreshToken(String userName) {
    RefreshToken refreshToken =
        RefreshToken.builder().user(userRepository.findByUsername(userName).get())
            .token(UUID.randomUUID().toString())
            .expiryDate(Instant.now().plusMillis(10 * 60 * 1000)).build();
    return refreshTokenRepository.save(refreshToken);
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken verifyExpiration(RefreshToken refreshToken) {
    if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(refreshToken);
      throw new RuntimeException(refreshToken.getToken() + " Refresh Token " +
          "was expired! Please log in again");
    }
    return refreshToken;
  }
}
