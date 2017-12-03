package ru.dokwork.sugar.mockito

import org.mockito.Mockito._
import org.scalatest.FreeSpec
import org.scalatest.prop.PropertyChecks

class MockitoSugarExamples extends FreeSpec with MockitoSugar {

  "Example for argument matchers" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    list.slice(0, 5)
    // then:
    verify(list).slice(*[Int], ==[Int](_ > 0))
    // or: verify(list).slice(any[Int], argThat[Int](_ > 0))
  }

  "Example for argument captor" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    val captor = argumentCaptor[Int]
    // when:
    list.take(5)
    verify(list).take(captor)
    // then:
    assert(captor.last == 5)
  }

  "Example for implicit answer" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    doAnswer((n: Int) ⇒ (1 to n).toList).when(list).take(*[Int])
    // then:
    assert(list.take(5) == (1 to 5).toList)
  }

  "Example with shouldHave matcher" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    list.take(5)
    // then:
    list shouldHave invocation(_.take(*[Int]), atLeast(1))
  }

  "Property-based test with shouldHave matcher" in new PropertyChecks {
    forAll { (i: Int) ⇒
      // given:
      val list: List[Int] = mock[List[Int]]
      // when:
      list.take(i)
      // then:
      list shouldHave invocation(_.take(i))
    }
  }
}
