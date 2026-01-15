package xyz.pushpad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PushpadTest {
  @Test
  void signatureForGeneratesCorrectSignature() {
    Pushpad pushpad = new Pushpad("5374d7dfeffa2eb49965624ba7596a09", 123L);
    String signature = pushpad.signatureFor("user12345");

    assertEquals("6627820dab00a1971f2a6d3ff16a5ad8ba4048a02b2d402820afc61aefd0b69f",
        signature);
  }
}
