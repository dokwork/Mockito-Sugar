package ru.dokwork.sugar.mockito

import org.mockito.Mockito._
import org.scalatest.FreeSpec

class SugarExamples extends FreeSpec with Sugar {

  "Example for argument matchers" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    list.slice(0, 5)
    // then:
    verify(list).slice(*[Int], ==[Int](_ > 0))
    // or: verify(list).slice(any[Int], argThat[Int](_ > 0))
  }

  "Examples for argument captor" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    val captor = argumentCaptor[Int]
    // when:
    list.take(5)
    verify(list).take(captor.capture())
    // then:
    assert(captor.getValue == 5)
  }

  "Example for implicit answer" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    doAnswer((n: Int) ⇒ (1 to n).toList).when(list).take(*[Int])
    // then:
    assert(list.take(5) == (1 to 5).toList)
  }
}
