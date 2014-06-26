package com.example.auth.http;

import com.example.auth.core.TokenRepository;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static com.example.auth.http.ReplyMatchers.isBadRequest;
import static com.example.auth.http.ReplyMatchers.isOk;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ivan Stefanov <ivan.stefanov@clouway.com>
 */
public class VerificationEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private TokenRepository repository = context.mock(TokenRepository.class);

  private VerificationEndpoint endpoint = new VerificationEndpoint(repository);

  @Test
  public void existingToken() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).verify("b93568bbf73436511032843ec459f08b");
      will(returnValue(true));
    }});

    Reply<?> reply = endpoint.verify("b93568bbf73436511032843ec459f08b");

    assertThat(reply, isOk());
  }

  @Test
  public void notExistingToken() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).verify("2f5a7bba8d0c5e713f059eefd0de9662");
      will(returnValue(false));
    }});

    Reply<?> reply = endpoint.verify("2f5a7bba8d0c5e713f059eefd0de9662");

    assertThat(reply, isBadRequest());
  }
}