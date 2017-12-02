package ru.dokwork.sugar.mockito

import org.mockito.Mockito._
import org.scalatest.FreeSpec

class MatchersExamples extends FreeSpec with Sugar {

  "Example of use" in new Matchers {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    list.take(5)
    // then:
    list shouldHave invocation(_.take(*[Int]), atLeast(1))
  }
}
