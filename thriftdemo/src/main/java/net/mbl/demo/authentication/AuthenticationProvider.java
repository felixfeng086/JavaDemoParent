package net.mbl.demo.authentication;



import javax.security.sasl.AuthenticationException;

/**
 * Abstraction for an authentication provider at SASL server for
 * {@link AuthType#SIMPLE} and {@link AuthType#CUSTOM}.
 */
public interface AuthenticationProvider {

  /**
   * Factory for {@link AuthenticationProvider}.
   */
  class Factory {

    // prevent instantiation
    private Factory() {}

    /**
     * @param authType authentication type to use
     * @return the generated {@link AuthenticationProvider}
     * @throws AuthenticationException when unsupported authentication type is used
     */
    public static AuthenticationProvider create(AuthType authType)
        throws AuthenticationException {
      switch (authType) {
        case SIMPLE:
          return new SimpleAuthenticationProvider();
        case CUSTOM:
          String customProviderName ="";/*
              Configuration.get(PropertyKey.SECURITY_AUTHENTICATION_CUSTOM_PROVIDER_CLASS);*/
          return new CustomAuthenticationProvider(customProviderName);
        default:
          throw new AuthenticationException("Unsupported AuthType: " + authType.getAuthName());
      }
    }
  }

  /**
   * The authenticate method is called by the {@link PlainSaslServerCallbackHandler} in
   * the {@link PlainSaslServer} layer to authenticate users for their requests. If a user is to be
   * granted, return nothing/throw nothing. When a user is to be disallowed, throw an appropriate
   * {@link AuthenticationException}.
   *
   * @param user The username received over the connection request
   * @param password The password received over the connection request
   *
   * @throws AuthenticationException when a user is found to be invalid by the implementation
   */
  void authenticate(String user, String password) throws AuthenticationException;
}
