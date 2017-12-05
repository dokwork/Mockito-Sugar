package ru.dokwork.sugar.mockito

import org.mockito.Mockito._
import org.scalatest.AsyncFreeSpec

import scala.concurrent.Future

class MockitoSugarExamples extends AsyncFreeSpec with MockitoSugar {

  "Example with shouldHave matcher" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    list.take(5)
    // then:
    list shouldHave invocation(_.take(any[Int]), atLeast(1))
  }

  "Example for argument matchers" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    list.slice(0, 5)
    // then:
    list shouldHave invocation(_.slice(any[Int], argThat[Int](_ > 0)))
  }

  "Example for argument captor" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    val captor = argumentCaptor[Int]
    // when:
    list.take(5)
    verify(list).take(captor)
    // then:
    assert(captor.value == 5)
  }

  "Example for implicit answer" in {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    doAnswer((n: Int) ⇒ (1 to n).toList).when(list).take(any[Int])
    // then:
    assert(list.take(5) == (1 to 5).toList)
  }

  "Example for answer with Future" - {
    trait Example {
      def doSomething: Future[String]
    }
    "with 'when' syntax" in {
      // given:
      val example = mock[Example]
      when(example.doSomething).thenReturnAsFuture("Hello world!")
      // when:
      val result = example.doSomething
      // then:
      assert(result.isInstanceOf[Future[String]])
      result.map(s ⇒ assert(s == "Hello world!"))
    }
    "with 'do' syntax" in {
      // given:
      val example = mock[Example]
      doReturnAsFuture("Hello world!").when(example).doSomething
      // when:
      val result = example.doSomething
      // then:
      assert(result.isInstanceOf[Future[String]])
      result.map(s ⇒ assert(s == "Hello world!"))
    }
  }
}
