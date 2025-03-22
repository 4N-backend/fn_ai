package com.fn.ai.product.common;

import com.fn.ai.product.config.TearDownExecutor;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@Import(TearDownExecutor.class)
@ActiveProfiles("test")
@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
public abstract class UnitTestSupport {

  @Autowired
  private TearDownExecutor tearDownExecutor;

  @AfterEach
  void tearDown() {
    tearDownExecutor.execute();
  }
}